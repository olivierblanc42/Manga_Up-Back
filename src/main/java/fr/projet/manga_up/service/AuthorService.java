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
     * Récupère une page paginée d'auteurs.
     *
     * @param pageable un objet {@link Pageable} contenant les informations de pagination (numéro de la page, taille de la page)
     *                et de tri des résultats
     * @return une page d'auteurs {@link Page<Author>} correspondant aux paramètres de pagination fournis
     */
    public Page<Author> getAllAuthor(Pageable pageable){
        LOGGER.info("getAllAuthor");
        return authorDao.findAllAuthor(pageable);
    }

    /**
     * Récupère un auteur par son identifiant.
     *
     * @param id l'identifiant unique de l'auteur à récupérer
     * @return l'objet {@link Author} correspondant à l'ID fourni
     * @throws ResponseStatusException si aucun auteur n'est trouvé avec l'ID donné
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

    /**
     * Sauvegarde un nouvel auteur à partir d'un DTO.
     *
     * @param authorDto un objet {@link AuthorDto} contenant les informations de l'auteur à sauvegarder
     * @return un objet {@link AuthorDto} représentant l'auteur sauvegardé
     * @throws IllegalArgumentException si le nom de famille de l'auteur est nul ou vide
     */
    public AuthorDto saveAuthorDto(AuthorDto authorDto) {
        if (authorDto.getLastName() == null || authorDto.getLastName().isEmpty()) {
            throw new IllegalArgumentException("Lastname cannot be null or empty");
        }
        Author author = authorMapper.toEntity(authorDto);
        Author savedAuthor = authorDao.save(author);
        return authorMapper.toDto(savedAuthor);
    }

    /**
     * Met à jour les informations d'un auteur existant par son identifiant.
     *
     * @param id l'identifiant unique de l'auteur à mettre à jour
     * @param authorDto un objet {@link AuthorDto} contenant les nouvelles valeurs des attributs de l'auteur
     * @return un objet {@link AuthorDto} représentant l'auteur mis à jour
     * @throws EntityNotFoundException si aucun auteur n'est trouvé avec l'ID donné
     */
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


    /**
     * Supprime un auteur par son identifiant.
     *
     * @param id l'identifiant unique de l'auteur à supprimer
     */
    public  void deleteAuthor(Integer id){
        Author author = authorDao.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Genre not found"));


        for(Manga manga : author.getMangas() ){
            manga.getAuthors().remove(author);
        }

        author.getMangas().clear();

        authorDao.delete(author);
    }





    /**
     * Récupère l'ensemble des auteurs sous forme de DTO.
     *
     * @return un ensemble de {@link AuthorDto} représentant tous les auteurs disponibles dans la base de données
     */
    public Set<AuthorDto> getAllAuthorDto2() {
        List<Author> authors = new ArrayList<>();
        authorDao.findAll().forEach(authors::add);

        return authors.stream()
                .map(authorMapper::toDto)
                .collect(Collectors.toSet());
    }

}
