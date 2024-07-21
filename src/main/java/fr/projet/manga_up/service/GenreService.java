package fr.projet.manga_up.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import fr.projet.manga_up.model.Manga;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

import fr.projet.manga_up.controller.GenreController;
import fr.projet.manga_up.dao.GenreDao;
import fr.projet.manga_up.model.Genre;

@Component
public class GenreService {

	private static final Logger LOGGER = LoggerFactory.getLogger(GenreController.class);

	@Autowired
	private GenreDao genreDao;

	public Genre getGenre(Integer id) {
		Optional<Genre> genreOptional = genreDao.findById(id);
		LOGGER.debug("Récupération info genre : ");
		if (genreOptional.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Aucun genre n'a été trouvé");
		} else {
			return genreOptional.get();
		}
	}


	public List<Genre> getSixGenre() {
		return genreDao.findGenreLimit6();
	}

	public Page<Genre> findAllGenrePageable(Pageable pageable) {
		return genreDao.findAllGenrePageable(pageable);
	}



}
