package fr.projet.manga_up.service;

import fr.projet.manga_up.dao.AddressDao;
import fr.projet.manga_up.dto.AddressDto;
import fr.projet.manga_up.mapper.AddressMapper;
import fr.projet.manga_up.model.Address;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class AddressService {

    private static final Logger LOGGER= LoggerFactory.getLogger(AddressService.class);


    @Autowired
    private AddressDao addressDao;
    @Autowired
    private AddressMapper addressMapper;


    /**
     * Récupère une page paginée d'adresses.
     *
     * @param pageable un objet {@link Pageable} qui contient les informations de pagination et de tri
     * @return une page de résultats {@link Page<Address>} contenant les adresses
     */
    public Page<Address> getAddresses(Pageable pageable) {
        LOGGER.info("getAllAddresses");
        return addressDao.findAllAddresses(pageable);
    }

    /**
     * Récupère une adresse par son identifiant.
     *
     * <p> Si l'adresse
     *  n'est pas trouvée, une exception {@link ResponseStatusException} avec un statut HTTP 404 (NOT_FOUND) est levée.
     *
     *
     * @param id l'identifiant unique de l'adresse à récupérer
     * @return l'objet {@link Address} correspondant à l'ID fourni
     * @throws ResponseStatusException si aucune adresse n'est trouvée avec l'ID donné
     */
    public Address getAddress(Integer id) {
        Optional<Address> addressOptional = addressDao.findById(id);
        if (addressOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Address n'existe pas");
        }else{
            return addressOptional.get();
        }
    }

    /**
     * Sauvegarde une nouvelle adresse
     *
     *
     * @param addressDto un objet {@link AddressDto} contenant les informations de l'adresse à sauvegarder
     * @return un objet {@link AddressDto} représentant l'adresse persistée
     */
    @Transactional
    public AddressDto saveAddress(AddressDto addressDto) {
        LOGGER.info("getAuthorDto");
        Address address = addressMapper.toEntity(addressDto);
        address = addressDao.save(address);
        return addressMapper.toDto(address);
    }



    public void deleteAddressById(Integer id){


    }

    /**
     * Met à jour une adresse existante par son identifiant.
     *
     * @param id l'identifiant unique de l'adresse à mettre à jour
     * @param addressDto un objet {@link AddressDto} contenant les nouvelles valeurs des attributs de l'adresse
     * @return un objet {@link AddressDto} représentant l'adresse mise à jour
     * @throws ResponseStatusException si l'adresse avec l'ID donné n'est pas trouvée
     */
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





    /**
     * Récupère l'ensemble des adresses sous forme de DTO.
     *
     * @return un ensemble de {@link AddressDto} représentant toutes les adresses disponibles
     */
    public Set<AddressDto> getAllAddressesDto() {
        LOGGER.info("getAllAddressesDto");
        List<Address> addressList = new ArrayList<Address>();
        addressDao.findAll().forEach(addressList::add);

        return addressList.stream()
                .map(addressMapper::toDto)
                .collect(Collectors.toSet());
    }


}
