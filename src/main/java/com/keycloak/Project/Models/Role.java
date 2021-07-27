package com.keycloak.Project.Models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String identifier;
    private String name;
    private Boolean clientRole;
    private String realm;
    private String description;

    public Role() {

    }

    public Role(long id, String identifier, String name, Boolean clientRole, String realm, String description) {
        this.id = id;
        this.identifier = identifier;
        this.name = name;
        this.clientRole = clientRole;
        this.realm = realm;
        this.description = description;
    }

    public Role(String identifier, String name, Boolean clientRole, String realm, String description) {
        this.identifier = identifier;
        this.name = name;
        this.clientRole = clientRole;
        this.realm = realm;
        this.description = description;
    }

    public String getRealm() {
        return realm;
    }

    public void setRealm(String realm) {
        this.realm = realm;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getClientRole() {
        return clientRole;
    }

    public void setClientRole(Boolean clientRole) {
        this.clientRole = clientRole;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Role [clientRole=" + clientRole + ", id=" + id + ", identifier=" + identifier + ", name=" + name
                + ", realm=" + realm + ", description=" + description + "]";
    }

}
