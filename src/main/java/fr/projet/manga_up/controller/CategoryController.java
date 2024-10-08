package fr.projet.manga_up.controller;

import fr.projet.manga_up.dto.AuthorDto;
import fr.projet.manga_up.dto.CategoryDto;
import fr.projet.manga_up.model.Genre;
import fr.projet.manga_up.model.Manga;
import fr.projet.manga_up.model.Picture;
import fr.projet.manga_up.service.MangaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import fr.projet.manga_up.model.Category;
import fr.projet.manga_up.service.CategoryService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/categories")
public class CategoryController {

	private static final Logger LOGGER = LoggerFactory.getLogger(CategoryController.class);

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private MangaService mangaService;



	@Operation(summary = "Récupère une catégorie avec l'id'", description = "Retourne une catégorie")
	@ApiResponse(responseCode = "201", description = "la catégorie a été trouvé avec succès")
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Category> getCategoryId(@PathVariable Integer id) {
		LOGGER.info("Obtenir une categorie");
		Category category = categoryService.getCategory(id);
		return ResponseEntity.ok(category);
	}



	@Operation(summary= "Récupération des Catégories avec la pagination")
	@ApiResponse(responseCode = "201", description = "une mist de Catégories a bien été trouver")
	@GetMapping()
	public ResponseEntity<Page<Category>> getCategories(
			@PageableDefault(
					page = 0,
					size = 9,
					sort="createdAt",
					direction = Sort.Direction.DESC) Pageable pageable

	) {
		LOGGER.info("Récupération de la liste des categories");
		Page<Category> category = categoryService.getCategories(pageable);
		LOGGER.info("pageable : {}", pageable);

		LOGGER.info("Mangas : {}", category);
		return ResponseEntity.ok(category);
	}





	/**
	 *
	 * @param id L'id qui représente le Genre que l'on souhaite obtenir.
	 * @param pageable Pagination pour les Manga. Récupère notamment le numéro de page demandé.
	 * Ex d'url qui doit être utilisé : localhost:8080/users?page=2&size=5&sort=createdAt,DESC
	 * @return Retourne le Manga de l'id spécifié + les 6 premiers commentaires si on arrive pour
	 * la première fois sur la page. Sinon récupère la page demandé par l'utilisateur grâce à la pagination.
	 */
	@Operation(summary = "Récupère des categories avec l'id'", description = "Retourne des categories")
	@GetMapping(value="category/{id}", produces= MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getCategory(
			@PathVariable("id") Integer id,
			@PageableDefault(
					page = 0,
					size = 10,
					sort="createdAt",
					direction = Sort.Direction.DESC) Pageable pageable
	){
		LOGGER.info("Pageable : {}", pageable);
		LOGGER.info("Dans controller getMangaId, id : {}", id);
		Map<String, Object> response = new HashMap<>();
		Category category = categoryService.getCategory(id);
		Page<Manga> mangas = mangaService.getMangaByIdCategory(id,pageable);
		response.put("category",category);
		LOGGER.info("mangas : {}", mangas);
		response.put("mangas",mangas);
		return ResponseEntity.ok(response);

	}



	@Operation(summary= "Creation d' une nouvelle catégorie")
	@ApiResponse(responseCode = "201", description = " une nouvelle categorie a bien été crée")
	@PostMapping
	public ResponseEntity<CategoryDto> createAuthor(@RequestBody CategoryDto categoryDto) {
		LOGGER.info("createAuthor : {}", categoryDto);
		CategoryDto createdCategory = categoryService.createdCategory(categoryDto);
		return ResponseEntity.ok(createdCategory);
	}


	@Operation(summary= "suppression d'une catégorie")
	@ApiResponse(responseCode = "201", description = "la catégorie à bien été supprimé")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteCategory(@PathVariable Integer id) {
		LOGGER.info("deleteCategory : {}", id);
		categoryService.deleteCategory(id);
		return ResponseEntity.ok().build();
	}

	@Operation(summary= "mise à d'une catégorie")
	@ApiResponse(responseCode = "201", description = "la catégorie à bien été mise à jour")
	@PutMapping("/dto/{id}")
	public ResponseEntity<CategoryDto> updateCategory(@PathVariable Integer id, @RequestBody CategoryDto categoryDto) {
		try{
			CategoryDto updateCategory = categoryService.updatedCategory(id,categoryDto);
			return ResponseEntity.ok(updateCategory);
		}catch (EntityNotFoundException e){
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

	}

	@GetMapping("/dto")
	public ResponseEntity<List<CategoryDto>> getAllCategory() {
		List<CategoryDto> categoryDto = categoryService.getAllCategoryDto();
		return ResponseEntity.ok(categoryDto);
	}



}
