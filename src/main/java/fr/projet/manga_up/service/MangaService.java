package fr.projet.manga_up.service;

import java.util.List;
import java.util.Optional;
import fr.projet.manga_up.dao.UserDao;
import fr.projet.manga_up.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import fr.projet.manga_up.dao.MangaDao;
import fr.projet.manga_up.model.Manga;

@Component
public class MangaService {

	private static final Logger LOGGER= LoggerFactory.getLogger(MangaService.class);

	@Autowired
	private MangaDao mangaDao;
	@Autowired
	private UserDao userDao;

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

	public List<Manga> getAllManga() {
        return mangaDao.findAllManga();
	}

	public List<Manga> getMangaLimitOne() {
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
}
