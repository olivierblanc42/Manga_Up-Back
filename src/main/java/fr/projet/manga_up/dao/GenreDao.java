package fr.projet.manga_up.dao;

import fr.projet.manga_up.model.Manga;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import fr.projet.manga_up.model.Genre;

import java.util.List;

@Repository
public interface GenreDao extends CrudRepository<Genre, Integer> {



    @Query(value = "SELECT * FROM genre Limit 6 ", nativeQuery = true)
    List<Genre>  findGenreLimit6();



    @Query(  "FROM Genre  ")
    Page<Genre> findAllGenrePageable(Pageable pageable);
}
