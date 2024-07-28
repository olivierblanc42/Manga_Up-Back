package fr.projet.manga_up.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import fr.projet.manga_up.model.Manga;
import fr.projet.manga_up.model.User;
import fr.projet.manga_up.service.MangaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import fr.projet.manga_up.service.UserService;

import java.lang.runtime.ObjectMethods;
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

    @Operation(summary = "Récupère un user avec l'id'", description = "Retourne un user ")
    @ApiResponse(responseCode = "201", description = "Un nouveau user est enregistré avec succès")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getUser(@PathVariable("id") Integer id){
        LOGGER.info("getUser récupération de l'utilisateur par son id : {}", id);
        User user=userService.getUser(id);
        List<Integer> mangasId=userService.getAllMangaByUserId(id);
        LOGGER.info("User : {}", user);
        LOGGER.info("Liste des id des mangas : {}", mangasId);
        Map<String, Object> response = new HashMap<>();
        response.put("user", user);
        response.put("mangasId", mangasId);
        return ResponseEntity.ok(response);
    }
    /*
    * récupérer tout les users
    * */
    @GetMapping()
        public ResponseEntity<List<User>> getUsers() {
            LOGGER.info("list de tout les utilisateurs");
            List<User> users=userService.getAllUsers();
            return ResponseEntity.ok(users);
        }









}