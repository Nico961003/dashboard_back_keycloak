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
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;

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

    public List<Map<String, String>> rolesCli(String idClient) {
        Keycloak instance = instance();
        String idCliente = idClient;// "ClienteSmartCentral";
        String realm = "SpringBoot";
        String idUser = "3cc1542f-7cce-4cdc-93c3-3defe039dc94";
        String json = "";
        TokenManager tokenmanager = instance.tokenManager();
        String token = "Bearer\n" + tokenmanager.getAccessTokenString();
        String link = "http://localhost:8080/auth/admin/realms/" + realm + "/users/" + idUser
                + "/role-mappings/clients/" + idCliente + "/available";

        // String jsonInput = "[\n\t{\n\t\t\"id\":\"" + idRoleC + "\",\n\t\t\"name\":\""
        // + nameR
        // + "\",\n\t\t\"containerId\":\"" + idCliente + "\"\n\t}\n]";

        // StringEntity entity = new StringEntity(jsonInput,
        // ContentType.APPLICATION_JSON);
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(link);
        request.addHeader("Authorization", token);
        // request.setEntity(entity);
        JSONArray jsonArrPru = new JSONArray();
        List<Map<String, String>> rolesLS = new ArrayList<Map<String, String>>();

        try {
            HttpResponse response = httpClient.execute(request);
            InputStream inputStream = response.getEntity().getContent();
            // println inputStream
            System.out.println("RESPONSE: " + response);
            json = IOUtils.toString(inputStream, "UTF-8");
            // jsonTotal=json
            jsonArrPru = new JSONArray(json);
            for (int i = 0; i < jsonArrPru.length(); i++) {
                Map<String, String> dats = new HashMap<String, String>();
                String cad1 = jsonArrPru.getJSONObject(i).getString("name");
                String cad2 = jsonArrPru.getJSONObject(i).getString("description");
                String cad3 = jsonArrPru.getJSONObject(i).getString("id");
                String cad4 = jsonArrPru.getJSONObject(i).getString("containerId");
                System.out.println(cad1 + " " + cad2 + " " + cad3 + " " + cad4);
                dats.put("id", cad3);
                dats.put("name", cad1);
                dats.put("description", cad2);
                dats.put("containerId", cad4);
                rolesLS.add(dats);
            }
            // System.out.println("JSON: " + jsonArrPru);
        } catch (Exception exh) {
            System.out.println(exh);
        }
        return rolesLS;
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
