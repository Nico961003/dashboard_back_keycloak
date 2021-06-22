package com.back.dashboard.keycloak.servicesimpl;  

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.IDToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.back.dashboard.keycloak.model.User;
import com.back.dashboard.keycloak.services.UserService;

import com.back.dashboard.keycloak.model.User;
import com.back.dashboard.keycloak.services.UserService;

@Service
public class UserServiceImpl implements UserService {




   @Override
   public void createUser(User user) {
      System.out.println("usuario creado ");
   }

   public void prueba(){
      System.out.println("hola service implement");
   }

   @Override
   public void updateUser(String id, User user) {
      System.out.println("hola service implement");
   }

   @Override
   public void deleteUser(String id) {
      System.out.println("hola service implement");
   }

   @Override
   public Collection<User> getUsers() {
      System.out.println("hola service implement");
      return null;
   }


}