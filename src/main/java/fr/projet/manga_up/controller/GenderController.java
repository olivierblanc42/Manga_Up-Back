package fr.projet.manga_up.controller;


import fr.projet.manga_up.model.Gender;
import fr.projet.manga_up.service.GenderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/genders")
public class GenderController {
    private static final Logger LOGGER= LoggerFactory.getLogger(MangaController.class);

    @Autowired
private GenderService genderService;

    @GetMapping()
    public ResponseEntity<List<Gender>> getAllGenders(){
        LOGGER.info("Récupération de la liste des genres des users");
        List<Gender> genders = genderService.getAllGenders();
        LOGGER.info("Mangas : {}", genders);
        return ResponseEntity.ok(genders);
    }

    @GetMapping(value = "/{id}",produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Gender> getGenderById(@PathVariable int id){
        LOGGER.info("recuperation d'un genre d'utilisateur avec son id");
        Gender gender = genderService.getGender(id);
        LOGGER.info("author : {}", gender);
        return ResponseEntity.ok(gender);
    }

  /*  @Operation(summary = "Sauvegarde  de genre pour utilisateur")
    @ApiResponse(responseCode = "201", description = "Des nouveaus mangas sont enregistrés avec succès")
    @PostMapping
    public Gender saveGender(@RequestBody Gender gender){
        return genderService.saveGender(gender);
    }*/


    @DeleteMapping("/{id}")
    public ResponseEntity<Gender> deleteArticleById(@PathVariable("id")Integer id) {
        LOGGER.info("Suppression du genre" + id);
        genderService.deleteGenderById(id);
        return ResponseEntity.ok().build();
    }

}
