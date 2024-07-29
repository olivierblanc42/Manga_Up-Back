package fr.projet.manga_up.service;

import fr.projet.manga_up.dao.UserDao;
import fr.projet.manga_up.model.Manga;
import fr.projet.manga_up.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Component
public class UserService {

    private static final Logger LOGGER= LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserDao userDao;


   /* public User getUserByUsernameAndPassword(String username, String password) {
        LOGGER.info("getUserByUsernameAndPassword");
        return userDao.getUserByUsernameAndPassword(username, password);
    }*/

    public User getUser(Integer id){
        LOGGER.info("getUser");
        Optional<User> ou = userDao.findById(id);
        LOGGER.info("User : {}", ou);
        if(ou.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Aucun utilisateur n'a été trouvé.");
        }else {
            return ou.get();
        }
    }

    public List<Integer> getAllMangaByUserId(Integer id){
        List<Integer> mangasId=userDao.getAllMangaByUserId(id);
        return mangasId;
    }

    public List<User> getAllUsers(){
         return  userDao.findAllUsers();
    }

}
