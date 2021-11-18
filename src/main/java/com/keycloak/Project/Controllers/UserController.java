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

import com.keycloak.Project.Models.User;
import com.keycloak.Project.Repository.UserRepository;
import com.keycloak.Project.Services.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserRepository repository;

    @Autowired
    UserService userService;

    @RequestMapping(value = "/userK", method = RequestMethod.GET)
    public ResponseEntity<String> getUser(@RequestHeader String Authorization) {
        KeycloakAuthenticationToken authentication = (KeycloakAuthenticationToken) SecurityContextHolder.getContext()
                .getAuthentication();
        final Principal principal = (Principal) authentication.getPrincipal();
        System.out.println("entre con el usuario: " + principal);
        return ResponseEntity.ok("Hello " + principal);
    }

    @GetMapping("/viewUsers")
    public List<UserRepresentation> userss(@RequestHeader String Authorization) {
        List<UserRepresentation> lsUsersU = new ArrayList<UserRepresentation>();
        try {
            lsUsersU = userService.users();
            System.out.println("Lista de usuarios");
        } catch (Exception ev) {
            System.out.println(ev);
        }
        return lsUsersU;
    }

    @GetMapping("/viewUser/{id}")
    public UserRepresentation user(@RequestHeader String Authorization, @PathVariable String id) {
        UserRepresentation userU = new UserRepresentation();
        try {
            userU = userService.user(id);
            System.out.println("Usuario consultado");
        } catch (Exception eco) {
            System.out.println(eco);
            System.out.println("Usuario no encontrado");
        }
        return userU;
    }

    @GetMapping("/viewUser/{idUser}/{idClient}")
    public List<Map<String, String>> userRole(@PathVariable String idClient, @PathVariable String idUser,
            @RequestHeader String Authorization) {
        List<Map<String, String>> roleU = new ArrayList<Map<String, String>>();
        try {
            roleU = userService.rolesCli(idClient, idUser);
            System.out.println("Usuario consultado");
        } catch (Exception eco) {
            System.out.println(eco);
            System.out.println("Usuario no encontrado");
        }
        return roleU;
    }

    @GetMapping("/viewUser/{idUser}/{idClient}/available")
    public List<Map<String, String>> userRoleAvi(@PathVariable String idClient, @PathVariable String idUser,
            @RequestHeader String Authorization) {
        List<Map<String, String>> roleU = new ArrayList<Map<String, String>>();
        try {
            roleU = userService.rolesAvi(idClient, idUser);
            System.out.println("Usuario consultado");
        } catch (Exception eco) {
            System.out.println(eco);
            System.out.println("Usuario no encontrado");
        }
        return roleU;
    }

    @PostMapping("/createUser")
    @ResponseStatus(HttpStatus.CREATED)
    void createUser(@RequestBody User user, @RequestHeader String Authorization) {

        String creadoKey = "";
        String sendCredentials = "";
        List<String> attributes = user.getAttributes();

        try {
            creadoKey = userService.createUser(user, attributes);
            sendCredentials = userService.sendCredentials(user);
            // System.out.println(sendCredentials);
        } catch (Exception e) {
            System.out.println("no creado: " + e);
        }
        // return repository.save(user);
    }

    @PutMapping("/updateUser/{id}")
    public void updateUser(@RequestBody User user, @PathVariable String id, @RequestHeader String Authorization) {
        // List<Map<String, String>> rolesClient = user.getRolesClient();
        String upUser = "";
        try {
            upUser = userService.updateUser(id, user);// , rolesClient);
            System.out.println(upUser);
        } catch (Exception eu) {
            System.out.println("ERROR Controller");
            System.out.println(eu);
        }
    }

    @DeleteMapping("/deleteUser/{id}")
    public void deleteUser(@RequestHeader String Authorization, @PathVariable String id) {
        String deleteU = "";
        try {
            deleteU = userService.deleteUser(id);
            System.out.println(deleteU);
        } catch (Exception ed) {
            System.out.println(ed);
        }
    }

    @GetMapping("/viewUserName/{username}")
    public UserRepresentation userForName(@RequestHeader String Authorization, @PathVariable String username) {
        UserRepresentation userU = new UserRepresentation();
        try {
            userU = userService.userForUsername(username);
            System.out.println("Usuario consultado");
        } catch (Exception eco) {
            System.out.println(eco);
            System.out.println("Usuario no encontrado");
        }
        return userU;
    }

    @GetMapping("/sendRestoreEmail/{email}")
    public String sendRestoreEmail(@PathVariable String email) {
        UserRepresentation userE = new UserRepresentation();
        String id = "";
        // UserRepresentation restore = new UserRepresentation();

        try {
            userE = userService.userForEmail(email.toString());
            id = userE.getId().toString();
            Keycloak instance = userService.sendEmail(id.toString());
            if (userE.isEnabled() == false) {
                id = "inactivo";
            } else {
                if (id != "") {
                    id = "success";
                } else {
                    id ="";
                }
            }
        } catch (Exception eco) {
            // System.out.println(eco);
            System.out.println("Usuario no encontrado");
        }

        return id;
    }

    @GetMapping("/viewUserEmail/{email}")
    public UserRepresentation userForEmail(@RequestHeader String Authorization, @PathVariable String email) {
        System.out.println(email);
        UserRepresentation userE = new UserRepresentation();
        try {
            userE = userService.userForEmail(email.toString());
            System.out.println("Usuario consultado");
        } catch (Exception eco) {
            System.out.println(eco);
            System.out.println("Usuario no encontrado");
        }
        return userE;
    }

    @PostMapping("/logout/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    void logoutUser(@PathVariable String id) {

        String realm = System.getenv("REALM_KEY");
        try {
            Keycloak instance = userService.logoutUser(realm, id.toString());
        } catch (Exception e) {
            System.out.println("no creado: " + e);
        }
        
    }

}