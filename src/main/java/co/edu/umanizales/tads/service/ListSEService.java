package co.edu.umanizales.tads.service;

import co.edu.umanizales.tads.model.Kid;
import co.edu.umanizales.tads.model.ListSE;
import co.edu.umanizales.tads.model.Node;
import lombok.Data;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

@Service
@Data
public class ListSEService {
    private ListSE kids;

    public ListSEService() {
        kids = new ListSE();

    }

     //public Node getKids() { return kids.getHead();}
    /*
    public Node getKids()
     */

    public void deleteByAge(byte age) { kids.getHead().getData().getAge(); }

    public void invert(){
        kids.invert();
    }

    /*
    
     */
}
