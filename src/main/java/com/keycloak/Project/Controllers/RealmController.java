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
import org.keycloak.admin.client.resource.*;
import com.keycloak.Project.Models.Realm;
import com.keycloak.Project.Services.RealmService;
import com.keycloak.Project.Repository.RealmRepository;

@RestController
@RequestMapping("/realm")
public class RealmController {

    @Autowired
    private RealmRepository realmRepository;

    @Autowired
    RealmService realmService;

    @PostMapping("/createRealm")
    public void createRealm(@RequestBody Realm realm, @RequestHeader String Authorization) {
        String name = realm.getName();
        Boolean enabled = realm.getEnabled();
        Keycloak instance = Keycloak.getInstance(
                "http://" + System.getenv("HOST_KEY") + "" + ":" + "" + System.getenv("PORT_KEY") + "" + "/auth",
                System.getenv("REALM_KEY"), System.getenv("USER_KEY"), System.getenv("PASS_KEY"),
                System.getenv("CLIENT_KEY"), "password");
        RealmRepresentation realmRep = new RealmRepresentation();

        // try{
        // realmRep.setName(name);
        // realmRep.setEnabled(enabled);
        // // instance.realm("+System.getenv("REALM_KEY")+").realms().create(realmRep);
        // }catch(Exception erl){
        // System.out.println(erl);
        // }

    }

}