package com.keycloak.Project.Services;

import com.keycloak.Project.Models.Client;
import com.keycloak.Project.Repository.ClientRepository;

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

@Component
public class ClientService {
    public Keycloak instance() {
        Keycloak instanceU = Keycloak.getInstance(
                System.getenv("HOST_KEY") + "/auth",
                System.getenv("REALM_KEY"), System.getenv("USER_KEY"), System.getenv("PASS_KEY"),
                System.getenv("CLIENT_KEY"), "password");
        return instanceU;
    }

    public List<ClientRepresentation> clients() {
        Keycloak instance = instance();
        List<ClientRepresentation> lsClientes = instance.realm(System.getenv("REALM_KEY")).clients().findAll();
        return lsClientes;
    }

    public ClientRepresentation client(String name) {
        Keycloak instance = instance();
        ClientRepresentation cliente = instance.realm(System.getenv("REALM_KEY")).clients().findByClientId(name).get(0);
        return cliente;
    }

    public String createClient(String name, String rootUrl, String adminUrl, String realm, Boolean enabled,
            String description) {
        System.out.println(name + " " + rootUrl + " " + adminUrl + " " + realm);
        Keycloak instance = instance();
        ClientRepresentation cliente = new ClientRepresentation();
        String mess = "";
        try {
            cliente.setId(name);
            cliente.setName(name);
            cliente.setBearerOnly(false);
            cliente.setPublicClient(false);
            // cliente.setSecret("******");
            cliente.setProtocol("openid-connect");
            List<String> redirectUris = new ArrayList<String>();
            List<String> webUris = new ArrayList<String>();
            webUris.add(adminUrl);
            cliente.setWebOrigins(webUris);
            redirectUris.add(adminUrl + "/*");
            cliente.setRedirectUris(redirectUris);
            cliente.setRootUrl(rootUrl);
            cliente.setAdminUrl(adminUrl);
            cliente.setEnabled(enabled);
            cliente.setDescription(description);
            // cliente.setDefaultRoles(new String[] { "foo-role" });///creacion de roles a
            // nivel cliente
            // cliente.setBaseUrl("");

            // realmResource.clients().create(cliente);
            instance.realm(realm).clients().create(cliente);
            mess = "Cliente " + name + " creado";
        } catch (Exception ecl) {
            System.out.println(ecl);
            mess = "Cliente no creado";
        }

        return mess;
    }

    public String deleteClient(String name) {
        String borrarCliente = "";
        Keycloak instance = instance();
        try {

            instance.realm(System.getenv("REALM_KEY")).clients().get(name).remove();

            borrarCliente = "Se borro el cliente " + name;
        } catch (Exception ed) {
            System.out.println(ed);
            borrarCliente = "No se borro cliente";
        }
        return borrarCliente;
    }

    public String updateClient(String name, String rootUrl, String adminUrl, String realm, Boolean enabled,
            String description, String nameC) {

        String updateC = "";
        Keycloak instance = instance();
        try {
            ClientRepresentation cliente = instance.realm(System.getenv("REALM_KEY")).clients().findByClientId(name)
                    .get(0);

            cliente.setName(nameC);
            // cliente.setId(nameC);
            cliente.setBearerOnly(false);
            cliente.setPublicClient(false);
            // cliente.setSecret("******");
            cliente.setProtocol("openid-connect");
            List<String> redirectUris = new ArrayList<String>();
            List<String> webUris = new ArrayList<String>();
            webUris.add(adminUrl);
            cliente.setWebOrigins(webUris);
            redirectUris.add(adminUrl + "/*");
            cliente.setRedirectUris(redirectUris);
            cliente.setRootUrl(rootUrl);
            cliente.setAdminUrl(adminUrl);
            cliente.setEnabled(enabled);
            cliente.setDescription(description);
            // cliente.setBaseUrl("");
            instance.realm(realm).clients().get(name).update(cliente);

            updateC = "Se actualizo el cliente " + nameC;
        } catch (Exception eup) {
            System.out.println(eup);
            updateC = "No se actualizo cliente";
        }
        return updateC;
    }

}