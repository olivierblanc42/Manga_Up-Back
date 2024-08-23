package fr.projet.manga_up.mapper;

import fr.projet.manga_up.dao.GenreDao;
import fr.projet.manga_up.dao.MangaDao;
import fr.projet.manga_up.dto.GenreDto;
import fr.projet.manga_up.dto.MangaDTO;
import fr.projet.manga_up.model.Category;
import fr.projet.manga_up.model.Genre;
import fr.projet.manga_up.model.Manga;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;


@Component
public class GenreMapper {

    @Autowired
    private MangaDao mangaDao;

    public GenreDto toDTO(Genre genre) {
        GenreDto dto = new GenreDto();
        dto.setId(genre.getId());
        dto.setLabel(genre.getLabel());
        dto.setCreatedDate(genre.getCreatedDate());
        dto.setImg(genre.getImg());
        dto.setMangaIds(genre.getMangas().stream().map(Manga::getId).collect(Collectors.toSet()));
        return dto;
    }

    public Genre toEntity(GenreDto dto) {
        Genre genre = new Genre();
        genre.setId(dto.getId());
        genre.setLabel(dto.getLabel());
        genre.setCreatedDate(dto.getCreatedDate());
        genre.setImg(dto.getImg());
        if (dto.getMangaIds() != null) {
            genre.setMangas(dto.getMangaIds().stream()
                    .map(id -> mangaDao.findById(id)
                            .orElseThrow(() -> new RuntimeException("Manga not found")))
                    .collect(Collectors.toSet()));
        }
        return genre;
    }
}
