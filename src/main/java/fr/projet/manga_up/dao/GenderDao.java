package fr.projet.manga_up.dao;

import fr.projet.manga_up.model.Gender;
import fr.projet.manga_up.model.Genre;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GenderDao extends CrudRepository<Gender, Integer> {

    @Query("FROM Gender ")
    Page<Gender> findAll(Pageable pageable);

    @Query("FROM Gender ")
    List<Gender> findAllList();
}
