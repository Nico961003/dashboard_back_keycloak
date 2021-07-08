package com.keycloak.Project.Repository;

// import org.springframework.data.repository.CrudRepository;
import com.keycloak.Project.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}