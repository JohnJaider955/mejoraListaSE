package co.edu.umanizales.tads.service;

import co.edu.umanizales.tads.model.Kid;
import co.edu.umanizales.tads.model.ListSE;
import lombok.Data;
import org.springframework.stereotype.Service;

@Service
@Data
public class ListSEService {
    private ListSE kids;

    public ListSEService() {
        kids = new ListSE();
    }

    public void KidsDataService() {
        kids = new ListSE();
    }

     //public Node getKids() { return kids.getHead();}
    /*
    public Node getKids()
     */

    public void deleteByAge(byte age) { kids.getHead().getData().getAge(); }

    public void invert() throws Exception {
        kids.invert();
    }

    public void addToStart(Kid kid) throws Exception {
        kids.addToStart(kid);
    }

    public void addToFinal(Kid kid){
        kids.addToFinal(kid);
    }

}
