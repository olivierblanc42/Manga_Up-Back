package fr.projet.manga_up.service;



import fr.projet.manga_up.dao.AuthorDao;
import fr.projet.manga_up.model.Author;
import fr.projet.manga_up.model.Manga;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Component
public class AuthorService {

    private static final Logger LOGGER= LoggerFactory.getLogger(MangaService.class);

    @Autowired
    private AuthorDao authorDao;

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
 public Author createAuthor(Author author){
        LOGGER.info("createAuthor");
        return authorDao.save(author);
  }

  public  void deleteAuthor(Integer id){
        LOGGER.info("deleteAuthor");
        authorDao.deleteById(id);
  }
}
