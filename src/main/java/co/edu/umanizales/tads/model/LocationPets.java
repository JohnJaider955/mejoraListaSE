package co.edu.umanizales.tads.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.*;
@Data
@AllArgsConstructor
public class LocationPets {
    @NotNull
    @NotEmpty
    private String code;
    @NotNull
    @NotEmpty
    private String name;
}
