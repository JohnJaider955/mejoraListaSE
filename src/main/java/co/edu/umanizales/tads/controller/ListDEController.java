package co.edu.umanizales.tads.controller;

import co.edu.umanizales.tads.controller.dto.*;
import co.edu.umanizales.tads.exception.ListDEException;
import co.edu.umanizales.tads.exception.ListSEException;
import co.edu.umanizales.tads.model.*;
import co.edu.umanizales.tads.service.ListDEService;
import co.edu.umanizales.tads.service.LocationPetsService;
import co.edu.umanizales.tads.service.RangeAgePetsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/listde")
public class ListDEController {
    @Autowired
    private ListDEService listDEService;

    @Autowired
    private LocationPetsService locationPetsService;

    @Autowired
    private RangeAgePetsService rangeAgePetsService;

    @GetMapping
    public ResponseEntity<ResponseDTO> getPets() {
        return new ResponseEntity<>(new ResponseDTO(
                200, listDEService.getPets().print(), null), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ResponseDTO> addPet(@RequestBody @Valid PetDTO petDTO) {
        LocationPets locationPets = locationPetsService.getLocationsPetsByCode(petDTO.getCodeLocationPet());
        if (locationPets == null) {
            return new ResponseEntity<>(new ResponseDTO(
                    404, "La ubicación no existe ", null), HttpStatus.OK);
        } try {
            listDEService.getPets().addPet(new Pet(petDTO.getIdentificationPet(),
                    petDTO.getGenderPet(), petDTO.getAgePet(),
                    petDTO.getNamePet(), locationPets));
        } catch (ListDEException e) {
            return new ResponseEntity<>(new ResponseDTO(
                    409, "Ya existe una mascota con ese código", null
            ), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new ResponseDTO(
                200, "Se ha añadido.", null), HttpStatus.OK);
    }

    //Adicionar mascotas
    @GetMapping(path = "/addpet")
    public ResponseEntity<ResponseDTO> add(@RequestBody @Valid Pet pet) {
        try {
            if (pet == null) {
                throw new ListDEException("La mascota no tiene datos");
            }
            listDEService.getPets().addPet(pet);
            return new ResponseEntity<>(new ResponseDTO(200,
                    "La mascota ha sido añadida", null), HttpStatus.OK);
        } catch (ListDEException e) {
            return new ResponseEntity<>(new ResponseDTO(500,
                    "Error al añadir la mascota", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Invertir lista
    @GetMapping("/invertpets")
    public ResponseEntity<ResponseDTO> invertPets() {
        try {
            listDEService.invertPets();
            return new ResponseEntity<>(new ResponseDTO(
                    200, "Se ha invertido la lista",
                    null), HttpStatus.OK);
        } catch (ListDEException e) {
            return new ResponseEntity<>(new ResponseDTO(
                    500, "Error al invertir la lista",
                    null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Mascotas masculinas al inicio y femeninos al final.
    @GetMapping("/orderpetsmaletostart")
    public ResponseEntity<ResponseDTO> orderPetsMaleToStart() {
        try {
            listDEService.getPets().orderPetsToStart();
            return new ResponseEntity<>(new ResponseDTO(
                    200, "Se han añadido las mascotas masculinas al inicio, las femeninas al final.",
                    null), HttpStatus.OK);
        } catch (ListDEException e) {
            return new ResponseEntity<>(new ResponseDTO(
                    500, "Ocurrió un error al ordenar el género de las mascotas.", null),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Intercalar mascota masculina, femenina, masculina, femenina
    @GetMapping(path="/petsintercalate")
    public ResponseEntity<ResponseDTO> petMaleIntercalateFemale()  {
        try {
            listDEService.getPets().intercalatePetsGender();
            return new ResponseEntity<>(new ResponseDTO(200, "Las mascotas se han intercalado.",
                    null), HttpStatus.OK);
        } catch (ListDEException e) {
            return new ResponseEntity<>(new ResponseDTO(
                    500, "Ocurrió un error al intercalar el género de las mascotas",
                    null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Dada una edad eliminar a las mascotas del código dado
    @GetMapping(path = "/deletepet/{code}")
    public ResponseEntity<ResponseDTO> deletePetByAge(@PathVariable String code)  {
        try {
            listDEService.deletePetByIdentification(code);
            return new ResponseEntity<>(new ResponseDTO(
                    200, "Las mascotas con el código" + code + "han sido eliminados.",
                    null), HttpStatus.OK);
        } catch (ListDEException e) {
            return new ResponseEntity<>(new ResponseDTO(
                    500, "Error al eliminar las mascotas.",
                    null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Obtener el promedio de edad de las mascotas de la lista
    @GetMapping(path="/averageagepets")
    public ResponseEntity<ResponseDTO> averageAge(){
        float averageAgePet;
        try {
            averageAgePet = listDEService.getPets().averageAgePets();
            return new ResponseEntity<>(new ResponseDTO(200, "El promedio es de" + averageAgePet + " por edad.",
                    null), HttpStatus.OK);
        } catch (ListDEException e) {
            return new ResponseEntity<>(new ResponseDTO(500, "Error al calcular la edad promedio.",
                    null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Generar un reporte que me diga cuantas mascotas hay de cada ciudad.
    @GetMapping(path = "/petsbylocations")
    public ResponseEntity<ResponseDTO> getPetsByLocation() {
        try {
            List<PetsByLocationDTO> petsByLocationDTOList = new ArrayList<>();
            for (LocationPets loc : locationPetsService.getLocationsPets()) {
                int count = listDEService.getPets().getCountPetsByLocationCode(loc.getCode());
                if (count > 0) {
                    petsByLocationDTOList.add(new PetsByLocationDTO(loc, count));
                }
            }
            return new ResponseEntity<>(new ResponseDTO(
                    200, petsByLocationDTOList,
                    null), HttpStatus.OK);
        } catch (ListDEException e) {
            return new ResponseEntity<>(new ResponseDTO(
                    500, "Ocurrió un error al obtener las mascotas por ubicación.", null),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/petsbydepartments")
    public ResponseEntity<ResponseDTO> getPetsByDepartmentCode() {
        List<PetsByLocationDTO> petsByLocationDTOList = new ArrayList<>();
        try {
            for (LocationPets loc : locationPetsService.getLocationsPets()) {
                int count = listDEService.getPets().getCountPetsByDepartmentCode(loc.getCode());
                if (count > 0) {
                    petsByLocationDTOList.add(new PetsByLocationDTO(loc, count));
                }
            }
            return new ResponseEntity<>(new ResponseDTO(
                    200, petsByLocationDTOList,
                    null), HttpStatus.OK);
        } catch (ListDEException e) {
            return new ResponseEntity<>(new ResponseDTO(
                    500, "Error al obtener la lista de mascotas por departamento.",
                    null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/petsbylocationgenders/{age}")
    public ResponseEntity<ResponseDTO> getReportPetsLocationGenders(@PathVariable byte age) {
        ReportPetsDTO reportPets =
                new ReportPetsDTO(locationPetsService.getLocationsPetsByCodeSize(8));
        listDEService.getPets().getReportPetsByLocationGendersByAge(age,reportPets);
        return new ResponseEntity<>(new ResponseDTO(
                200, reportPets,null),HttpStatus.OK);
    }

    //Método que me permita decirle a una mascota determinada que adelante un número de posiciones dadas
    @GetMapping(path = "/passpositions/{codepet}/{move}")
    public ResponseEntity<ResponseDTO> passPetPosition(@PathVariable  String codepet,  @PathVariable int move ) {
        try {
            listDEService.getPets().passPetPosition(codepet,move, listDEService.getPets());
            return new ResponseEntity<>(new ResponseDTO(200, "La mascota se ha adelantado de posición", null), HttpStatus.OK);
        } catch (ListDEException e) {
            return new ResponseEntity<>(new ResponseDTO(500, "Ha ocurrido un error al adelantar la posición de la mascota", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Método que me permita decirle a una mascota determinada que pierda un número de posiciones dada
    @GetMapping(path = "/lostposition/{codepet}/{move}")
    public ResponseEntity<ResponseDTO> LostPetPosition(@PathVariable  String codepet,  @PathVariable int move )  {
        try {
            listDEService.getPets().backPetPositions(codepet,move);
            return new ResponseEntity<>(new ResponseDTO(200, "La mascota ha perdido posiciones", null), HttpStatus.OK);
        } catch (ListDEException e) {
            return new ResponseEntity<>(new ResponseDTO(500, "Error al intentar mover la mascota de posición",
                    null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Obtener un informe de mascotas por rango de edades
    @GetMapping(path="/rangeagekidspets")
    public ResponseEntity<ResponseDTO> getRangeAgePets() {
        List<RangeAgesPetsDTO> petsRangeDTOList = new ArrayList<>();
        try {
            for(RangeAgePets i: rangeAgePetsService.getRangesPets()){
                int quantity = listDEService.getPets().rangePetsByAge(i.getFromPet(), i.getToPet());
                petsRangeDTOList.add(new RangeAgesPetsDTO(i, quantity));
            }
            return new ResponseEntity<>(new ResponseDTO(200, petsRangeDTOList, null), HttpStatus.OK);
        } catch (ListDEException e) {
            return new ResponseEntity<>(new ResponseDTO(500, "Error al obtener el rango de edades", null),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Implementar un método que me permita enviar al final de la lista a las mascotas que su nombre inicie con una letra dada
    @GetMapping(path="/petletter/{initial}")
    public ResponseEntity<ResponseDTO> boysByLetter(@PathVariable char initial) {
        try {
            listDEService.getPets().petsByLetter(Character.toUpperCase(initial));
            return new ResponseEntity<>(new ResponseDTO(200, "Las mascotas con esa letra se han enviado al final de la lista.", null), HttpStatus.OK);
        } catch (ListDEException e) {
            return new ResponseEntity<>(new ResponseDTO(500, "Ocurrió un error al enviar al final la lista de mascotas por la letra dada.",
                    null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/change_extremes")
    public ResponseEntity<ResponseDTO> changeExtremes() {
        try {
            listDEService.getPets().changesPetExtremes();
            return new ResponseEntity<>(new ResponseDTO(
                    200, "Se ha intercambiado los extremos ", null), HttpStatus.OK);
        } catch (ListDEException e) {
            return new ResponseEntity<>(new ResponseDTO(
                    500, "Ha ocurrido un error al intercambiar los extremos", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Llamo en el controller el método hecho en listDE
    @GetMapping(path = "/deletekamikaze/{codepet}")
    public ResponseEntity<ResponseDTO> deleteKamikazeByPosition(@PathVariable String codePet)  {
            listDEService.deleteByCode(codePet);
            return new ResponseEntity<>(new ResponseDTO(
                    200, "Se ha eliminado",
                    null), HttpStatus.OK);
    }
}
