package fr.projet.manga_up.service;

import java.util.*;
import java.util.stream.Collectors;

import fr.projet.manga_up.dto.GenreDto;
import fr.projet.manga_up.mapper.GenreMapper;
import fr.projet.manga_up.model.Manga;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.persistence.EntityNotFoundException;
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
import org.springframework.transaction.annotation.Transactional;
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

	@Autowired
	private GenreMapper genreMapper;

	/**
	 * Récupère un genre par son identifiant depuis la base de données.
	 *
	 * Cette méthode cherche un genre spécifique en utilisant son identifiant unique. Si aucun genre n'est trouvé,
	 * une exception est levée pour indiquer que la ressource n'existe pas.
	 *
	 * @param id l'identifiant du genre à récupérer
	 * @return le genre correspondant à l'identifiant fourni
	 * @throws ResponseStatusException si aucun genre n'est trouvé avec l'identifiant donné, avec un statut HTTP 404 (Not Found)
	 */
	public Genre getGenre(Integer id) {
		Optional<Genre> genreOptional = genreDao.findById(id);
		LOGGER.debug("Récupération info genre : ");
		if (genreOptional.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Aucun genre n'a été trouvé");
		} else {
			return genreOptional.get();
		}
	}

	/**
	 * Récupère une liste de six genres depuis la base de données.
	 *
	 * Cette méthode retourne une liste contenant les six premiers genres disponibles dans la base de données.
	 * La limite de six genres est définie au niveau de la requête effectuée par le DAO.
	 *
	 * @return une liste de six genres
	 */
	public List<Genre> getSixGenre() {

		return genreDao.findGenreLimit6();
	}

	/**
	 * Récupère une page de genres depuis la base de données en utilisant des critères de pagination.
	 *
	 * Cette méthode retourne une page de genres en fonction des paramètres de pagination fournis.
	 * La pagination est gérée par l'objet {@link Pageable} qui spécifie le numéro de la page, la taille de la page,
	 * et éventuellement des critères de tri.
	 *
	 * @param pageable les critères de pagination, y compris le numéro de la page, la taille de la page et les options de tri
	 * @return une {@link Page} de genres correspondant aux critères de pagination fournis
	 */
	public Page<Genre> findAllGenrePageable(Pageable pageable) {
		return genreDao.findAllGenrePageable(pageable);
	}

	/**
	 * Enregistre un genre dans la base de données.
	 *
	 * Cette méthode persiste un objet {@link Genre} dans la base de données. Si le genre n'existe pas encore,
	 * il sera créé ; si le genre existe déjà (par exemple, si un identifiant est fourni et qu'il correspond à un genre existant),
	 * il sera mis à jour.
	 *
	 * @param genre l'objet {@link Genre} à enregistrer dans la base de données
	 * @return l'objet {@link Genre} enregistré, avec un identifiant généré ou mis à jour
	 */
	public  Genre saveGenre(Genre genre) {

		return genreDao.save(genre);
	}


	public void removeGenreMangas(Integer id) {
		Genre genre = genreDao.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Genre not found"));


		for (Manga manga : genre.getMangas()) {
			manga.getGenres().remove(genre);  // Si la relation est bidirectionnelle, il faut aussi le retirer côté Manga
		}
		//// Dissocier les mangas
		genre.getMangas().clear();

		genreDao.delete(genre);
	}



	public GenreDto getGenreById(Integer id) {
		Genre genre = genreDao.findById(id)
				.orElseThrow(() -> new RuntimeException("Genre not found"));
		return genreMapper.toDTO(genre);
	}



	public GenreDto saveGenreDTO(GenreDto dto) {
		Genre genre = genreMapper.toEntity(dto);
		genre = genreDao.save(genre);
		return genreMapper.toDTO(genre);
	}

	public Set<GenreDto> getAllGenresDto() {
		List<Genre> genres = new ArrayList<>();
		genreDao.findAll().forEach(genres::add);

		return genres.stream()
				.map(genreMapper::toDTO)
				.collect(Collectors.toSet());

	}

	@Transactional
	public GenreDto updateGenre(Integer id ,GenreDto dto) {
		// Trouve le genre du manga existant
		Genre genre = genreDao.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Genre not found"));
		genre.setLabel(dto.getLabel());

		genre =genreDao.save(genre);
		return genreMapper.toDTO(genre);
	}




}
