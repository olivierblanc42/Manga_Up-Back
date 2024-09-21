package fr.projet.manga_up.controller;

import fr.projet.manga_up.dao.GenreDao;
import fr.projet.manga_up.dto.GenderDto;
import fr.projet.manga_up.dto.GenreDto;
import fr.projet.manga_up.model.Comment;
import fr.projet.manga_up.model.Gender;
import fr.projet.manga_up.model.Manga;
import fr.projet.manga_up.service.MangaService;
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

import fr.projet.manga_up.model.Genre;
import fr.projet.manga_up.service.GenreService;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/genres")
public class GenreController {

	private static final Logger LOGGER = LoggerFactory.getLogger(GenreController.class);

	@Autowired
	private GenreService genreService;
	@Autowired
	private GenreDao genreDao;
	@Autowired
	private MangaService mangaService;


	@Operation(summary = "Récupére un genre par son Id")
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Genre> getGenreId(@PathVariable Integer id) {
		LOGGER.info("Obtenir un genre");
		Genre genre = genreService.getGenre(id);
		return ResponseEntity.ok(genre);
	}

	@Operation(summary= "Récupérer six genres dans la base de données")
	@GetMapping(value="/six", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Genre>> getGenreSix() {
		LOGGER.info("Récupération de 6 genres");
		List<Genre> genres = genreService.getSixGenre() ;
		LOGGER.info("Mangas : {}", genres);
		return ResponseEntity.ok(genres);
	}

	@Operation(summary= "Récupérer tous les genres avec la pagination")
	@GetMapping()
	public ResponseEntity<Page<Genre>> getGenres(
			@PageableDefault(
					page = 0,
					size = 10,
					sort="createdDate",
					direction = Sort.Direction.DESC) Pageable pageable

	) {
		LOGGER.info("Récupération de la liste des mangas");
		Page<Genre> genres = genreService.findAllGenrePageable(pageable);
		LOGGER.info("pageable : {}", pageable);

		LOGGER.info("Mangas : {}", genres);
		return ResponseEntity.ok(genres);
	}









	/**
	 *
	 * @param id L'id qui représente le Genre que l'on souhaite obtenir.
	 * @param pageable Pagination pour les Manga. Récupère notamment le numéro de page demandé.
	 * Ex d'url qui doit être utilisé : localhost:8080/users?page=2&size=5&sort=createdAt,DESC
	 * @return Retourne le Manga de l'id spécifié + les 6 premiers commentaires si on arrive pour
	 * la première fois sur la page. Sinon récupère la page demandé par l'utilisateur grâce à la pagination.
	 */
	@Operation(summary = "Récupère des genres avec l'id'", description = "Retourne des mangas")
	@GetMapping(value="genre/{id}", produces= MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getGenre(
			@PathVariable("id") Integer id,
			@PageableDefault(
					page = 0,
					size = 10,
					sort="createdAt",
					direction = Sort.Direction.DESC) Pageable pageable
	){
		LOGGER.info("Pageable : {}", pageable);
		LOGGER.info("Dans controller getMangaId, id : {}", id);
		Map<String, Object> response = new HashMap<>();
		Genre genre = genreService.getGenre(id);
		Page<Manga> mangas = mangaService.getMangaByIdGenre(id,pageable);
		response.put("genre",genre);
		LOGGER.info("mangas : {}", mangas);
		response.put("mangas",mangas);
		return ResponseEntity.ok(response);

	}


	@Operation(summary= "sauvegarde de genre pour les mangas ")
	@ApiResponse(responseCode = "201", description = " un nouveau genre a été enregisté avec succès")
	@PostMapping
	public Genre saveGenre(@RequestBody Genre genre) {

		return genreService.saveGenre(genre);
	}



	@Operation(summary= "Suppression d'un genre pour les mangas")
	@DeleteMapping("/{id}")
	public ResponseEntity<Genre> deleteArticleById(@PathVariable("id")Integer id) {
		LOGGER.info("Suppression du genre" + id);
		genreService.removeGenreMangas(id);
		return ResponseEntity.ok().build();
	}

	@Operation(summary=" ")
	@GetMapping("/dto")
	public ResponseEntity<Set<GenreDto>> getGenreDto() {
		Set<GenreDto> genreDto = genreService.getAllGenresDto();
		return ResponseEntity.ok(genreDto);
	}

	@PutMapping("/dto/{id}")
	public ResponseEntity<GenreDto> updateGenre(@PathVariable Integer id, @RequestBody GenreDto genreDto) {
		try{
			GenreDto updatedGenre = genreService.updateGenre(id, genreDto);
			return ResponseEntity.ok(updatedGenre);
		}catch (EntityNotFoundException e){
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}



}
