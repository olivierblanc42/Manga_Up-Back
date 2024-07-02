package fr.projet.manga_up.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.projet.manga_up.model.Genre;
import fr.projet.manga_up.service.GenreService;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/genres")
public class GenreController {

	private static final Logger LOGGER = LoggerFactory.getLogger(GenreController.class);

	@Autowired
	private GenreService genreService;

	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Genre> getGenreId(@PathVariable Integer id) {
		LOGGER.info("Obtenir un genre");
		Genre genre = genreService.getGenre(id);
		return ResponseEntity.ok(genre);
	}

	/**
	 *
	 *
	 * @return une liste de six genre@
	 */
	@GetMapping(value ="/six",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Genre>> getGenreSix() {
		LOGGER.info("Récupération des  genres ");
		List<Genre> genres = genreService.getSixGenres();
		return ResponseEntity.ok(genres);
	}



	/**
	 *
	 *
	 * @return une liste avec tout les genres
	 */
	@GetMapping()
	public ResponseEntity<List<Genre>> getAllGenre() {
		LOGGER.info("Récupération des  genres ");
		List<Genre> genres = genreService.getAllGenres();
		return ResponseEntity.ok(genres);
	}

}
