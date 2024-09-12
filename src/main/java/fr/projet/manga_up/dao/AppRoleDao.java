package fr.projet.manga_up.dao;

import fr.projet.manga_up.model.Address;
import fr.projet.manga_up.model.AppRole;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface AppRoleDao extends CrudRepository<AppRole, String> {
    @Query(value="SELECT * FROM role ar WHERE ar.role= :role", nativeQuery = true)
    AppRole findByRole(@Param("role") String role);
}
