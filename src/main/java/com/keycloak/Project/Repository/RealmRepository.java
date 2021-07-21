package com.keycloak.Project.Repository;

// import org.springframework.data.repository.CrudRepository;
import com.keycloak.Project.Models.Realm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RealmRepository extends JpaRepository<Realm, Long> {
 
}