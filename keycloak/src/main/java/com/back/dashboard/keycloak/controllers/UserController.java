package com.back.dashboard.keycloak.controllers;

import java.security.Principal;
import java.util.Map;

import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.IDToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.back.dashboard.keycloak.model.User;
import com.back.dashboard.keycloak.services.UserService;

@Controller
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public ResponseEntity<Object> createUser(@RequestBody User user) {
        System.out.println("entre\n\n\n");
        KeycloakAuthenticationToken authentication = (KeycloakAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        final Principal principal = (Principal) authentication.getPrincipal(); 

        if (principal instanceof KeycloakPrincipal) {
            System.out.println("\n\nKeycloak user controller");
            System.out.println("\n" + principal + "\n");
            KeycloakPrincipal<KeycloakSecurityContext> kPrincipal = (KeycloakPrincipal<KeycloakSecurityContext>) principal;
            System.out.println("\nkPrincipal : " + kPrincipal + "\n");
            IDToken token = kPrincipal.getKeycloakSecurityContext().getIdToken();
            System.out.println("\n token : " + token + "\n");
        }
        userService.createUser(user);
        System.out.println("entre con post a user");
        return new ResponseEntity<>("User was created successfully", HttpStatus.CREATED);
     }
}