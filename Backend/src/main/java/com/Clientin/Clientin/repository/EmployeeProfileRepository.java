package com.Clientin.Clientin.repository;

import com.Clientin.Clientin.entity.EmployeeProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EmployeeProfileRepository extends JpaRepository<EmployeeProfile, String>, JpaSpecificationExecutor<EmployeeProfile> {

    // Automatic query methods
    List<EmployeeProfile> findByPosition(String position);
    List<EmployeeProfile> findByDepartment(String department);
    List<EmployeeProfile> findBySalary(Double salary);
    
    // Custom JPQL queries
    @Query("SELECT ep FROM EmployeeProfile ep WHERE ep.position = :value")
    List<EmployeeProfile> findByPositionCustom(String value);

    @Query("SELECT ep FROM EmployeeProfile ep")
    List<EmployeeProfile> findAllEmployeeProfiles();
    
    // Relationship queries
    List<EmployeeProfile> findByUserId(String userId);
}