package com.keycloak.Project.Models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String rootUrl;
    private String adminUrl;
    private String realm;
    private Boolean enabled;
    private String description;

    public Client() {

    }

    public Client(Long id, String name, String rootUrl, String adminUrl, String realm, Boolean enabled,
            String description) {
        this.id = id;
        this.name = name;
        this.rootUrl = rootUrl;
        this.adminUrl = adminUrl;
        this.realm = realm;
        this.enabled = enabled;
        this.description = description;
    }

    public Client(String name, String rootUrl, String adminUrl, String realm, Boolean enabled, String description) {
        this.name = name;
        this.rootUrl = rootUrl;
        this.adminUrl = adminUrl;
        this.realm = realm;
        this.enabled = enabled;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRootUrl() {
        return rootUrl;
    }

    public void setRootUrl(String rootUrl) {
        this.rootUrl = rootUrl;
    }

    public String getAdminUrl() {
        return adminUrl;
    }

    public void setAdminUrl(String adminUrl) {
        this.adminUrl = adminUrl;
    }

    public String getRealm() {
        return realm;
    }

    public void setRealm(String realm) {
        this.realm = realm;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Client{" + "id=" + id + ", name='" + name + '\'' + ", rootUrl='" + rootUrl + '\'' + ", adminUrl='"
                + adminUrl + '\'' + ", realm='" + realm + '\'' + ", enabled='" + enabled + '\'' + ", description='"
                + description + '\'' + "}";
    }
}
