package fr.projet.manga_up.mapper;

import fr.projet.manga_up.dao.GenderDao;
import fr.projet.manga_up.dto.GenderDto;
import fr.projet.manga_up.model.Gender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GenderMapper {

    @Autowired
    private GenderDao genderDao;

    public GenderDto toDto(Gender gender) {
        GenderDto dto = new GenderDto();
        dto.setId(gender.getId());
        dto.setLabel(gender.getLabel());

        return dto;
    }

    public Gender toEntity(GenderDto dto) {
        Gender gender = new Gender();
        gender.setId(dto.getId());
        gender.setLabel(dto.getLabel());
        return gender;
    }



}



// Gestion des utilisateurs associés
//voir si on modifi la relation en relation bidiritionnele
      /*  if (dto.getUserId() != null) {

            // Conversion du Set en List
            List<User> usersList = dto.getUserId().stream()
                    .map(id -> userDao.findById(id)
                            .orElseThrow(() -> new RuntimeException("Manga not found"))).distinct().collect(Collectors.toList());

            // Définition des mangas dans la catégorie
            gender.setUsers(usersList);
        }*/
