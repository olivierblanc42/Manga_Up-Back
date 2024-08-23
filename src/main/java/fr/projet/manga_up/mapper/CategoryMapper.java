package fr.projet.manga_up.mapper;

import fr.projet.manga_up.dao.CategoryDao;
import fr.projet.manga_up.dao.MangaDao;
import fr.projet.manga_up.dto.CategoryDto;
import fr.projet.manga_up.model.Category;
import fr.projet.manga_up.model.Manga;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class CategoryMapper {

    @Autowired
    private CategoryDao categoryDao;
   @Autowired
   private MangaDao mangaDao;


    public CategoryDto toDto(Category category) {
        // Crée une nouvelle instance de CategoryDto
        CategoryDto dto = new CategoryDto();

        // Copie les valeurs des attributs de l'entité Category dans le DTO
        dto.setId(category.getId());  // Copie l'identifiant de la catégorie
        dto.setName(category.getName()); // Copie le nom de la catégorie
        dto.setDescription(category.getDescription()); // Copie la description de la catégorie
        dto.setCreatedAt(category.getCreatedAt()); // Copie la date de création de la catégorie
        dto.setMangaIds(category.getMangas().stream().map(Manga::getId).collect(Collectors.toList()));
        // Retourne le DTO rempli
        return dto;
    }



    public Category toEntity(CategoryDto dto) {
        // Crée une nouvelle instance de Category
        Category category = new Category();

        // Copie les valeurs des attributs du DTO dans l'entité Category
        category.setId(dto.getId());  // Définit l'identifiant de la catégorie à partir du DTO
        category.setName(dto.getName()); // Définit le nom de la catégorie à partir du DTO
        category.setDescription(dto.getDescription()); // Définit la description de la catégorie à partir du DTO
        category.setCreatedAt(dto.getCreatedAt()); // Définit la date de création de la catégorie à partir du DTO

        // Gestion des mangas associés
        if (dto.getMangaIds() != null) {

            // Conversion du Set en List
            List<Manga> mangasList = dto.getMangaIds().stream()
                    .map(id -> mangaDao.findById(id)
                            .orElseThrow(() -> new RuntimeException("Manga not found"))).distinct().collect(Collectors.toList());

            // Définition des mangas dans la catégorie
            category.setMangas(mangasList);
        }

        // Retourne l'entité remplie
        return category;
    }
}
