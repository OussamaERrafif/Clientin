package com.Clientin.Clientin.service.impl;

import com.Clientin.Clientin.entity.Client;
import com.Clientin.Clientin.entity.Log;
import com.Clientin.Clientin.dto.ClientDTO;
import com.Clientin.Clientin.mapper.ClientMapper;
import com.Clientin.Clientin.repository.ClientRepository;
import com.Clientin.Clientin.service.ClientService;
import com.Clientin.Clientin.service.LogService;
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
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;
    private final LogService logService;

    @Override
    @Transactional
    public ClientDTO create(ClientDTO dto) {
        log.debug("Creating new Client");
        try {
            logService.logAction(Log.LogLevel.INFO, Log.LogAction.CREATE, "Client", null, "system", "Starting client creation transaction");
            Client entity = clientMapper.toEntity(dto);
            Client saved = clientRepository.save(entity);
            logService.logCreate("Client", saved.getId(), "system", "Client entity created and saved to database");
            return clientMapper.toDTO(saved);
        } catch (Exception e) {
            logService.logError("Client", null, "system", "Failed to create client", e.getMessage());
            throw new RuntimeException("Error creating entity", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ClientDTO> findById(String id) {
        log.debug("Fetching Client with ID: {}", id);
        return clientRepository.findById(id)
                .map(clientMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ClientDTO> findAll(Pageable pageable) {
        log.debug("Fetching paged Client results");
        return clientRepository.findAll(pageable)
                .map(clientMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClientDTO> findAll() {
        log.debug("Fetching all Client entities");
        return clientMapper.toDTOList(clientRepository.findAll());
    }

    @Override
    @Transactional
    public ClientDTO update(String id, ClientDTO dto) {
        log.debug("Updating Client with ID: {}", id);
        return clientRepository.findById(id)
                .map(existingEntity -> {
                    clientMapper.partialUpdate(dto, existingEntity);
                    return clientMapper.toDTO(clientRepository.save(existingEntity));
                })
                .orElseThrow(() -> new EntityNotFoundException(
                    "Client not found with id: " + id
                ));
    }

    @Override
    @Transactional
    public void delete(String id) {
        log.debug("Deleting Client with ID: {}", id);
        if (!clientRepository.existsById(id)) {
            throw new EntityNotFoundException(
                "Client not found with id: " + id
            );
        }
        clientRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ClientDTO> search(Specification<Client> spec, Pageable pageable) {
        log.debug("Searching Client with specification");
        return clientRepository.findAll(spec, pageable)
                .map(clientMapper::toDTO);
    }

    @Override
    @Transactional
    public ClientDTO partialUpdate(String id, ClientDTO dto) {
        log.debug("Partial update for Client ID: {}", id);
        return clientRepository.findById(id)
                .map(entity -> {
                    clientMapper.partialUpdate(dto, entity);
                    return clientMapper.toDTO(clientRepository.save(entity));
                })
                .orElseThrow(() -> new EntityNotFoundException(
                    "Client not found with id: " + id
                ));
    }

    @Override
    @Transactional(readOnly = true)
    public boolean exists(String id) {
        return clientRepository.existsById(id);
    }

    @Override
    @Transactional
    public List<ClientDTO> bulkCreate(List<ClientDTO> dtos) {
        log.debug("Bulk creating Client entities: {} items", dtos.size());
        List<Client> entities = clientMapper.toEntityList(dtos);
        return clientMapper.toDTOList(clientRepository.saveAll(entities));
    }

    @Override
    @Transactional
    public void bulkDelete(List<String> ids) {
        log.debug("Bulk deleting Client entities: {} items", ids.size());
        clientRepository.deleteAllById(ids);
    }
}