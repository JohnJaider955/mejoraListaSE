package co.edu.umanizales.tads.model.listade;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Pet {
    private String nombre;
    private String raza;
    private int edad;
}
