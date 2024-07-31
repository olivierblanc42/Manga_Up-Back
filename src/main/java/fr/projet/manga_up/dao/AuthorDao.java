package fr.projet.manga_up.dao;


import fr.projet.manga_up.model.Author;
import fr.projet.manga_up.model.Manga;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorDao extends CrudRepository<Author, Integer> {

    @Query( "  FROM Author  ")
    Page<Author> findAllAuthor(Pageable pageable);




}
