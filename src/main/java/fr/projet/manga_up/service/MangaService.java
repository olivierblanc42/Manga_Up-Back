package fr.projet.manga_up.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import fr.projet.manga_up.dao.MangaDao;
import fr.projet.manga_up.model.Manga;

@Component
public class MangaService {
	
	@Autowired
	private MangaDao mangaDao;
	
	public Manga getManga(Integer id){
		Optional<Manga> mo=mangaDao.findById(id);
		if(mo.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Aucun manga n'a été trouvé");
		}else {
			return mo.get();
		}
	}

	public List<Manga> getTenManga() {
		List<Manga> mangas = mangaDao.FindTenManga();
		return mangas;
	}

}
