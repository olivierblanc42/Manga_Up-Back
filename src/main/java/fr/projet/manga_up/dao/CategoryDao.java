package fr.projet.manga_up.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import fr.projet.manga_up.model.Category;

@Repository
public interface CategoryDao extends CrudRepository<Category, Integer> {

}