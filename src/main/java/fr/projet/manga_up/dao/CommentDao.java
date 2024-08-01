package fr.projet.manga_up.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import fr.projet.manga_up.model.Comment;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentDao extends CrudRepository<Comment, Integer> {
	@Query("FROM Comment c WHERE c.manga.id = :idManga")
	Page<Comment> findAllById(@Param("idManga") Integer idManga, Pageable pageable);

	@Query(value = "SELECT c.rating FROM comment  c WHERE c.id_manga = :idManga", nativeQuery = true)
	List<Integer> findAllRatingByIdManga(@Param("idManga") Integer idManga);

	@Query("From Comment ")
    List<Comment> findAll();

}
