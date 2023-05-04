package co.edu.umanizales.tads.controller;

import co.edu.umanizales.tads.controller.dto.*;
import co.edu.umanizales.tads.exception.ListSEException;
import co.edu.umanizales.tads.model.Kid;
import co.edu.umanizales.tads.model.Location;
import co.edu.umanizales.tads.model.RangeAgeKids;
import co.edu.umanizales.tads.service.ListSEService;
import co.edu.umanizales.tads.service.LocationService;
import co.edu.umanizales.tads.service.RangeAgeService;
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

    @Autowired
    private RangeAgeService rangeAgeService;

    @GetMapping
    public ResponseEntity<ResponseDTO> getKids() {
        return new ResponseEntity<>(new ResponseDTO(
                200, listSEService.getKids(), null), HttpStatus.OK);
    }

    //Invertir lista
    @GetMapping("/invert")
    public ResponseEntity<ResponseDTO> invert() throws Exception {
        listSEService.invert();
        return new ResponseEntity<>(new ResponseDTO(
                200, "Se ha invertido la lista",
                null), HttpStatus.OK);
    }

    @GetMapping("/addtostart")
    public ResponseEntity<ResponseDTO> addToStart(@RequestBody Kid kid) throws Exception{
        listSEService.addToStart(kid);
        return new ResponseEntity<>(new ResponseDTO(
                200, "Se ha añadido al inicio.",
                null), HttpStatus.OK);
    }

    //Niños al inicio y niñas al final
    @GetMapping("/orderboystostart")
    public ResponseEntity<ResponseDTO> orderBoysToStart() throws Exception {
        listSEService.getKids().orderBoysToStart();
        return new ResponseEntity<>(new ResponseDTO(
                200, "Se han añadido los niños al inicio, las niñas al final.",
                null), HttpStatus.OK);
    }

    //Intercalar niño, niña, niño, niña
    @GetMapping(path="/boythengirl")
    public ResponseEntity<ResponseDTO> boyThenGirl() throws ListSEException {
        listSEService.getKids().intercalateBoysGirls();
        return new ResponseEntity<>(new ResponseDTO(200, "Los niños se han intercalado.",
                null), HttpStatus.OK);
    }

    @GetMapping("/addtofinal")
    public ResponseEntity<ResponseDTO> addToFinal(@RequestBody Kid kid) throws Exception {
        listSEService.addToStart(kid);
        return new ResponseEntity<>(new ResponseDTO(
                200, "Se ha añadido al final",
                null), HttpStatus.OK);
    }

    @GetMapping(path = "/add")
    public ResponseEntity<ResponseDTO> add(@RequestBody Kid kid) throws ListSEException {
        listSEService.getKids().add(kid);
        return new ResponseEntity<>(new ResponseDTO(
                200, "El niño ha sido añadido", null), HttpStatus.OK);
    }

    //Dada una edad eliminar a los niños de la edad dada
    @GetMapping(path = "/delete/{age}")
    public ResponseEntity<ResponseDTO> deleteKidByAge(@PathVariable byte age) throws Exception {
        listSEService.getKids().deleteByAge(age);
        return new ResponseEntity<>(new ResponseDTO(
                200, "Los niños han sido eliminado.",
                null), HttpStatus.OK);
    }

    //Obtener el promedio de edad de los niños de la lista
    @GetMapping(path="/averageage")
    public ResponseEntity<ResponseDTO> averageAge(){
        return new ResponseEntity<>(new ResponseDTO(200,
                listSEService.getKids().averageAge(), null), HttpStatus.OK);
    }

    //Implementar un método que me permita enviar al final de la lista a los niños que su nombre inicie con una letra dada
    @GetMapping(path="/sendkidletter/{initial}")
    public ResponseEntity<ResponseDTO> sendBottomByLetter(@PathVariable char initial) throws ListSEException {
        listSEService.getKids().boysByLetter(Character.toUpperCase(initial));
        return new ResponseEntity<>(new ResponseDTO(200, "Los niños con esa letra se han enviado al final de la lista.",
                null), HttpStatus.OK);
    }

    //Obtener un informe de niños por rango de edades
    @GetMapping(path="/rangeagekids")
    public ResponseEntity<ResponseDTO> getRangeAgeKids() {
        List<RangeAgesKidsDTO> kidsRangeDTOList = new ArrayList<>();

        for(RangeAgeKids i: rangeAgeService.getRanges()){
            int quantity = listSEService.getKids().rangeByAge(i.getFrom(), i.getTo());
            kidsRangeDTOList.add(new RangeAgesKidsDTO(i, quantity));
        }
        return new ResponseEntity<>(new ResponseDTO(200, kidsRangeDTOList, null), HttpStatus.OK);
    }

    @GetMapping(path="/forwardpositions/{positions}")
    public ResponseEntity<ResponseDTO> forwardPositions(@PathVariable String identification, int positions) throws Exception {
        listSEService.getKids().passByPosition(identification, positions);
        return new ResponseEntity<>(new ResponseDTO(200, "El niño ha adelantado de posición", null), HttpStatus.OK);
    }

    @GetMapping(path = "/change_extremes")
    public ResponseEntity<ResponseDTO> changeExtremes() throws ListSEException {
        listSEService.getKids().changesExtremes();
        return new ResponseEntity<>(new ResponseDTO(
                200, "Se ha intercambiado los extremos ", null), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ResponseDTO> addKid(@RequestBody KidDTO kidDTO) {
        Location location = locationService.getLocationsByCode(kidDTO.getCodeLocation());
        if (location == null) {
            return new ResponseEntity<>(new ResponseDTO(
                    404, "La ubicación no existe ", null), HttpStatus.OK);
        } try {
            listSEService.getKids().add(new Kid(kidDTO.getIdentification(),
                    kidDTO.getName(), kidDTO.getAge(),
                    kidDTO.getGender(), location));
        } catch (ListSEException e) {
            return new ResponseEntity<>(new ResponseDTO(
                    409, "Ya existe un niño con ese código", null
            ), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new ResponseDTO(
                200, "Se ha añadido", null), HttpStatus.OK);
    }

    @GetMapping(path = "/kidsbylocations")
    public ResponseEntity<ResponseDTO> getKidsByLocation() {
        List<KidsByLocationDTO> kidsByLocationDTOList = new ArrayList<>();
        for (Location loc : locationService.getLocations()) {
            int count = listSEService.getKids().getCountKidsByLocationCode(loc.getCode());
            if (count > 0) {
                kidsByLocationDTOList.add(new KidsByLocationDTO(loc, count));
            }
        }
        return new ResponseEntity<>(new ResponseDTO(
                200, kidsByLocationDTOList,
                null), HttpStatus.OK);
    }

    @GetMapping(path = "/kidsbydepartments")
    public ResponseEntity<ResponseDTO> getKidsByDepartmentCode() {
        List<KidsByLocationDTO> kidsByLocationDTOList = new ArrayList<>();
        for (Location loc : locationService.getLocations()) {
            int count = listSEService.getKids().getCountKidsByDepartmentCode(loc.getCode());
            if (count > 0) {
                kidsByLocationDTOList.add(new KidsByLocationDTO(loc, count));
            }
        }
        return new ResponseEntity<>(new ResponseDTO(
                200, kidsByLocationDTOList,
                null), HttpStatus.OK);
    }

    @GetMapping(path = "/kidsbylocationgenders/{age}")
    public ResponseEntity<ResponseDTO> getReportKidsLocationGenders(@PathVariable byte age) {
        ReportKidsDTO report =
                new ReportKidsDTO(locationService.getLocationsByCodeSize(8));
        listSEService.getKids().getReportKidsByLocationGendersByAge(age,report);
        return new ResponseEntity<>(new ResponseDTO(
                200, report,null),HttpStatus.OK);
    }
}
