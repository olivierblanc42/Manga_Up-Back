package fr.projet.manga_up.dao;

import fr.projet.manga_up.model.Category;
import fr.projet.manga_up.model.Manga;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@SpringBootTest
public class MangaDaoTest {
    @Autowired
    private MangaDao mangaDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private GenreDao genreDao;

    @Autowired
    private CategoryDao categoryDao;

    @Test
    public void testSave() {
        // ARRANGE
        Category category = new Category();
        category.setName("Sport");
        category.setDescription("Description de la catégorie sport");
        categoryDao.save(category);

        Manga manga = new Manga();
        manga.setTitle("Haikyuu");
        manga.setCategory(category);


        //ACT
        Manga savedManga = mangaDao.save(manga);

        //ASSERT
        Assertions.assertNotNull(savedManga);
        Assertions.assertNotNull(savedManga.getId());
        Assertions.assertEquals("Haikyuu", savedManga.getTitle());
    }

    @Test
    public void testGetMangaByName() {
        //ARRANGE
        Category category = new Category();
        category.setName("Sport");
        category.setDescription("Description de la catégorie sport");
        categoryDao.save(category);

        Manga manga = new Manga();
        manga.setTitle("Haikyuu");
        manga.setCategory(category);


        // ACT
        List<Manga> mangas = mangaDao.getMangaByName(manga.getTitle());

        // ASSERT
        Assertions.assertNotNull(manga.getTitle());
        Assertions.assertEquals("Haikyuu", manga.getTitle());
    }
    @Test
    public void testFindNineManga() {
        //ARRANGE
        Category category = new Category();
        category.setName("Sport");
        category.setDescription("Description de la catégorie sport");
        categoryDao.save(category);

        Manga manga = new Manga();
        manga.setTitle("Haikyuu");
        manga.setCategory(category);


        // ACT
        List<Manga> mangas = mangaDao.findNineManga();

        // ASSERT
        Assertions.assertNotNull(mangas);
        Assertions.assertEquals("Haikyuu", mangas.get(0).getTitle());
    }

    @Test
    public void testFindMangaLimitOne() {
        //ARRANGE
        Category category = new Category();
        category.setName("Sport");
        category.setDescription("Description de la catégorie sport");
        categoryDao.save(category);

        Manga manga = new Manga();
        manga.setTitle("Haikyuu");
        manga.setCategory(category);


        // ACT
        Manga savedManga = mangaDao.findMangaLimitOne();

        // ASSERT
        Assertions.assertNotNull(savedManga);
        Assertions.assertEquals("Haikyuu", savedManga.getTitle());
    }

    @Test
    public void testFindMangaOrderByDate() {
        //ARRANGE
        Category category = new Category();
        category.setName("Sport");
        category.setDescription("Description de la catégorie sport");
        categoryDao.save(category);

        Manga manga = new Manga();
        manga.setReleaseDate(Instant.ofEpochSecond(2015- 2 -12));
        manga.setCategory(category);


        // ACT
        List<Manga> mangas = mangaDao.findMangaOrderByDate();

        // ASSERT
        Assertions.assertNotNull(mangas);
        Assertions.assertNotEquals(Instant.ofEpochSecond(2015- 2 -12), mangas.get(0).getReleaseDate());
    }

}
