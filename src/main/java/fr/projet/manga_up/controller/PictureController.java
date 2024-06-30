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

import fr.projet.manga_up.model.Picture;
import fr.projet.manga_up.service.PictureService;

@CrossOrigin(origins = "*") 
@RestController
@RequestMapping("/api/pictures")
public class PictureController {
	private static final Logger LOGGER=LoggerFactory.getLogger(MangaController.class);
	
	@Autowired
	private PictureService pictureService;
	
	/**
	 * 
	 * @param id L'id qui représente le Manga et qui permettra de récupérer les images associés.
	 * @return Retourne une liste de picture.
	 */
	@GetMapping(value="/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Picture>> getPicturesByIdManga(@PathVariable Integer id){
//		LOGGER.info("Méthode getPicturesByIdManga, id : {}", id);
		List<Picture> pictures=pictureService.getPicturesByIdManga(id);
//		LOGGER.info("List pictures : {}", pictures);
		return ResponseEntity.ok(pictures);
	}
}
