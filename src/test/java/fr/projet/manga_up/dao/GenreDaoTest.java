package fr.projet.manga_up.dao;


import fr.projet.manga_up.model.Genre;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.Instant;

@SpringBootTest
public class GenreDaoTest {

    @Autowired
    private GenreDao genreDao;


    @Test
    public void testAdd(){
        //ARRANGE
        Genre genre = new Genre();
        genre.setLabel("Test Genre");
        genre.setCreatedDate(Instant.ofEpochSecond(2015- 2 -12));
        //ACT
        Genre savedGenre = genreDao.save(genre);
        //ASSERT
        Assertions.assertNotNull(savedGenre);
        Assertions.assertNotNull(savedGenre.getId());
    }


    @Test
    public void testUpdate(){
        //ARRANGE
        Genre genre = new Genre();
        genre.setLabel("Test Genre");
        genre.setCreatedDate(Instant.ofEpochSecond(2015- 2 -12));
        //ACT
      Genre savedGenre =  genreDao.save(genre);
        savedGenre.setLabel("New Genre");
        genreDao.save(savedGenre);
        Genre updatedGenre = genreDao.findById(savedGenre.getId()).get();
        //ASSERT
        Assertions.assertNotNull(updatedGenre);
        Assertions.assertNotNull(updatedGenre.getLabel());

    }

@Test
    public void testFindAlGenre(){
    //ARRANGE
    Genre genre = new Genre();
        genre.setLabel("Test Genre");
        genre.setCreatedDate(Instant.ofEpochSecond(2015- 2 -12));


        Genre genre2 = new Genre();
        genre2.setLabel("Test Genre List");
        genre2.setCreatedDate(Instant.ofEpochSecond(2015- 2 -12));

    // ACT
    Genre savedGenre = genreDao.save(genre);
    Genre savedGenre2 = genreDao.save(genre2);
    Page<Genre> genres = genreDao.findAllGenrePageable(Pageable.unpaged());

// ASSERT
    Assertions.assertNotNull(savedGenre);
    Assertions.assertNotNull(savedGenre2);
    Assertions.assertNotNull(genres);
}




}
