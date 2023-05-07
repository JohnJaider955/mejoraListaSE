package co.edu.umanizales.tads.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.*;
@Data
@AllArgsConstructor
public class Kid {
    @NotNull
    @NotBlank(message = "Este campo requiere información.")
    private String identification;
    @NotNull
    @NotBlank(message = "Este campo requiere información.")
    @Size(min=2, max = 30, message = "La longitud máxima del nombre es de 30 caracteres.")
    private String name;
    @NotNull(message = "Este campo requiere información.")
    @Positive
    @Min(value = 1, message = "La edad mínima debe ser mayor a 0.")
    @Max(value = 15, message = "La edad máxima debe ser menor o igual a 15.")
    private byte age;
    @NotNull(message = "Este campo requiere información.")
    private char gender;
    @NotNull(message = "Este campo requiere información.")
    private Location location;
}
