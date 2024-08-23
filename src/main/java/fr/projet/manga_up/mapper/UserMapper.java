package fr.projet.manga_up.mapper;

import fr.projet.manga_up.dao.AddressDao;
import fr.projet.manga_up.dao.GenderDao;
import fr.projet.manga_up.dao.UserDao;
import fr.projet.manga_up.dto.UserDto;
import fr.projet.manga_up.model.Address;
import fr.projet.manga_up.model.Category;
import fr.projet.manga_up.model.Gender;
import fr.projet.manga_up.model.User;
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

    public UserDto toDto(User user) {
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


    public User toEntity(UserDto userDto) {
        User user = new User();
        user.setId(userDto.getId());
        user.setLastname(userDto.getLastname());
        user.setFisrtname(userDto.getFisrtname());
        user.setLastname(userDto.getLastname());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setUsername(userDto.getUserName());

        if (userDto.getAddressId() != null) {
            Address address = addressDao.findById(userDto.getAddressId())
                    .orElseThrow(() -> new RuntimeException("Category not found"));
            user.setAddress(address);
        }

        if (userDto.getGenderId() != null) {
            Gender gender = genderDao.findById(userDto.getGenderId())
                    .orElseThrow(() -> new RuntimeException("Category not found"));
            user.setGender(gender);
        }
        return user;
    }

}
