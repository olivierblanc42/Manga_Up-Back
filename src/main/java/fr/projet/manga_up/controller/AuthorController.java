package fr.projet.manga_up.controller;


import fr.projet.manga_up.dto.AuthorDto;
import fr.projet.manga_up.dto.MangaDTO;
import fr.projet.manga_up.model.Author;
import fr.projet.manga_up.model.Genre;
import fr.projet.manga_up.model.Manga;
import fr.projet.manga_up.service.AuthorService;
import fr.projet.manga_up.service.MangaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/authors")
public class AuthorController {
    private static final Logger LOGGER= LoggerFactory.getLogger(MangaController.class);

    @Autowired
    private AuthorService authorService;
    @Autowired
    private MangaService mangaService;



    @Operation(summary= "Récupération des auteurs avec la pagination")
    @ApiResponse(responseCode = "201", description = " un nouveau auteur a été crée avec succès")
    @GetMapping
    public ResponseEntity<?> getAllAuthors(
            @PageableDefault(
                    page = 0,
                    size = 10,
                    sort="createdAt",
                    direction = Sort.Direction.DESC) Pageable pageable

    )
    {
        LOGGER.info("Récupération de la liste des authors");
        Page<Author> authors =  authorService.getAllAuthor(pageable);
        LOGGER.info("authors : {}", authors);
        return ResponseEntity.ok(authors);
    }


    @Operation(summary = "Récupération d'un auteur grâce à son Id", description = "Retourne un auteur ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "l'Auteur a bien été trouvé "),
            @ApiResponse(responseCode = "404", description = "Auteur not found")
    })

    @GetMapping(value = "/{id}", produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Author> getAuthor(@PathVariable int id){
        LOGGER.info("getAuthor : {}", id);
        Author author = authorService.getAuthor(id);
        LOGGER.info("author : {}", author);
        return ResponseEntity.ok(author);
    }


    @Operation(summary = "Récupère toute les mangas de l'auteur avec l'id de l'auteur ", description = "Retourne une liste de manga ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "les manga de l'auteur ont bien été trouvé"),
            @ApiResponse(responseCode = "404", description = "Auteur not found")
    })
    @GetMapping(value="author/{id}", produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAuthorByID(
            @PathVariable("id") Integer id,
            @PageableDefault(
                    page = 0,
                    size = 10,
                    sort="createdAt",
                    direction = Sort.Direction.DESC) Pageable pageable
    ){
        Map<String,Object> response = new HashMap<>();
        Author author = authorService.getAuthor(id);
        Page<Manga> mangas = mangaService.getMangaByIdAuthor(id,pageable);
        response.put("author",author);
        LOGGER.info("mangas : {}", mangas);
        response.put("mangas",mangas);
        return ResponseEntity.ok(response);
    }



    @Operation(summary= "Creation du nouveau auteur")
    @ApiResponse(responseCode = "201", description = " un nouveau auteur a été crée avec succès")
    @PostMapping
    public ResponseEntity<AuthorDto> createAuthor(@RequestBody AuthorDto authorDto) {
        LOGGER.info("Received Author DTO: {}", authorDto);

        // Validez les données reçues
        if (authorDto.getLastName() == null || authorDto.getLastName().isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }

        AuthorDto createdAuthor = authorService.saveAuthorDto(authorDto);
        return ResponseEntity.ok(createdAuthor);
    }

    @Operation(summary = " supprime un autheur")
    @ApiResponse(responseCode = "201", description = "a bien été supprimé ")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAuthor(@PathVariable int id) {
        LOGGER.info("deleteAuthor : {}", id);
        authorService.deleteAuthor(id);
        return ResponseEntity.ok().build();
    }



  /*  @PutMapping("/{id}")
    public ResponseEntity<Author> updateAuthor(@PathVariable Integer id, @RequestBody Author authorDetails) {
        Author updatedAuthor = authorService.updateAuthor(id, authorDetails);
        return ResponseEntity.ok(updatedAuthor);
    }*/

    @PutMapping("/{id}")
    public ResponseEntity<AuthorDto> updateAuthor(@PathVariable Integer id, @RequestBody AuthorDto authorUpdateDto) {
        try{
            AuthorDto updateAuthor = authorService.updateAuthorTest(id ,authorUpdateDto) ;
            return ResponseEntity.ok(updateAuthor);
        }catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

    }



    @GetMapping("/dto")
    public ResponseEntity<Set<AuthorDto>> getAllAuthor() {
        Set<AuthorDto> authorDto = authorService.getAllAuthorDto2();
        return ResponseEntity.ok(authorDto);
    }




}

