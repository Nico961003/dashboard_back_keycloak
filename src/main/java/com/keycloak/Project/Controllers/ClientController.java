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
import org.keycloak.admin.client.resource.*;
import com.keycloak.Project.Models.Client;
import com.keycloak.Project.Services.ClientService;
import com.keycloak.Project.Repository.ClientRepository;
// import com.keycloak.Project.Models.Client;

@RestController
@RequestMapping("/client")
public class ClientController {

    @Autowired
    private ClientRepository repository;

    @Autowired
    ClientService clientService;

    @GetMapping("/viewClients")
    List<ClientRepresentation> clients(@RequestHeader String Authorization) {
        List<ClientRepresentation> lsClients = new ArrayList<ClientRepresentation>();
        lsClients = clientService.clients();
        System.out.println("Se consulto la lista de clientes");
        return lsClients;
    }

    @GetMapping("/viewClient/{name}")
    ClientRepresentation client(@PathVariable String name, @RequestHeader String Authorization) {
        ClientRepresentation client = new ClientRepresentation();
        client = clientService.client(name);
        System.out.println("Se consulto al cliente " + name);
        return client;
    }

    @PostMapping("/createClient")
    @ResponseStatus(HttpStatus.CREATED)
    void createClient(@RequestBody Client client, @RequestHeader String Authorization) {
        String name = client.getName();
        String rootUrl = client.getRootUrl();
        String adminUrl = client.getAdminUrl();
        String realm = client.getRealm();
        Boolean enabled = client.getEnabled();
        String description = client.getDescription();
        String createC = "";
        try {
            createC = clientService.createClient(name, rootUrl, adminUrl, realm, enabled, description);
            System.out.println(createC);
        } catch (Exception ecl) {
            System.out.println("No se creo cliente");
            System.out.println(ecl);

        }

    }

    @DeleteMapping("/deleteClient/{name}")
    void deleteClient(@RequestHeader String Authorization, @PathVariable String name) {
        String deleteC = "";
        try {
            deleteC = clientService.deleteClient(name);
        } catch (Exception ede) {
            System.out.println(ede);
            deleteC = "No se borro";
        }
        System.out.println(deleteC);

    }

    @PutMapping("/updateClient/{name}")
    void updateClient(@RequestBody Client client, @RequestHeader String Authorization, @PathVariable String name) {
        String updateC = "";
        // String name = client.getName();
        String rootUrl = client.getRootUrl();
        String adminUrl = client.getAdminUrl();
        String realm = client.getRealm();
        Boolean enabled = client.getEnabled();
        String description = client.getDescription();

        try {
            updateC = clientService.updateClient(name, rootUrl, adminUrl, realm, enabled, description);

        } catch (Exception exup) {
            System.out.println(exup);
            updateC = "No se actualizo cliente";
        }
        System.out.println(updateC);
    }
}