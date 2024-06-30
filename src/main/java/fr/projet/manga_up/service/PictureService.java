package fr.projet.manga_up.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fr.projet.manga_up.dao.PictureDao;
import fr.projet.manga_up.model.Picture;

@Component
public class PictureService {
	
	@Autowired
	private PictureDao pictureDao;
	
	public List<Picture> getPicturesByIdManga(Integer id) {
		return pictureDao.findAllById(id);
	}
}
