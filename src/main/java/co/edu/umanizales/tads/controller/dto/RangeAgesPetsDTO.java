package co.edu.umanizales.tads.controller.dto;

import co.edu.umanizales.tads.model.RangeAgePets;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RangeAgesPetsDTO {
    private RangeAgePets rangePets;
    int quantityPets;
}
