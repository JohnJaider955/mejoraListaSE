package co.edu.umanizales.tads.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import javax.validation.constraints.*;

@NotNull
@Data
@AllArgsConstructor
public class Pet {

    @NotNull
    @NotBlank(message = "Este campo requiere información.")
    private String codePet;
    @NotNull(message = "Este campo requiere información.")
    private char genderPet;
    @NotNull(message = "Este campo requiere información.")
    @Positive
    @Min(value = 1, message = "La edad mínima debe ser mayor a 0.")
    @Max(value = 15, message = "La edad máxima debe ser menor o igual a 15.")
    private int agePet;
    @NotNull
    @NotBlank(message = "Este campo requiere información.")
    @Size(min=2, max = 30, message = "La longitud máxima del nombre es de 30 caracteres.")
    private String namePet;
    @NotNull(message = "Este campo requiere información.")
    private LocationPets locationPets;

    private boolean bath;

}
