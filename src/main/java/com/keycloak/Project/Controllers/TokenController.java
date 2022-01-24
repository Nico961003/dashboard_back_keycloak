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
import com.keycloak.Project.Models.User;



@RestController
@RequestMapping("/token")
public class TokenController {
    @Autowired
    UserService userService;

    @PostMapping("/newToken")
    public ResponseEntity<String> token(@RequestBody User user) {

        String name = user.getUsername();
        String pass = user.getPassword();
        String realm = System.getenv("REALM_KEY");// user.getRealm();
        String tok = "";

        try {
            Keycloak instance = userService.instanceT(realm, name, pass);
            TokenManager tokenmanager = instance.tokenManager();
            String accessToken = tokenmanager.getAccessTokenString();
            tok = accessToken;

            // System.out.println(tok);
        } catch (Exception eto) {
            System.out.println("No se pudo obtener token \n" + eto);
        }

        return ResponseEntity.ok(tok);
    }

    @PostMapping("/refreshToken")
    public ResponseEntity<String> refreshToken(@RequestBody User user) {

        String tok = "";
        String clientId = user.getIdClient();
        String authorization = user.getRefresh();

        // System.out.println(clientId);
        // System.out.println(authorization);
        
        try {

            String refreshToken = userService.refreshT(authorization, clientId);
            tok = refreshToken;

            // System.out.println(tok);
        } catch (Exception eto) {
            System.out.println("No se pudo obtener token \n" + eto);
        }

        return ResponseEntity.ok(tok);
    }


}
