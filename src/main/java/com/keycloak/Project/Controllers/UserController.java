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
import java.util.ArrayList;

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
import com.keycloak.Project.Services.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserRepository repository;

    @Autowired
    UserService userService;

    @RequestMapping(value = "/userK", method = RequestMethod.GET)
    public ResponseEntity<String> getUser(@RequestHeader String Authorization) {
        KeycloakAuthenticationToken authentication = (KeycloakAuthenticationToken) SecurityContextHolder.getContext()
                .getAuthentication();
        final Principal principal = (Principal) authentication.getPrincipal();
        System.out.println("entre con el usuario: " + principal);
        return ResponseEntity.ok("Hello " + principal);
    }

    @GetMapping("/viewUsers")
    List<UserRepresentation> userss(@RequestHeader String Authorization) {
        List<UserRepresentation> lsUsersU = new ArrayList<UserRepresentation>();
        try {
            lsUsersU = userService.users();
            System.out.println("Lista de usuarios");
        } catch (Exception ev) {
            System.out.println(ev);
        }
        return lsUsersU;
    }

    @GetMapping("/viewUser/{id}")
    UserRepresentation user(@RequestHeader String Authorization, @PathVariable String id) {
        UserRepresentation userU = new UserRepresentation();
        try {
            userU = userService.user(id);
            System.out.println("Usuario consultado");
        } catch (Exception eco) {
            System.out.println(eco);
            System.out.println("Usuario no encontrado");
        }
        return userU;
    }

    @PostMapping("/createUser")
    @ResponseStatus(HttpStatus.CREATED)
    void createUser(@RequestBody User user, @RequestHeader String Authorization) {

        String creadoKey = "";
        try {
            creadoKey = userService.createUser(user);
            System.out.println(creadoKey);
        } catch (Exception e) {
            System.out.println("no creado: " + e);
        }
        // return repository.save(user);
    }

    @PutMapping("/updateUser/{id}")
    void updateUser(@RequestBody User user, @RequestHeader String Authorization, @PathVariable String id) {
        String username = user.getUsername();
        String lastName = user.getLastName();
        String firstName = user.getFirstName();
        String email = user.getEmail();
        String pass = user.getPassword();
        String realm = user.getRealm();
        String group = user.getGroup();
        Boolean enabled = user.getEnabled();
        String upUser = "";
        try {
            upUser = userService.updateUser(id, username, lastName, firstName, email, pass, realm, group, enabled);
            System.out.println(upUser);
        } catch (Exception eu) {
            System.out.println(eu);
        }
    }

    @DeleteMapping("/deleteUser/{id}")
    void deleteUser(@RequestHeader String Authorization, @PathVariable String id) {
        String deleteU = "";
        try {
            deleteU = userService.deleteUser(id);
            System.out.println(deleteU);
        } catch (Exception ed) {
            System.out.println(ed);
        }
    }

}