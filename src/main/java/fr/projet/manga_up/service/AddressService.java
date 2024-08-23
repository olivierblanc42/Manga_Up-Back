package fr.projet.manga_up.service;

import fr.projet.manga_up.dao.AddressDao;
import fr.projet.manga_up.dto.AddressDto;
import fr.projet.manga_up.mapper.AddressMapper;
import fr.projet.manga_up.model.Address;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Component
public class AddressService {

    private static final Logger LOGGER= LoggerFactory.getLogger(AddressService.class);


    @Autowired
    private AddressDao addressDao;
    @Autowired
    private AddressMapper addressMapper;


    public Address getAddress(Integer id) {
        Optional<Address> addressOptional = addressDao.findById(id);
        if (addressOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Address n'existe pas");
        }else{
            return addressOptional.get();
        }
    }


   @Transactional
    public AddressDto saveAddress(AddressDto addressDto) {
        LOGGER.info("getAuthorDto");
        Address address = addressMapper.toEntity(addressDto);
        address = addressDao.save(address);
        return addressMapper.toDto(address);
    }



    public void deleteAddressById(Integer id){
        System.out.println("je supprime une addresse");
        if (addressDao.existsById(id)) {
            addressDao.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "L'adresse n'existe pas");
        }

    }


    @Transactional
    public AddressDto updateAddress(Integer id ,AddressDto addressDto) {
        // trouver l'adresse éxistante
        Address address = addressDao.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Address n'existe pas"));
        //Mettre à jour les attributs de l'entité

        address.setLine1(addressDto.getLine1());
        address.setLine2(addressDto.getLine2());
        address.setLine3(addressDto.getLine3());
        address.setCity(addressDto.getCity());
        address.setPostalCode(addressDto.getPostalCode());

        address = addressDao.save(address);
        return addressMapper.toDto(address);
    }

}
