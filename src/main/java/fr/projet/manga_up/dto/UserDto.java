package fr.projet.manga_up.dto;

import fr.projet.manga_up.model.Address;
import fr.projet.manga_up.model.AppRole;
import fr.projet.manga_up.model.Gender;

import java.util.Set;

public class UserDto {
   private int id;
   private String userName;
   private String email;
   private String firstname;
   private String lastname;
   private String password;
   private String phone;
   private Address address;
   private Integer genderId;
   private Gender gender;
   private Set<AppRole> roles;

   public Set<AppRole> getRoles() {
      return roles;
   }

   public void setRoles(Set<AppRole> roles) {
      this.roles = roles;
   }

   public int getId() {
      return id;
   }

   public void setId(int id) {
      this.id = id;
   }

   public String getUserName() {
      return userName;
   }

   public void setUserName(String userName) {
      this.userName = userName;
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

   public void setFirstname(String firstname) {
      this.firstname = firstname;
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

   public Address getAddress() {
      return address;
   }

   public void setAddress(Address address) {
      this.address = address;
   }


   public Integer getGenderId() {
      return genderId;
   }

   public void setGenderId(Integer genderId) {
      this.genderId = genderId;
   }

   public Gender getGender() {
      return gender;
   }

   public void setGender(Gender gender) {
      this.gender = gender;
   }

   public String getPhone() {
      return phone;
   }

   public void setPhone(String phone) {
      this.phone = phone;
   }
}
