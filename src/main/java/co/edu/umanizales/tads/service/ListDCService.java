package co.edu.umanizales.tads.service;

import co.edu.umanizales.tads.model.ListDC;
import lombok.Data;
import org.springframework.stereotype.Service;

@Service
@Data
public class ListDCService {
    private ListDC petsDECircular;

    public ListDCService() {
        petsDECircular = new ListDC();
    }
}
