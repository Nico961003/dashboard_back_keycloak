package com.keycloak.Project.Services;  

import com.keycloak.Project.Models.User;
import com.keycloak.Project.Repository.UserRepository;

import java.security.Principal;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.IDToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.Arrays;
import org.keycloak.adapters.springsecurity.client.*;
//import org.keycloak.keycloak-admin-client.*
import org.keycloak.admin.client.*;
import org.keycloak.representations.idm.*;
import org.keycloak.protocol.oidc.*;
import org.keycloak.admin.client.token.*;
import org.keycloak.admin.client.token.TokenManager;
//import org.keycloak.representations.idm.UserRepresentation
import org.keycloak.admin.client.resource.UserResource;
import org.springframework.stereotype.Component;
 
@Component
public class UserService{
    public Keycloak instance(){
        Keycloak instanceU = Keycloak.getInstance("http://localhost" + ":" + "8080" + "/auth", "SpringBoot", "user1", "user1", "login", "password");
        return instanceU;
    }

    public List<UserRepresentation> users(){
        Keycloak instance = instance(); 
        List<UserRepresentation> lsUsersU = instance.realm("SpringBoot").users().search("");
        return lsUsersU;
    }

    public UserRepresentation user(String id){
        Keycloak instance = instance();
        UserResource userUp = instance.realm("SpringBoot").users().get(id);
        UserRepresentation userU = userUp.toRepresentation();
        return userU;
    }

    public String createUser(String username, String lastname, String firstname, String email, String pass, String realm, String role, Boolean enable){
        Keycloak instance = instance();
        CredentialRepresentation credential = new CredentialRepresentation();
        // RealmResource realmResource = instance.realm(realm);
        // RoleRepresentation realmRole = realmResource.roles().get(role).toRepresentation();
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(pass);
        credential.setTemporary(true);
        UserRepresentation userN = new UserRepresentation();
        userN.setUsername(username);
        userN.setFirstName(firstname);
        userN.setLastName(lastname);
        userN.setCredentials(Arrays.asList(credential));
        userN.setEnabled(enable);
        // userN.setGroups(Arrays.asList("user"));
        userN.setEmail(email);
        // System.out.println(userN);
        instance.realm(realm).users().create(userN);

        return "Usuario "+username+" Creado";
    }

    public String updateUser(String id, String username, String lastname, String firstname, String email, String pass, String realm, String role, Boolean enable){
        Keycloak instance = instance(); 
        UserResource userUp = instance.realm("SpringBoot").users().get(id);
        UserRepresentation userRep = userUp.toRepresentation();
        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(pass);
        credential.setTemporary(true);
        userRep.setUsername(username);
        userRep.setFirstName(firstname);
        userRep.setLastName(lastname);
        userRep.setCredentials(Arrays.asList(credential));
        userRep.setEnabled(enable);
        userRep.setEmail(email); 
        userUp.update(userRep);

        return "Usuario "+username+" Actualizado";
    }

    public String deleteUser(String id){
        Keycloak instance = instance();
        instance.realm("SpringBoot").users().get(id).remove();
        return "El usuario con el id: "+id+ " se elimino correctamente.";

    }
}