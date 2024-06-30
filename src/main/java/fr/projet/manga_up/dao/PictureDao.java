package fr.projet.manga_up.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import fr.projet.manga_up.model.Picture;
import org.springframework.stereotype.Repository;

@Repository
public interface PictureDao extends CrudRepository<Picture, Integer> {

	@Query(value="SELECT * FROM pictures p WHERE p.id_mangas = :id", nativeQuery=true)
	List<Picture> findAllById(Integer id);

}
