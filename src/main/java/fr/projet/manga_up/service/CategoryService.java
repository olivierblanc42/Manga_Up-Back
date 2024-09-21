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
	 * <p>Cette méthode cherche une catégorie dans la base de données en utilisant son identifiant.
	 * Si la catégorie est trouvée, elle est retournée. Sinon, une exception {@link ResponseStatusException}
	 * avec un statut HTTP 404 (NOT_FOUND) est levée pour indiquer que la catégorie n'a pas été trouvée.
	 *
	 * <p>Un message de débogage est enregistré pour suivre la récupération des informations de la catégorie.
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
	 * <p>Cette méthode utilise un objet {@link Pageable} pour définir les paramètres de pagination et de tri des résultats.
	 * Elle appelle le DAO pour obtenir une page de catégories en fonction de ces paramètres.
	 *
	 * <p>La méthode est conçue pour fournir une liste paginée de catégories, facilitant ainsi la gestion des grandes quantités
	 * de données en les divisant en pages plus petites.
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
	 * <p>Cette méthode convertit un objet {@link CategoryDto} en une entité {@link Category}, puis sauvegarde
	 * l'entité dans la base de données via le DAO. L'entité sauvegardée est ensuite convertie à nouveau en DTO
	 * et renvoyée.
	 *
	 * <p>La méthode est annotée avec {@link Transactional}, ce qui garantit que la création de la catégorie est
	 * effectuée dans une transaction. Si une erreur survient, la transaction est annulée.
	 *
	 * <p>Le message de log enregistré indique que l'ajout d'une nouvelle catégorie est en cours.
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
	 * <p>Cette méthode recherche une catégorie dans la base de données en utilisant son identifiant.
	 * Si la catégorie est trouvée, tous les mangas associés à cette catégorie sont dissociés
	 * (leur catégorie est mise à {@code null}), et chaque manga est sauvegardé. Ensuite, la catégorie est supprimée
	 * de la base de données.
	 *
	 * <p>La méthode est annotée avec {@link Transactional}, ce qui garantit que toutes les opérations
	 * (dissociation des mangas et suppression de la catégorie) sont effectuées dans une seule transaction.
	 * Si une erreur survient, la transaction est annulée.
	 *
	 * <p>Si la catégorie avec l'ID fourni n'existe pas, une {@link EntityNotFoundException} est levée.
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
	 * <p>Cette méthode cherche une catégorie dans la base de données en utilisant son identifiant.
	 * Si la catégorie est trouvée, ses attributs sont mis à jour avec les valeurs fournies dans l'objet
	 * {@link CategoryDto}. La catégorie mise à jour est ensuite sauvegardée dans la base de données
	 * et convertie à nouveau en DTO avant d'être renvoyée.
	 *
	 * <p>La méthode est annotée avec {@link Transactional}, ce qui garantit que la mise à jour de la catégorie
	 * est effectuée dans une transaction. Si une erreur survient, la transaction est annulée.
	 *
	 * <p>Le message de log enregistré indique que la mise à jour de la catégorie est en cours.
	 *
	 * <p>Il peut être nécessaire de mettre à jour les mangas associés à cette catégorie, si applicable.
	 * Cette logique n'est pas implémentée ici mais pourrait être ajoutée si nécessaire.
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
	 * <p>Cette méthode récupère toutes les entités {@link Category} depuis la base de données à l'aide du DAO.
	 * Ensuite, elle convertit chaque entité en un objet {@link CategoryDto} en utilisant un mapper, et collecte
	 * les résultats dans une liste.
	 *
	 * <p>Cette méthode est utile pour obtenir toutes les catégories disponibles et les transformer en DTO
	 * pour une utilisation dans des couches plus élevées comme la présentation ou le transfert de données.
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
