package com.keycloak.Project.Controllers;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.security.RolesAllowed;

import com.keycloak.Project.Services.UserService;

import java.security.Principal;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.IDToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.keycloak.adapters.springsecurity.client.*;
import org.springframework.web.bind.annotation.*;
//import org.keycloak.keycloak-admin-client.*
import org.keycloak.admin.client.*;
import org.keycloak.representations.idm.*;
import org.keycloak.protocol.oidc.*;
import org.keycloak.admin.client.token.*;
import org.keycloak.admin.client.token.TokenManager;
//import org.keycloak.representations.idm.UserRepresentation
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.*;
// import com.keycloak.Project.Services.UserService;

@RestController
@RequestMapping("/token")
public class TokenController{
    @Autowired
    UserService userService;

    @GetMapping("/newToken")
    public ResponseEntity<String> token(){
        String tok = "";
        try{
            Keycloak instance = userService.instance();
            TokenManager tokenmanager = instance.tokenManager();
            String accessToken = tokenmanager.getAccessTokenString();
            // println(tokenmanager.getAccessTokenString())

            tok = accessToken;
            System.out.println(tok);
        }catch(Exception eto){
            System.out.println("No se pudo ontener token \n"+ eto);
        }
        return ResponseEntity.ok(tok);
    }

}
