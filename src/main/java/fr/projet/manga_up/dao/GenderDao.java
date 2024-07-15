package fr.projet.manga_up.dao;

import fr.projet.manga_up.model.Gender;
import fr.projet.manga_up.model.Genre;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenderDao extends CrudRepository<Gender, Integer> {
}
