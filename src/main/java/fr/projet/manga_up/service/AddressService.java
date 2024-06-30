package fr.projet.manga_up.service;

import fr.projet.manga_up.dao.AddressDao;
import fr.projet.manga_up.model.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Component
public class AddressService {

    @Autowired
    private AddressDao addressDao;


    public Address saveAddress(Address address){
        System.out.println("j'enregistre une addresse");
        return addressDao.save(address);

    }

    public void deleteAddressById(Integer id){
        System.out.println("je supprime une addresse");
        if (addressDao.existsById(id)) {
            addressDao.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "L'adresse n'existe pas");
        }

    }


    public Address getAddress(Integer id) {
        Optional<Address> addressOptional = addressDao.findById(id);
        if (addressOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Address n'existe pas");
        }else{
            return addressOptional.get();
        }
    }

}
