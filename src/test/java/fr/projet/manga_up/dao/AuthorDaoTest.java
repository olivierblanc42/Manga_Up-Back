package fr.projet.manga_up.dao;

import fr.projet.manga_up.model.Author;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AuthorDaoTest {

    @Autowired
    private AuthorDao authorDao;

    @Test
    public void TestSaveAuthor() {
        //ARRANGE

        Author author = new Author();
        author.setLastname("test");
        author.setFirstname("test");
        author.setDescription("Test Author");
        //ACT
        Author savedAuthor =  authorDao.save(author);
        //ASSERT
        Assertions.assertNotNull(savedAuthor);
        Assertions.assertNotNull(savedAuthor.getId());

    }


}
