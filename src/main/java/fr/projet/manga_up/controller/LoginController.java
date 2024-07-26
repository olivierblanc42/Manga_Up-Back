package fr.projet.manga_up.controller;

import fr.projet.manga_up.model.User;
import fr.projet.manga_up.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDao userDao;

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

    @GetMapping("/user")
    public ResponseEntity<?> getUser(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            User user = userDao.findByUsername(authentication.getName());
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
            User admin = userDao.findByUsername(authentication.getName());
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
        private User user;

        public UserResponse(String message, User user) {
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

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }
    }
}
