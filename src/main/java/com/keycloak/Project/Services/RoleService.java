package com.keycloak.Project.Services;

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

    public void createRole(String roleName) {
        Keycloak instance = instance();
        RoleRepresentation roleR = new RoleRepresentation();
        roleR.setName(roleName);
        instance.realm("SpringBoot").roles().create(roleR);
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

    public void deleteR(String roleName) {
        Keycloak instance = instance();
        instance.realm("SpringBoot").roles().get(roleName).remove();

    }

}
