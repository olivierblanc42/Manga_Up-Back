package fr.projet.manga_up.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.projet.manga_up.model.Comment;
import fr.projet.manga_up.model.User;
import fr.projet.manga_up.service.CommentService;
import fr.projet.manga_up.service.UserService;
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
	 *
	 * @param id L'id qui représente le Manga que l'on souhaite obtenir.
	 * @param pageable Pagination pour les commentaires. Récupère notamment le numéro de page demandé.
	 * Ex d'url qui doit être utilisé : localhost:8080/users?page=2&size=5&sort=createdAt,DESC
	 * @return Retourne le Manga de l'id spécifié + les 6 premiers commentaires si on arrive pour
	 * la première fois sur la page. Sinon récupère la page demandé par l'utilisateur grâce à la pagination.
	 */
	@GetMapping(value="/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getManga(
			@PathVariable("id") Integer id,
			@PageableDefault(
				page = 0,
				size = 6,
				sort="createdAt",
				direction = Sort.Direction.DESC) Pageable pageable
	){
		LOGGER.info("Pageable : {}", pageable);
		LOGGER.info("Dans controller getMangaId, id : {}", id);
		Map<String, Object> response = new HashMap<>();
		Manga manga=mangaService.getManga(id);
		Page<Comment> comments=commentService.getCommentsByIdManga(id, pageable);
		List<Integer> listRating=commentService.findAllRatingByIdManga(id);
		response.put("manga", manga);
        LOGGER.info("comments : {}", comments);
		response.put("comments", comments);
		response.put("ratingAll", listRating);
		return ResponseEntity.ok(response);
	}

	@GetMapping()
	public ResponseEntity<List<Manga>> getMangas() {
		LOGGER.info("Récupération de la liste des mangas");
		List<Manga> mangas =  mangaService.getAllManga();
		LOGGER.info("Mangas : {}", mangas);
		return ResponseEntity.ok(mangas);
	}

	@GetMapping(value="/oderOne", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Manga> getMangaLimitOne(){
		LOGGER.info("Récupération 1 manga");
		Manga manga =  mangaService.getMangaLimitOne();
		LOGGER.info("Mangas : {}", manga);
		return ResponseEntity.ok(manga);
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
		mangaService.addUserInFavorite(_user.getId(), idManga);
	    return ResponseEntity.ok().build();
	}

	@DeleteMapping(value = "/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> deleteUserAsFavorite(
			@PathVariable("id") Integer idManga,
			@RequestBody User _user){
		LOGGER.info("deleteUserAsFavorite id : {}", idManga);
		LOGGER.info("deleteUserAsFavorite body : {}", _user.getId());
		mangaService.deleteUserAsFavorite(_user.getId(), idManga);
		return ResponseEntity.ok().build();
	}
}