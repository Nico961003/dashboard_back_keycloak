package com.keycloak.Project.Services;

import com.keycloak.Project.Models.User;
import com.keycloak.Project.Repository.UserRepository;
import com.keycloak.Project.Services.RoleService;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
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

import javax.annotation.concurrent.NotThreadSafe;

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
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;

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
        // System.out.println("ID user: " + userU.getId());
        // instance.realm(realm).users().get(userU.getId()).update(userN);
        //////////////// put validation if idClient is null
        TokenManager tokenmanager = instance.tokenManager();
        String token = "Bearer\n" + tokenmanager.getAccessTokenString();
        List<Map<String, String>> rolesU = new ArrayList<Map<String, String>>();
        rolesU = user.getRolesClient();
        for (int k = 0; k < rolesU.size(); k++) {
            Map<String, String> rolesCli = new HashMap<String, String>();
            rolesCli = rolesU.get(k);
            String idCliente = rolesCli.get("idClient");// user.getIdClient();// "ClienteSmartCentral";
            String idRoleC = rolesCli.get("idRole");// user.getIdRole();// "04c8d43c-894e-45d4-838c-d342166fd0d6";
            String nameR = rolesCli.get("nameRole");// user.getNameRole();// "role_despachador";
            // System.out.println("ROLES => \n" + rolesU);
            // System.out.println("ROLE => \n" + rolesU.get(0));

            // System.out.println("idCliente => " + rolesCli.get("idClient"));
            // System.out.println("idRole => " + rolesCli.get("idRole"));
            // System.out.println("nameRole => " + rolesCli.get("nameRole"));

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
                // System.out.println("RESPONSE: " + response);
            } catch (Exception exh) {
                System.out.println(exh);
            }
        }

        return "Usuario " + username + " Creado";
    }

    public String updateUser(String id, User user) {// , List<Map<String, String>> rolesClient) {

        String username = user.getUsername();
        String lastName = user.getLastName();
        String firstName = user.getFirstName();
        String email = user.getEmail();
        // System.out.println("EMAIL: " + email);
        String pass = user.getPassword();
        String realm = user.getRealm();
        String group = user.getGroup();
        Boolean enabled = user.getEnabled();

        Keycloak instance = instance();
        UserResource userUp = instance.realm(realm).users().get(id);
        UserRepresentation userRep = userUp.toRepresentation();
        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(pass);
        credential.setTemporary(false);
        // userRep.setUsername(username);
        userRep.setFirstName(firstName);
        userRep.setLastName(lastName);
        userRep.setCredentials(Arrays.asList(credential));
        userRep.setEnabled(enabled);
        userRep.setEmail(email);
        // userRep.setGroups(Arrays.asList(group));
        userUp.update(userRep);

        TokenManager tokenmanager = instance.tokenManager();
        String token = "Bearer\n" + tokenmanager.getAccessTokenString();
        Map<String, List<String>> rolesUserO = new HashMap<String, List<String>>();
        try {
            rolesUserO = userRep.getClientRoles();
            // System.out.println("ROLES: " + rolesUserO);
        } catch (Exception exr) {
            System.out.println(exr);
        }
        String idUser = id;
        String idClient = "ClienteSmartCentral";
        List<Map<String, String>> rolesClient = rolesCli(idClient, idUser);
        // System.out.println("ROLES_ACTUALES: " + rolesClient);

        for (int k = 0; k < rolesClient.size(); k++) {
            Map<String, String> rolesCli = new HashMap<String, String>();
            rolesCli = rolesClient.get(k);
            String idCliente = rolesCli.get("containerId");// user.getIdClient();// "ClienteSmartCentral";
            String idRoleC = rolesCli.get("id");// user.getIdRole();// "04c8d43c-894e-45d4-838c-d342166fd0d6";
            String nameR = rolesCli.get("name");// user.getNameRole();// "role_despachador";
            // System.out.println(
            // "idCliente : " + idCliente + "\n" + "idRole : " + idRoleC + "\n" + "nameR : "
            // + nameR + "\n");
            String link = "http://localhost:8080/auth/admin/realms/" + realm + "/users/" + id
                    + "/role-mappings/clients/" + idCliente;
            String jsonInput = "[\n\t{\n\t\t\"id\":\"" + idRoleC + "\",\n\t\t\"name\":\"" + nameR
                    + "\",\n\t\t\"containerId\":\"" + idCliente + "\"\n\t}\n]";

            StringEntity entity = new StringEntity(jsonInput, ContentType.APPLICATION_JSON);
            // String[] restResponse = new String[2];

            try {
                CloseableHttpClient httpclient = HttpClients.createDefault();
                HttpDeleteWithBody httpDelete = new HttpDeleteWithBody(link);
                httpDelete.addHeader("Authorization", token);
                httpDelete.setEntity(entity);
                Header requestHeaders[] = httpDelete.getAllHeaders();
                CloseableHttpResponse response = httpclient.execute(httpDelete);
                // restResponse[0] =
                // Integer.toString((response.getStatusLine().getStatusCode()));
                // restResponse[1] = EntityUtils.toString(response.getEntity());
                // System.out.println("RESPONSE: " + restResponse);
                // System.out.println((response.getStatusLine().getStatusCode()));
            } catch (Exception exh) {
                // System.out.println("XXDD");
                System.out.println(exh);
            }
        }

        List<Map<String, String>> rolesClientN = user.getRolesClient();

        for (int k = 0; k < rolesClientN.size(); k++) {
            Map<String, String> rolesCli = new HashMap<String, String>();
            rolesCli = rolesClientN.get(k);
            String idCliente = rolesCli.get("idClient");// user.getIdClient();// "ClienteSmartCentral";
            String idRoleC = rolesCli.get("idRole");// user.getIdRole();// "04c8d43c-894e-45d4-838c-d342166fd0d6";
            String nameR = rolesCli.get("nameRole");// user.getNameRole();// "role_despachador";

            String link = "http://localhost:8080/auth/admin/realms/" + realm + "/users/" + id
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
                // System.out.println("RESPONSE: " + response);
            } catch (Exception exh) {
                // System.out.println("XXDD");
                System.out.println(exh);
            }
        }

        String usernameB = userRep.getUsername();
        return "Usuario " + usernameB + " Actualizado";
    }

    public String deleteUser(String id) {
        Keycloak instance = instance();
        instance.realm("SpringBoot").users().get(id).remove();
        return "El usuario con el id: " + id + " se elimino correctamente.";

    }

    public List<Map<String, String>> rolesCli(String idClient, String idUser) {
        Keycloak instance = instance();
        String idCliente = idClient;// "ClienteSmartCentral";
        String realm = "SpringBoot";
        // String idUser = "3cc1542f-7cce-4cdc-93c3-3defe039dc94";
        String json = "";
        TokenManager tokenmanager = instance.tokenManager();
        String token = "Bearer\n" + tokenmanager.getAccessTokenString();
        String link = "http://localhost:8080/auth/admin/realms/" + realm + "/users/" + idUser
                + "/role-mappings/clients/" + idCliente /* + "/available" */;

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

    public List<Map<String, String>> rolesAvi(String idClient, String idUser) {
        Keycloak instance = instance();
        String idCliente = idClient;// "ClienteSmartCentral";
        String realm = "SpringBoot";
        // String idUser = "3cc1542f-7cce-4cdc-93c3-3defe039dc94";
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

    public List<RoleRepresentation> rolesClientes(String idClient) {
        Keycloak instance = instance();
        String realm = "SpringBoot";
        List<RoleRepresentation> rolesR = instance.realm(realm).clients().get(idClient).roles().list(); // roleResource.toRepresentation();
        return rolesR;

    }
}

@NotThreadSafe
class HttpDeleteWithBody extends HttpEntityEnclosingRequestBase {
    public static final String METHOD_NAME = "DELETE";

    public String getMethod() {
        return METHOD_NAME;
    }

    public HttpDeleteWithBody(final String uri) {
        super();
        setURI(URI.create(uri));
    }

    public HttpDeleteWithBody(final URI uri) {
        super();
        setURI(uri);
    }

    public HttpDeleteWithBody() {
        super();
    }
}