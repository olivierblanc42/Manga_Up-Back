package fr.projet.manga_up.mapper;

import fr.projet.manga_up.dao.MangaDao;
import fr.projet.manga_up.dto.AuthorDto;
import fr.projet.manga_up.model.Author;
import fr.projet.manga_up.model.Manga;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class AuthorMapper {
    @Autowired
    private MangaDao mangaDao;

    public AuthorDto toDto(Author author) {
        AuthorDto dto = new AuthorDto();
        dto.setId(author.getId());
        dto.setLastName(author.getLastname());
        dto.setFirstName(author.getFirstname());
        dto.setDescription(author.getDescription());
        dto.setCreatedAt(author.getCreatedAt());
        dto.setImg(author.getImg());
        dto.setDescription(author.getDescription());



        return dto;

    }
    public Author toEntity(AuthorDto dto) {
        Author author = new Author();
        author.setId(dto.getId());
        author.setLastname(dto.getLastName());
        author.setFirstname(dto.getFirstName());
        author.setDescription(dto.getDescription());
        author.setCreatedAt(dto.getCreatedAt());
        author.setImg(dto.getImg());

        // Log pour débogage
        System.out.println("Author Entity: " + author);

        return author;

    }



}
