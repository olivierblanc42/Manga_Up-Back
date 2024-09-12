package fr.projet.manga_up.dto;

import fr.projet.manga_up.model.Address;
import fr.projet.manga_up.model.Gender;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RegisterDTO {
    private static final Logger LOGGER= LoggerFactory.getLogger(RegisterDTO.class);
    private String username;
    private String email;
    private String firstname;
    private String lastname;
    private String password;
    private Address address;
    private Gender gender;

    public RegisterDTO(String username, String email, String firstname, String lastname, String password, Address address, Gender gender) {
        LOGGER.info("Constructeur RegisterDto - Début");
        LOGGER.info("Constructeur RegisterDto - username : {}", username);
        this.username = username;
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.password = password;
        this.address = address;
        this.gender = gender;
        LOGGER.info("Constructeur RegisterDto - Fin");
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String fisrtname) {
        this.firstname = fisrtname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
