package fr.projet.manga_up.controller;

import java.util.List;

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

import fr.projet.manga_up.model.Comment;
import fr.projet.manga_up.service.CommentService;

@CrossOrigin(origins = "*") 
@RestController
@RequestMapping("/api/comments")
public class CommentController {
	
	private static final Logger LOGGER=LoggerFactory.getLogger(MangaController.class);
	
	@Autowired
	private CommentService commentService;
	
	/**
	 * 
	 * @param id L'id qui représente le Manga et qui permettra de récupérer les commentaires associés.
	 * @return Retourne une liste de commentaires.
	 */
	@GetMapping(value="/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Comment>> getCommentsByIdManga(@PathVariable Integer id){
		LOGGER.info("Méthode getCommentsByIdManga, id : {}", id);
		List<Comment> comments=commentService.getCommentsByIdManga(id);
		LOGGER.info("List comments : {}", comments);
		return ResponseEntity.ok(comments);
	}
}
