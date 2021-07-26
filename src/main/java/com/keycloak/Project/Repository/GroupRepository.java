package com.keycloak.Project.Repository;

import com.keycloak.Project.Models.Group;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Group, Long> {

}
