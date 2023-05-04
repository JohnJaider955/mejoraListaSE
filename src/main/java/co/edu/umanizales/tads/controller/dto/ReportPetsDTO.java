package co.edu.umanizales.tads.controller.dto;

import co.edu.umanizales.tads.model.LocationPets;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ReportPetsDTO {
    private List<LocationGenderQuantityPetsDTO> locationGenderQuantityPetsDTOS;

    public ReportPetsDTO(List<LocationPets> cities){
        locationGenderQuantityPetsDTOS = new ArrayList<>();
        for(LocationPets locationPets: cities){
            locationGenderQuantityPetsDTOS.add(new
                    LocationGenderQuantityPetsDTO(locationPets.getName()));
        }
    }

    //método actualizar
    public void updateQuantityPets(String city, char gender){
        for(LocationGenderQuantityPetsDTO loc: locationGenderQuantityPetsDTOS){
            if (loc.getCity().equals(city)) {
                for(PetGenderDTO petGenderDTO: loc.getGenders()){
                    if(petGenderDTO.getGender()==gender){
                        petGenderDTO.setQuantity(petGenderDTO.getQuantity()+1);
                        loc.setTotal(loc.getTotal()+1);
                        return;
                    }
                }
            }
        }
    }

}
