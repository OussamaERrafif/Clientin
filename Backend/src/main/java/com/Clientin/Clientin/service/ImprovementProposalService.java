package com.Clientin.Clientin.service;

import com.Clientin.Clientin.dto.ImprovementProposalDTO;
import com.Clientin.Clientin.entity.ImprovementProposal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

public interface ImprovementProposalService {
    
    // Basic CRUD operations
    ImprovementProposalDTO create(ImprovementProposalDTO dto);
    Optional<ImprovementProposalDTO> findById(String id);
    Page<ImprovementProposalDTO> findAll(Pageable pageable);
    List<ImprovementProposalDTO> findAll();
    ImprovementProposalDTO update(String id, ImprovementProposalDTO dto);
    void delete(String id);
    
    // Advanced operations
    Page<ImprovementProposalDTO> search(Specification<ImprovementProposal> spec, Pageable pageable);
    ImprovementProposalDTO partialUpdate(String id, ImprovementProposalDTO dto);
    boolean exists(String id);
    
    // Bulk operations
    List<ImprovementProposalDTO> bulkCreate(List<ImprovementProposalDTO> dtos);
    void bulkDelete(List<String> ids);
}