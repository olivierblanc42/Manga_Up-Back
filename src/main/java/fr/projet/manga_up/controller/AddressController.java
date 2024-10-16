package fr.projet.manga_up.controller;

import fr.projet.manga_up.dto.AddressDto;
import fr.projet.manga_up.dto.AuthorDto;
import fr.projet.manga_up.dto.GenreDto;
import fr.projet.manga_up.model.Address;
import fr.projet.manga_up.service.AddressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/addresses")
public class AddressController {

    private static final Logger LOGGER= LoggerFactory.getLogger(AddressController.class);

    @Autowired
    private AddressService addressService;

    @Operation(summary=" Tout les adresses avec pagination")
    @ApiResponse(responseCode = "201", description = "Toutes les addresses ont été récupérer")
    @GetMapping
    public ResponseEntity<?> getAllAddresses(

            @PageableDefault(
                    page = 0,
                    size = 10,
                    sort="id",
                    direction = Sort.Direction.DESC) Pageable pageable
    ) {
        LOGGER.info("Liste des genres utilisateur");
        Page<Address> addresses = addressService.getAddresses(pageable);
        LOGGER.info("gender = {}", addresses);
        return  ResponseEntity.ok(addresses);
    }

    @Operation(summary = "Sauvegarde d'une nouvelle addresse")
    @ApiResponse(responseCode = "201", description = "Une nouvelle addresse est enregistrée avec succès")
    @PostMapping
    public ResponseEntity<AddressDto> createAddress(@RequestBody AddressDto addressDto) {
        LOGGER.info("createAuthor : {}", addressDto);
        AddressDto createdAddress = addressService.saveAddress(addressDto);
        return ResponseEntity.ok(createdAddress);
    }

    @Operation(summary = "Récupère une addresse avec l'id'", description = "Retourne une addresse")
    @ApiResponse(responseCode = "201", description = "l'addresse a été trouvée avec succès")
    @GetMapping("/{id}")
    public Address getAddressById(@PathVariable("id")Integer id) {
        LOGGER.info("Récupération d'une addresse avec l'id : " + id);
        return  addressService.getAddress(id);

    }


    @Operation(summary = "Suppression d'une addresse avec son ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "l'addresse a été supprimée avec succès"),
            @ApiResponse(responseCode = "404", description = "addresse not found")
    })
    @DeleteMapping("/{id}")
    public void deleteAddressById(@PathVariable("id")Integer id) {
        LOGGER.info("Suppression d'une addresse " + id);
        addressService.deleteAddressById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AddressDto> updateAddress(@PathVariable Integer id, @RequestBody AddressDto addressUpdateDto) {
        try{
            AddressDto updateAddress = addressService.updateAddress(id ,addressUpdateDto) ;
            return ResponseEntity.ok(updateAddress);
        }catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }





    @Operation(summary=" ")
    @GetMapping("/dto")
    public ResponseEntity<Set<AddressDto>> getAddressDto() {
        Set<AddressDto> addressDto = addressService.getAllAddressesDto();
        return ResponseEntity.ok(addressDto);
    }



}
