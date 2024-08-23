package fr.projet.manga_up.dto;

import fr.projet.manga_up.model.Address;

public class UserDto {
   private int id;
   private String userName;
   private String email;
   private String  fisrtname;
   private String lastname;
   private String password;
   private Integer addressId;
   private Integer genderId;

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

   public String getFisrtname() {
      return fisrtname;
   }

   public void setFisrtname(String fisrtname) {
      this.fisrtname = fisrtname;
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


   public Integer getAddressId() {
      return addressId;
   }

   public void setAddressId(Integer addressId) {
      this.addressId = addressId;
   }


   public Integer getGenderId() {
      return genderId;
   }

   public void setGenderId(Integer genderId) {
      this.genderId = genderId;
   }
}
