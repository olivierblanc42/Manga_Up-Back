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

import fr.projet.manga_up.model.Manga;
import fr.projet.manga_up.service.MangaService;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/mangas")
public class MangaController {
	private static final Logger LOGGER=LoggerFactory.getLogger(MangaController.class);

	@Autowired
	private MangaService mangaService;

	/**
	 *
	 * @param id L'id qui représente le Manga que l'on souhaite obtenir.
	 * @return Retourne le Manga de l'id spécifié.
	 */
	@GetMapping(value="/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Manga> getManga(@PathVariable Integer id){
		LOGGER.info("Méthode getMangaId, id : {}", id);
		Manga manga=mangaService.getManga(id);
		LOGGER.info("Manga : {}", manga);
		return ResponseEntity.ok(manga);
	}

	@GetMapping()
	public ResponseEntity<List<Manga>> getTenManga() {
		LOGGER.info("Récupération de 10 manga");
		List<Manga> mangas =  mangaService.getTenManga();
		LOGGER.info("Mangas : {}", mangas);
		return ResponseEntity.ok(mangas);
	}

}