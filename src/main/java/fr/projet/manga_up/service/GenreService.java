package fr.projet.manga_up.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
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
		LOGGER.debug("Récupération info genre : ", genreOptional);
		if (genreOptional.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ce genre n'a pas été trouvé");
		} else {
			return genreOptional.get();
		}
	}

}
