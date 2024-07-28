package fr.projet.manga_up.dao;

import fr.projet.manga_up.model.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Repository
public interface UserDao extends CrudRepository<User, Integer> {


    User getUserByUsernameAndPassword(String username, String password);

    @Modifying
    @Query(value = "SELECT Id_manga FROM user_manga WHERE id_user= :id", nativeQuery = true)
    List<Integer> getAllMangaByUserId(@Param("id") Integer id);

    User findByUsername(String userName);

}
