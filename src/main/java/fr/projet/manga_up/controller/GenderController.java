package fr.projet.manga_up.controller;


import fr.projet.manga_up.dto.AddressDto;
import fr.projet.manga_up.dto.GenderDto;
import fr.projet.manga_up.dto.MangaDTO;
import fr.projet.manga_up.model.Gender;
import fr.projet.manga_up.service.GenderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/genders")
public class GenderController {
    private static final Logger LOGGER= LoggerFactory.getLogger(MangaController.class);

    @Autowired
    private GenderService genderService;

    @Operation(summary= "Récupération des genre d'utilisateur")
    @GetMapping("/pagination")
    public ResponseEntity<?> getAllGenders(
            @PageableDefault(
                    page = 0,
                    size = 10,
                    sort="id",
                    direction = Sort.Direction.DESC) Pageable pageable
    ){
        LOGGER.info("Récupération de la liste des genres des users");
        Page<Gender> genders = genderService.getAllGenders(pageable);
        LOGGER.info("Mangas : {}", genders);
        return ResponseEntity.ok(genders);
    }


    @Operation(summary= "Récupération des genre d'utilisateur")
    @GetMapping()
    public ResponseEntity<List<Gender>> getAllGenders(){
        LOGGER.info("Récupération de la liste des genres des users");
        List<Gender> genders = genderService.getAllGendersList();
        LOGGER.info("Mangas : {}", genders);
        return ResponseEntity.ok(genders);
    }

    @Operation(summary= "Récupération d'un genre graçe a son Id")
    @GetMapping(value = "/{id}",produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Gender> getGenderById(@PathVariable int id){
        LOGGER.info("recuperation d'un genre d'utilisateur avec son id");
        Gender gender = genderService.getGender(id);
        LOGGER.info("author : {}", gender);
        return ResponseEntity.ok(gender);
    }




    @Operation(summary = "Sauvegarde  de genre pour utilisateur")
    @ApiResponse(responseCode = "201", description = "Des nouveaus mangas sont enregistrés avec succès")
    @PostMapping("/dto")
    public ResponseEntity<GenderDto> createdGender(@RequestBody GenderDto gender) {
        GenderDto createdGender = genderService.saveGender(gender);
        return ResponseEntity.ok(createdGender);
    }






    @Operation(summary= "Suppression d'un genre pour les utilisateurs")
    @DeleteMapping("/dto/{id}")
    public ResponseEntity<Void> deleteArticleById(@PathVariable("id")Integer id) {
        LOGGER.info("Suppression du genre" + id);
        genderService.deleteGender(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/dto/{id}")
    public ResponseEntity<GenderDto> updateGender(@PathVariable Integer id, @RequestBody GenderDto gender) {
        try{
            GenderDto updatedGender = genderService.updateGender(id, gender);
            return ResponseEntity.ok(updatedGender);
        }catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Operation(summary=" ")
    @GetMapping("/dto")
    public ResponseEntity<Set<GenderDto>> getAddressDto() {
        Set<GenderDto> genderDto = genderService.getAllGenderDto();
        return ResponseEntity.ok(genderDto);
    }


}
