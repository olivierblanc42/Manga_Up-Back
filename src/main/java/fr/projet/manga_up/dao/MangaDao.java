package fr.projet.manga_up.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;

import fr.projet.manga_up.model.Manga;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public interface MangaDao extends CrudRepository<Manga, Integer> {

	@Query("SELECT m FROM Manga m WHERE m.title LIKE %:name%")
	List<Manga> getMangaByName(@Param("name") String name);

	/*@Query(value = "SELECT m FROM Manga m JOIN m.genres g WHERE g.label = :genreLabel")
	List<Manga> getMangaByGenre(@Param("genreLabel") String genreLabel);*/

	@Query(value ="SELECT * FROM manga LIMIT 9", nativeQuery = true)
	List<Manga> findNineManga();

	@Modifying
	@Query(value = "INSERT INTO user_manga (id_user, id_manga) VALUES (?, ?)", nativeQuery = true)
	void addUserInFavorite(@Param("idUser") Integer idUser, @Param("idManga") Integer idManga);

	@Modifying
	@Query(value = "DELETE FROM `user_manga` um WHERE um.id_user= :idUser AND um.id_manga= :idManga", nativeQuery = true)
	void deleteUserAsFavorite(@Param("idUser") Integer idUser, @Param("idManga") Integer idManga);

	@Query(value ="SELECT * FROM manga ", nativeQuery = true)
	List<Manga> findAllManga();

	@Query(value = "SELECT * FROM manga LIMIT 1" , nativeQuery = true)
	Manga findMangaLimitOne();

	@Query(value = "SELECT * FROM `manga` ORDER BY `release_date` DESC Limit 9;" , nativeQuery = true)
    List<Manga> findMangaOrderByDate();


	@Query(  "FROM Manga  ")
	Page<Manga> findAllMangaPageable(Pageable pageable);


	/**
	 *  requete pour recuperer les mangas avec l'id du genre
	 **/
	@Query(value="SELECT m FROM Manga m JOIN m.genres g WHERE g.id = :idGenre" )
	Page<Manga>findAllMangaByIdGenre(@Param("idGenre")Integer idGenre,Pageable pageable);

	      /**
			*  requete pour recuperer les manga avec l'id de category
			**/
	@Query(value="SELECT m FROM Manga m JOIN m.category c WHERE c.id = :idCategory" )
	Page<Manga>findAllMangaByIdCategory(@Param("idCategory")Integer idCategory ,Pageable pageable);

	/**
	 *  requete pour recuperer les mangas avec l'id de l'auteur
	 **/
	@Query(value = "SELECT m FROM Manga m  JOIN m.authors a  WHERE a.id = :authorId")
	Page<Manga> findAllMangaByIdAuthor(@Param("authorId") Integer authorId,Pageable pageable);


	@Query(value ="SELECT * FROM manga", nativeQuery = true)
	List<Manga> findAllMangaNotPagiantion();
}
