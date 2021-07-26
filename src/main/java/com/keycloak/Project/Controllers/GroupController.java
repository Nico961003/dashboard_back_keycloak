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

import com.keycloak.Project.Models.Group;
import com.keycloak.Project.Repository.GroupRepository;
import com.keycloak.Project.Services.GroupService;

@RestController
@RequestMapping("/group")
public class GroupController {

    @GetMapping("/groups")
    List<GroupRepresentation> groups() {
        Keycloak instance = Keycloak.getInstance("http://localhost" + ":" + "8080" + "/auth", "SpringBoot", "user1",
                "user1", "login", "password");
        List<GroupRepresentation> grupos = new ArrayList<GroupRepresentation>();
        try {
            grupos = instance.realm("SpringBoot").groups().groups();
        } catch (Exception eg) {
            System.out.println(eg);
        }

        return grupos;
    }

    @PostMapping("/createGroup")
    @ResponseStatus(HttpStatus.CREATED)
    void createGroup(@RequestBody Group group) {
        System.out.println("GROUP: " + group);
        Keycloak instance = Keycloak.getInstance("http://localhost" + ":" + "8080" + "/auth", "SpringBoot", "user1",
                "user1", "login", "password");
        GroupRepresentation groupR = new GroupRepresentation();

        // group.setId(id);
        groupR.setName(group.getName());
        groupR.setPath(group.getPath());
        groupR.setRealmRoles(group.getRolesR());
        instance.realm("SpringBoot").groups().add(groupR);

    }

}
