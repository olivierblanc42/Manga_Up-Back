package fr.projet.manga_up.dao;


import fr.projet.manga_up.model.Category;
import org.glassfish.jaxb.runtime.v2.schemagen.xmlschema.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@SpringBootTest
public class CategoryDaoTest {

  @Autowired
    private CategoryDao categoryDao;




  @Test
 public void testSaveCategory(){
      //ARRANGE

      Category category = new Category();
      category.setName("test");
      category.setDescription("description");

      //ACT
    Category savedCategory  = categoryDao.save(category);

      //ASSERT
      Assertions.assertNotNull(savedCategory);
      Assertions.assertNotNull(category.getId());
  }


@Test
    public void testUpdateCategory(){
    //ARRANGE
    Category category = new Category();
      category.setName("test");
      category.setDescription("description");
//ACT
    Category savedCategory  = categoryDao.save(category);
      savedCategory.setDescription("updated");
      categoryDao.save(savedCategory);
      Category updatedCategory = categoryDao.findById(savedCategory.getId()).get();
    //ASSERT

    Assertions.assertNotNull(updatedCategory);
      Assertions.assertNotNull(updatedCategory.getDescription());

}



@Test
    public void testFindAllCategories(){
      //ARRANGE
      Category category = new Category();
      category.setName("test");
      category.setDescription("description");
      Category savedCategory  = categoryDao.save(category);

      Category category2 = new Category();
      category2.setName("test2");
      category2.setDescription("description2");
      Category savedCategory2  = categoryDao.save(category2);
    // ACT
    Page<Category> categories = categoryDao.findAllCategoriesPageable(Pageable.unpaged());

    // ASSERT
    Assertions.assertNotNull(savedCategory);
    Assertions.assertNotNull(savedCategory2);
    Assertions.assertNotNull(categories);
}


}
