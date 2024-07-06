package fr.projet.manga_up.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import fr.projet.manga_up.model.Manga;

@Repository
public interface MangaDao extends CrudRepository<Manga, Integer> {

	@Query(value ="SELECT * FROM manga LIMIT 9", nativeQuery = true)
	List<Manga> findNineManga();

	@Query(value ="SELECT * FROM manga ", nativeQuery = true)
	List<Manga> findAllManga();

	@Query(value = "SELECT * FROM manga LIMIT 1" , nativeQuery = true)
	List<Manga> findMangaLimitOne();

	@Query(value = "SELECT * FROM `manga` ORDER BY `release_date` DESC Limit 9;" , nativeQuery = true)
    List<Manga> findMangaOrderByDate();


}
