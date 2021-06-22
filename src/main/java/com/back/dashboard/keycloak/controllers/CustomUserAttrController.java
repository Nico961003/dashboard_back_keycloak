package com.back.dashboard.keycloak.controllers;

import java.security.Principal;
import java.util.Map;

import static org.springframework.http.HttpStatus.*;
import org.keycloak.adapters.springsecurity.client.*;
import org.keycloak.admin.client.*;
import org.keycloak.representations.idm.*;
import org.keycloak.admin.client.token.*;
import org.keycloak.admin.client.resource.UserResource;
import java.util.Arrays;
import java.util.List;

import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.IDToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.back.dashboard.keycloak.model.User;
import com.back.dashboard.keycloak.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class CustomUserAttrController {

    @Autowired
    UserService userService;

    @GetMapping(path = "/users")
    public String getUserInfo(Model model) {

        KeycloakAuthenticationToken authentication = (KeycloakAuthenticationToken) SecurityContextHolder.getContext()
            .getAuthentication();

        System.out.println("\n\n\n\n " + authentication + "\n\n\n");

        final Principal principal = (Principal) authentication.getPrincipal(); 

        String dob = "";
        if (principal instanceof KeycloakPrincipal) {
            System.out.println("\n\nKeycloak");
            
            System.out.println("\n" + principal + "\n");

            KeycloakPrincipal<KeycloakSecurityContext> kPrincipal = (KeycloakPrincipal<KeycloakSecurityContext>) principal;
            IDToken token = kPrincipal.getKeycloakSecurityContext()
                .getIdToken();

            Map<String, Object> customClaims = token.getOtherClaims();

            if (customClaims.containsKey("DOB")) {
                dob = String.valueOf(customClaims.get("DOB"));
            }
        }

        model.addAttribute("username", principal.getName());

        model.addAttribute("dob", dob);
        return "userInfo";
    }


    @GetMapping(path = "/prueba")
    public String getUserInfo2(Model model) {

        KeycloakAuthenticationToken authentication = (KeycloakAuthenticationToken) SecurityContextHolder.getContext()
            .getAuthentication();
        
        userService.prueba();
        System.out.println("\n\n\n\n " + authentication + "\n\n\n");

        final Principal principal = (Principal) authentication.getPrincipal(); 



        try {
            System.out.println("\n\nentre a key\n\n");
            Keycloak instance = Keycloak.getInstance("http://localhost:8080/auth", "master", "admin", "admin","admin-cli", "password");                                                                                                    
            TokenManager tokenmanager = instance.tokenManager();
            CredentialRepresentation credential = new CredentialRepresentation();
            System.out.println(credential);
            credential.setType(CredentialRepresentation.PASSWORD);
            credential.setValue("prueba2");
            // credential.setTemporary(user.passwordExpired)
            UserRepresentation userN = new UserRepresentation();
            userN.setUsername("prueba2");
            userN.setFirstName("prueba2");
            userN.setLastName("User");
            userN.setCredentials(Arrays.asList(credential));
            // userN.setEnabled(user.enabled);
            // userN.setGroups(Arrays.asList("R_STATION"))
            userN.setEmail("prueba@gmail.com");
            instance.realm("master").users().create(userN);
            System.out.println("\n\nsali a key\n\n" + userN);
        } catch (Exception e) {
            System.out.println("error : " + e);
        }    




        String dob = "";
        if (principal instanceof KeycloakPrincipal) {
            System.out.println("\n\nKeycloak");
            
            System.out.println("\n" + principal + "\n");

            KeycloakPrincipal<KeycloakSecurityContext> kPrincipal = (KeycloakPrincipal<KeycloakSecurityContext>) principal;
            IDToken token = kPrincipal.getKeycloakSecurityContext()
                .getIdToken();

            Map<String, Object> customClaims = token.getOtherClaims();

            if (customClaims.containsKey("DOB")) {
                dob = String.valueOf(customClaims.get("DOB"));
            }
        }

        model.addAttribute("username", principal.getName());

        model.addAttribute("dob", dob);
        return "userInfo";
    }

}