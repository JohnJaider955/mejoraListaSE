package co.edu.umanizales.tads.service;

import co.edu.umanizales.tads.model.RangeAgeKids;
import co.edu.umanizales.tads.model.RangeAgePets;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Data
public class RangeAgePetsService {
    private List<RangeAgePets> rangesPets;

    public RangeAgePetsService(){
        rangesPets = new ArrayList<>();
        rangesPets.add(new RangeAgePets(1,3));
        rangesPets.add(new RangeAgePets(4,6));
        rangesPets.add(new RangeAgePets(7,9));
        rangesPets.add(new RangeAgePets(10,12));
        rangesPets.add(new RangeAgePets(13,15));
    }
}
