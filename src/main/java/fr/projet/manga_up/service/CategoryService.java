package fr.projet.manga_up.service;

import java.util.*;
import java.util.stream.Collectors;

import fr.projet.manga_up.dao.MangaDao;
import fr.projet.manga_up.dto.AuthorDto;
import fr.projet.manga_up.dto.CategoryDto;
import fr.projet.manga_up.mapper.CategoryMapper;
import fr.projet.manga_up.model.Author;
import fr.projet.manga_up.model.Manga;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import fr.projet.manga_up.controller.CategoryController;
import fr.projet.manga_up.dao.CategoryDao;
import fr.projet.manga_up.model.Category;

@Component
public class CategoryService {

	private static final Logger LOGGER = LoggerFactory.getLogger(CategoryController.class);

	@Autowired
	private CategoryDao categoryDao;
	@Autowired
	private CategoryMapper categoryMapper;
	@Autowired
	private MangaDao mangaDao;




	/**
	 * Récupère une catégorie par son identifiant.
	 *
	 * @param id l'identifiant unique de la catégorie à récupérer
	 * @return l'objet {@link Category} correspondant à l'ID fourni
	 * @throws ResponseStatusException si la catégorie avec l'ID donné n'est pas trouvée
	 */
	public Category getCategory(Integer id) {
		Optional<Category> categoryOptional = categoryDao.findById(id);
		LOGGER.debug("Récupération info genre");
		if (categoryOptional.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "cette catégori n'a pas été trouvé");
		} else {
			return categoryOptional.get();
		}
	}

	/**
	 * Récupère une page paginée de catégories.
	 *
	 * @param pageable un objet {@link Pageable} contenant les informations de pagination (numéro de la page, taille de la page)
	 *                et de tri des résultats
	 * @return une page de catégories {@link Page<Category>} correspondant aux paramètres de pagination fournis
	 */
	public Page<Category> getCategories(Pageable pageable){

		return categoryDao.findAllCategoriesPageable(pageable);
	}



	/**
	 * Crée une nouvelle catégorie à partir d'un DTO.
	 *
	 * @param categoryDto un objet {@link CategoryDto} contenant les informations de la catégorie à créer
	 * @return un objet {@link CategoryDto} représentant la catégorie créée
	 */
	@Transactional
	public CategoryDto createdCategory(CategoryDto categoryDto) {
		LOGGER.info("addAuthorDto");
		Category category = categoryMapper.toEntity(categoryDto);
		category = categoryDao.save(category);
		return categoryMapper.toDto(category);
	}

	/**
	 * Supprime une catégorie par son identifiant après avoir dissocié les mangas associés.
	 *
	 * @param categoryId l'identifiant unique de la catégorie à supprimer
	 * @throws EntityNotFoundException si la catégorie avec l'ID donné n'existe pas
	 */
	@Transactional
	public void deleteCategory(int categoryId ) {
		Category category = categoryDao.findById(categoryId)
				.orElseThrow(()-> new EntityNotFoundException("La catégorie n'existe pas"));

		// Dissocier les mangas
		for (Manga manga : category.getMangas()){
			manga.setCategory(null);
			mangaDao.save(manga);
		}
		categoryDao.delete(category);
	}




	/**
	 * Met à jour les informations d'une catégorie existante par son identifiant.
	 *
	 * @param id l'identifiant unique de la catégorie à mettre à jour
	 * @param categoryDto un objet {@link CategoryDto} contenant les nouvelles valeurs des attributs de la catégorie
	 * @return un objet {@link CategoryDto} représentant la catégorie mise à jour
	 * @throws EntityNotFoundException si la catégorie avec l'ID donné n'est pas trouvée
	 */
	@Transactional
	public CategoryDto updatedCategory(Integer id,CategoryDto categoryDto) {
		//trouver l'auteur existant
		Category category = categoryDao.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Category not found"));
		//Mettre à jour les attributs de l'entité
		category.setName(categoryDto.getName());
		category.setDescription(categoryDto.getDescription());

		// voir m'est à jour les manga coté category


		category = categoryDao.save(category);
		LOGGER.info("updateCategory");
		return categoryMapper.toDto(category);
	}


	/**
	 * Récupère la liste complète des catégories sous forme de DTO.
	 *
	 * @return une liste de {@link CategoryDto} représentant toutes les catégories disponibles dans la base de données
	 */
	public List<CategoryDto> getAllCategoryDto() {
		// Crée une nouvelle liste pour stocker les entités Category récupérées de la base de données
		List<Category> categories = new ArrayList<>();

		// Utilise la méthode findAll() du DAO pour récupérer toutes les catégories de la base de données
		// Ensuite, ajoute chaque catégorie récupérée à la liste 'categories'
		categoryDao.findAll().forEach(categories::add);

		// Utilise un flux (stream) pour convertir chaque entité Category en un DTO (Data Transfer Object)
		// à l'aide du mapper, puis collecte les résultats dans une liste
		return categories.stream()
				.map(categoryMapper::toDto)
				.collect(Collectors.toList());
	}

}
