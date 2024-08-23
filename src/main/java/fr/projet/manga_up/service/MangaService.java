package fr.projet.manga_up.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
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



	public List<Manga> getMangaByName(String name){
		LOGGER.info("getMangaByName name : {}", name);
		return mangaDao.getMangaByName(name);
	}

	public Manga getManga(Integer id){
		Optional<Manga> mo=mangaDao.findById(id);
		if(mo.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Aucun manga n'a été trouvé");
		}else {
			return mo.get();
		}
	}

	public List<Manga> getNineManga() {
		List<Manga> mangas = mangaDao.findNineManga();
		return mangas;
	}

	public Page<Manga> findAllMangaPageable( Pageable pageable) {
		return mangaDao.findAllMangaPageable(pageable);
	}

	public Manga getMangaLimitOne() {
		return mangaDao.findMangaLimitOne();
	}

	public List<Manga> getMangaOrderDateLimit9(){
		return mangaDao.findMangaOrderByDate();
	}

	public Manga addUserInFavorite(Integer idUser, Integer idManga){
		LOGGER.info("addUserInFavorite");
		Manga manga = mangaDao.findById(idManga).orElse(null);
		User user = userDao.findById(idUser).orElse(null);

		if(user!=null && manga!=null){
			LOGGER.info("addUserInFavorite manga : {}", manga.getId());
			LOGGER.info("addUserInFavorite : {}", user.getId());
			mangaDao.addUserInFavorite(user.getId(), manga.getId());
		}
		return manga;
	}

	public void deleteUserAsFavorite(Integer idUser, Integer idManga){
		LOGGER.info("deleteUserAsFavorite");
		LOGGER.info("idUser : {}", idUser);
		LOGGER.info("idManga : {}", idManga);
		mangaDao.deleteUserAsFavorite(idUser, idManga);
	}


	public Page<Manga> getMangaByIdGenre(Integer id,Pageable pageable ){
		return mangaDao.findAllMangaByIdGenre(id,pageable);
	}

	public Page<Manga> getMangaByIdCategory(Integer id,Pageable pageable ){
		return mangaDao.findAllMangaByIdCategory(id,pageable);
	}

	public Page<Manga> getMangaByIdAuthor(Integer id ,Pageable pageable){
		return mangaDao.findAllMangaByIdAuthor(id,pageable);
	}


	/*public Manga createManga(Manga manga){
		LOGGER.info("createManga");
		return mangaDao.save(manga);
	}*/

	/*public void deleteManga(Integer id){
		LOGGER.info("deleteManga");
		mangaDao.deleteById(id);
	}*/



	public Manga updateManga(Integer id,Manga oldManga){
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

		// Dissocier les genres et les auteurs (si nécessaire)
		manga.getGenres().clear();
		manga.getAuthors().clear();

		// Supprimer le manga
		mangaDao.delete(manga);
	}

}
