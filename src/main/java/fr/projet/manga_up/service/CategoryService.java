package fr.projet.manga_up.service;

import java.util.Optional;

import fr.projet.manga_up.model.Manga;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import fr.projet.manga_up.controller.CategoryController;
import fr.projet.manga_up.dao.CategoryDao;
import fr.projet.manga_up.model.Category;

@Component
public class CategoryService {

	private static final Logger LOGGER = LoggerFactory.getLogger(CategoryController.class);

	@Autowired
	private CategoryDao categoryDao;

	public Category getCategory(Integer id) {
		Optional<Category> categoryOptional = categoryDao.findById(id);
		LOGGER.debug("Récupération info genre");
		if (categoryOptional.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "cette catégori n'a pas été trouvé");
		} else {
			return categoryOptional.get();
		}
	}

	public Page<Category> getCategories(Pageable pageable){
		return categoryDao.findAllCategoriesPageable(pageable);
}

   public Category createCategory(Category category) {
		return categoryDao.save(category);
   }


   public void deleteCategory(Integer id) {
		 categoryDao.deleteById(id);
   }



}
