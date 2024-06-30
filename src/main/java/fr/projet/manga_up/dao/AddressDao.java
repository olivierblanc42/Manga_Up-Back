package fr.projet.manga_up.dao;

import org.springframework.data.repository.CrudRepository;

import fr.projet.manga_up.model.Address;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressDao extends CrudRepository<Address, Integer> {

}
