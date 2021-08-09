package com.keycloak.Project.Services;

import com.keycloak.Project.Models.User;
import com.keycloak.Project.Repository.UserRepository;

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
import java.util.Collections;
import java.util.HashMap;

import org.keycloak.adapters.springsecurity.client.*;
import java.util.ArrayList;
//import org.keycloak.keycloak-admin-client.*
import org.keycloak.admin.client.*;
import org.keycloak.representations.idm.*;
import org.keycloak.protocol.oidc.*;
import org.keycloak.admin.client.token.*;
import org.keycloak.admin.client.token.TokenManager;
//import org.keycloak.representations.idm.UserRepresentation
import org.keycloak.admin.client.resource.UserResource;
import org.springframework.stereotype.Component;
import org.keycloak.models.AdminRoles;
// import org.keycloak.models.Constants;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;

@Component
public class UserService {
    public Keycloak instance() {
        Keycloak instanceU = Keycloak.getInstance("http://localhost" + ":" + "8080" + "/auth", "SpringBoot", "user1",
                "user1", "login", "password");
        return instanceU;
    }

    public Keycloak instanceT(String realm, String user, String password) {
        Keycloak instanceU = Keycloak.getInstance("http://localhost" + ":" + "8080" + "/auth", realm, user, password,
                "login", "password");
        return instanceU;
    }

    public List<UserRepresentation> users() {
        Keycloak instance = instance();
        List<UserRepresentation> lsUsersU = instance.realm("SpringBoot").users().search("");
        return lsUsersU;
    }

    public UserRepresentation user(String id) {
        Keycloak instance = instance();
        UserResource userUp = instance.realm("SpringBoot").users().get(id);
        UserRepresentation userU = userUp.toRepresentation();
        return userU;
    }

    public String createUser(User user) {

        String username = user.getUsername();
        String lastName = user.getLastName();
        String firstName = user.getFirstName();
        String email = user.getEmail();
        String pass = user.getPassword();
        String realm = user.getRealm();
        String group = user.getGroup();
        Boolean enabled = user.getEnabled();
        // System.out.println("userName: " + userName + "\n" + "lastName: " + lastName +
        // "\n" + "firstName: " + firstName
        // + "\n" + "email: " + email + "\n" + "Password: " + pass + "\n" + "Realm: " +
        // realm + "\n" + "group: "
        // + group + "\n" + "Enable: " + enable + "\n");
        System.out.println(user);
        Keycloak instance = instance();
        CredentialRepresentation credential = new CredentialRepresentation();
        // RealmResource realmResource = instance.realm(realm);
        // groupRepresentation realmgroup =
        // realmResource.groups().get(group).toRepresentation();
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(pass);
        credential.setTemporary(false);
        UserRepresentation userN = new UserRepresentation();
        userN.setUsername(username);
        userN.setFirstName(firstName);
        userN.setLastName(lastName);
        userN.setCredentials(Arrays.asList(credential));
        userN.setEnabled(enabled);
        // userN.setId(group);
        // userN.setGroups(Arrays.asList(group));
        // userN.setClientgroups(Collections.singletonMap(Constants.REALM_MANAGEMENT_CLIENT_ID,
        // Collections.singletonList(Admingroups.MANAGE_CLIENTS)));
        // List<String> groupls = new ArrayList<String>();
        // groupls.add(group);
        // System.out.println(groupls);
        // userN.setRealmgroups(Arrays.asList("user"));
        userN.setEmail(email);
        // System.out.println(userN);

        // Map<String, List<String>> clientRoles = new HashMap<>();
        // clientRoles.put("ClienteSmartCentral",
        // Collections.singletonList("role_userB2"));
        // userN.setClientRoles(clientRoles);
        instance.realm(realm).users().create(userN);

        UserRepresentation userU = instance.realm(realm).users().search(username).get(0);
        System.out.println("ID user: " + userU.getId());
        // instance.realm(realm).users().get(userU.getId()).update(userN);
        String idCliente = "ClienteSmartCentral";
        String idRoleC = "04c8d43c-894e-45d4-838c-d342166fd0d6";
        String nameR = "role_despachador";

        TokenManager tokenmanager = instance.tokenManager();
        String token = "Bearer\n" + tokenmanager.getAccessTokenString();
        String link = "http://localhost:8080/auth/admin/realms/" + realm + "/users/" + userU.getId()
                + "/role-mappings/clients/" + idCliente;
        String jsonInput = "[\n\t{\n\t\t\"id\":\"" + idRoleC + "\",\n\t\t\"name\":\"" + nameR
                + "\",\n\t\t\"containerId\":\"" + idCliente + "\"\n\t}\n]";

        StringEntity entity = new StringEntity(jsonInput, ContentType.APPLICATION_JSON);
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost request = new HttpPost(link);
        request.addHeader("Authorization", token);
        request.setEntity(entity);

        try {
            HttpResponse response = httpClient.execute(request);
            System.out.println("RESPONSE: " + response);
        } catch (Exception exh) {
            System.out.println(exh);
        }

        return "Usuario " + username + " Creado";
    }

    public String updateUser(String id, String username, String lastName, String firstName, String email, String pass,
            String realm, String group, Boolean enabled) {
        Keycloak instance = instance();
        UserResource userUp = instance.realm(realm).users().get(id);
        UserRepresentation userRep = userUp.toRepresentation();
        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(pass);
        credential.setTemporary(false);
        userRep.setUsername(username);
        userRep.setFirstName(firstName);
        userRep.setLastName(lastName);
        userRep.setCredentials(Arrays.asList(credential));
        userRep.setEnabled(enabled);
        userRep.setEmail(email);
        userRep.setGroups(Arrays.asList(group));
        userUp.update(userRep);

        return "Usuario " + username + " Actualizado";
    }

    public String deleteUser(String id) {
        Keycloak instance = instance();
        instance.realm("SpringBoot").users().get(id).remove();
        return "El usuario con el id: " + id + " se elimino correctamente.";

    }
}