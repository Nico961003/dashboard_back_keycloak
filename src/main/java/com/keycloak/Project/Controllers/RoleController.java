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

import com.keycloak.Project.Models.Role;
import com.keycloak.Project.Services.RoleService;
import com.keycloak.Project.Repository.RoleRepository;

@RestController
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    RoleService roleService;

    @PostMapping("/createRole")
    @ResponseStatus(HttpStatus.CREATED)
    ResponseEntity<String> createRole(@RequestBody Role role, @RequestHeader String Authorization) {
        String messagC = "";
        String roleName = role.getName();
        String description = role.getDescription();
        try {
            roleService.createRole(roleName, description);
            messagC = "Rol " + roleName + " Creado";
        } catch (Exception ecr) {
            System.out.println(ecr);
            messagC = "Rol " + roleName + " no creado\n" + ecr;
        }
        return ResponseEntity.ok(messagC);

    }

    @GetMapping("/roles")
    public List<RoleRepresentation> rolesC(@RequestHeader String Authorization) {

        List<RoleRepresentation> lsRoles = new ArrayList<RoleRepresentation>(); // instance.realm("SpringBoot").roles().list();
        try {
            lsRoles = roleService.rolesC();
        } catch (Exception els) {
            System.out.println(els);
        }
        return lsRoles;
    }

    @GetMapping("/role/{roleName}")
    RoleRepresentation roleC(@PathVariable String roleName, @RequestHeader String Authorization) {
        RoleRepresentation roleR = new RoleRepresentation();// instance.realm("SpringBoot").roles().get(roleName).toRepresentation();
        try {
            roleR = roleService.roleC(roleName);
        } catch (Exception ecro) {
            System.out.println(ecro);
        }
        return roleR;
    }

    @PutMapping("/updateRole/{roleName}")
    void updateRole(@RequestBody Role role, @RequestHeader String Authorization, @PathVariable String roleName) {

    }

    @DeleteMapping("/deleteRole/{roleName}")
    ResponseEntity<String> deleteRole(@PathVariable String roleName, @RequestHeader String Authorization) {
        // System.out.println("ROLE NAME: " + roleName);
        String messg = "";
        try {
            roleService.deleteR(roleName);
            messg = "Se borro";
        } catch (Exception edr) {
            System.out.println("No se borro\n" + edr);
            messg = "No se borro\n" + edr;

        }
        return ResponseEntity.ok(messg);

    }

}
