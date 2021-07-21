package com.keycloak.Project.Models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Realm{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Boolean enabled;

    public Realm(){

    }

    public Realm(Long id, String name, Boolean enabled){
        this.id = id;
        this.name = name;
        this.enabled = enabled;
    }

    public Realm(String name, Boolean enabled){
        this.name = name;
        this.enabled = enabled;
    }

    public long getid(){
        return id;
    }
    public void setId(long id){
        this.id = id;
    }

    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }

    public Boolean getEnabled(){
        return enabled;
    }
    public void setEnabled(Boolean enabled){
        this.enabled = enabled;
    }


}