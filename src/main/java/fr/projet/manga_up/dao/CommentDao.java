package fr.projet.manga_up.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import fr.projet.manga_up.model.Comment;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentDao extends CrudRepository<Comment, Integer> {
	@Query(value="SELECT * FROM comment c WHERE c.id_manga = :id", nativeQuery=true)
	List<Comment> findAllById(Integer id);
}
