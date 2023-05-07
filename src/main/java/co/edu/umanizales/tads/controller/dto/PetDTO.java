package co.edu.umanizales.tads.controller.dto;

import lombok.Data;

@Data
public class PetDTO {
    private String identificationPet;
    private String namePet;
    private byte agePet;
    private char genderPet;
    private String codeLocationPet;
}
