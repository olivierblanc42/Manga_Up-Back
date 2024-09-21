package fr.projet.manga_up.mapper;


import fr.projet.manga_up.dao.AddressDao;
import fr.projet.manga_up.dto.AddressDto;
import fr.projet.manga_up.model.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AddressMapper {


    @Autowired
    private AddressDao addressDao;

    public AddressDto toDto(Address address) {
        AddressDto addressDto = new AddressDto();
        addressDto.setId(address.getId());
        addressDto.setLine1(address.getLine1());
        addressDto.setLine2(address.getLine2());
        addressDto.setLine3(address.getLine3());
        addressDto.setCity(address.getCity());
        addressDto.setPostalCode(address.getPostalCode());
        return addressDto;
    }

    public Address toEntity(AddressDto addressDto) {
        Address address = new Address();
        address.setId(addressDto.getId());
        address.setLine1(addressDto.getLine1());
        address.setLine2(addressDto.getLine2());
        address.setLine3(addressDto.getLine3());
        address.setCity(addressDto.getCity());
        address.setPostalCode(addressDto.getPostalCode());
        return address;
    }




}
