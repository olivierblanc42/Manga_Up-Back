package fr.projet.manga_up.mapper;

import fr.projet.manga_up.controller.AuthController;
import fr.projet.manga_up.dao.AddressDao;
import fr.projet.manga_up.dao.GenderDao;
import fr.projet.manga_up.dao.UserDao;
import fr.projet.manga_up.dto.RegisterDTO;
import fr.projet.manga_up.dto.UserDto;
import fr.projet.manga_up.model.Address;
import fr.projet.manga_up.model.Gender;
import fr.projet.manga_up.model.AppUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    @Autowired
    private UserDao userDao;
    @Autowired
    private AddressDao addressDao;
    @Autowired
    private GenderDao genderDao;

    private static final Logger LOGGER= LoggerFactory.getLogger(UserMapper.class);

    public RegisterDTO toDtoRegister(AppUser user) {
        return new RegisterDTO(
                user.getUsername(),
                user.getEmail(),
                user.getFirstname(),
                user.getLastname(),
                user.getPassword(),
                user.getAddress(),
                user.getGender(),
                user.getRoles()
        );
    }

    public UserDto toDto(AppUser user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setLastname(user.getLastname());
        userDto.setFirstname(user.getFirstname());
        userDto.setEmail(user.getEmail());
        userDto.setUserName(user.getUsername());
        userDto.setPassword(user.getPassword());
        if (user.getAddress() != null) {
            userDto.setAddress(user.getAddress());
        }

        if (user.getGender() != null) {
            userDto.setGenderId(user.getGender().getId());
        }

        if(user.getRoles() != null) {
            userDto.setRoles(user.getRoles());
        }
        return userDto;
    }

    public AppUser toEntityRegister(RegisterDTO registerDTO) {
        AppUser user = new AppUser();
        user.setUsername(registerDTO.getUsername());
        user.setEmail(registerDTO.getEmail());
        user.setFisrtname(registerDTO.getFirstname());
        user.setLastname(registerDTO.getLastname());
        user.setPassword(registerDTO.getPassword());
        user.setAddress(registerDTO.getAddress());
        user.setGender(registerDTO.getGender());

        return user;
    }


    public AppUser toEntity(UserDto userDto) {
        AppUser user = new AppUser();
        user.setId(userDto.getId());
        user.setLastname(userDto.getLastname());
        user.setFisrtname(userDto.getFirstname());
        user.setLastname(userDto.getLastname());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setUsername(userDto.getUserName());
        user.setAddress(userDto.getAddress());
        /*
        if (userDto.getAddress() != null) {
            LOGGER.info("userDto.getAddress() : {}", userDto.getAddress());
            Address address = addressDao.findById(userDto.getAddress().getId())
                    .orElseThrow(() -> new RuntimeException("Address not found"));
            user.setAddress(address);
        }
         */

        if (userDto.getGenderId() != null) {
            Gender gender = genderDao.findById(userDto.getGenderId())
                    .orElseThrow(() -> new RuntimeException("Gender not found"));
            user.setGender(gender);
        }
        return user;
    }

}
