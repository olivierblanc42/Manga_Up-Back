package fr.projet.manga_up.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import fr.projet.manga_up.model.Picture;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PictureDao extends CrudRepository<Picture, Integer> {

	@Query(value="SELECT * FROM picture p WHERE p.id_manga = :id", nativeQuery=true)
	List<Picture> findAllById(@Param("id") Integer id);
}
