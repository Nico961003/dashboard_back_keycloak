package com.keycloak.Project.Models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import java.util.List;
import java.util.Map;

import javax.persistence.ElementCollection;
import javax.persistence.OneToMany;


@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String username;
    private String lastName;
    private String firstName;
    private String email;
    private String password;
    private String realm;
    private String group;
    private Boolean enabled;
    private String idClient;

    // private String idClient;
    // private String idRole;
    // private String nameRole;

    @ElementCollection(targetClass = String.class)
    // @OneToMany(targetEntity=Student.class, mappedBy="college",
    // fetch=FetchType.EAGER)
    private List<Map<String, String>> rolesClient;

    @ElementCollection(targetClass = String.class)
    private List<String> attributes;

    public User() {
    }

    public User(long id, String username, String lastName, String firstName, String email, String password,
            String realm, String group, Boolean enabled, List<Map<String, String>> rolesClient, String idClient, List<String> attributes) {
        this.id = id;
        this.username = username;
        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
        this.password = password;
        this.realm = realm;
        this.group = group;
        this.enabled = enabled;
        this.rolesClient = rolesClient;
        this.idClient = idClient;
        this.attributes = attributes;
    }

    public User(String username, String lastName, String firstName, String email, String password, String realm,
            String group, Boolean enabled, List<Map<String, String>> rolesClient, String idClient, List<String> attributes) {
        this.username = username;
        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
        this.password = password;
        this.realm = realm;
        this.group = group;
        this.enabled = enabled;
        this.rolesClient = rolesClient;
        this.idClient = idClient;
        this.attributes = attributes;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRealm() {
        return realm;
    }

    public void setRealm(String realm) {
        this.realm = realm;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public List<Map<String, String>> getRolesClient() {
        return rolesClient;
    }

    public void setRolesClient(List<Map<String, String>> rolesClient) {
        this.rolesClient = rolesClient;
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

    @Override
    public String toString() {
        return "User [attributes=" + attributes + ",email=" + email + ", enabled=" + enabled + ", firstName=" + firstName + ", group=" + group
                + ", idClient=" + idClient + ", lastName=" + lastName + ", password=" + password + ", realm=" + realm
                + ", rolesClient=" + rolesClient + ", username=" + username + "]";
    }

}