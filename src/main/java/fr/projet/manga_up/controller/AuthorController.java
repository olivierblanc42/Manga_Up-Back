package fr.projet.manga_up.controller;


import fr.projet.manga_up.model.Author;
import fr.projet.manga_up.model.Genre;
import fr.projet.manga_up.model.Manga;
import fr.projet.manga_up.service.AuthorService;
import fr.projet.manga_up.service.MangaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/authors")
public class AuthorController {
    private static final Logger LOGGER= LoggerFactory.getLogger(MangaController.class);

    @Autowired
    private AuthorService authorService;
    @Autowired
    private MangaService mangaService;

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


    @Operation(summary = "Récupère des auteur avec l'id'", description = "Retourne un auteur ")
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



}

