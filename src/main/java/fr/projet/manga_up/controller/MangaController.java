package fr.projet.manga_up.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.util.JSONPObject;
import fr.projet.manga_up.model.Comment;
import fr.projet.manga_up.model.User;
import fr.projet.manga_up.service.CommentService;
import fr.projet.manga_up.service.UserService;
import org.apache.tomcat.util.json.JSONParser;
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
	@Autowired
	private UserService userService;

	/**
	 * Récupère le manga et ses caracteristiques.
	 * @param id L'id qui représente le Manga que l'on souhaite obtenir.
	 * @return Retourne le Manga de l'id spécifié.
	 */
	@GetMapping(value="/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getManga(@PathVariable("id") Integer id){
		LOGGER.info("Dans controller getMangaId, id : {}", id);
		Map<String, Object> response = new HashMap<>();
		Manga manga=mangaService.getManga(id);
		List<Comment> comments=commentService.getCommentsByIdManga(id);
		response.put("manga", manga);
		response.put("comments", comments);
		return ResponseEntity.ok(response);
	}

	@GetMapping()
	public ResponseEntity<List<Manga>> getMangas() {
		LOGGER.info("Récupération de la liste des mangas");
		List<Manga> mangas =  mangaService.getAllManga();
		LOGGER.info("Mangas : {}", mangas);
		return ResponseEntity.ok(mangas);
	}

	@GetMapping(value="/oderDate", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Manga>> getOderDate() {
		LOGGER.info("Récupération de 10 manga ");
		List<Manga> mangas =  mangaService.getMangaOrderDateLimit9();
		LOGGER.info("Mangas : {}", mangas);
		return ResponseEntity.ok(mangas);
	}

	@GetMapping(value="/nine", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Manga>> getNineMangas() {
		LOGGER.info("Récupération de 9 manga ");
		List<Manga> mangas =  mangaService.getNineManga();
		LOGGER.info("Mangas : {}", mangas);
		return ResponseEntity.ok(mangas);
	}

   @PostMapping(value="/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> addUserInFavorite(
			@PathVariable("id") Integer idManga,
   			@RequestBody User _user){
		LOGGER.info("addUserInFavorite id : {}", idManga);
		LOGGER.info("idUser : {}", _user.getId());
		Manga manga=mangaService.addUserInFavorite(_user.getId(), idManga);
	    List<Comment> comments=commentService.getCommentsByIdManga(idManga);
	    Map<String, Object> response = new HashMap<>();
	    response.put("manga", manga);
	    response.put("comments", comments);
	    return ResponseEntity.ok(response);
	}

	@DeleteMapping(value = "/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> deleteUserAsFavorite(
			@PathVariable("id") Integer idManga,
			@RequestBody User _user){
		LOGGER.info("deleteUserAsFavorite id : {}", idManga);
		LOGGER.info("deleteUserAsFavorite body : {}", _user.getId());
		mangaService.deleteUserAsFavorite(_user.getId(), idManga);
		Manga manga=mangaService.getManga(idManga);
		List<Comment> comments=commentService.getCommentsByIdManga(idManga);
		Map<String, Object> response = new HashMap<>();
		response.put("manga", manga);
		response.put("comments", comments);
		return ResponseEntity.ok(response);
	}
}