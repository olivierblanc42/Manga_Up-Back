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
