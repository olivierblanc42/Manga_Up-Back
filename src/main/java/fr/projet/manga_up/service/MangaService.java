package fr.projet.manga_up.service;

import java.util.*;
import java.util.stream.Collectors;

import fr.projet.manga_up.dao.*;
import fr.projet.manga_up.dto.GenreDto;
import fr.projet.manga_up.dto.MangaDTO;
import fr.projet.manga_up.mapper.MangaMapper;
import fr.projet.manga_up.model.*;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Component
public class MangaService {

	private static final Logger LOGGER= LoggerFactory.getLogger(MangaService.class);

	@Autowired
	private MangaDao mangaDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private CategoryDao categoryDao;
	@Autowired
	private GenreDao genreDao;
	@Autowired
	private AuthorDao authorDao;
	@Autowired
	private MangaMapper mangaMapper;
	@Autowired
	private MangaDTO mangaDto;


	/**
	 * Récupère une liste de mangas par leur nom.
	 *
	 * @param name le nom du manga à rechercher
	 * @return une liste d'objets {@link Manga} correspondant au nom fourni
	 */
	public List<Manga> getMangaByName(String name){
		LOGGER.info("getMangaByName name : {}", name);
		return mangaDao.getMangaByName(name);
	}



	/**
	 * Récupère un manga par son identifiant.
	 *
	 * @param id l'identifiant du manga à rechercher
	 * @return l'objet {@link Manga} correspondant à l'identifiant fourni
	 * @throws ResponseStatusException si aucun manga n'est trouvé avec l'identifiant fourni
	 */
	public Manga getManga(Integer id){
		Optional<Manga> mo=mangaDao.findById(id);
		if(mo.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Aucun manga n'a été trouvé");
		}else {
			return mo.get();
		}
	}

	/**
	 * Récupère une liste de neuf mangas.
	 *
	 * @return une liste de neuf objets {@link Manga}
	 */
	public List<Manga> getNineManga() {
		List<Manga> mangas = mangaDao.findNineManga();
		return mangas;
	}
	/**
	 * Récupère une page de mangas de manière paginée.
	 *
	 * @param pageable un objet {@link Pageable} définissant les paramètres de pagination
	 * @return une page de mangas sous forme d'objet {@link Page} contenant les résultats paginés
	 */
	public Page<Manga> findAllMangaPageable( Pageable pageable) {

		return mangaDao.findAllMangaPageable(pageable);
	}

	/**
	 *
	 * @return Récupère tous les mangas dans une liste.
	 */
	public List<Manga> findAllManga() {
		return mangaDao.findAllManga();
	}
	/**
	 * Récupère un manga unique.
	 *
	 * @return un objet {@link Manga} représentant un seul manga
	 */
	public Manga getMangaLimitOne() {

		return mangaDao.findMangaLimitOne();
	}

	/**
	 * Récupère une liste de neuf mangas triés par date.
	 *
	 * @return une liste de neuf objets {@link Manga} triés par date
	 */
	public List<Manga> getMangaOrderDateLimit9(){

		return mangaDao.findMangaOrderByDate();
	}

	/**
	 * Ajoute un manga aux favoris d'un utilisateur.
	 *
	 * @param idUser l'identifiant de l'utilisateur
	 * @param idManga l'identifiant du manga
	 * @return l'objet {@link Manga} ajouté aux favoris de l'utilisateur, ou {@code null} si l'utilisateur ou le manga n'existe pas
	 */
	public Manga addUserInFavorite(Integer idUser, Integer idManga){
		LOGGER.info("addUserInFavorite");
		Manga manga = mangaDao.findById(idManga).orElse(null);
		AppUser user = userDao.findById(idUser).orElse(null);

		if(user!=null && manga!=null){
			LOGGER.info("addUserInFavorite manga : {}", manga.getId());
			LOGGER.info("addUserInFavorite : {}", user.getId());
			mangaDao.addUserInFavorite(user.getId(), manga.getId());
		}
		return manga;
	}

	/**
	 * Supprime un manga des favoris d'un utilisateur.
	 *
	 * @param idUser l'identifiant de l'utilisateur dont le manga sera supprimé des favoris
	 * @param idManga l'identifiant du manga à supprimer des favoris de l'utilisateur
	 */
	public void deleteUserAsFavorite(Integer idUser, Integer idManga){
		LOGGER.info("deleteUserAsFavorite");
		LOGGER.info("idUser : {}", idUser);
		LOGGER.info("idManga : {}", idManga);
		mangaDao.deleteUserAsFavorite(idUser, idManga);
	}

	/**
	 * Récupère une page de mangas pour un genre spécifique.
	 *
	 * @param id l'identifiant du genre pour lequel les mangas sont recherchés
	 * @param pageable un objet {@link Pageable} définissant les paramètres de pagination (taille de la page, numéro de la page, tri, etc.)
	 * @return une page de mangas sous forme d'objet {@link Page}, contenant les résultats paginés pour le genre spécifié
	 */
	public Page<Manga> getMangaByIdGenre(Integer id,Pageable pageable ){
		return mangaDao.findAllMangaByIdGenre(id,pageable);
	}

	/**
	 * Récupère une page de mangas pour une catégorie spécifique.
	 *
	 * @param id l'identifiant de la catégorie pour laquelle les mangas sont recherchés
	 * @param pageable un objet {@link Pageable} définissant les paramètres de pagination (taille de la page, numéro de la page, tri, etc.)
	 * @return une page de mangas sous forme d'objet {@link Page}, contenant les résultats paginés pour la catégorie spécifiée
	 */
	public Page<Manga> getMangaByIdCategory(Integer id,Pageable pageable ){
		return mangaDao.findAllMangaByIdCategory(id,pageable);
	}

	/**
	 * Récupère une page de mangas pour un auteur spécifique.
	 *
	 * @param id l'identifiant de l'auteur pour lequel les mangas sont recherchés
	 * @param pageable un objet {@link Pageable} définissant les paramètres de pagination (taille de la page, numéro de la page, tri, etc.)
	 * @return une page de mangas sous forme d'objet {@link Page}, contenant les résultats paginés pour l'auteur spécifié
	 */
	public Page<Manga> getMangaByIdAuthor(Integer id ,Pageable pageable){
		return mangaDao.findAllMangaByIdAuthor(id,pageable);
	}



	public Manga updateManga1(Integer id,Manga oldManga){
		LOGGER.info("updateManga");

		Optional<Manga> optionalManga=mangaDao.findById(id);
		if (optionalManga.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Aucun manga n'a été trouvé");
		}
		Manga updateManga = optionalManga.get();

		updateManga.setTitle(oldManga.getTitle());
		updateManga.setPrice(oldManga.getPrice());
		updateManga.setPointFidelity(oldManga.getPointFidelity());


		if (oldManga.getCategory() != null) {
			Optional<Category> optionalCategory = categoryDao.findById(oldManga.getCategory().getId());
			if (optionalCategory.isEmpty()) {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Aucune catégorie n'a été trouvée");
			}
			updateManga.setCategory(optionalCategory.get());
		}




		return mangaDao.save(updateManga);

	}




	/**
	 * Sauvegarde un manga et retourne son DTO associé.
	 *
	 * @param dto l'objet {@link MangaDTO} à sauvegarder
	 * @return l'objet {@link MangaDTO} correspondant au manga sauvegardé
	 */
	@Transactional
	public MangaDTO saveMangaTest(MangaDTO dto) {
		Manga manga = mangaMapper.toEntity(dto);
		manga = mangaDao.save(manga);
		return MangaMapper.toDto(manga);
	}

	@Transactional
	public void deleteManga(int mangaId) {
		Manga manga = mangaDao.findById(mangaId)
				.orElseThrow(() -> new EntityNotFoundException("Manga not found"));

		for(Genre genre : manga.getGenres()   ){
			genre.getMangas().remove(manga);
		}

		for(Author author : manga.getAuthors()   ){
			author.getMangas().remove(manga);
		}

		// Dissocier les genres et les auteurs (si nécessaire)
		manga.getGenres().clear();
		manga.getAuthors().clear();

		// Supprimer le manga
		mangaDao.delete(manga);
	}



	public MangaDTO updateManga(Integer id, MangaDTO mangaDto) {
		// Trouve l'entité existante
		Manga manga = mangaDao.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Manga not found"));

		// Met à jour les attributs de l'entité
		manga.setTitle(mangaDto.getTitle());
		manga.setSummary(mangaDto.getSummary());
		manga.setPrice(mangaDto.getPrice());

		// Met à jour la catégorie
		Category category = categoryDao.findById(mangaDto.getCategoryId())
				.orElseThrow(() -> new RuntimeException("Category not found"));
		manga.setCategory(category);

		// Met à jour les genres
		Set<Genre> updatedGenres = new HashSet<>();
		if (mangaDto.getGenreIds() != null) {
			for (Integer genreId : mangaDto.getGenreIds()) {
				Genre genre = genreDao.findById(genreId)
						.orElseThrow(() -> new RuntimeException("Genre not found: " + genreId));
				updatedGenres.add(genre);
			}
		}
		manga.setGenres(updatedGenres);

		Set<Author> updatedAuthors = new HashSet<>();
		if (mangaDto.getAuthorIds() != null) {
			for (Integer authorId : mangaDto.getAuthorIds()) {
				Author author = authorDao.findById(authorId)
						.orElseThrow(() -> new RuntimeException("Author not found: " + authorId));

				updatedAuthors.add(author);
			}
		}

		manga.setAuthors(updatedAuthors);

		// Sauvegarde les modifications
		manga = mangaDao.save(manga);

		return MangaMapper.toDto(manga);
	}
}
