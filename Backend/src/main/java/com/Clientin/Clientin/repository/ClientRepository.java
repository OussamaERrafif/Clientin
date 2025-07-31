package com.Clientin.Clientin.repository;

import com.Clientin.Clientin.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client, String>, JpaSpecificationExecutor<Client> {

    // Automatic query methods
    List<Client> findByName(String name);
    List<Client> findByEmail(String email);
    List<Client> findByIsAnonymous(Boolean isAnonymous);
    
    // Custom JPQL queries
    @Query("SELECT c FROM Client c WHERE c.name = :value")
    List<Client> findByNameCustom(String value);

    @Query("SELECT c FROM Client c")
    List<Client> findAllClients();
}