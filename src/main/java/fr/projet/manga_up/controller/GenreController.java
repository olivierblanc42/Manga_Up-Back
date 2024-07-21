package fr.projet.manga_up.controller;

import fr.projet.manga_up.dao.GenreDao;
import fr.projet.manga_up.model.Comment;
import fr.projet.manga_up.model.Manga;
import fr.projet.manga_up.service.MangaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.projet.manga_up.model.Genre;
import fr.projet.manga_up.service.GenreService;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Genre> getGenreId(@PathVariable Integer id) {
		LOGGER.info("Obtenir un manga");
		Genre genre = genreService.getGenre(id);
		return ResponseEntity.ok(genre);
	}


	@GetMapping(value="/six", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Genre>> getGenreSix() {
		LOGGER.info("Récupération de 10 manga ");
		List<Genre> genres = genreService.getSixGenre() ;
		LOGGER.info("Mangas : {}", genres);
		return ResponseEntity.ok(genres);
	}


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
	/*@Operation(summary = "Récupère des genres avec l'id'", description = "Retourne des mangas")
	@GetMapping(value="genre/{label}", produces= MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getGenre(
			@PathVariable("label") String  label,
			@PageableDefault(
					page = 0,
					size = 10,
					sort="created_date",
					direction = Sort.Direction.DESC) Pageable pageable
	){
		LOGGER.info("Pageable : {}", pageable);
		LOGGER.info("Dans controller getGenreId, id : {}", id);
		Map<String, Object> response = new HashMap<>();
		Genre genre = genreService.getGenre(id);
		Page<Manga> mangas = mangaService.getMangaByIdGenre(id,pageable);
		response.put("genre",genre);
		LOGGER.info("mangas : {}", mangas);
		response.put("mangas",mangas);
		return ResponseEntity.ok(response);

	}
*/

	@GetMapping(value="genre/{id}", produces= MediaType.APPLICATION_JSON_VALUE)
	@Parameter(in = ParameterIn.QUERY,
			description = "Zero-based page index (0..N)",
			name = "page", // !
			schema = @Schema(type = "integer", defaultValue = "1"))
	@Parameter(in = ParameterIn.QUERY,
			description = "Zero-based page index (0..N)",
			name = "size", // !
			schema = @Schema(type = "integer", defaultValue = "6"))
	@Parameter(in = ParameterIn.QUERY,
			description = "Zero-based page index (0..N)",
			name = "sort", // !
			schema = @Schema(type = "string", defaultValue = "createdAt"))
	public ResponseEntity<?> getGenre(
			@PathVariable("id") Integer id,
			@ParameterObject Pageable pageable

	)
	{
		Map<String, Object> response = new HashMap<>();
		Genre genre = genreService.getGenre(id);
		Page<Manga> mangas= mangaService.getMangaByIdGenre(id,pageable);
		response.put("genre",genre);
		LOGGER.info("mangas : {}", mangas);
		response.put("mangas",mangas);
		return ResponseEntity.ok(response);
	}








}
