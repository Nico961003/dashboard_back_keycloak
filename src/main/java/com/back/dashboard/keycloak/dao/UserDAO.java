package com.back.dashboard.keycloak.dao;
import org.springframework.data.repository.CrudRepository;
import com.back.dashboard.keycloak.model.User;

public interface UserDAO extends CrudRepository<User, Long> {

}
