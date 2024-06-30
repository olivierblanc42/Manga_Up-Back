package fr.projet.manga_up.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import fr.projet.manga_up.model.Genre;

@Repository
public interface GenreDao extends CrudRepository<Genre, Integer> {

}
