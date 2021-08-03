package com.keycloak.Project.Models;

import java.util.*;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ElementCollection;

@Entity
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String path;
    private String realm;
    private String idRole;
    private String nameRole;

    // @ElementCollection
    // private List<String> rolesC;

    // @ElementCollection
    // private List<String> rolesR;

    public Group() {
    }

    public Group(Long id, String name, String path, String realm, String idRole, String nameRole) {
        this.id = id;
        this.name = name;
        this.path = path;
        this.realm = realm;
        this.idRole = idRole;
        this.nameRole = nameRole;
    }

    public Group(String name, String path, String realm, String idRole, String nameRole) {
        this.name = name;
        this.path = path;
        this.realm = realm;
        this.idRole = idRole;
        this.nameRole = nameRole;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getRealm() {
        return realm;
    }

    public void setRealm(String realm) {
        this.realm = realm;
    }

    public String getIdRole() {
        return idRole;
    }

    public void setIdRole(String idRole) {
        this.idRole = idRole;
    }

    public String getNameRole() {
        return nameRole;
    }

    public void setNameRole(String nameRole) {
        this.nameRole = nameRole;
    }

    @Override
    public String toString() {
        return "Group [id=" + id + ", idRole=" + idRole + ", name=" + name + ", nameRole=" + nameRole + ", path=" + path
                + ", realm=" + realm + "]";
    }

    // public List<String> getRolesC() {
    // return rolesC;
    // }

    // public void setRolesC(List<String> rolesC) {
    // this.rolesC = rolesC;
    // }

    // public List<String> getRolesR() {
    // return rolesR;
    // }

    // public void setRolesR(List<String> rolesR) {
    // this.rolesR = rolesR;
    // }

    // @Override
    // public String toString() {
    // return "Group [id=" + id + ", name=" + name + ", path=" + path + ", rolesC="
    // + rolesC + ", rolesR=" + rolesR
    // + "]";
    // }

}
