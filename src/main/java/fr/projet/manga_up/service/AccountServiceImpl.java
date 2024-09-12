package fr.projet.manga_up.service;

import fr.projet.manga_up.dao.AppRoleDao;
import fr.projet.manga_up.dao.UserDao;
import fr.projet.manga_up.model.AppRole;
import fr.projet.manga_up.model.AppUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class AccountServiceImpl {
    private static final Logger LOGGER= LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserDao userDao;
    @Autowired
    private AppRoleDao appRoleDao;

    public AppUser loadUserByEmail(String email){
        return userDao.findByEmail(email);
    }

    public AppUser loadUserByUsername(String username) {
        return userDao.findByUsername(username);
    }

    public void addRoleToUser(AppUser user , String role){
        LOGGER.info(">>>>> addRoleToUser");
        LOGGER.info("role : {}", role);
        Set<AppRole> setAppRole = new HashSet<>();

        // On récupère l'objet de type role.
        AppRole appRole=appRoleDao.findByRole(role);
        // On regarde si l'utilisateur à au moins un rôle.
        if(user.getRoles() == null){
            setAppRole.add(appRole);
            user.setRoles(setAppRole);
        }else{
            user.getRoles().add(appRole);
        }
        LOGGER.info("roles : {} user id : {}", user.getRoles(), user.getId());
    }
}
