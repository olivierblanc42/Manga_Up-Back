package fr.projet.manga_up.service;



import fr.projet.manga_up.dao.AuthorDao;
import fr.projet.manga_up.dao.UserDao;
import fr.projet.manga_up.dto.AuthorDto;
import fr.projet.manga_up.dto.MangaDTO;
import fr.projet.manga_up.dto.UserDto;
import fr.projet.manga_up.mapper.AuthorMapper;
import fr.projet.manga_up.mapper.MangaMapper;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class AuthorService {

    private static final Logger LOGGER= LoggerFactory.getLogger(MangaService.class);

    @Autowired
    private AuthorDao authorDao;
    @Autowired
    private AuthorMapper authorMapper;
    @Autowired
    private MangaMapper mangaMapper;
    @Autowired
    private UserDao userDao;

    /**
     * Toute la liste des auteurs
     * */
    public Page<Author> getAllAuthor(Pageable pageable){
        LOGGER.info("getAllAuthor");
        return authorDao.findAllAuthor(pageable);
    }


    /**
     * un auteur par son id
     */

    public Author getAuthor(Integer id){
        Optional<Author> mo=authorDao.findById(id);
        if(mo.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Aucun auteur n'a été trouvé");
        }else {
            return mo.get();
        }
    }


/* public Author createAuthor(Author author){
        LOGGER.info("createAuthor");
        return authorDao.save(author);
  }*/


    public AuthorDto saveAuthorDto(AuthorDto authorDto) {
        if (authorDto.getLastName() == null || authorDto.getLastName().isEmpty()) {
            throw new IllegalArgumentException("Lastname cannot be null or empty");
        }
        Author author = authorMapper.toEntity(authorDto);
        Author savedAuthor = authorDao.save(author);
        return authorMapper.toDto(savedAuthor);
    }


    @Transactional
    public AuthorDto updateAuthorTest(Integer id,AuthorDto authorDto){
        //trouver l'auteur existant
        Author author = authorDao.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Author not found"));
        //Mettre à jour les attributs de l'entité
        author.setLastname(authorDto.getLastName());
        author.setFirstname(authorDto.getFirstName());
        author.setCreatedAt(authorDto.getCreatedAt());
        author.setDescription(authorDto.getDescription());
        author.setImg(authorDto.getImg());

        // voir si mettre a jour les mangas de ce cotés ?



        author = authorDao.save(author);
        LOGGER.info("updateAuthor");
        return authorMapper.toDto(author);

    }



    public  void deleteAuthor(Integer id){
        LOGGER.info("deleteAuthor");
        authorDao.deleteById(id);
    }

    public Set<AuthorDto> getAllUserDto2() {
        List<Author> authors = new ArrayList<>();
        authorDao.findAll().forEach(authors::add);

        return authors.stream()
                .map(authorMapper::toDto)
                .collect(Collectors.toSet());
    }

}
