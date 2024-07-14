package fr.projet.manga_up.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import fr.projet.manga_up.model.Manga;
import fr.projet.manga_up.model.User;
import fr.projet.manga_up.service.MangaService;
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
}