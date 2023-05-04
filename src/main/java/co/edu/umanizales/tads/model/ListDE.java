package co.edu.umanizales.tads.model;


import co.edu.umanizales.tads.controller.dto.ReportPetsDTO;
import lombok.Data;

@Data
public class ListDE {
    private NodeDE head;
    private int size;

    /*
    Algoritmo de adicionar al final
    Se pide de entrada un niño
    si hay datos
    si
        llamo a un ayudante y le digo que se posicione en la cabeza
        mientras en el brazo exista algo
            pasese al siguiente
        va estar ubicado en el último

        meto al niño en un costal (nuevo costal)
        y le digo al último que tome el nuevo costal
    no
        metemos el niño en el costal y ese costal es la cabeza
     */
    public void addPet(Pet pet) {
        if (this.head != null) {
            NodeDE temp = this.head;
            while (temp.getNextDE() != null) {
                temp = temp.getNextDE();
            }
            NodeDE newPet = new NodeDE(pet);
            temp.setNextDE(newPet);
            newPet.setPrevious(temp);
        } else {
            this.head = new NodeDE(pet);
        }
    }

    public void addPetsToStart(Pet pet) {
        if (head != null) {
            NodeDE newNodeDE = new NodeDE(pet);
            newNodeDE.setNextDE(head);
            head.setPrevious(newNodeDE);
            head = newNodeDE;
        } else {
            head = new NodeDE(pet);
        }
        size++;
    }

    public void invertPets() {
        if (this.head != null) {
            ListDE listCP = new ListDE();
            NodeDE temp = this.head;
            while (temp != null) {
                listCP.addPetsToStart(temp.getDataDE());
                temp = temp.getNextDE();
            }
            this.head = listCP.getHead();
        }
    }

    public void orderPetsToStart() {
        if (this.head != null) {
            ListDE listCP = new ListDE();
            NodeDE temp = this.head;
            while (temp != null) {
                if (temp.getDataDE().getGenderPet() == 'M') {
                    listCP.addPetsToStart(temp.getDataDE());
                } else {
                    listCP.addPet(temp.getDataDE());
                }
                temp = temp.getNextDE();
            }
            this.head = listCP.getHead();
        }
    }

    public void changesPetExtremes() {
        if (this.head != null && this.head.getNextDE() != null) {
            NodeDE temp = this.head;
            while (temp.getNextDE() != null) {
                temp = temp.getNextDE();
            }

            Pet copy = this.head.getDataDE();
            this.head.setDataDE(temp.getDataDE());
            temp.setDataDE(copy);

            NodeDE tempPrev = temp.getPrevious();
            NodeDE headNext = this.head.getNextDE();
            this.head.setNextDE(temp.getNextDE());
            this.head.setPrevious(temp);
            temp.setNextDE(headNext);
            temp.setPrevious(tempPrev);
        }
    }

    public void intercalatePetsGender() {
        ListDE listPetMale = new ListDE();
        ListDE listPetFemale = new ListDE();
        NodeDE temp = this.head;
        while (temp != null) {
            if (temp.getDataDE().getGenderPet() == 'M') {
                listPetMale.addPet(temp.getDataDE());
            }
            if (temp.getDataDE().getGenderPet() == 'F') {
                listPetFemale.addPet(temp.getDataDE());
            }
            temp = temp.getNextDE();
        }
        ListDE newListPetsFemale = new ListDE();
        NodeDE petMaleNode = listPetMale.getHead();
        NodeDE petFemaleNode = listPetFemale.getHead();
        while (petMaleNode != null || petFemaleNode != null) {
            if (petMaleNode != null) {
                newListPetsFemale.addPet(petMaleNode.getDataDE());
                petMaleNode = petMaleNode.getNextDE();
            }
            if (petFemaleNode != null) {
                newListPetsFemale.addPet(petFemaleNode.getDataDE());
                petFemaleNode = petFemaleNode.getNextDE();
            }
        }
        this.head = newListPetsFemale.getHead();
    }

    public float averageAgePets() {
        if (head != null) {
            NodeDE temp = head;
            int contador = 0;
            int ages = 0;
            while (temp.getNextDE() != null) {
                contador++;
                ages = ages + temp.getDataDE().getAgePet();
                temp = temp.getNextDE();
            }
            return (float) ages / contador;
        } else {
            return (float) 0;
        }
    }

    public int rangePetsByAge(int min, int max){
        NodeDE temp = head;
        int count = 0;
        while(temp != null){
            if(temp.getDataDE().getAgePet() >= min && temp.getDataDE().getAgePet() <= max){
                count++;
            }
            temp = temp.getNextDE();
        }
        return count;
    }

    public void deleteByPetCode (String petCodeDE){
        NodeDE temp = head;
        NodeDE prev = null;

        while (temp.getNextDE() != null && temp.getDataDE().getCodePet() == petCodeDE) {
            prev = temp;
            temp = temp.getNextDE();
        }

        if(temp != null){
            if (prev == null){
                head = temp.getNextDE();
                if (head != null){
                    head.setPrevious(null);
                }
            }else {
                prev.setNextDE(temp.getNextDE());
                if (temp.getNextDE() != null){
                    temp.getNextDE().setPrevious(prev);
                }
            }
            size--;
        }
    }

    public void passByPosition(String codePet, int positions){
        if (head != null){
            if(positions<size){
                if(head.getDataDE().getCodePet()==codePet){

                }
                else{
                    int count = 1;
                    NodeDE temp = head;
                    while(temp.getNextDE().getDataDE().getCodePet()!=codePet){
                        temp = temp.getNextDE();
                        count++;
                        if(temp.getNextDE()!=null){
                            return;
                        }
                    }
                    NodeDE temp2=new NodeDE(temp.getNextDE().getDataDE());
                    temp2.setPrevious(temp);
                    temp.setNextDE(temp2);
                    if(positions >= count+1){
                        addPetsToStart(temp2.getDataDE());
                    }
                }
            }
        }
    }

    public void getReportPetsByVetGendersByAge(int age, ReportPetsDTO report){
        if (head!=null){
            NodeDE temp = this.head;
            while (temp != null){
                if(temp.getDataDE().getAgePet()>age){
                    report.updateQuantityPets(temp.getDataDE().getLocationPets().getName(),
                            temp.getDataDE().getGenderPet());
                }
                temp = temp.getNextDE();
            }
        }
    }
}



