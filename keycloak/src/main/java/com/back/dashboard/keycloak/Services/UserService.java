package com.back.dashboard.keycloak.services;  
import java.util.List;  
import com.back.dashboard.keycloak.model.User;
import java.util.Collection;

public interface UserService   
{  
    public abstract void createUser(User user);  
    public abstract void prueba();  

    public abstract void updateUser(String id, User user);
    public abstract void deleteUser(String id);
    public abstract Collection<User> getUsers();
}  