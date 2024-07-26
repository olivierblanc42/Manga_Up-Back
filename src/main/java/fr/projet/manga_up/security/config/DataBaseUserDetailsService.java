package fr.projet.manga_up.security.config;

import fr.projet.manga_up.dao.UserDao;
import fr.projet.manga_up.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class DataBaseUserDetailsService implements UserDetailsService {

    @Autowired
    private UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String userName) {
        User user = userDao.findByUsername(userName);
        if (user == null) {
            throw new UsernameNotFoundException(userName);
        }
        return (UserDetails) user;
    }
}
