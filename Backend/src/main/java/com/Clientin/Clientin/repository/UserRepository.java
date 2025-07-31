package com.Clientin.Clientin.repository;

import com.Clientin.Clientin.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, String>, JpaSpecificationExecutor<User> {

    // Automatic query methods
    List<User> findByName(String name);
    List<User> findByEmail(String email);
    List<User> findByRole(User.Role role);
    
    // Custom JPQL queries
    @Query("SELECT u FROM User u WHERE u.name = :value")
    List<User> findByNameCustom(String value);

    @Query("SELECT u FROM User u")
    List<User> findAllUsers();
}