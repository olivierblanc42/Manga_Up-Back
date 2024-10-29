package fr.projet.manga_up.dao;


import fr.projet.manga_up.model.Address;
import fr.projet.manga_up.model.Category;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@SpringBootTest
public class AddressDaoTest {
    @Autowired
    private AddressDao addressDao;

    @Test
    public void testSaveAddress(){
        //ARRANGE

         Address address = new Address();
         address.setCity("San Francisco");
         address.setCountry("USA");
         address.setLine1("1");
        address.setLine2("1");
        address.setLine3("1");
        address.setPostalCode("1");
        //ACT
     Address savedAddress = addressDao.save(address);
        //ASSERT
        Assertions.assertNotNull(savedAddress);
        Assertions.assertNotNull(savedAddress.getId());
    }


    @Test
    public void testUpdateAddress(){
        //ARRANGE
        Address address = new Address();
        address.setCity("San Francisco");
        address.setCountry("USA");
        address.setLine1("1");
        address.setLine2("1");
        address.setLine3("1");
        address.setPostalCode("1");





       //ACT
        Address savedAddress  = addressDao.save(address);
        savedAddress.setCountry("France");
        addressDao.save(savedAddress);
        Address updatedAddress = addressDao.findById(savedAddress.getId()).get();
        //ASSERT

        Assertions.assertNotNull(updatedAddress);
        Assertions.assertNotNull(updatedAddress.getCity());

    }

    @Test
    public void testFindAllCategories(){
        //ARRANGE
        Address address = new Address();
        address.setCity("San TEST Francisco");
        address.setCountry("USA");
        address.setLine1("1");
        address.setLine2("1");
        address.setLine3("1");
        address.setPostalCode("1");

        Address address2 = new Address();
        address2.setCity("San Francisco");
        address2.setCountry("USA");
        address2.setLine1("1");
        address2.setLine2("1");
        address2.setLine3("1");
        address2.setPostalCode("12");
        // ACT
        Address savedAddress1  = addressDao.save(address);
        Address savedAddress2 = addressDao.save(address2);

        Page<Address> addresses = addressDao.findAllAddresses(Pageable.unpaged());

        // ASSERT
        Assertions.assertNotNull(savedAddress2);
        Assertions.assertNotNull(savedAddress1);
        Assertions.assertNotNull(addresses);
    }


}
