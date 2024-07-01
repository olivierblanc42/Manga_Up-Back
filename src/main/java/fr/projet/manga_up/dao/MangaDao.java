package fr.projet.manga_up.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import fr.projet.manga_up.model.Manga;

@Repository
public interface MangaDao extends CrudRepository<Manga, Integer> {

	@Query(value ="SELECT * FROM manga LIMIT 10", nativeQuery = true)
	List<Manga> FindTenManga();

	@Query(value ="SELECT * FROM manga ", nativeQuery = true)
	List<Manga> findAllManga();

}
