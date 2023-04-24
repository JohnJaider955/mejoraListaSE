package co.edu.umanizales.tads.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
public class ReportKidsDTO {
    private List<KidGenderDTO> genders;

    private int quantity;
}
