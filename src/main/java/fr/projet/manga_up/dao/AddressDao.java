package fr.projet.manga_up.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import fr.projet.manga_up.model.Address;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressDao extends CrudRepository<Address, Integer> {

    @Query("From Address ")
    Page<Address> findAllAddresses(Pageable pageable);

}
