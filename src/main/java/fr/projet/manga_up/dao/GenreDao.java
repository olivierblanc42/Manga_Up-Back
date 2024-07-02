package fr.projet.manga_up.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import fr.projet.manga_up.model.Genre;

import java.util.List;

@Repository
public interface GenreDao extends CrudRepository<Genre, Integer> {


    @Query(value ="SELECT * FROM genre", nativeQuery = true)
    List<Genre> findALLGenre();


    @Query(value ="SELECT * FROM genre LIMIT 6", nativeQuery = true)
    List<Genre> findSixGenres();
}
