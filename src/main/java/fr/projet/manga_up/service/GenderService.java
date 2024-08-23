package fr.projet.manga_up.service;

import fr.projet.manga_up.controller.GenreController;
import fr.projet.manga_up.dao.GenderDao;
import fr.projet.manga_up.dto.GenderDto;
import fr.projet.manga_up.mapper.GenderMapper;
import fr.projet.manga_up.model.Gender;
import fr.projet.manga_up.model.Manga;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Component
public class GenderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GenreController.class);

    @Autowired
   private GenderDao genderDao;
    @Autowired
    private GenderMapper genderMapper;

    public List<Gender> getAllGenders() {
        return genderDao.findAll();
    }

    public Gender getGender(Integer id){
        Optional<Gender> mo=genderDao.findById(id);
        if(mo.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Aucun manga n'a été trouvé");
        }else {
            return mo.get();
        }
    }


  /*  public void deleteGenderById(Integer id){
       genderDao.deleteById(id);
    }

     public Gender saveGender(Gender gender){
       return genderDao.save(gender);
     }*/

  @Transactional
    public GenderDto saveGender(GenderDto genderDto) {
      Gender gender = genderMapper.toEntity(genderDto);
      gender = genderDao.save(gender);
      return genderMapper.toDto(gender);
  }

    @Transactional

    public void deleteManga(int genderId) {
        Gender gender = genderDao.findById(genderId)
                .orElseThrow(() -> new EntityNotFoundException("Manga not found"));
        genderDao.delete(gender);
    }



}
