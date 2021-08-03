package com.keycloak.Project.Services;

import com.keycloak.Project.Models.Group;
import com.keycloak.Project.Repository.GroupRepository;

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
import org.springframework.stereotype.Component;
import org.keycloak.admin.client.resource.*;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import java.io.*;
import org.apache.commons.io.IOUtils;

import org.json.JSONArray;
import java.util.TimeZone;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Component
public class GroupService {
    public Keycloak instance() {
        Keycloak instanceU = Keycloak.getInstance("http://localhost" + ":" + "8080" + "/auth", "SpringBoot", "user1",
                "user1", "login", "password");
        return instanceU;
    }

    public void registerRoles(String realm, String idG, String tokenG, String idR, String nameR)
            throws ClientProtocolException, IOException {
        String token = "Bearer\n" + tokenG;
        System.out.println(token);
        String link = "http://localhost:8080/auth/admin/realms/" + realm + "/groups/" + idG + "/role-mappings/realm";
        System.out.println("LINK: " + link);
        String jsonInput = "[\n\t{\n\t\t\"id\":\"" + idR + "\",\n\t\t\"name\":\"" + nameR + "\"\n\t}\n]";
        System.out.println(jsonInput);
        StringEntity entity = new StringEntity(jsonInput, ContentType.APPLICATION_JSON);
        System.out.println("ENTITY:  " + entity);
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost request = new HttpPost(link);
        request.addHeader("Authorization", token);
        request.setEntity(entity);

        HttpResponse response = httpClient.execute(request);
        System.out.println("RESPONSE: " + response);
        // InputStream inputStream = response.getEntity().getContent();
    }

    public void createGroup(Group group) {
        Keycloak instance = instance();
        GroupRepresentation groupR = new GroupRepresentation();
        // group.setId(id);
        groupR.setName(group.getName());
        groupR.setPath(group.getName());
        // groupR.setRealmRoles(group.getRolesR());
        // List<String> roles = new ArrayList<String>();
        // roles.add("user");
        // System.out.println(roles);
        // groupR.setRealmRoles(roles);// group.getRolesR());
        // groupR.setClientRoles(Arrays.asList("user"));
        instance.realm("SpringBoot").groups().add(groupR);

        GroupRepresentation grupo = new GroupRepresentation();
        try {
            grupo = instance.realm("SpringBoot").getGroupByPath("/" + group.getName());
        } catch (Exception eg) {
            System.out.println(eg);
        }

        System.out.println(grupo.getId());
        String idG = grupo.getId();
        TokenManager tokenmanager = instance.tokenManager();
        String tokenG = tokenmanager.getAccessTokenString();
        String idR = group.getIdRole();// "f0e83c63-c882-498f-b511-32bc8a729100";
        String nameR = group.getNameRole();// "patron";
        String realm = group.getRealm();
        try {
            registerRoles(realm, idG, tokenG, idR, nameR);
            System.out.println("Se creo");
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public GroupRepresentation groupR(String name, String realm) {
        Keycloak instance = instance();
        GroupRepresentation grupo = new GroupRepresentation();
        try {
            grupo = instance.realm("SpringBoot").getGroupByPath("/" + name);
        } catch (Exception eg) {
            System.out.println(eg);
        }
        return grupo;
    }

    public List<GroupRepresentation> groups(String realm) {
        Keycloak instance = instance();
        List<GroupRepresentation> grupos = new ArrayList<GroupRepresentation>();
        try {
            grupos = instance.realm(realm).groups().groups();
        } catch (Exception eg) {
            System.out.println(eg);
        }

        return grupos;
    }

}
