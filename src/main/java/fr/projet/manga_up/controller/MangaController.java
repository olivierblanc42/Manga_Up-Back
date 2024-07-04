package fr.projet.manga_up.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.projet.manga_up.model.Comment;
import fr.projet.manga_up.service.CommentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import fr.projet.manga_up.model.Manga;
import fr.projet.manga_up.service.MangaService;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/mangas")
public class MangaController {
	private static final Logger LOGGER=LoggerFactory.getLogger(MangaController.class);

	@Autowired
	private MangaService mangaService;
	@Autowired
	private CommentService commentService;

	/**
	 * Récupère le manga et ses caracteristiques.
	 * @param id L'id qui représente le Manga que l'on souhaite obtenir.
	 * @return Retourne le Manga de l'id spécifié.
	 */
	@GetMapping(value="/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getManga(@PathVariable("id") Integer id){
		LOGGER.info("Méthode getMangaId, id : {}", id);
		Map<String, Object> response = new HashMap<>();
		Manga manga=mangaService.getManga(id);
		List<Comment> comments=commentService.getCommentsByIdManga(id);
		response.put("manga", manga);
		response.put("comments", comments);
		LOGGER.info("Manga : {}", manga);
		LOGGER.info("Comments : {}", comments);
		return ResponseEntity.ok(response);
	}

	@GetMapping()
	public ResponseEntity<List<Manga>> getMangas() {
		LOGGER.info("Récupération de la liste des mangas");
		List<Manga> mangas =  mangaService.getAllManga();
		LOGGER.info("Mangas : {}", mangas);
		return ResponseEntity.ok(mangas);
	}

	@GetMapping(value="/ten", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Manga>> getTenMangas() {
		LOGGER.info("Récupération de 10 manga ");
		List<Manga> mangas =  mangaService.getTenManga();
		LOGGER.info("Mangas : {}", mangas);
		return ResponseEntity.ok(mangas);
	}

   @PostMapping(value="/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> addUserInFavorite(@PathVariable("id") String id){
		LOGGER.info("addUserInFavorite : {}", id);
		return ResponseEntity.ok("ok");
	}

	@DeleteMapping(value = "/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> deleteUserAsFavorite(@PathVariable("id") String id){
		LOGGER.info("deleteUserAsFavorite : {}", id);
		return ResponseEntity.ok("ok");
	}
}