package fr.projet.manga_up.controller;

import fr.projet.manga_up.dto.RegisterDTO;
import fr.projet.manga_up.dto.UserDto;
import fr.projet.manga_up.model.AppUser;
import fr.projet.manga_up.dao.UserDao;
import fr.projet.manga_up.service.AccountServiceImpl;
import fr.projet.manga_up.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class AuthController {
    private static final Logger LOGGER= LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private AccountServiceImpl accountServiceImpl;

    @Autowired
    private UserDao userDao;
    @Autowired
    private UserService userService;

    @Operation(summary = "Enregistrement d'un nouvelle utilisateur.")
    @ApiResponse(responseCode = "201", description = "Un nouvelle utilisateur à été enregistré avec succè !")
    @PostMapping(value = "/register", consumes ={MediaType.APPLICATION_JSON_VALUE}, produces={MediaType
            .APPLICATION_JSON_VALUE})
    public ResponseEntity<?> register(@RequestBody RegisterDTO registerDTO){
        LOGGER.info("Méthode register enregistrement de l'utilisateur : {}", registerDTO);
        LOGGER.info("Méthode register email: {}", registerDTO.getEmail());

        Map<String, Object> response = new HashMap<>();
        Map<String, String> messageError=new LinkedHashMap<>();
        AppUser uPerUsername = accountServiceImpl.loadUserByUsername(registerDTO.getUsername());
        AppUser uPerEmail = accountServiceImpl.loadUserByEmail(registerDTO.getEmail());

        if(uPerUsername != null){
            messageError.put("username", registerDTO.getUsername());
        }
        if(uPerEmail != null){
            messageError.put("email", registerDTO.getEmail());
        }

        if( ! messageError.isEmpty()){
            response.put("success", false);
            response.put("message", messageError);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

        LOGGER.info("Après saveUserDto");
        response.put("success", true);
        response.put("message", "Vous êtes enregistré !");
        response.put("user", userService.saveUserDtoRegister(registerDTO));
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Authentification de l'utilisateur ")
    @ApiResponse(responseCode = "200", description = "Un utilisateur c'est authentifié avec succès !")
    @PostMapping(value="/users/login", consumes ={MediaType.APPLICATION_JSON_VALUE}, produces={MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> authenticateUser(@RequestBody AppUser appUser) {
        Map<String, Object> response = new HashMap<>();
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                        appUser.getUsername(),
                        appUser.getPassword())
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String authorities=authentication.getAuthorities()
                    .stream().map(GrantedAuthority::getAuthority)
                    .collect(Collectors.joining(" "));
            LOGGER.info("authorities : {}", authorities);
            return ResponseEntity.ok(userDao.findByUsername(authentication.getName()));
            //return ResponseEntity.ok(new LoginResponse("Authenticated successfully", HttpStatus.OK));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new LoginResponse("Invalid credentials", HttpStatus.UNAUTHORIZED));
        }
    }

/*
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            return ResponseEntity.ok(new LoginResponse("Authenticated successfully", HttpStatus.OK));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new LoginResponse("Invalid credentials", HttpStatus.UNAUTHORIZED));
        }
    }
*/
    @GetMapping("/user")
    public ResponseEntity<?> getUser(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            AppUser user = userDao.findByUsername(authentication.getName());
            if (user != null) {
                return ResponseEntity.ok(new UserResponse("Welcome, User", user));
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new UserResponse("Unauthorized", null));
    }

    @GetMapping("/admin")
    public ResponseEntity<?> getAdmin(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated() && authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"))) {
            AppUser admin = userDao.findByUsername(authentication.getName());
            if (admin != null) {
                return ResponseEntity.ok(new UserResponse("Welcome, Admin", admin));
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new UserResponse("Unauthorized", null));
    }

    // Classes pour les requêtes et les réponses
    public static class LoginRequest {
        private String username;
        private String password;

        // getters and setters
        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    public static class LoginResponse {
        private String message;
        private HttpStatus status;

        public LoginResponse(String message, HttpStatus status) {
            this.message = message;
            this.status = status;
        }

        // getters and setters
        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public HttpStatus getStatus() {
            return status;
        }

        public void setStatus(HttpStatus status) {
            this.status = status;
        }
    }

    public static class UserResponse {
        private String message;
        private AppUser user;

        public UserResponse(String message, AppUser user) {
            this.message = message;
            this.user = user;
        }

        // getters and setters
        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public AppUser getUser() {
            return user;
        }

        public void setUser(AppUser user) {
            this.user = user;
        }

    }
}
