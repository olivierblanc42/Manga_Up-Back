package fr.projet.manga_up.service;

import fr.projet.manga_up.controller.MangaController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import fr.projet.manga_up.dao.CommentDao;
import fr.projet.manga_up.model.Comment;

import java.util.List;
import java.util.Optional;

@Component
public class CommentService {
	private static final Logger LOGGER= LoggerFactory.getLogger(MangaController.class);

	@Autowired
	private CommentDao commentDao;
	
	public Page<Comment> getCommentsByIdManga(Integer id, Pageable pageable) {
		LOGGER.info("Méthode getCommentsByIdManga, pageable : {}", pageable);
		return commentDao.findAllById(id, pageable);
	}

	public List<Integer> findAllRatingByIdManga(Integer idManga){
		LOGGER.info("Méthode findAllRatingByIdManga");
		return commentDao.findAllRatingByIdManga(idManga);

	}

	public Comment saveComment(Comment comment) {
		return commentDao.save(comment);
	}

	public void deleteComment(Integer id) {
		commentDao.deleteById(id);
	}


	public List<Comment> getComments(){
		return commentDao.findAll();
	}
}
