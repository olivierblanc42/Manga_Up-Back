package fr.projet.manga_up.controller;

import java.util.List;

import fr.projet.manga_up.model.Picture;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

	@Operation(summary= "recuperation de tout les commentaires" )
	@ApiResponse(responseCode = "201", description = " un nouveau commentaire a été créé ")
    @GetMapping
	public ResponseEntity<List<Comment>>   getAllComments(){
		List<Comment> comments = commentService.getComments();
		return ResponseEntity.ok(comments);
	}



	/**
	 * 
	 * @param id L'id qui représente le Manga et qui permettra de récupérer les commentaires associés.
	 * @return Retourne une liste de commentaires suivant les spécifications de la pagination.
	 */
	/*@Operation(summary = "Récupère une page avec l'id'", description = "Retourne une page")
	@ApiResponse(responseCode = "201", description = "la page a été trouvé avec succès")
	@GetMapping(value="/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
	@Parameter(in = ParameterIn.QUERY,
			description = "Zero-based page index (0..N)",
			name = "page", // !
			schema = @Schema(type = "integer", defaultValue = "0"))
	@Parameter(in = ParameterIn.QUERY,
			description = "Zero-based page index (0..N)",
			name = "size", // !
			schema = @Schema(type = "integer", defaultValue = "6"))
	@Parameter(in = ParameterIn.QUERY,
			description = "Zero-based page index (0..N)",
			name = "sort", // !
			schema = @Schema(type = "string", defaultValue = "createdAt"))
	public ResponseEntity<?> getCommentsByIdManga(
			@PathVariable("id") Integer id,
			@ParameterObject Pageable pageable

	){
		LOGGER.info("Méthode getCommentsByIdManga, id : {}", id);
		Page<Comment> comments=commentService.getCommentsByIdManga(id, pageable);
		LOGGER.info("List comments : {}", comments);
		return ResponseEntity.ok(comments);
	}*/


	@Operation(summary= "Creation d'un nouveau commentaire" )
	@ApiResponse(responseCode = "201", description = " un nouveau commentaire a été créé ")
	@PostMapping
	public Comment saveComment(@RequestBody Comment comment) {
		return commentService.saveComment(comment);
	}

	@Operation(summary= "Suppression d'un commentaire" )
    @DeleteMapping("/{id}")
	public ResponseEntity<?> deleteComment(@PathVariable("id") Integer id) {
		commentService.deleteComment(id);
		return ResponseEntity.ok().build();
	}



}

