package fr.projet.manga_up.controller;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
	
	private static final Logger LOGGER=LoggerFactory.getLogger(CommentController.class);
	
	@Autowired
	private CommentService commentService;
	
	/**
	 * 
	 * @param id L'id qui représente le Manga et qui permettra de récupérer les commentaires associés.
	 * @return Retourne une liste de commentaires suivant les spécifications de la pagination.
	 */
	@Operation(summary = "Récupère une page avec l'id'", description = "Retourne une page")
	@ApiResponse(responseCode = "201", description = "la page a été trouvé avec succès")
	@GetMapping(value="/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getCommentsByIdManga(
			@PathVariable("id") Integer id,
			@PageableDefault(
					page = 0,
					size = 6,
					sort="createdAt",
					direction = Sort.Direction.DESC) Pageable pageable
	){
		LOGGER.info("Méthode getCommentsByIdManga, id : {}", id);
		Page<Comment> comments=commentService.getCommentsByIdManga(id, pageable);
		LOGGER.info("List comments : {}", comments);
		return ResponseEntity.ok(comments);
	}
}
