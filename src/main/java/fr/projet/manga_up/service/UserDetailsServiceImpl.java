package fr.projet.manga_up.service;

import fr.projet.manga_up.dao.UserDao;
import fr.projet.manga_up.model.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username) {
        AppUser user = userDao.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("L'utilisateur %s n'a pas été trouvé", username));
        }
        List<SimpleGrantedAuthority> authorities=user.getRoles().stream()
                .map(r->new SimpleGrantedAuthority(r.getRole())).collect(Collectors.toList());
        UserDetails userDetails= User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities(authorities).build();
        return userDetails;
    }
}

