package com.keycloak.Project.Controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.security.RolesAllowed;

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

import com.keycloak.Project.Models.User;
import com.keycloak.Project.Repository.UserRepository;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserRepository repository;

    @RequestMapping(value = "/userK", method = RequestMethod.GET)
    public ResponseEntity<String> getUser(@RequestHeader String Authorization) {
        KeycloakAuthenticationToken authentication = (KeycloakAuthenticationToken) SecurityContextHolder.getContext()
                .getAuthentication();
        final Principal principal = (Principal) authentication.getPrincipal();

        System.out.println("entre con el usuario: " + principal);
        return ResponseEntity.ok("Hello " + principal);
    }

    @GetMapping("/viewUsers")
    List<UserRepresentation> users(@RequestHeader String Authorization) {
        Keycloak instanceU = Keycloak.getInstance("http://localhost" + ":" + "8080" + "/auth", "SpringBoot", "user1",
                "user1", "login", "password");
        List<UserRepresentation> lsUsersU = instanceU.realm("SpringBoot").users().search("");
        System.out.println("Lista de usuarios");
        return lsUsersU;
    }

    @GetMapping("/viewUser/{id}")
    UserRepresentation user(@RequestHeader String Authorization, @PathVariable String id) {
        Keycloak instanceU = Keycloak.getInstance("http://localhost" + ":" + "8080" + "/auth", "SpringBoot", "user1",
                "user1", "login", "password");
        UserResource userUp = instanceU.realm("SpringBoot").users().get(id);
        UserRepresentation userU = userUp.toRepresentation();
        return userU;
    }

    @PostMapping("/createUser")
    @ResponseStatus(HttpStatus.CREATED)
    User createUser(@RequestBody User user, @RequestHeader String Authorization) {
        String username = user.getUsername();
        String lastname = user.getLastname();
        String firstname = user.getFirstname();
        String email = user.getEmail();
        String pass = user.getPassword();
        String realm = user.getRealm();
        String role = user.getRole();
        Boolean enable = user.getEnable();
        try {
            Keycloak instance = Keycloak.getInstance("http://localhost" + ":" + "8080" + "/auth", "SpringBoot", "user1",
                    "user1", "login", "password");
            TokenManager tokenmanager = instance.tokenManager();
            // String accessToken = tokenmanager.getAccessTokenString();
            // System.out.println(tokenmanager.getAccessTokenString());
            CredentialRepresentation credential = new CredentialRepresentation();
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
            instance.realm(realm).users().create(userN);
            System.out.println("si se creo en keycloak");
            // user.lastLogin = new Date();
            // userService.save(user)
        } catch (Exception e) {
            System.out.println("no creado: " + e);
        }
        return repository.save(user);
    }

    @PutMapping("/updateUser/{id}")
    void updateUser(@RequestBody User user, @RequestHeader String Authorization, @PathVariable String id) {
        String username = user.getUsername();
        String lastname = user.getLastname();
        String firstname = user.getFirstname();
        String email = user.getEmail();
        String pass = user.getPassword();
        String realm = user.getRealm();
        String role = user.getRole();
        Boolean enable = user.getEnable();

        Keycloak instance = Keycloak.getInstance("http://localhost" + ":" + "8080" + "/auth", "SpringBoot", "user1",
                "user1", "login", "password");
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
    }

    @DeleteMapping("/deleteUser/{id}")
    void deleteUser(@RequestHeader String Authorization, @PathVariable String id) {
        Keycloak instance = Keycloak.getInstance("http://localhost" + ":" + "8080" + "/auth", "SpringBoot", "user1",
                "user1", "login", "password");
        instance.realm("SpringBoot").users().get(id).remove();
    }

}