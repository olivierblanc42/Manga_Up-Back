package fr.projet.manga_up.dao;

import java.util.List;
import java.util.Set;

import fr.projet.manga_up.model.AppRole;
import fr.projet.manga_up.model.AppUser;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.transaction.annotation.Transactional;


@Transactional
@Repository
public interface UserDao extends CrudRepository<AppUser, Integer> {


    //User getUserByUsernameAndPassword(String username, String password);

    @Modifying
    @Query(value = "SELECT Id_manga FROM user_manga WHERE id_user= :id", nativeQuery = true)
    List<Integer> getAllMangaByUserId(@Param("id") Integer id);

    AppUser findByUsername(String username);

    AppUser findByEmail(String email);

    @Query( "FROM AppUser  ")
    List<AppUser> findAllUsers();
}
