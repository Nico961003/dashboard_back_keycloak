package com.keycloak.Project.Controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.security.RolesAllowed;

import java.io.IOException;
import java.security.Principal;

import org.apache.http.client.ClientProtocolException;
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

import com.keycloak.Project.Models.Group;
import com.keycloak.Project.Repository.GroupRepository;
import com.keycloak.Project.Services.GroupService;

@RestController
@RequestMapping("/group")
public class GroupController {

    @Autowired
    private GroupRepository repository;

    @Autowired
    GroupService groupService;

    @GetMapping("/groups")
    List<GroupRepresentation> groups(@RequestHeader String Authorization) {
        String realm = System.getenv("REALM_KEY");
        List<GroupRepresentation> grupos = new ArrayList<GroupRepresentation>();
        try {
            grupos = groupService.groups(realm);
        } catch (Exception ecg) {
            System.out.println(ecg);
        }
        return grupos;
    }

    @PostMapping("/createGroup")
    @ResponseStatus(HttpStatus.CREATED)
    void createGroup(@RequestBody Group group, @RequestHeader String Authorization) {
        System.out.println("GROUP: " + group);
        try {
            groupService.createGroup(group);
            System.out.println("Grupo " + group.getName() + " creado.");
        } catch (Exception ecg) {
            System.out.println("No se creo grupo\n" + ecg);
        }

    }

    @GetMapping("/group/{name}")
    GroupRepresentation group(@PathVariable String name, @RequestHeader String Authorization) {
        GroupRepresentation grupo = new GroupRepresentation();
        String realm = System.getenv("REALM_KEY");
        try {
            grupo = groupService.groupR(name, realm);
        } catch (Exception eg) {
            System.out.println(eg);
        }

        return grupo;
    }

}
