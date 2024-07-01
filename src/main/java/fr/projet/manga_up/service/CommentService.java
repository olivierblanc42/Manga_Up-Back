package fr.projet.manga_up.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fr.projet.manga_up.dao.CommentDao;
import fr.projet.manga_up.model.Comment;

@Component
public class CommentService {
	
	@Autowired
	private CommentDao commentDao;
	
	public List<Comment> getCommentsByIdManga(Integer id) {
		return commentDao.findAllById(id);
	}
}
