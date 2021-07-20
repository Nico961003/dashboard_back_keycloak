package com.keycloak.Project.Repository;

// import org.springframework.data.repository.CrudRepository;
import com.keycloak.Project.Models.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
 
}