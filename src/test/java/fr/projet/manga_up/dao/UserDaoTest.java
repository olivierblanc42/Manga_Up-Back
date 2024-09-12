package fr.projet.manga_up.dao;

import fr.projet.manga_up.model.Address;
import fr.projet.manga_up.model.Gender;
import fr.projet.manga_up.model.AppUser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
public class UserDaoTest {

    @Autowired
    private UserDao userDao;

    @Autowired
    private AddressDao addressDao;

    @Autowired
    private GenderDao genderDao;

    private String uniqueEmail = "test" + System.currentTimeMillis() + "@test.com";
    private String uniqueUsername = "testUser" + System.currentTimeMillis();

    @Test
    public void testSave() {
        // ARRANGE
        Address address = new Address();
        address.setLine1("4 Rue laplace");
        address.setCity("Besançon");
        address.setPostalCode("25000");
        Address savedAddress = addressDao.save(address);

        Gender gender = new Gender();
        gender.setLabel("H");
        Gender savedGender = genderDao.save(gender);

        AppUser user = new AppUser();
        user.setUsername(uniqueUsername);
        user.setEmail(uniqueEmail);
        user.setPassword("password");
        user.setAddress(savedAddress);
        user.setGender(savedGender);

        // ACT
        AppUser savedUser = userDao.save(user);

        // ASSERT
        Assertions.assertNotNull(savedUser);
        Assertions.assertNotNull(savedUser.getId());
        Assertions.assertEquals(uniqueUsername, savedUser.getUsername());
        Assertions.assertEquals(uniqueEmail, savedUser.getEmail());
    }

    @Test
    public void testFindByUsername() {
        // ARRANGE
        Address address = new Address();
        address.setLine1("4 Rue laplace");
        address.setCity("Besançon");
        address.setPostalCode("25000");
        Address savedAddress = addressDao.save(address);

        Gender gender = new Gender();
        gender.setLabel("H");
        Gender savedGender = genderDao.save(gender);

        AppUser user = new AppUser();
        user.setUsername(uniqueUsername);
        user.setEmail(uniqueEmail);
        user.setPassword("password");
        user.setAddress(savedAddress);
        user.setGender(savedGender);

        // ACT
        AppUser savedUser = userDao.save(user);
        AppUser foundUser = userDao.findByUsername(uniqueUsername);

        // ASSERT
        Assertions.assertNotNull(foundUser);
        Assertions.assertEquals(uniqueUsername, foundUser.getUsername());
        Assertions.assertEquals(uniqueEmail, foundUser.getEmail());
    }

    @Test
    @Transactional
    public void testGetAllMangaByUserId() {
        //ARRANGE
        Integer userId = 1;  // Assurez-vous que cet utilisateur existe dans votre base de données


        // ACT
        List<Integer> mangaIds = userDao.getAllMangaByUserId(userId);

        // ASSERT
        Assertions.assertNotNull(mangaIds);
       // Assertions.assertFalse(mangaIds.isEmpty());


        //Assertions.assertTrue(mangaIds.contains(2));

    }
}
