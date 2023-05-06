package co.edu.umanizales.tads.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class ErrorDTO {
    private int code;
    private String message;
}
