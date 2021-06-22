package com.back.dashboard.keycloak.dao;
import org.springframework.data.repository.CrudRepository;
import com.back.dashboard.keycloak.model.Customer;

public interface CustomerDAO extends CrudRepository<Customer, Long> {

}
