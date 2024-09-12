package fr.projet.manga_up.mapper;

import fr.projet.manga_up.dao.AddressDao;
import fr.projet.manga_up.dao.GenderDao;
import fr.projet.manga_up.dao.UserDao;
import fr.projet.manga_up.dto.RegisterDTO;
import fr.projet.manga_up.dto.UserDto;
import fr.projet.manga_up.model.Address;
import fr.projet.manga_up.model.Gender;
import fr.projet.manga_up.model.AppUser;
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

    public RegisterDTO toDtoRegister(AppUser user) {
        return new RegisterDTO(
                user.getUsername(),
                user.getEmail(),
                user.getFisrtname(),
                user.getLastname(),
                user.getPassword(),
                user.getAddress(),
                user.getGender()
        );
    }

    public UserDto toDto(AppUser user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setLastname(user.getLastname());
        userDto.setFisrtname(user.getFisrtname());
        userDto.setLastname(user.getLastname());
        userDto.setEmail(user.getEmail());
        userDto.setUserName(user.getUsername());
        userDto.setPassword(user.getPassword());
        if (user.getAddress() != null) {
            userDto.setAddressId(user.getAddress().getId());
        }

        if (user.getGender() != null) {
            userDto.setGenderId(user.getGender().getId());
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
        user.setFisrtname(userDto.getFisrtname());
        user.setLastname(userDto.getLastname());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setUsername(userDto.getUserName());

        if (userDto.getAddressId() != null) {
            Address address = addressDao.findById(userDto.getAddressId())
                    .orElseThrow(() -> new RuntimeException("Address not found"));
            user.setAddress(address);
        }

        if (userDto.getGenderId() != null) {
            Gender gender = genderDao.findById(userDto.getGenderId())
                    .orElseThrow(() -> new RuntimeException("Gender not found"));
            user.setGender(gender);
        }
        return user;
    }

}
