package com.Clientin.Clientin.repository;

import com.Clientin.Clientin.entity.Absence;
import com.Clientin.Clientin.entity.Absence.AbsenceType;
import com.Clientin.Clientin.entity.Absence.AbsenceStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AbsenceRepository 
    extends JpaRepository<Absence, String>, JpaSpecificationExecutor<Absence> {

    // Automatic query methods
    List<Absence> findByEmployeeId(String employeeId);
    List<Absence> findByAbsenceType(AbsenceType absenceType);
    List<Absence> findByStartDate(LocalDate startDate);
    List<Absence> findByStatus(AbsenceStatus status);
    
    // Custom JPQL queries
    @Query("SELECT a FROM Absence a WHERE a.employeeId = :employeeId")
    List<Absence> findByEmployeeIdCustom(String employeeId);

    @Query("SELECT a FROM Absence a WHERE a.status = :status")
    List<Absence> findByStatusCustom(AbsenceStatus status);
    
    // Relationship queries
    
}