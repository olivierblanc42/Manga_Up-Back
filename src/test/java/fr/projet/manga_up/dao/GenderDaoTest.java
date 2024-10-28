package fr.projet.manga_up.dao;


import fr.projet.manga_up.model.Gender;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class GenderDaoTest {
    @Autowired
    private GenderDao genderDao;

    @Test
    public void test() {
        // ARRANGE
        Gender gender = new Gender();
        gender.setLabel("test");
        //ACT
      Gender savedGender =  genderDao.save(gender);
        //ASSERT
        Assertions.assertNotNull(savedGender);

    }
@Test
    public void testUpdateGender(){
    //ARRANGE
    Gender gender = new Gender();
        gender.setLabel("test2");
        genderDao.save(gender);
    //ACT
Gender saveGender = genderDao.save(gender);
saveGender.setLabel("update");
genderDao.save(saveGender);

Gender updatedGender = genderDao.findById(saveGender.getId()).get();
    //ASSERT
    Assertions.assertNotNull(updatedGender);
}


}
