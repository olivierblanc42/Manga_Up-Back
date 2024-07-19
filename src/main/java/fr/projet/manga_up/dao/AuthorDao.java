package fr.projet.manga_up.dao;


import fr.projet.manga_up.model.Author;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorDao extends CrudRepository<Author, Integer> {

    @Query(value = "SELECT * FROM author " , nativeQuery = true)
    List<Author> findAllAuthor();

}
