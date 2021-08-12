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
        Keycloak instanceU = Keycloak.getInstance(
                "http://" + System.getenv("HOST_KEY") + "" + ":" + "" + System.getenv("PORT_KEY") + "" + "/auth",
                System.getenv("REALM_KEY"), System.getenv("USER_KEY"), System.getenv("PASS_KEY"),
                System.getenv("CLIENT_KEY"), "password");
        return instanceU;
    }

    public void createRole(String roleName, String description, String realm, String idClient, List<String> attributes,
            List<String> status) {
        Keycloak instance = instance();
        RoleRepresentation roleR = new RoleRepresentation();
        roleR.setName(roleName);
        roleR.setDescription(description);
        roleR.setClientRole(true);
        // roleR.singleAttribute("consultSales", "false");
        Map<String, List<String>> atributos = new HashMap<>();
        // List<String> estado = status.get("value");
        // List<String> atribut = attributes.get("value");
        // System.out.println(atribut.size());
        for (int j = 0; j < attributes.size(); j++) {
            // System.out.println(atribut.get(j));
            String atr = attributes.get(j);
            String est = status.get(j);
            atributos.put(atr, Arrays.asList(est));
        }

        // System.out.println("ATRIBUTOS: " + attributes);

        roleR.setAttributes(atributos);
        // String clientId = "ClienteSmartCentral";
        ClientRepresentation clientRepresentation = new ClientRepresentation();
        try {
            clientRepresentation = instance.realm(realm).clients().findByClientId(idClient).get(0);
            System.out.println("cliente con id de: " + clientRepresentation.getId());
            instance.realm(realm).clients().get(clientRepresentation.getId()).roles().create(roleR);
            RoleResource roleResource = instance.realm(realm).clients().get(clientRepresentation.getId()).roles()
                    .get(roleName);
            roleResource.update(roleR);
        } catch (Exception exr) {
            System.out.println("No se asigno rol \n" + exr);
        }

        // instance.realm("+System.getenv("REALM_KEY")+").roles().create(roleR);
    }

    public List<RoleRepresentation> rolesC() {
        Keycloak instance = instance();
        List<RoleRepresentation> lsRoles = instance.realm(System.getenv("REALM_KEY")).roles().list();
        return lsRoles;
    }

    public RoleRepresentation roleC(String roleName) {
        Keycloak instance = instance();
        RoleRepresentation roleR = instance.realm(System.getenv("REALM_KEY")).roles().get(roleName).toRepresentation();
        return roleR;

    }

    public RoleRepresentation roleCliente(String idClient, String roleName) {
        Keycloak instance = instance();
        String realm = System.getenv("REALM_KEY");
        // String idClient = "ClienteSmartCentral";
        RoleResource roleResource = instance.realm(realm).clients().get(idClient).roles().get(roleName);
        RoleRepresentation roleR = roleResource.toRepresentation();
        // System.out.println("ROLE: \n" + roleR);
        return roleR;

    }

    public List<RoleRepresentation> rolesClientes(String idClient) {
        Keycloak instance = instance();
        String realm = System.getenv("REALM_KEY");
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
        String realm = System.getenv("REALM_KEY");
        instance.realm(realm).clients().get(idClient).roles().deleteRole(roleName);

    }

    public void deleteR(String roleName) {
        Keycloak instance = instance();
        instance.realm(System.getenv("REALM_KEY")).roles().get(roleName).remove();

    }

    public void updateRoleC(String roleName, String description, String idClient, List<String> attributes,
            List<String> status) {
        Keycloak instance = instance();
        String realm = System.getenv("REALM_KEY");
        RoleRepresentation roleR = new RoleRepresentation();
        roleR.setDescription(description);
        roleR.setClientRole(true);

        roleR.setName(roleName);
        Map<String, List<String>> atributos = new HashMap<>();
        // List<String> estado = status.get("value");
        // List<String> atribut = attributes.get("value");
        for (int j = 0; j < attributes.size(); j++) {
            String atr = attributes.get(j);
            String est = status.get(j);
            atributos.put(atr, Arrays.asList(est));
        }
        roleR.setAttributes(atributos);
        try {
            RoleResource roleResource = instance.realm(realm).clients().get(idClient).roles().get(roleName);
            roleResource.update(roleR);// update role
        } catch (Exception exup) {
            System.out.println("No se actualizo: " + exup);
        }
    }

}
