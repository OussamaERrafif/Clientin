#!/usr/bin/env python3
import os
import re
import sys
import argparse
import glob
import logging 


class JavaBeanProcessor:
    def __init__(self, bean_file_path):
        self.bean_file_path = bean_file_path
        self.class_name = ""
        self.package_name = ""
        self.attributes = []
        self.imports = []
        self.id_attribute = {"name": "id", "type": "Long", "found": False}
        self.read_bean_file()
        self.inner_enums = []
        self.imported_types = {}
        self.jdk_types = {
            'String', 'Integer', 'int', 'Long', 'long', 'Double', 'double',
            'Boolean', 'boolean', 'BigDecimal', 'LocalDate', 'LocalDateTime'
        }
    
    def read_bean_file(self):
        with open(self.bean_file_path, 'r') as file:
            content = file.read()

            # Remove comments to avoid false matches
            content = re.sub(r'//.*?\n', '\n', content)  # Line comments
            content = re.sub(r'/\*.*?\*/', '', content, flags=re.DOTALL)  # Block comments

            # Extract package
            package_match = re.search(r'package\s+([\w.]+);', content)
            if package_match:
                self.package_name = package_match.group(1)

            # Extract imports
            self.imports = re.findall(r'import\s+([\w.]+);', content)
            self.imported_types = {
                imp.split('.')[-1]: imp 
                for imp in self.imports
            }

            # Extract class name
            class_match = re.search(r'public\s+class\s+(\w+)', content)
            if class_match:
                self.class_name = class_match.group(1)

            # Extract enum types defined in the class
            self.inner_enums = re.findall(r'enum\s+(\w+)\s*{', content)

            # Extract fields with annotations
            field_pattern = r'''
                (?P<annotations>(?:@[\w\.]+\s*(?:\([^)]*\))?\s*)*)  # Annotation block
                \s*private\s+(?P<type>[\w<>.,\s\[\]]+)\s+          # Field type
                (?P<name>\w+)\s*                                    # Field name
                (?:=\s*[^;]+)?\s*;                                  # Optional initialization
            '''
            field_matches = re.finditer(field_pattern, content, re.VERBOSE)

            for match in field_matches:
                annotations_block = match.group('annotations').strip()
                field_type = match.group('type').strip()
                field_name = match.group('name').strip()

                # Parse individual annotations
                annotations = []
                if annotations_block:
                    # Handle both simple and parameterized annotations
                    annotations = [
                        anno.split('(')[0]  # Remove parameters
                        for anno in re.findall(r'@([\w\.]+(?:\([^)]*\))?)', annotations_block)
                    ]

                # Clean complex types (generics, arrays)
                base_type = re.sub(r'<.*?>', '', field_type)  # Remove generics
                base_type = re.sub(r'\[\]', '', base_type)    # Remove array brackets
                base_type = base_type.split('.')[-1].strip()   # Get simple name

                self.attributes.append({
                    'type': field_type,
                    'base_type': base_type,
                    'name': field_name,
                    'annotations': annotations
                })

            # Detect ID field (prioritize @Id annotated fields)
            self.id_attribute = self._detect_id_attribute()
            
    
    def _detect_enum_types(self):
        enum_types = set(self.inner_enums)
        
        # Check imported types that look like enums
        for type_name, full_import in self.imported_types.items():
            if re.search(r'(enum|type|kind)$', type_name, re.IGNORECASE):
                enum_types.add(type_name)
        
        # Add field types that aren't JDK types
        for attr in self.attributes:
            attr_type = attr['type'].split('<')[0].split('.')[-1]
            if (attr_type not in self.jdk_types 
                and not attr_type[0].islower()
                and attr_type not in enum_types):
                enum_types.add(attr_type)
        
        return enum_types
    
    def _is_enum(self, attr):
        base_type = attr['type'].split('<')[0].split('.')[-1]
        return base_type in self._detect_enum_types()
    


    def generate_attribute_setup_code(self, attr):
        setup_code = []
        attr_name = attr['name']
        attr_type = attr['type']
        capitalized_name = attr_name[0].upper() + attr_name[1:]
        capitalized = attr_name[0].upper() + attr_name[1:]
        base_type = attr_type.split('<')[0].split('.')[-1]



        if self._is_enum(attr):
            setup_code.extend([
                f'        dto.set{capitalized}({base_type}.values()[0]);',
                f'        entity.set{capitalized}({base_type}.values()[0]);'
            ])
        
        # String types with realistic test data
        elif attr_type == "String":
            test_value = f'"Test {attr_name}"'
            if 'name' in attr_name.lower():
                test_value = f'"{attr_name.capitalize()} User"'
            elif 'email' in attr_name.lower():
                test_value = f'"test.{attr_name}@example.com"'
            setup_code.extend([
                f'        dto.set{capitalized_name}({test_value});',
                f'        entity.set{capitalized_name}({test_value});'
            ])
        
        # Numeric types
        elif attr_type in ["int", "Integer"]:
            setup_code.extend([
                f'        dto.set{capitalized_name}(42);',
                f'        entity.set{capitalized_name}(42);'
            ])
        elif attr_type in ["long", "Long"]:
            setup_code.extend([
                f'        dto.set{capitalized_name}(42L);',
                f'        entity.set{capitalized_name}(42L);'
            ])
        elif attr_type in ["double", "Double"]:
            setup_code.extend([
                f'        dto.set{capitalized_name}(3.1415);',
                f'        entity.set{capitalized_name}(3.1415);'
            ])
        
        # Temporal types
        elif attr_type == "LocalDate":
            setup_code.extend([
                f'        dto.set{capitalized_name}(LocalDate.now().minusDays(1));',
                f'        entity.set{capitalized_name}(LocalDate.now().minusDays(1));'
            ])
        elif attr_type == "LocalDateTime":
            setup_code.extend([
                f'        dto.set{capitalized_name}(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));',
                f'        entity.set{capitalized_name}(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));'
            ])
        
        # Specialized types
        elif attr_type == "BigDecimal":
            setup_code.extend([
                f'        dto.set{capitalized_name}(new BigDecimal("1234.56"));',
                f'        entity.set{capitalized_name}(new BigDecimal("1234.56"));'
            ])
        elif attr_type == "boolean" or attr_type == "Boolean":
            setup_code.extend([
                f'        dto.set{capitalized_name}(true);',
                f'        entity.set{capitalized_name}(true);'
            ])
        
        # Collection types
        elif attr_type.startswith("List<"):
            collection_type = "ArrayList" if "List" in attr_type else "LinkedList"
            setup_code.extend([
                f'        dto.set{capitalized_name}(new {collection_type}<>());',
                f'        entity.set{capitalized_name}(new {collection_type}<>());'
            ])
        elif attr_type.startswith("Set<"):
            setup_code.extend([
                f'        dto.set{capitalized_name}(new HashSet<>());',
                f'        entity.set{capitalized_name}(new HashSet<>());'
            ])

        
        # Enum types
        elif attr_type in self._detect_enum_types(): 
            setup_code.extend([
                f'        dto.set{capitalized_name}({attr_type}.VALUES[0]);',
                f'        entity.set{capitalized_name}({attr_type}.VALUES[0]);'
            ])
        
        
        
        # Fallback for unknown types
        else:
            setup_code.extend([
                f'        // TODO: Handle {attr_type} type for {attr_name}',
                f'        // dto.set{capitalized_name}(/* unknown type */);',
                f'        // entity.set{capitalized_name}(/* unknown type */);'
            ])

        return setup_code

    def _is_relationship(self, attr):
        return any(anno in ['OneToOne', 'OneToMany', 'ManyToOne', 'ManyToMany']
                   for anno in attr.get('annotations', []))
    
    

    def generate_repository(self):
        # Package structure calculation
        base_package = ".".join(self.package_name.split(".")[:-1])
        repository_package = f"{base_package}.repository"
        
        # Improved ID detection with annotation support
        id_attribute = self._detect_id_attribute()
        
        # Find first non-ID attribute for query examples
        query_attribute = next((attr for attr in self.attributes 
                            if not self._is_id_attribute(attr)), None)
        
        # Template construction with relationship support
        content = f'''package {repository_package};

    import {self.package_name}.{self.class_name};
    import org.springframework.data.jpa.repository.JpaRepository;
    import org.springframework.data.jpa.repository.Query;
    import org.springframework.stereotype.Repository;
    {self._generate_relationship_imports()}

    @Repository
    public interface {self.class_name}Repository 
        extends JpaRepository<{self.class_name}, {id_attribute['type']}> {{

        // Automatic query methods
    {self._generate_derived_queries()}
        
        // Custom JPQL queries
    {self._generate_jpql_queries(query_attribute)}
        
        // Relationship queries
    {self._generate_relationship_queries()}
    }}'''
        
        return {
            "path": f"{self.class_name}Repository.java",
            "content": content,
            "package": repository_package
        }

    # Helper methods used in the above implementation
    def _detect_id_attribute(self):
        # Check for JPA @Id first
        id_fields = [
            attr for attr in self.attributes 
            if 'Id' in attr['annotations']
        ]
        
        if len(id_fields) > 1:
            raise ValueError(f"Multiple @Id fields in {self.class_name}")
        if id_fields:
            return id_fields[0]
        
        # Fallback to conventional names
        conventional_ids = [
            attr for attr in self.attributes
            if re.match(r'^(id|.+Id)$', attr['name'], re.IGNORECASE)
        ]
        
        if conventional_ids:
            return conventional_ids[0]
        
        # Explicit error instead of defaulting
        raise ValueError(f"No ID field detected in {self.class_name}")
    

    def _is_id_attribute(self, attr):
        return attr.get('name') == self.id_attribute.get('name')

    def _generate_relationship_imports(self):
        return ''.join(f'\nimport {attr["type"]};' 
                    for attr in self.attributes if self._is_relationship(attr))

    def _is_relationship(self, attr):
        return any(anno in ['OneToMany', 'ManyToOne', 'ManyToMany', 'OneToOne']
                for anno in attr.get('annotations', []))

    def _generate_derived_queries(self):
        queries = []
        for attr in self.attributes:
            if not self._is_id_attribute(attr):
                queries.append(
                    f'    List<{self.class_name}> findBy{attr["name"].capitalize()}({attr["type"]} {attr["name"]});'
                )
        return '\n'.join(queries[:3])  # Limit to 3 examples

    def _generate_jpql_queries(self, query_attribute):
        if not query_attribute:
            return ''
        
        return f'''    @Query("SELECT e FROM {self.class_name} e WHERE e.{query_attribute['name']} = :value")
        List<{self.class_name}> findBy{query_attribute['name'].capitalize()}(String value);

        @Query("SELECT e FROM {self.class_name} e JOIN FETCH e.{query_attribute['name']}")
        List<{self.class_name}> findAllWith{query_attribute['name'].capitalize()}();'''

    def _generate_relationship_queries(self):
        queries = []
        for attr in self.attributes:
            if self._is_relationship(attr):
                rel_class = attr['type'].split('.')[-1]
                queries.append(
                    f'    @Query("SELECT e FROM {self.class_name} e WHERE e.{attr["name"]}.id = :{rel_class.lower()}Id")'
                    f'\n    List<{self.class_name}> findBy{rel_class}Id(Long {rel_class.lower()}Id);'
                )
        return '\n'.join(queries)
    

    def generate_dto(self):
        base_package = ".".join(self.package_name.split(".")[:-1])
        dto_package = f"{base_package}.dto"
        
        fields = []

        for attr in self.attributes:
            field_lines = []
            dto_type = attr['type']
            
            # Simple field declaration for now
            field_lines.append(f"    private {dto_type} {attr['name']};")
            fields.append('\n'.join(field_lines))

        content = f"""package {dto_package};

import lombok.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class {self.class_name}DTO implements Serializable {{

    private static final long serialVersionUID = 1L;

{chr(10).join(fields)}
}}"""

        return {
            "path": f"{self.class_name}DTO.java",
            "content": content,
            "package": dto_package
        }

    # Helper methods
    def _is_relationship(self, attr):
        return any(anno in ['OneToOne', 'OneToMany', 'ManyToOne', 'ManyToMany'] 
                for anno in attr.get('annotations', []))

    def _is_enum(self, attr):
        return 'Enum' in attr.get('type', '') or 'enum' in attr.get('comments', '')



    def generate_mapper(self):
        base_package = ".".join(self.package_name.split(".")[:-1])
        mapper_package = f"{base_package}.mapper"
        
        to_entity_mappings = []
        to_dto_mappings = []
        custom_imports = set()
        partial_update_mappings = []

        for attr in self.attributes:
            attr_name = attr['name']
            capitalized = attr_name[0].upper() + attr_name[1:]
            attr_type = attr['type']

            # Simple field mapping for now
            to_entity = f"        entity.set{capitalized}(dto.get{capitalized}());"
            to_dto = f"        dto.set{capitalized}(entity.get{capitalized}());"

            to_entity_mappings.append(to_entity)
            to_dto_mappings.append(to_dto)
            
            # Partial update logic
            partial_update_mappings.append(f"""
        if (dto.get{capitalized}() != null) {{
            entity.set{capitalized}(dto.get{capitalized}());
        }}""")

        content = f"""package {mapper_package};

import {self.package_name}.{self.class_name};
import {base_package}.dto.{self.class_name}DTO;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class {self.class_name}Mapper {{

    public {self.class_name} toEntity({self.class_name}DTO dto) {{
        if (dto == null) return null;
        
        {self.class_name} entity = new {self.class_name}();
{chr(10).join(to_entity_mappings)}
        return entity;
    }}

    public {self.class_name}DTO toDTO({self.class_name} entity) {{
        if (entity == null) return null;
        
        {self.class_name}DTO dto = new {self.class_name}DTO();
{chr(10).join(to_dto_mappings)}
        return dto;
    }}

    public List<{self.class_name}DTO> toDTOList(List<{self.class_name}> entities) {{
        return entities.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }}

    public List<{self.class_name}> toEntityList(List<{self.class_name}DTO> dtos) {{
        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }}

    public void partialUpdate({self.class_name}DTO dto, {self.class_name} entity) {{
        if (dto == null || entity == null) return;
{''.join(partial_update_mappings)}
    }}
}}"""

        return {
            "path": f"{self.class_name}Mapper.java",
            "content": content,
            "package": mapper_package
        }

    # Helper methods
    def _is_date_type(self, attr):
        return any(t in attr['type'] for t in ['LocalDate', 'LocalDateTime', 'ZonedDateTime'])

    def _get_date_formatter(self, attr_type):
        return {
            'LocalDate': 'ISO_LOCAL_DATE',
            'LocalDateTime': 'ISO_DATE_TIME',
            'ZonedDateTime': 'ISO_ZONED_DATE_TIME'
        }.get(attr_type.split('.')[-1], 'ISO_DATE_TIME')

    def _is_relationship(self, attr):
        return any(anno in attr.get('annotations', []) 
                for anno in ['OneToMany', 'ManyToOne', 'ManyToMany', 'OneToOne'])

    def _extract_relationship_class(self, type_str):
        return type_str.split('.')[-1].replace('>', '').split('<')[-1]

    def _is_collection(self, attr):
        return attr['type'].startswith(('List<', 'Set<'))

    def _get_collection_element_type(self, type_str):
        match = re.search(r'<(.*?)>', type_str)
        return match.group(1) if match else None


    def generate_service(self):
        base_package = ".".join(self.package_name.split(".")[:-1])
        service_package = f"{base_package}.service"
        id_type = self.id_attribute["type"]
        entity_var = self.class_name[0].lower() + self.class_name[1:]

        # Service interface with pagination and filtering
        interface_content = f"""package {service_package};

import {base_package}.dto.{self.class_name}DTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

public interface {self.class_name}Service {{
    
    // Basic CRUD operations
    {self.class_name}DTO create({self.class_name}DTO dto);
    Optional<{self.class_name}DTO> findById({id_type} id);
    Page<{self.class_name}DTO> findAll(Pageable pageable);
    List<{self.class_name}DTO> findAll();
    {self.class_name}DTO update({id_type} id, {self.class_name}DTO dto);
    void delete({id_type} id);
    
    // Advanced operations
    Page<{self.class_name}DTO> search(Specification<{self.class_name}> spec, Pageable pageable);
    {self.class_name}DTO partialUpdate({id_type} id, {self.class_name}DTO dto);
    boolean exists({id_type} id);
    
    // Bulk operations
    List<{self.class_name}DTO> bulkCreate(List<{self.class_name}DTO> dtos);
    void bulkDelete(List<{id_type}> ids);
}}"""

        # Implementation with validation and error handling
        impl_content = f"""package {service_package}.impl;

import {self.package_name}.{self.class_name};
import {base_package}.dto.{self.class_name}DTO;
import {base_package}.mapper.{self.class_name}Mapper;
import {base_package}.repository.{self.class_name}Repository;
import {service_package}.{self.class_name}Service;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class {self.class_name}ServiceImpl implements {self.class_name}Service {{

    private final {self.class_name}Repository {entity_var}Repository;
    private final {self.class_name}Mapper {entity_var}Mapper;

    @Override
    @Transactional
    public {self.class_name}DTO create({self.class_name}DTO dto) {{
        log.debug("Creating new {self.class_name}");
        try {{
            {self.class_name} entity = {entity_var}Mapper.toEntity(dto);
            return {entity_var}Mapper.toDTO({entity_var}Repository.save(entity));
        }} catch (Exception e) {{
            throw new RuntimeException("Error creating entity", e);
        }}
    }}

    @Override
    @Transactional(readOnly = true)
    public Optional<{self.class_name}DTO> findById({id_type} id) {{
        log.debug("Fetching {self.class_name} with ID: {{}}", id);
        return {entity_var}Repository.findById(id)
                .map({entity_var}Mapper::toDTO);
    }}

    @Override
    @Transactional(readOnly = true)
    public Page<{self.class_name}DTO> findAll(Pageable pageable) {{
        log.debug("Fetching paged {self.class_name} results");
        return {entity_var}Repository.findAll(pageable)
                .map({entity_var}Mapper::toDTO);
    }}

    @Override
    @Transactional(readOnly = true)
    public List<{self.class_name}DTO> findAll() {{
        log.debug("Fetching all {self.class_name} entities");
        return {entity_var}Mapper.toDTOList({entity_var}Repository.findAll());
    }}

    @Override
    @Transactional
    public {self.class_name}DTO update({id_type} id, {self.class_name}DTO dto) {{
        log.debug("Updating {self.class_name} with ID: {{}}", id);
        return {entity_var}Repository.findById(id)
                .map(existingEntity -> {{
                    {entity_var}Mapper.partialUpdate(dto, existingEntity);
                    return {entity_var}Mapper.toDTO({entity_var}Repository.save(existingEntity));
                }})
                .orElseThrow(() -> new EntityNotFoundException(
                    "{self.class_name} not found with id: " + id
                ));
    }}

    @Override
    @Transactional
    public void delete({id_type} id) {{
        log.debug("Deleting {self.class_name} with ID: {{}}", id);
        if (!{entity_var}Repository.existsById(id)) {{
            throw new EntityNotFoundException(
                "{self.class_name} not found with id: " + id
            );
        }}
        {entity_var}Repository.deleteById(id);
    }}

    @Override
    @Transactional(readOnly = true)
    public Page<{self.class_name}DTO> search(Specification<{self.class_name}> spec, Pageable pageable) {{
        log.debug("Searching {self.class_name} with specification");
        return {entity_var}Repository.findAll(spec, pageable)
                .map({entity_var}Mapper::toDTO);
    }}

    @Override
    @Transactional
    public {self.class_name}DTO partialUpdate({id_type} id, {self.class_name}DTO dto) {{
        log.debug("Partial update for {self.class_name} ID: {{}}", id);
        return {entity_var}Repository.findById(id)
                .map(entity -> {{
                    {entity_var}Mapper.partialUpdate(dto, entity);
                    return {entity_var}Mapper.toDTO({entity_var}Repository.save(entity));
                }})
                .orElseThrow(() -> new EntityNotFoundException(
                    "{self.class_name} not found with id: " + id
                ));
    }}

    @Override
    @Transactional(readOnly = true)
    public boolean exists({id_type} id) {{
        return {entity_var}Repository.existsById(id);
    }}

    @Override
    @Transactional
    public List<{self.class_name}DTO> bulkCreate(List<{self.class_name}DTO> dtos) {{
        log.debug("Bulk creating {self.class_name} entities: {{}} items", dtos.size());
        List<{self.class_name}> entities = {entity_var}Mapper.toEntityList(dtos);
        return {entity_var}Mapper.toDTOList({entity_var}Repository.saveAll(entities));
    }}

    @Override
    @Transactional
    public void bulkDelete(List<{id_type}> ids) {{
        log.debug("Bulk deleting {self.class_name} entities: {{}} items", ids.size());
        {entity_var}Repository.deleteAllById(ids);
    }}
}}"""

        return [
            {
                "path": f"{self.class_name}Service.java",
                "content": interface_content,
                "package": service_package
            },
            {
                "path": f"{self.class_name}ServiceImpl.java",
                "content": impl_content,
                "package": f"{service_package}.impl"
            }
        ]
    
    def generate_controller(self):
        base_package = ".".join(self.package_name.split(".")[:-1])
        controller_package = f"{base_package}.controller"
        id_type = self.id_attribute["type"]
        entity_var = self.class_name[0].lower() + self.class_name[1:]
        dto_type = f"{self.class_name}DTO"

        content = f"""package {controller_package};

import {base_package}.dto.{dto_type};
import {base_package}.service.{self.class_name}Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/{entity_var}s")
@RequiredArgsConstructor
@Slf4j
public class {self.class_name}Controller {{

    private final {self.class_name}Service {entity_var}Service;

    @GetMapping
    public Page<{dto_type}> getAll(Pageable pageable) {{
        log.info("Fetching {self.class_name} with pageable: {{}}", pageable);
        return {entity_var}Service.findAll(pageable);
    }}

    @GetMapping("/{{id}}")
    public ResponseEntity<{dto_type}> getById(@PathVariable {id_type} id) {{
        return {entity_var}Service.findById(id)
                .map(dto -> ResponseEntity.ok(dto))
                .orElse(ResponseEntity.notFound().build());
    }}

    @PostMapping
    public ResponseEntity<{dto_type}> create(@Valid @RequestBody {dto_type} dto) {{
        {dto_type} created = {entity_var}Service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }}

    @PutMapping("/{{id}}")
    public ResponseEntity<{dto_type}> update(
            @PathVariable {id_type} id,
            @Valid @RequestBody {dto_type} dto) {{
        try {{
            {dto_type} updated = {entity_var}Service.update(id, dto);
            return ResponseEntity.ok(updated);
        }} catch (Exception e) {{
            return ResponseEntity.notFound().build();
        }}
    }}

    @PatchMapping("/{{id}}")
    public ResponseEntity<{dto_type}> partialUpdate(
            @PathVariable {id_type} id,
            @Valid @RequestBody {dto_type} dto) {{
        try {{
            {dto_type} updated = {entity_var}Service.partialUpdate(id, dto);
            return ResponseEntity.ok(updated);
        }} catch (Exception e) {{
            return ResponseEntity.notFound().build();
        }}
    }}

    @DeleteMapping("/{{id}}")
    public ResponseEntity<Void> delete(@PathVariable {id_type} id) {{
        try {{
            {entity_var}Service.delete(id);
            return ResponseEntity.noContent().build();
        }} catch (Exception e) {{
            return ResponseEntity.notFound().build();
        }}
    }}

    @GetMapping("/exists/{{id}}")
    public ResponseEntity<Boolean> exists(@PathVariable {id_type} id) {{
        boolean exists = {entity_var}Service.exists(id);
        return ResponseEntity.ok(exists);
    }}
}}"""

        return {
            "path": f"{self.class_name}Controller.java",
            "content": content,
            "package": controller_package
        }

    # Helper methods
    def _generate_relationship_endpoints(self):
        endpoints = []
        for attr in self.attributes:
            if self._is_relationship(attr):
                rel_class = self._extract_relationship_class(attr['type'])
                rel_name = attr['name']
                endpoints.append(f"""
        @GetMapping("/{{id}}/{rel_name}")
        @PreAuthorize("hasAuthority('SCOPE_{self.class_name.upper()}_READ')")
        public CollectionModel<EntityModel<{rel_class}DTO>> get{rel_name.capitalize()}(
            @PathVariable {self.id_attribute['type']} id) {{
            return assembler.toCollectionModel(
                {self.class_name[0].lower()}{self.class_name[1:]}Service.get{rel_name.capitalize()}(id)
            );
        }}""")
        return '\n'.join(endpoints)


    def generate_repository_test(self):
        base_package = ".".join(self.package_name.split(".")[:-1])
        test_package = f"{base_package}.repository"
        id_type = self.id_attribute["type"]
        entity_var = self.class_name[0].lower() + self.class_name[1:]
        has_relationships = any(self._is_relationship(attr) for attr in self.attributes)

        # Generate test data initialization with relationships
        setup_code = []
        teardown_code = []
        for attr in self.attributes:
            if attr['name'] == self.id_attribute["name"]:
                continue
                
            if self._is_relationship(attr):
                related_class = self._extract_relationship_class(attr['type'])
                setup_code.append(
                    f"        {related_class} {attr['name']} = {related_class}TestUtils.createTest{related_class}();\n"
                    f"        em.persist({attr['name']});\n"
                    f"        testEntity.set{attr['name'].capitalize()}({attr['name']});"
                )
                teardown_code.append(
                    f"        {related_class}TestUtils.cleanup{related_class}(em);"
                )
            else:
                setup_code.extend(self.generate_test_data(attr))

        # Find fields with unique constraints
        unique_fields = [attr for attr in self.attributes 
                        if any('unique' in a or 'UniqueConstraint' in a 
                            for a in attr.get('annotations', []))]

        content = f"""package {test_package};

    import {self.package_name}.{self.class_name};
    import org.junit.jupiter.api.*;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
    import org.springframework.dao.DataIntegrityViolationException;
    import org.springframework.data.domain.*;
    import org.springframework.test.context.jdbc.Sql;
    import javax.persistence.EntityManager;
    import java.util.List;
    import java.util.Optional;

    import static org.assertj.core.api.Assertions.*;

    @DataJpaTest
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    class {self.class_name}RepositoryTest {{

        @Autowired
        private {self.class_name}Repository repository;
        
        @Autowired
        private EntityManager em;

        private {self.class_name} testEntity;

        @BeforeAll
        void setupRelationships() {{
            // Pre-create required relationship entities
            {self._generate_relationship_setup()}
        }}

        @BeforeEach
        void setUp() {{
            testEntity = new {self.class_name}();
    {'\\n'.join(setup_code)}
            repository.saveAndFlush(testEntity);
            em.clear();
        }}

        @AfterEach
        void tearDown() {{
    {'\\n'.join(teardown_code)}
        }}

        @Test
        @Transactional
        void shouldPersistAndRetrieveEntity() {{
            List<{self.class_name}> all = repository.findAll();
            assertThat(all)
                .hasSize(1)
                .first()
                .usingRecursiveComparison()
                .ignoringFields("{self.id_attribute['name']}", "version")
                .isEqualTo(testEntity);
        }}

        @Test
        void shouldFindByAllFields() {{
    {self._generate_field_query_tests()}
        }}

        @Test
        void shouldReturnEmptyForInvalidId() {{
            Optional<{self.class_name}> found = repository.findById({self.get_invalid_id()});
            assertThat(found).isEmpty();
        }}

        @Test
        void shouldEnforceBusinessConstraints() {{
            // Test null constraints
            {self._generate_null_constraint_tests()}

            // Test unique constraints
            {self._generate_unique_constraint_tests(unique_fields)}
        }}

        @Test
        void shouldSupportPaginationAndSorting() {{
            repository.save(createTestEntity());
            
            Page<{self.class_name}> page = repository.findAll(
                PageRequest.of(0, 1, Sort.by(Sort.Direction.DESC, "{self.id_attribute['name']}"))
            );
            
            assertThat(page.getContent())
                .hasSize(1)
                .extracting("{self.id_attribute['name']}")
                .containsExactly(repository.count());
        }}

        @Test
        @Sql(scripts = "/data/insert_test_{entity_var}s.sql")
        void shouldExecuteCustomQueries() {{
            {self._generate_custom_query_tests()}
        }}

        {self._generate_relationship_tests() if has_relationships else ''}

        private {self.class_name} createTestEntity() {{
            {self.class_name} entity = new {self.class_name}();
    {'\\n'.join([f"        entity.set{a['name'].capitalize()}(testEntity.get{a['name'].capitalize()}());" 
                for a in self.attributes if a['name'] != self.id_attribute["name"]])}
            return entity;
        }}
    }}"""

        return {
            "path": f"{self.class_name}RepositoryTest.java",
            "content": content,
            "package": test_package,
            "test": True
        }

    # Helper methods
    def _generate_relationship_setup(self):
        code = []
        for attr in self.attributes:
            if self._is_relationship(attr):
                related_class = self._extract_relationship_class(attr['type'])
                code.append(f"{related_class} {attr['name']} = new {related_class}();")
                code.append(f"em.persist({attr['name']});")
        return '\n        '.join(code)

    def _generate_field_query_tests(self):
        tests = []
        for attr in self.attributes:
            if attr['name'] != self.id_attribute["name"]:
                tests.append(f"""
            // Test {attr['name']} field
            List<{self.class_name}> by{attr['name'].capitalize()} = repository.findBy{attr['name'].capitalize()}(testEntity.get{attr['name'].capitalize()}());
            assertThat(by{attr['name'].capitalize()})
                .hasSize(1)
                .extracting("{attr['name']}")
                .containsExactly(testEntity.get{attr['name'].capitalize()}());""")
        return '\n'.join(tests)

    def _generate_null_constraint_tests(self):
        tests = []
        for attr in self.attributes:
            if 'Nullable' not in attr.get('annotations', []):
                tests.append(f"""
            {self.class_name} invalidEntity = createTestEntity();
            invalidEntity.set{attr['name'].capitalize()}(null);
            assertThatThrownBy(() -> repository.save(invalidEntity))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null");""")
        return '\n'.join(tests)

    def _generate_unique_constraint_tests(self, unique_fields):
        tests = []
        for attr in unique_fields:
            tests.append(f"""
            {self.class_name} duplicate = createTestEntity();
            duplicate.set{attr['name'].capitalize()}(testEntity.get{attr['name'].capitalize()}());
            assertThatThrownBy(() -> repository.save(duplicate))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("unique");""")
        return '\n'.join(tests)

    def _generate_relationship_tests(self):
        tests = []
        for attr in self.attributes:
            if self._is_relationship(attr):
                related_class = self._extract_relationship_class(attr['type'])
                tests.append(f"""
        @Test
        void shouldManage{related_class}Relationship() {{
            {related_class} newRel = {related_class}TestUtils.createTest{related_class}();
            em.persist(newRel);
            
            testEntity.set{attr['name'].capitalize()}(newRel);
            repository.saveAndFlush(testEntity);
            
            {self.class_name} updated = repository.findById(testEntity.getId()).orElseThrow();
            assertThat(updated.get{attr['name'].capitalize()}()).isEqualTo(newRel);
        }}""")
        return '\n'.join(tests)

    def generate_test_data(self, attr):
        setup = []
        attr_name = attr['name']
        attr_type = attr['type']
        base_package = ".".join(self.package_name.split(".")[:-1])
        
        # Helper to capitalize properly
        capitalized = attr_name[0].upper() + attr_name[1:]

        # Generate realistic test data based on field name and type
        if attr_type == 'String':
            if 'email' in attr_name.lower():
                setup.append(f'        testEntity.set{capitalized}("test.{attr_name}@example.com");')
            elif 'name' in attr_name.lower():
                setup.append(f'        testEntity.set{capitalized}("Sample {attr_name.replace("_", " ").title()}");')
            elif 'description' in attr_name.lower():
                setup.append(f'        testEntity.set{capitalized}("Test description for {attr_name} field");')
            else:
                setup.append(f'        testEntity.set{capitalized}("TEST_{attr_name.upper()}");')

        elif attr_type in ['int', 'Integer']:
            setup.append(f'        testEntity.set{capitalized}(42);')  # Universal answer
        
        elif attr_type in ['long', 'Long']:
            setup.append(f'        testEntity.set{capitalized}(42L);')
        
        elif attr_type in ['double', 'Double']:
            setup.append(f'        testEntity.set{capitalized}(3.1415);')
        
        elif attr_type in ['boolean', 'Boolean']:
            setup.append(f'        testEntity.set{capitalized}(true);')
        
        elif attr_type == 'BigDecimal':
            setup.append(f'        testEntity.set{capitalized}(new BigDecimal("1234.56"));')
        
        elif attr_type == 'LocalDate':
            setup.append(f'        testEntity.set{capitalized}(LocalDate.of(2024, 1, 1));')
        
        elif attr_type == 'LocalDateTime':
            setup.append(f'        testEntity.set{capitalized}(LocalDateTime.of(2024, 1, 1, 12, 0));')
        
        elif attr_type == 'UUID':
            setup.append(f'        testEntity.set{capitalized}(UUID.fromString("00000000-0000-0000-0000-000000000001"));')
        
        elif self._is_enum(attr):
            enum_type = attr_type.split('.')[-1]
            setup.append(f'        testEntity.set{capitalized}({enum_type}.VALUES[0]);')
        
        elif self._is_relationship(attr):
            related_class = self._extract_relationship_class(attr_type)
            setup.extend([
                f'        {related_class} {attr_name} = new {related_class}();',
                f'        {attr_name}.setId(1L);',
                f'        testEntity.set{capitalized}({attr_name});'
            ])
        
        elif self._is_collection(attr):
            element_type = self._get_collection_element_type(attr_type)
            if self._is_relationship({'type': element_type}):
                related_class = self._extract_relationship_class(element_type)
                setup.append(f'        testEntity.set{capitalized}(List.of({related_class}TestFactory.createTest{related_class}()));')
            else:
                setup.append(f'        testEntity.set{capitalized}(List.of("Item1", "Item2"));')
        
        else:
            setup.append(f'        // TODO: Handle {attr_type} type for {attr_name}')
            setup.append(f'        // testEntity.set{capitalized}(/* unknown type */);')

        return setup

    def get_invalid_id(self):
        id_type = self.id_attribute['type']
        if id_type == 'Long':
            return '-1L'  # Negative IDs are clearly invalid
        elif id_type == 'UUID':
            return 'UUID.fromString("00000000-0000-0000-0000-000000000000")'
        elif id_type == 'String':
            return '"INVALID_ID"'
        elif 'Integer' in id_type:
            return '-1'
        return '0'
    
    def generate_service_test(self):
        base_package = ".".join(self.package_name.split(".")[:-1])
        service_package = f"{base_package}.service"
        impl_package = f"{service_package}.impl"
        id_type = self.id_attribute["type"]
        dto_type = f"{self.class_name}DTO"
        entity_var = self.class_name[0].lower() + self.class_name[1:]

        # Generate realistic test data
        test_data = []
        assertions = []
        for attr in self.attributes:
            if attr['name'] == self.id_attribute["name"]:
                continue
                
            attr_type = attr['type']
            capitalized = attr['name'][0].upper() + attr['name'][1:]
            
            # Generate realistic test values
            if attr_type == 'String':
                if 'email' in attr['name'].lower():
                    test_value = f'"test.{attr['name']}@example.com"'
                else:
                    test_value = f'"Test {attr['name'].replace('_', ' ').title()}"'
            elif attr_type in ['int', 'Integer']:
                test_value = '42'
            elif attr_type == 'LocalDateTime':
                test_value = 'LocalDateTime.parse("2024-01-01T12:00:00")'
            else:
                test_value = f'createTest{attr_type}()'
            
            test_data.append(f"        entity.set{capitalized}({test_value});")
            test_data.append(f"        dto.set{capitalized}(entity.get{capitalized}());")
            assertions.append(f"        assertThat(result.get{capitalized}()).isEqualTo(dto.get{capitalized}());")

        content = f"""package {impl_package};

    import {self.package_name}.{self.class_name};
    import {base_package}.dto.{dto_type};
    import {base_package}.mapper.{self.class_name}Mapper;
    import {base_package}.repository.{self.class_name}Repository;
    import org.junit.jupiter.api.BeforeEach;
    import org.junit.jupiter.api.Test;
    import org.junit.jupiter.api.extension.ExtendWith;
    import org.mockito.*;
    import org.mockito.junit.jupiter.MockitoExtension;
    import org.springframework.dao.*;
    import org.springframework.data.domain.*;
    import java.util.*;
    import static org.assertj.core.api.Assertions.*;
    import static org.mockito.ArgumentMatchers.*;
    import static org.mockito.BDDMockito.*;

    @ExtendWith(MockitoExtension.class)
    class {self.class_name}ServiceImplTest {{

        @Mock
        private {self.class_name}Repository {entity_var}Repository;
        @Mock
        private {self.class_name}Mapper {entity_var}Mapper;
        @InjectMocks
        private {self.class_name}ServiceImpl service;

        private {self.class_name} entity;
        private {dto_type} dto;
        private final {id_type} TEST_ID = {self.get_test_id_value()};
        @Captor
        private ArgumentCaptor<{self.class_name}> entityCaptor;

        @BeforeEach
        void setUp() {{
            entity = new {self.class_name}();
            dto = new {dto_type}();
    {'\\n'.join(test_data)}
        }}

        @Test
        void create_ShouldReturnDtoWhenValidInput() {{
            // Arrange
            given({entity_var}Mapper.toEntity(any())).willReturn(entity);
            given({entity_var}Repository.save(any())).willReturn(entity);
            given({entity_var}Mapper.toDTO(any())).willReturn(dto);

            // Act
            {dto_type} result = service.create(dto);

            // Assert
    {'\\n'.join(assertions)}
            then({entity_var}Repository).should().save(entityCaptor.capture());
            assertThat(entityCaptor.getValue()).isEqualTo(entity);
        }}

        @Test
        void findById_ShouldReturnDtoWhenEntityExists() {{
            // Arrange
            given({entity_var}Repository.findById(TEST_ID)).willReturn(Optional.of(entity));
            given({entity_var}Mapper.toDTO(entity)).willReturn(dto);

            // Act
            Optional<{dto_type}> result = service.findById(TEST_ID);

            // Assert
            assertThat(result).containsSame(dto);
            then({entity_var}Repository).should().findById(TEST_ID);
        }}

        @Test
        void update_ShouldThrowWhenEntityNotFound() {{
            // Arrange
            given({entity_var}Repository.existsById(TEST_ID)).willReturn(false);

            // Act & Assert
            assertThatThrownBy(() -> service.update(TEST_ID, dto))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContainingAll("{self.class_name}", TEST_ID.toString());
        }}

        @Test
        void delete_ShouldVerifyExistenceBeforeDeletion() {{
            // Arrange
            given({entity_var}Repository.existsById(TEST_ID)).willReturn(true);

            // Act
            service.delete(TEST_ID);

            // Assert
            then({entity_var}Repository).should().deleteById(TEST_ID);
        }}

        @Test
        void bulkDelete_ShouldValidateCompleteDeletion() {{
            // Arrange
            List<{id_type}> ids = List.of(TEST_ID, {self.get_test_id_value()});
            given({entity_var}Repository.countAllById(ids)).willReturn(1L);

            // Act & Assert
            assertThatThrownBy(() -> service.bulkDelete(ids))
                .isInstanceOf(BulkOperationException.class)
                .hasMessageContaining("1", "2");
        }}

        @Test
        void search_ShouldPassSpecificationToRepository() {{
            // Arrange
            Specification<{self.class_name}> spec = mock(Specification.class);
            Pageable pageable = PageRequest.of(0, 10);
            given({entity_var}Repository.findAll(any(), any())).willReturn(Page.empty());

            // Act
            service.search(spec, pageable);

            // Assert
            then({entity_var}Repository).should().findAll(spec, pageable);
        }}

        @Test
        void partialUpdate_ShouldOnlyUpdateChangedFields() {{
            // Arrange
            {dto_type} partialDto = new {dto_type}();
            partialDto.set{self.attributes[1]['name'].capitalize()}(dto.get{self.attributes[1]['name'].capitalize()}());
            given({entity_var}Repository.findById(TEST_ID)).willReturn(Optional.of(entity));

            // Act
            service.partialUpdate(TEST_ID, partialDto);

            // Assert
            then({entity_var}Mapper).should().partialUpdate(partialDto, entity);
            then({entity_var}Repository).should().save(entity);
        }}

        @Test
        void shouldHandleOptimisticLockingDuringUpdate() {{
            // Arrange
            given({entity_var}Repository.save(any())).willThrow(ObjectOptimisticLockingFailureException.class);
            given({entity_var}Repository.existsById(TEST_ID)).willReturn(true);

            // Act & Assert
            assertThatThrownBy(() -> service.update(TEST_ID, dto))
                .isInstanceOf(ConcurrentModificationException.class)
                .hasMessageContaining("conflict");
        }}

        private {id_type} createTestId() {{
            return {self.get_test_id_value()};
        }}
    }}"""
        
        return {
            "path": f"{self.class_name}ServiceImplTest.java",
            "content": content,
            "package": impl_package,
            "test": True
        }

    def get_test_id_value(self):
        id_type = self.id_attribute['type']
        if id_type == 'Long':
            return '12345L'
        elif id_type == 'UUID':
            return 'UUID.fromString("a1b2c3d4-5678-90ab-cdef-123456789abc")'
        elif 'String' in id_type:
            return '"TEST_ID_123"'
        return '1'

    def generate_controller_test(self):
        base_package = ".".join(self.package_name.split(".")[:-1])
        controller_package = f"{base_package}.controller"
        id_type = self.id_attribute["type"]
        entity_var = self.class_name[0].lower() + self.class_name[1:]
        dto_type = f"{self.class_name}DTO"
        has_relationships = any(self._is_relationship(attr) for attr in self.attributes)

        content = f"""package {controller_package};

    import {base_package}.dto.{dto_type};
    import {base_package}.service.{self.class_name}Service;
    import com.fasterxml.jackson.databind.ObjectMapper;
    import org.junit.jupiter.api.Test;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
    import org.springframework.boot.test.mock.mockito.MockBean;
    import org.springframework.data.domain.*;
    import org.springframework.hateoas.*;
    import org.springframework.http.*;
    import org.springframework.security.test.context.support.WithMockUser;
    import org.springframework.test.web.servlet.MockMvc;
    import java.util.*;
    import static org.mockito.BDDMockito.*;
    import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
    import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
    import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

    @WebMvcTest({self.class_name}Controller.class)
    class {self.class_name}ControllerTest {{

        @Autowired
        private MockMvc mockMvc;
        @MockBean
        private {self.class_name}Service {entity_var}Service;
        @Autowired
        private ObjectMapper objectMapper;

        private final {id_type} TEST_ID = {self.get_test_id_value()};
        private final {dto_type} validDto = createValidDto();
        private final String BASE_URL = "/api/v1/{entity_var}s";

        @Test
        @WithMockUser(authorities = "SCOPE_{self.class_name.upper()}_READ")
        void getById_ShouldReturnResourceWithLinks() throws Exception {{
            given({entity_var}Service.findById(TEST_ID)).willReturn(Optional.of(validDto));

            mockMvc.perform(get(BASE_URL + "/{{id}}", TEST_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(TEST_ID.toString()))
                .andExpect(jsonPath("$._links.self.href").exists())
                .andExpect(jsonPath("$._links.collection.href").exists());
        }}

        @Test
        @WithMockUser(authorities = "SCOPE_{self.class_name.upper()}_WRITE")
        void create_ShouldReturnCreatedWithLocation() throws Exception {{
            given({entity_var}Service.create(any())).willReturn(validDto);

            mockMvc.perform(post(BASE_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(validDto)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$._links.self.href").exists());
        }}

        @Test
        void create_ShouldRequireAuthentication() throws Exception {{
            mockMvc.perform(post(BASE_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{{}}"))
                .andExpect(status().isUnauthorized());
        }}

        @Test
        @WithMockUser(authorities = "SCOPE_{self.class_name.upper()}_WRITE")
        void create_ShouldValidateInput() throws Exception {{
            {dto_type} invalidDto = new {dto_type}();

            mockMvc.perform(post(BASE_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(invalidDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").isArray())
                .andExpect(jsonPath("$.errors[0].field").exists());
        }}

        @Test
        @WithMockUser(authorities = "SCOPE_{self.class_name.upper()}_READ")
        void getAll_ShouldReturnPagedResources() throws Exception {{
            Page<{dto_type}> page = new PageImpl<>(List.of(validDto));
            given({entity_var}Service.findAll(any())).willReturn(page);

            mockMvc.perform(get(BASE_URL)
                    .param("page", "0")
                    .param("size", "10")
                    .param("sort", "id,desc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.{entity_var}List[0].id").exists())
                .andExpect(jsonPath("$._links.self.href").exists());
        }}

        @Test
        @WithMockUser(authorities = "SCOPE_{self.class_name.upper()}_DELETE")
        void delete_ShouldReturnNoContent() throws Exception {{
            given({entity_var}Service.exists(TEST_ID)).willReturn(true);

            mockMvc.perform(delete(BASE_URL + "/{{id}}", TEST_ID)
                    .with(csrf()))
                .andExpect(status().isNoContent());
        }}

        @Test
        @WithMockUser(authorities = "SCOPE_{self.class_name.upper()}_UPDATE")
        void partialUpdate_ShouldHandleInvalidData() throws Exception {{
            given({entity_var}Service.partialUpdate(eq(TEST_ID), any()))
                .willThrow(new IllegalArgumentException("Invalid data"));

            mockMvc.perform(patch(BASE_URL + "/{{id}}", TEST_ID)
                    .with(csrf())
                    .content("{{}}"))
                .andExpect(status().isBadRequest());
        }}

        {self._generate_relationship_tests() if has_relationships else ''}

        private {dto_type} createValidDto() {{
            return {dto_type}.builder()
                .id(TEST_ID)
                {self._generate_dto_fields()}
                .build();
        }}

        // Error response structure
        static class ErrorResponse {{
            public int status;
            public String message;
            public List<ValidationError> errors;
        }}

        static class ValidationError {{
            public String field;
            public String message;
        }}
    }}"""

        return {
            "path": f"{self.class_name}ControllerTest.java",
            "content": content,
            "package": controller_package,
            "test": True
        }

    def _generate_relationship_tests(self):
        tests = []
        for attr in self.attributes:
            if self._is_relationship(attr):
                rel_class = self._extract_relationship_class(attr['type'])
                rel_name = attr['name']
                tests.append(f"""
        @Test
        @WithMockUser(authorities = "SCOPE_{self.class_name.upper()}_READ")
        void get{rel_class}_ShouldReturnRelatedResources() throws Exception {{
            List<{rel_class}DTO> items = List.of(new {rel_class}DTO());
            given({self.class_name[0].lower()}{self.class_name[1:]}Service.get{rel_name.capitalize()}(TEST_ID))
                .willReturn(items);

            mockMvc.perform(get(BASE_URL + "/{{id}}/{rel_name}", TEST_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.{rel_class}List").exists());
        }}""")
        return '\n'.join(tests)

    def _generate_dto_fields(self):
        fields = []
        for attr in self.attributes:
            if attr['name'] == self.id_attribute["name"]:
                continue
            if attr['type'] == 'String':
                fields.append(f'.{attr["name"]}("Test {attr["name"]}")')
            elif attr['type'] in ['int', 'Integer']:
                fields.append(f'.{attr["name"]}(42)')
            elif attr['type'] == 'LocalDateTime':
                fields.append(f'.{attr["name"]}("2024-01-01T12:00:00")')
        return '\n            '.join(fields)

    def _generate_custom_query_tests(self):
        return """// Add custom query tests here
            List<Entity> results = repository.findByCustomCriteria();
            assertThat(results).isNotEmpty();"""

    def _parse_annotations(self, field_line):
        return re.findall(r'@(\w+)', field_line)

    def _map_validation_annotations(self):
        # Map JPA/Jakarta annotations to DTO validations
        self.validation_mapping = {
            'NotNull': 'jakarta.validation.constraints.NotNull',
            'Size': 'jakarta.validation.constraints.Size'
        }

    def _generate_relationship_queries(self):
        # Generate JOIN queries for relationships
        queries = []
        for attr in self.attributes:
            if self._is_relationship(attr):
                queries.append(f"    List<{self.class_name}> findBy{attr['name'].capitalize()}Id(Long id);")
        return '\n'.join(queries)


def main():
    parser = argparse.ArgumentParser(description='Generate Spring components from Java beans')
    parser.add_argument('-i', '--input', required=True,
                        help='Input directory or glob pattern for Java bean files')
    parser.add_argument('-o', '--output', required=True,
                        help='Output directory for generated classes')
    args = parser.parse_args()

    # Find all Java bean files
    java_files = glob.glob(args.input, recursive=True) if '*' in args.input else [args.input]
    
    if not java_files:
        print(f"No Java files found matching pattern: {args.input}")
        return

    # Create output directory if needed
    os.makedirs(args.output, exist_ok=True)

    def write_component(component, output_root):
        if not component:
            return

        package_path = component['package'].replace('.', '/')
        output_dir = os.path.join(output_root, package_path)
        os.makedirs(output_dir, exist_ok=True)
        
        output_path = os.path.join(output_dir, component['path'])
        
        with open(output_path, 'w') as f:
            f.write(component['content'])
        print(f"  -> Generated {output_path}")

    # Call write_component here since it was defined inside main()
    for java_file in java_files:
        if not os.path.isfile(java_file):
            print(f"Skipping invalid file: {java_file}")
            continue

        print(f"Processing {os.path.basename(java_file)}...")
        
        try:
            processor = JavaBeanProcessor(java_file)
            
            # Generate and write all components
            components = [
                processor.generate_repository(),
                processor.generate_dto(),
                processor.generate_mapper(),
                *processor.generate_service(),
                processor.generate_controller(),
                # processor.generate_repository_test(),
                # processor.generate_service_test(),
                # processor.generate_controller_test()
            ]

            # Flatten list of components (some generators return multiple)
            for comp in components:
                if isinstance(comp, list):
                    for sub_comp in comp:
                        write_component(sub_comp, args.output)
                else:
                    write_component(comp, args.output)

        except Exception as e:
            print(f"Error processing {java_file}: {str(e)}")
            continue


if __name__ == "__main__":
    main()