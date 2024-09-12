package fr.projet.manga_up.controller;

import fr.projet.manga_up.dao.UserDao;
import fr.projet.manga_up.dto.UserDto;
import fr.projet.manga_up.model.Genre;
import fr.projet.manga_up.model.AppUser;
import fr.projet.manga_up.service.MangaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import fr.projet.manga_up.service.UserService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins="*")
@RestController
@RequestMapping("/api/users")
public class UserController {

    private static final Logger LOGGER= LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private MangaService mangaService;
    @Autowired
    private UserDao userDao;

    @Autowired
    private AuthenticationManager authenticationManager;


    @Operation(summary = "Récupère un user avec l'id'", description = "Retourne un user ")
    @ApiResponse(responseCode = "201", description = "Un nouveau user est enregistré avec succès")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getUser(@PathVariable("id") Integer id){
        LOGGER.info("getUser récupération de l'utilisateur par son id : {}", id);
        AppUser user=userService.getUser(id);
        List<Integer> mangasId=userService.getAllMangaByUserId(id);
        LOGGER.info("User : {}", user);
        LOGGER.info("Liste des id des mangas : {}", mangasId);
        Map<String, Object> response = new HashMap<>();
        response.put("user", user);
        response.put("mangasId", mangasId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Récupére tout les utilisateur")
    @GetMapping()
    public ResponseEntity<List<AppUser>> getUsers() {
        LOGGER.info("list de tout les utilisateurs");
        List<AppUser> users=userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

   /* @Operation(summary = "Creation d'un nouveau utilisateur ")
    @ApiResponse(responseCode = "201", description = "un nouveau utilisateur a été cre avec succès ")
    @PostMapping
    public User saveUser(@RequestBody User user){
        return userService.createUser(user);
    }*/


    @Operation(summary = "Creation d'un nouveau utilisateur ")
    @ApiResponse(responseCode = "201", description = "un nouveau utilisateur a été cre avec succès ")
    @PostMapping
    public ResponseEntity<UserDto> saveUser(@RequestBody UserDto userDto) {
        LOGGER.info("createAuthor : {}", userDto);
        UserDto createdUser = userService.saveUserDto(userDto);
        return ResponseEntity.ok(createdUser);
    }
/*
    @Operation(summary = "Login d'un utilisateur ")
    @ApiResponse(responseCode = "200", description = "Vous êtes authentifié ! ")
    @PostMapping("/login")
    public ResponseEntity<UserDto> loginUser(@RequestBody UserDto userDto) {
        LOGGER.info("authentication user : {}", userDto);
        authenticationManager.authenticate();
        UserDto user = userService.getUser();
        return ResponseEntity.ok(user);
    }
*/
    @Operation(summary = "supprime un utilisateur")
    @DeleteMapping("/{id}")
    public ResponseEntity<Genre> deleteUserById(@PathVariable("id")Integer id) {
        LOGGER.info("Suppression du genre" + id);

        userService.deleteUserById(id);
        return ResponseEntity.ok().build();
    }

}