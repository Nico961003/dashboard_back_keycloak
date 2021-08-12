package com.keycloak.Project.Models;

import java.util.List;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.ElementCollection;

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
    private String idClient;

    // @ElementCollection(targetClass = String.class)
    // // @OneToMany(targetEntity=Student.class, mappedBy="college",
    // // fetch=FetchType.EAGER)
    // private Map<String, List<String>> attributes;

    @ElementCollection(targetClass = String.class)
    private List<String> attributes;

    @ElementCollection(targetClass = String.class)
    private List<String> status;

    public Role() {

    }

    public Role(long id, String identifier, String name, Boolean clientRole, String realm, String description,
            String idClient, List<String> attributes, List<String> status) {
        this.id = id;
        this.identifier = identifier;
        this.name = name;
        this.clientRole = clientRole;
        this.realm = realm;
        this.description = description;
        this.idClient = idClient;
        this.attributes = attributes;
        this.status = status;
    }

    public Role(String identifier, String name, Boolean clientRole, String realm, String description, String idClient,
            List<String> attributes, List<String> status) {
        this.identifier = identifier;
        this.name = name;
        this.clientRole = clientRole;
        this.realm = realm;
        this.description = description;
        this.idClient = idClient;
        this.attributes = attributes;
        this.status = status;
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

    public String getIdClient() {
        return idClient;
    }

    public void setIdClient(String idClient) {
        this.idClient = idClient;
    }

    public List<String> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<String> attributes) {
        this.attributes = attributes;
    }

    public List<String> getStatus() {
        return status;
    }

    public void setStatus(List<String> status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Role [attributes=" + attributes + ", clientRole=" + clientRole + ", description=" + description
                + ", idClient=" + idClient + ", identifier=" + identifier + ", name=" + name + ", realm=" + realm
                + ", status=" + status + "]";
    }

}
