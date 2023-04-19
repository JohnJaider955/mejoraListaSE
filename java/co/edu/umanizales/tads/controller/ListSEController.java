package co.edu.umanizales.tads.controller;

import co.edu.umanizales.tads.controller.dto.KidDTO;
import co.edu.umanizales.tads.controller.dto.KidsByLocationDTO;
import co.edu.umanizales.tads.controller.dto.ResponseDTO;
import co.edu.umanizales.tads.model.Kid;
import co.edu.umanizales.tads.model.Location;
import co.edu.umanizales.tads.service.ListSEService;
import co.edu.umanizales.tads.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/listse")
public class ListSEController {
    @Autowired
    private ListSEService listSEService;
    @Autowired
    private LocationService locationService;

    @GetMapping
    public ResponseEntity<ResponseDTO> getKids(){
        return new ResponseEntity<>(new ResponseDTO(
                200,listSEService.getKids(),null), HttpStatus.OK);
    }

    @GetMapping("/invert")
    public ResponseEntity<ResponseDTO> invert(){
        listSEService.invert();
        return new ResponseEntity<>(new ResponseDTO(
                200,"SE ha invertido la lista",
                null), HttpStatus.OK);
    }

    @GetMapping("/addtostart")
    public ResponseEntity<ResponseDTO> addToStart(@RequestBody Kid kid){
        listSEService.addToStart(kid);
        return new ResponseEntity<>(new ResponseDTO(
                200,"Se ha añadido al inicio",
                null), HttpStatus.OK);
    }

    @GetMapping("/invert")
    public ResponseEntity<ResponseDTO> orderBoysToStart(){
        listSEService.invert();
        return new ResponseEntity<>(new ResponseDTO(
                200,"Se han añadido los niños al inicio",
                null), HttpStatus.OK);
    }


    @GetMapping("/addtofinal")
    public ResponseEntity<ResponseDTO> addToFinal(@RequestBody Kid kid){
        listSEService.addToStart(kid);
        return new ResponseEntity<>(new ResponseDTO(
                200,"Se ha añadido al final",
                null), HttpStatus.OK);
    }

    @GetMapping(path= "/add")
    public ResponseEntity<ResponseDTO> add(@RequestBody Kid kid){
        listSEService.getKids().add(kid);
        return new ResponseEntity<>(new ResponseDTO(
                200, "El niño ha sido añadido", null), HttpStatus.OK);
    }

    @DeleteMapping(path= "/delete/{age}")
    public ResponseEntity<ResponseDTO> deleteKidByAge (@PathVariable byte age){
        listSEService.getKids().deleteByAge(age);
        return new ResponseEntity<>(new ResponseDTO(
                200,  "El niño: " + listSEService.getKids() + "ha sido eliminado", null), HttpStatus.OK);
    }

    @GetMapping(path="/addKidByPosition")
    public ResponseEntity<ResponseDTO> addKidByPosition(@RequestBody Kid kid){
        listSEService.getKids().passByPosition(kid);
        return new ResponseEntity<>(new ResponseDTO(
                200, "El niño se movió de posición", null),HttpStatus.OK);
    }

    @GetMapping(path = "/change_extremes")
    public ResponseEntity<ResponseDTO> changeExtremes(){
        listSEService.getKids().changesExtremes();
        return new ResponseEntity<>(new ResponseDTO(
                200, "Se ha intercambiado los extremos ", null), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ResponseDTO> addKid(@RequestBody KidDTO kidDTO){
        Location location = locationService.getLocationsByCode(kidDTO.getCodeLocation());
        if(location == null) {
            return new ResponseEntity<>(new ResponseDTO(
                    404, "La ubicación no existe ", null), HttpStatus.OK);
        }
        else if( location != null) {
            listSEService.getKids().add(new Kid(kidDTO.getIdentification(),
                    kidDTO.getName(), kidDTO.getAge(),
                    kidDTO.getGender(), location));
            return new ResponseEntity<>(new ResponseDTO(
                    200, "Se ha añadido", null), HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(new ResponseDTO(
                    409, "Ya existe un niño con ese código", null
            ), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "/kidsbylocations")
    public ResponseEntity<ResponseDTO> getKidsByLocation(){
        List<KidsByLocationDTO> kidsByLocationDTOList = new ArrayList<>();
        for(Location loc: locationService.getLocations()){
            int count = listSEService.getKids().getCountKidsByLocationCode(loc.getCode());
            if(count>0){
                kidsByLocationDTOList.add(new KidsByLocationDTO(loc,count));
            }
        }
        return new ResponseEntity<>(new ResponseDTO(
                200,kidsByLocationDTOList,
                null), HttpStatus.OK);
    }

    @GetMapping(path = "/kidsbydepartments")
    public ResponseEntity<ResponseDTO> getKidsByDepartmentCode(){
        List<KidsByLocationDTO> kidsByLocationDTOList = new ArrayList<>();
        for(Location loc: locationService.getLocations()){
            int count = listSEService.getKids().getCountKidsByDepartmentCode(loc.getCode());
            if(count>0){
                kidsByLocationDTOList.add(new KidsByLocationDTO(loc, count));
            }
        }
        return new ResponseEntity<>(new ResponseDTO(
                200, kidsByLocationDTOList,
                null), HttpStatus.OK);
    }
}
