package com.keycloak.Project.Services;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.security.RolesAllowed;

import java.io.InputStream;
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
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Collections;

import org.keycloak.adapters.springsecurity.client.*;
//import org.keycloak.keycloak-admin-client.*
import org.keycloak.admin.client.*;
import org.keycloak.representations.idm.*;
import org.keycloak.protocol.oidc.*;
import org.keycloak.admin.client.token.*;
import org.keycloak.admin.client.token.TokenManager;
import org.keycloak.admin.client.resource.RoleResource;
//import org.keycloak.representations.idm.UserRepresentation
import org.keycloak.admin.client.resource.UserResource;

import com.keycloak.Project.Models.Role;
// import com.keycloak.Project.Services.RoleService;
import com.keycloak.Project.Repository.RoleRepository;
import org.springframework.stereotype.Component;

@Component
public class RoleService {

    public Keycloak instance() {
        Keycloak instanceU = Keycloak.getInstance("http://localhost" + ":" + "8080" + "/auth", "SpringBoot", "user1",
                "user1", "login", "password");
        return instanceU;
    }

    public void createRole(String roleName, String description, String realm, String idClient,
            Map<String, List<String>> attributes) {
        Keycloak instance = instance();
        RoleRepresentation roleR = new RoleRepresentation();
        roleR.setName(roleName);
        roleR.setDescription(description);
        roleR.setClientRole(true);
        // roleR.singleAttribute("consultSales", "false");
        // Map<String, List<String>> attributes = new HashMap<>();
        // attributes.put("consultPays", Collections.singletonList("true"));
        // attributes.put("consultSales", Collections.singletonList("false"));
        System.out.println("ATRIBUTOS: " + attributes);

        roleR.setAttributes(attributes);
        // String clientId = "ClienteSmartCentral";
        ClientRepresentation clientRepresentation = new ClientRepresentation();
        try {
            clientRepresentation = instance.realm(realm).clients().findByClientId(idClient).get(0);
            System.out.println("cliente con id de: " + clientRepresentation.getId());
            instance.realm(realm).clients().get(clientRepresentation.getId()).roles().create(roleR);
            RoleResource roleResource = instance.realm(realm).clients().get(idClient).roles().get(roleName);
            roleResource.update(roleR);
        } catch (Exception exr) {
            System.out.println("No se asigno rol \n" + exr);
        }

        // instance.realm("SpringBoot").roles().create(roleR);
    }

    public List<RoleRepresentation> rolesC() {
        Keycloak instance = instance();
        List<RoleRepresentation> lsRoles = instance.realm("SpringBoot").roles().list();
        return lsRoles;
    }

    public RoleRepresentation roleC(String roleName) {
        Keycloak instance = instance();
        RoleRepresentation roleR = instance.realm("SpringBoot").roles().get(roleName).toRepresentation();
        return roleR;

    }

    public RoleRepresentation roleCliente(String idClient, String roleName) {
        Keycloak instance = instance();
        String realm = "SpringBoot";
        // String idClient = "ClienteSmartCentral";
        RoleResource roleResource = instance.realm(realm).clients().get(idClient).roles().get(roleName);
        RoleRepresentation roleR = roleResource.toRepresentation();
        System.out.println("ROLE: \n" + roleR);
        return roleR;

    }

    public List<RoleRepresentation> rolesClientes(String idClient) {
        Keycloak instance = instance();
        String realm = "SpringBoot";
        // String idClient = "ClienteSmartCentral";
        // RoleResource roleResource =
        // instance.realm(realm).clients().get(idClient).roles().list();
        List<RoleRepresentation> rolesR = instance.realm(realm).clients().get(idClient).roles().list(); // roleResource.toRepresentation();
        // System.out.println("ROLEs: \n" + rolesR);

        // instance.realm(realm).clients().get(idClient).roles().deleteRole(roleName);//delete
        // role
        // RoleResource roleResource =
        // instance.realm(realm).clients().get(idClient).roles().get(roleName);
        // roleResource.update(roleR);//update role
        return rolesR;

    }

    public void deleteRC(String idClient, String roleName) {
        Keycloak instance = instance();
        String realm = "SpringBoot";
        instance.realm(realm).clients().get(idClient).roles().deleteRole(roleName);

    }

    public void deleteR(String roleName) {
        Keycloak instance = instance();
        instance.realm("SpringBoot").roles().get(roleName).remove();

    }

    public void updateRoleC(String roleName, String description, String idClient,
            Map<String, List<String>> attributes) {
        Keycloak instance = instance();
        String realm = "SpringBoot";
        RoleRepresentation roleR = new RoleRepresentation();
        roleR.setDescription(description);
        roleR.setClientRole(true);
        roleR.setAttributes(attributes);
        roleR.setName(roleName);
        try {
            RoleResource roleResource = instance.realm(realm).clients().get(idClient).roles().get(roleName);
            roleResource.update(roleR);// update role
        } catch (Exception exup) {
            System.out.println("No se actualizo: " + exup);
        }
    }

}
