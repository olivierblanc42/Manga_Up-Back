package fr.projet.manga_up.mapper;

import fr.projet.manga_up.dao.AuthorDao;
import fr.projet.manga_up.dao.CategoryDao;
import fr.projet.manga_up.dao.GenreDao;
import fr.projet.manga_up.dto.MangaDTO;
import fr.projet.manga_up.model.Author;
import fr.projet.manga_up.model.Category;
import fr.projet.manga_up.model.Manga;
import fr.projet.manga_up.model.Genre;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;


@Component
public class MangaMapper {

    @Autowired
    private GenreDao genreDao;
    @Autowired
    private AuthorDao authorDao;
    @Autowired
    private CategoryDao categoryDao;


    public static MangaDTO toDto(Manga manga) {
        MangaDTO dto = new MangaDTO();
        dto.setId(manga.getId());
        dto.setTitle(manga.getTitle());
        dto.setReleaseDate(manga.getReleaseDate());
        dto.setSummary(manga.getSummary());
        dto.setCreatedAt(manga.getCreatedAt());
        dto.setPrice(manga.getPrice());
        dto.setPointFidelity(manga.getPointFidelity());
        dto.setCategoryId(manga.getCategory() != null ? manga.getCategory().getId() : null);
        dto.setGenreIds(manga.getGenres().stream().map(Genre::getId).collect(Collectors.toSet()));
        dto.setAuthorIds(manga.getAuthors().stream().map(Author::getId).collect(Collectors.toSet()));
        return dto;
    }

    public Manga toEntity(MangaDTO dto) {
        Manga manga = new Manga();
        manga.setId(dto.getId());
        manga.setTitle(dto.getTitle());
        manga.setReleaseDate(dto.getReleaseDate());
        manga.setSummary(dto.getSummary());
        manga.setCreatedAt(dto.getCreatedAt());
        manga.setPrice(dto.getPrice());
        manga.setPointFidelity(dto.getPointFidelity());


        if (dto.getCategoryId() != null) {
            Category category = categoryDao.findById(dto.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Category not found"));
            manga.setCategory(category);
        }


        if (dto.getGenreIds() != null) {
            Set<Genre> genres = dto.getGenreIds().stream()
                    .map(id -> genreDao.findById(id)
                            .orElseThrow(() -> new RuntimeException("Genre not found")))
                    .collect(Collectors.toSet());
            manga.setGenres(genres);
        } else {
            manga.setGenres(Collections.emptySet()); // Default to empty set if no genres are provided
        }


        if (dto.getAuthorIds() != null) {
            Set<Author> authors = dto.getAuthorIds().stream()
                    .map(id -> authorDao.findById(id)
                            .orElseThrow(() -> new RuntimeException("Author not found")))
                    .collect(Collectors.toSet());
            manga.setAuthors(authors);
        } else {
            manga.setAuthors(Collections.emptySet()); // Default to empty set if no authors are provided
        }

        return manga;
    }


    public static void updateEntityFromDto(MangaDTO dto, Manga manga) {
        manga.setTitle(dto.getTitle());
        manga.setPrice(dto.getPrice());
        manga.setPointFidelity(dto.getPointFidelity());
        manga.setSummary(dto.getSummary());
    }


}
