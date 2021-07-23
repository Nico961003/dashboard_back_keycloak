package com.keycloak.Project.Repository;

import com.keycloak.Project.Models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

}
