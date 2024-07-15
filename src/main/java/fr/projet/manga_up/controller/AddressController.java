package fr.projet.manga_up.controller;

import fr.projet.manga_up.model.Address;
import fr.projet.manga_up.service.AddressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/addresses")
public class AddressController {

    private static final Logger LOGGER= LoggerFactory.getLogger(AddressController.class);


    @Autowired
    private AddressService addressService;


    @Operation(summary = "Sauvegarde d'une nouvelle addresse")
    @ApiResponse(responseCode = "201", description = "Une nouvelle addresse est enregistrée avec succès")
    @PostMapping
    public Address saveAddress(@RequestBody Address address) {
        LOGGER.info("Sauvegarde d'une addresse");
        return addressService.saveAddress(address);
    }

    @Operation(summary = "Récupère une addresse avec l'id'", description = "Retourne une addresse")
    @ApiResponse(responseCode = "201", description = "Une nouvelle addresse est enregistrée avec succès")
    @GetMapping("/{id}")
    public Address getAddressById(@PathVariable("id")Integer id) {
        LOGGER.info("Récupération d'une addresse avec l'id : " + id);
        return  addressService.getAddress(id);

    }


    @Operation(summary = "Suppression d'une addresse by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "l'addresse a été supprimée avec succès"),
            @ApiResponse(responseCode = "404", description = "addresse not found")
    })
    @DeleteMapping("/{id}")
    public void deleteAddressById(@PathVariable("id")Integer id) {
        LOGGER.info("Suppression d'une addresse " + id);
        addressService.deleteAddressById(id);
    }

}
