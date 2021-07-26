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

    @ElementCollection
    private List<String> rolesC;

    @ElementCollection
    private List<String> rolesR;

    public Group() {
    }

    public Group(Long id, String name, String path, List<String> rolesC, List<String> rolesR) {
        this.id = id;
        this.name = name;
        this.path = path;
        this.rolesC = rolesC;
        this.rolesR = rolesR;
    }

    public Group(String name, String path, List<String> rolesC, List<String> rolesR) {
        this.name = name;
        this.path = path;
        this.rolesC = rolesC;
        this.rolesR = rolesR;
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

    public List<String> getRolesC() {
        return rolesC;
    }

    public void setRolesC(List<String> rolesC) {
        this.rolesC = rolesC;
    }

    public List<String> getRolesR() {
        return rolesR;
    }

    public void setRolesR(List<String> rolesR) {
        this.rolesR = rolesR;
    }

    @Override
    public String toString() {
        return "Group [id=" + id + ", name=" + name + ", path=" + path + ", rolesC=" + rolesC + ", rolesR=" + rolesR
                + "]";
    }

}
