package co.edu.umanizales.tads.controller;

import co.edu.umanizales.tads.controller.dto.ResponseDTO;
import co.edu.umanizales.tads.model.Kid;
import co.edu.umanizales.tads.service.ListSEService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/listse")
public class ListSEController {
    @Autowired
    private ListSEService listSEService;

    @GetMapping
    public ResponseEntity<ResponseDTO> getKids(){
        return new ResponseEntity<>(new ResponseDTO(
                200,listSEService.getKids(),null), HttpStatus.OK);
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

}
