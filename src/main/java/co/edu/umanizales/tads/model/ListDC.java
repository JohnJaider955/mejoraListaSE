package co.edu.umanizales.tads.model;

import co.edu.umanizales.tads.exception.ListDEException;
import lombok.Data;

import java.util.Random;
@Data
public class ListDC {
    private NodeDE head;
    int size;

    public void addPetCircular(Pet pet) throws ListDEException {
        if (this.head != null) {
            NodeDE temp = this.head;
            while (temp.getNextDE() != this.head) {
                if(temp.getDataDE().getCodePet().equals(pet.getCodePet())){
                    throw new ListDEException("Ya existe una mascota");
                }
                temp = temp.getNextDE();
            }
            if(temp.getDataDE().getCodePet().equals(pet.getCodePet())){
                throw new ListDEException("Ya existe una mascota");
            }
            NodeDE newPet = new NodeDE(pet);
            temp.setNextDE(newPet);
            newPet.setPrevious(temp);
            newPet.setNextDE(this.head);
            this.head.setPrevious(newPet);
        }
        else {
            this.head = new NodeDE(pet);
            this.head.setNextDE(this.head);
            this.head.setPrevious(this.head);
        }
        size++;
    }

    public void addLedsToStart(Pet pet) {
        NodeDE newNodeDE = new NodeDE(pet);
        if (head == null) {
            newNodeDE.setNextDE(newNodeDE);
            newNodeDE.setPrevious(newNodeDE);
            head = newNodeDE;
        } else {
            newNodeDE.setNextDE(head);
            newNodeDE.setPrevious(head.getPrevious());
            head.getPrevious().setNextDE(newNodeDE);
            head.setPrevious(newNodeDE);
            head = newNodeDE;
        }
        size++;
    }
}
