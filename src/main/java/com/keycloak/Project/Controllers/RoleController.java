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

import org.json.JSONArray;

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
        String realm = role.getRealm();
        String idClient = role.getIdClient();
        Map<String, List<String>> attributes = role.getAttributes();
        Map<String, List<String>> status = role.getStatus();
        // Map<String, List<String>> attributes = role.getAttributes();
        System.out.println(role);
        try {
            roleService.createRole(roleName, description, realm, idClient, attributes, status);
            messagC = "Rol " + roleName + " Creado";
        } catch (Exception ecr) {
            System.out.println(ecr);
            messagC = "Rol " + roleName + " no creado\n" + ecr;
        }
        return ResponseEntity.ok(messagC);

    }

    @PutMapping("/rolesC/updateRoleC/{idClient}/{roleName}")
    ResponseEntity<String> updateRole(@RequestBody Role role, @PathVariable String idClient,
            @PathVariable String roleName) {// , @RequestHeader String Authorization) {
        String messagC = "";
        // String roleName = role.getName();
        String description = role.getDescription();
        String realm = role.getRealm();
        // String idClient = role.getIdClient();
        // Map<String, List<String>> attributes = role.getAttributes();
        Map<String, List<String>> attributes = role.getAttributes();
        Map<String, List<String>> status = role.getStatus();
        System.out.println(role);
        try {
            roleService.updateRoleC(roleName, description, idClient, attributes, status);
            messagC = "Rol " + roleName + " actualizado";
        } catch (Exception ecr) {
            System.out.println(ecr);
            messagC = "Rol " + roleName + " no actualizado\n" + ecr;
        }
        return ResponseEntity.ok(messagC);
    }

    @GetMapping("/roles")
    public List<RoleRepresentation> rolesC(@RequestHeader String Authorization) {

        List<RoleRepresentation> lsRoles = new ArrayList<RoleRepresentation>(); // instance.realm("+System.getenv("REALM_KEY")+").roles().list();
        try {
            lsRoles = roleService.rolesC();
        } catch (Exception els) {
            System.out.println(els);
        }
        return lsRoles;
    }

    // @GetMapping("/rolesC/{idClient}")
    // public List<Map<String, String>> rolesCli(@RequestHeader String
    // Authorization, @PathVariable String idClient) {

    // // List<RoleRepresentation> lsRoles = new ArrayList<RoleRepresentation>(); //
    // // instance.realm("+System.getenv("REALM_KEY")+").roles().list();
    // // JSONArray jsonArr = new JSONArray();
    // List<Map<String, String>> jsonArr = new ArrayList<Map<String, String>>();
    // try {
    // jsonArr = roleService.rolesCli(idClient);
    // } catch (Exception els) {
    // System.out.println(els);
    // }
    // // System.out.println("JSONArray: " + jsonArr + "\nXD");
    // return jsonArr;
    // }

    @GetMapping("/rolesC/{idClient}")
    public List<RoleRepresentation> rolesCli(@PathVariable String idClient, @RequestHeader String Authorization) {

        // List<RoleRepresentation> lsRoles = new ArrayList<RoleRepresentation>(); //
        // instance.realm("+System.getenv("REALM_KEY")+").roles().list();
        // JSONArray jsonArr = new JSONArray();
        List<RoleRepresentation> rolesC = new ArrayList<RoleRepresentation>();
        try {
            rolesC = roleService.rolesClientes(idClient);
        } catch (Exception els) {
            System.out.println(els);
        }
        // System.out.println("JSONArray: " + jsonArr + "\nXD");
        return rolesC;
    }

    @GetMapping("/rolesC/{idClient}/{roleName}")
    public RoleRepresentation rolesCliente(@PathVariable String idClient, @PathVariable String roleName,
            @RequestHeader String Authorization) {

        // List<RoleRepresentation> lsRoles = new ArrayList<RoleRepresentation>(); //
        // instance.realm("+System.getenv("REALM_KEY")+").roles().list();
        // JSONArray jsonArr = new JSONArray();
        // List<Map<String, String>> jsonArr = new ArrayList<Map<String, String>>();
        RoleRepresentation roleR = new RoleRepresentation();
        try {
            roleR = roleService.roleCliente(idClient, roleName);
        } catch (Exception els) {
            System.out.println(els);
        }
        // System.out.println("JSONArray: " + jsonArr + "\nXD");
        return roleR;
    }

    @GetMapping("/role/{roleName}")
    RoleRepresentation roleC(@PathVariable String roleName, @RequestHeader String Authorization) {
        RoleRepresentation roleR = new RoleRepresentation();// instance.realm("+System.getenv("REALM_KEY")+").roles().get(roleName).toRepresentation();
        try {
            roleR = roleService.roleC(roleName);
        } catch (Exception ecro) {
            System.out.println(ecro);
        }
        return roleR;
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

    @DeleteMapping("/deleteRoleC/{idClient}/{roleName}")
    ResponseEntity<String> deleteRoleCliente(@PathVariable String idClient, @PathVariable String roleName,
            @RequestHeader String Authorization) {
        // System.out.println("ROLE NAME: " + roleName);
        String messg = "";
        try {
            roleService.deleteRC(idClient, roleName);
            messg = "Se borro";
        } catch (Exception edr) {
            System.out.println("No se borro\n" + edr);
            messg = "No se borro\n" + edr;

        }
        return ResponseEntity.ok(messg);

    }

}
