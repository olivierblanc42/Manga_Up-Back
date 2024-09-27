package fr.projet.manga_up.service;

import fr.projet.manga_up.dao.AddressDao;
import fr.projet.manga_up.dao.GenderDao;
import fr.projet.manga_up.dao.UserDao;
import fr.projet.manga_up.dto.RegisterDTO;
import fr.projet.manga_up.dto.UserDto;
import fr.projet.manga_up.mapper.UserMapper;
import fr.projet.manga_up.model.AppUser;
import fr.projet.manga_up.model.Genre;
import org.apache.juli.logging.Log;
import org.mapstruct.control.MappingControl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
public class UserService {

    private static final Logger LOGGER= LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserDao userDao;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private AddressDao addressDao;
    @Autowired
    private GenderDao genderDao;
    @Autowired
    private AccountServiceImpl accountServiceImpl;
    @Autowired
    private PasswordEncoder passwordEncoder;


   /* public User getUserByUsernameAndPassword(String username, String password) {
        LOGGER.info("getUserByUsernameAndPassword");
        return userDao.getUserByUsernameAndPassword(username, password);
    }*/
    public AppUser getUserByUsername(String username){
        LOGGER.info("getUserByUsername");
        LOGGER.info("username : {}", username);
        AppUser appUser=userDao.getUserByUsername(username);
        LOGGER.info("appUser : {}", appUser);
        //UserDto rdto=userMapper.toDto(appUser);

        if(appUser == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Aucun utilisateur n'a été trouvé.");
        }else{
            return appUser;
        }
    }

    public AppUser getUser(Integer id){
        LOGGER.info("getUser");
        Optional<AppUser> ou = userDao.findById(id);
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

    public List<AppUser> getAllUsers(){
         return  userDao.findAllUsers();
    }

    public AppUser createUser(AppUser user){
        LOGGER.info("createUser");
        return userDao.save(user);
    }

    public void deleteUserById(Integer id){
        userDao.deleteById(id);
    }

    @Transactional
    public RegisterDTO saveUserDtoRegister(RegisterDTO registerDTO){
        LOGGER.info("saveUserDtoRegister registerDTO : {}", registerDTO);
        AppUser user = userMapper.toEntityRegister(registerDTO);
        LOGGER.info("saveUserDtoRegister user: {}", user);
        addressDao.save(user.getAddress());
        genderDao.save(user.getGender());
        LOGGER.info("saveUserDtoRegister user : {}", user);
        user.setCreatedAt(Instant.now());
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        user = userDao.save(user);
        accountServiceImpl.addRoleToUser(user, "USER");
        RegisterDTO rdto=userMapper.toDtoRegister(user);
        LOGGER.info("saveUserDtoRegister user dto : {}", rdto);
        return userMapper.toDtoRegister(user);
    }

    @Transactional
    public UserDto saveUserDto(UserDto userDto){
        LOGGER.info("saveUserDto");
        AppUser user = userMapper.toEntity(userDto);
        user =userDao.save(user);
        return userMapper.toDto(user);
    }


    public Page<AppUser> findAllUsersPageable( Pageable pageable) {

        return userDao.findAllUserPageable(pageable);
    }


}
