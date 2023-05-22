package co.edu.umanizales.tads.controller;

import co.edu.umanizales.tads.controller.dto.PetDTO;
import co.edu.umanizales.tads.controller.dto.ResponseDTO;
import co.edu.umanizales.tads.exception.ListDEException;
import co.edu.umanizales.tads.model.Location;
import co.edu.umanizales.tads.model.LocationPets;
import co.edu.umanizales.tads.service.LocationPetsService;
import co.edu.umanizales.tads.model.Pet;
import co.edu.umanizales.tads.service.ListDCService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/listdc")
public class ListDCController {
    @Autowired
    private ListDCService listDCService;

    @Autowired
    private LocationPetsService locationPetsService;

    @GetMapping
    public ResponseEntity<ResponseDTO> getPetsCircular() {
        return new ResponseEntity<>(new ResponseDTO(
                200, listDCService.getPetsDECircular().print(), null), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ResponseDTO> addPetCircular(@RequestBody @Valid PetDTO petDTO) {
        LocationPets locationPets = locationPetsService.getLocationsPetsByCode(petDTO.getCodeLocationPet());
        if (locationPets == null) {
            return new ResponseEntity<>(new ResponseDTO(
                    404, "La ubicación no existe ", null), HttpStatus.OK);
        } try {
            listDCService.getPetsDECircular().addPetCircular(
                    new Pet(petDTO.getIdentificationPet(),
                            petDTO.getGenderPet(), petDTO.getAgePet(),
                            petDTO.getNamePet(), locationPets,petDTO.isBath()));
        } catch (ListDEException e) {
            return new ResponseEntity<>(new ResponseDTO(
                    409, "Ya existe una mascota con ese código", null
            ), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new ResponseDTO(
                200, "Se ha añadido.", null), HttpStatus.OK);
    }
    @GetMapping("/randomleftright/{side}")
    public ResponseEntity<ResponseDTO> random(@PathVariable String side) {
        List<Pet> pets = listDCService.getPetsDECircular().print();

        if (pets.isEmpty()) {
            return new ResponseEntity<>(new ResponseDTO(
                    200, "No se encontraron mascotas",
                    null), HttpStatus.OK);
        } else {
            int randomPosition = listDCService.getPetsDECircular().bathPet(side);
            if (randomPosition == -1) {
                return new ResponseEntity<>(new ResponseDTO(
                        200, "No se pudo bañar ningún perro",
                        null), HttpStatus.OK);
            } else if (randomPosition == 0) {
                return new ResponseEntity<>(new ResponseDTO(
                        200, "El perro ya fue bañado en esa dirección" + randomPosition,
                        null), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ResponseDTO(
                        200, "Se ha bañado el perro número " + randomPosition + " en la dirección " + side,
                        null), HttpStatus.OK);
            }
        }
    }

    @PostMapping(path = "/addtostart")
    public ResponseEntity<ResponseDTO> addPetToStart(@RequestBody @Valid PetDTO petDTO) {
        try {
            LocationPets locationPets = locationPetsService.getLocationsPetsByCode(petDTO.getCodeLocationPet());
            if (locationPets == null) {
                return new ResponseEntity<>(new ResponseDTO(
                        404, "La ubicación no existe",
                        null), HttpStatus.OK);
            }
            listDCService.getPetsDECircular().addPetToStart(
                    new Pet(petDTO.getIdentificationPet(),
                            petDTO.getGenderPet(), petDTO.getAgePet(),
                            petDTO.getNamePet(), locationPets, petDTO.isBath()));
            return new ResponseEntity<>(new ResponseDTO(200, "Mascota adicionada al inicio", null),
                    HttpStatus.OK);
        } catch (ListDEException e) {
            return new ResponseEntity<>(new ResponseDTO(
                    409, e.getMessage(),
                    null), HttpStatus.OK);
        }
    }

    @PostMapping(path = "/addtofinal")
    public ResponseEntity<ResponseDTO> addPetToFinal(@RequestBody @Valid PetDTO petDTO) {
        try {
            LocationPets locationPets = locationPetsService.getLocationsPetsByCode(petDTO.getCodeLocationPet());
            if (locationPets == null) {
                return new ResponseEntity<>(new ResponseDTO(
                        404, "La ubicación no existe",
                        null), HttpStatus.OK);
            }
            listDCService.getPetsDECircular().addPetToEnd(
                    new Pet(petDTO.getIdentificationPet(),
                            petDTO.getGenderPet(), petDTO.getAgePet(),
                            petDTO.getNamePet(), locationPets, petDTO.isBath()));
            return new ResponseEntity<>(new ResponseDTO(200, "Mascota adicionada al final", null),
                    HttpStatus.OK);
        } catch (ListDEException e) {
            return new ResponseEntity<>(new ResponseDTO(
                    409, e.getMessage(),
                    null), HttpStatus.OK);
        }
    }

    @PostMapping(path = "/addpetposition/{position}")
    public ResponseEntity<ResponseDTO> addPetInPosition(@RequestBody Pet pet, @PathVariable int position) {
        try {
            listDCService.getPetsDECircular().addPetInPosition(position, pet);
            return new ResponseEntity<>(new ResponseDTO(200, "Mascota adicionada en la posición " + position, null),
                    HttpStatus.OK);
        } catch (ListDEException e) {
            return new ResponseEntity<>(new ResponseDTO(500, "Ocurrió un error al añadir por posición en la lista", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path="/petsintercalatecircular")
    public ResponseEntity<ResponseDTO> petMaleIntercalateFemaleCircular() {
        try {
            listDCService.getPetsDECircular().intercalatePetsGenderCircular();
            return new ResponseEntity<>(new ResponseDTO(200, "Las mascotas se han intercalado.", null), HttpStatus.OK);
        } catch (ListDEException e) {
            return new ResponseEntity<>(new ResponseDTO(500, "Ocurrió un error al intercalar el género de las mascotas", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
