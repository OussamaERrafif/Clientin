package com.Clientin.Clientin.repository;

import com.Clientin.Clientin.entity.Contract;
import com.Clientin.Clientin.entity.Contract.ContractType;
import com.Clientin.Clientin.entity.Contract.ContractStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ContractRepository 
    extends JpaRepository<Contract, String>, JpaSpecificationExecutor<Contract> {

    // Automatic query methods
    List<Contract> findByEmployeeId(String employeeId);
    List<Contract> findByContractType(ContractType contractType);
    List<Contract> findByStartDate(LocalDate startDate);
    List<Contract> findByStatus(ContractStatus status);
    
    // Custom JPQL queries
    @Query("SELECT c FROM Contract c WHERE c.employeeId = :employeeId")
    List<Contract> findByEmployeeIdCustom(String employeeId);

    @Query("SELECT c FROM Contract c WHERE c.status = :status")
    List<Contract> findByStatusCustom(ContractStatus status);
    
    // Relationship queries
    
}