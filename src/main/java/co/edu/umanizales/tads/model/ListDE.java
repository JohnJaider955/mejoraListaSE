package co.edu.umanizales.tads.model;


import co.edu.umanizales.tads.controller.dto.ReportKidsDTO;
import co.edu.umanizales.tads.controller.dto.ReportPetsDTO;
import co.edu.umanizales.tads.exception.ListDEException;
import co.edu.umanizales.tads.exception.ListSEException;
import lombok.Data;

@Data
public class ListDE {
    private NodeDE head;
    private int size;

    //Adicionar
    public void addPet(Pet pet) throws ListDEException {
        if (this.head != null) {
            NodeDE temp = this.head;
            while (temp.getNextDE() != null) {
                if(temp.getDataDE().getCodePet().equals(pet.getCodePet())){
                    throw new ListDEException("Ya existe un niño");
                }
                temp = temp.getNextDE();
            }
            if(temp.getDataDE().getCodePet().equals(pet.getCodePet())){
                throw new ListDEException("Ya existe un niño");
            }
            NodeDE newPet = new NodeDE(pet);
            temp.setNextDE(newPet);
            newPet.setPrevious(temp);
        } else {
            this.head = new NodeDE(pet);
        }
        size++;
    }

    //Invertir lista
    public void invertPets() throws ListDEException{
        if (this.head != null) {
            ListDE listCP = new ListDE();
            NodeDE temp = this.head;
            while (temp != null) {
                listCP.addPetsToStart(temp.getDataDE());
                temp = temp.getNextDE();
            }
            this.head = listCP.getHead();
        }
        else{
            throw new ListDEException("La lista está vacía");
        }
    }

    //Mascotas masculinas al inicio y femeninos al final.
    public void orderPetsToStart() throws ListDEException {
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
        else{
            throw new ListDEException("La lista está vacía");
        }
    }

    //Intercalar mascota masculino, femenino, masculino, femenino
    public void intercalatePetsGender() throws ListDEException{
        ListDE listPetMale = new ListDE();
        ListDE listPetFemale = new ListDE();
        NodeDE temp = this.head;

        if (temp == null) {
            throw new ListDEException("La lista está vacía");
        }

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

    //Dada un código eliminar a las mascotas del código dado
    public void deletePetByIdentification(String code) throws ListSEException {
        if (this.head != null) {
            if (this.head.getDataDE().getCodePet().equals(code)) {
                this.head = this.head.getNextDE();
                if (this.head != null) {
                    this.head.setPrevious(null);
                }
            }
            else {
                NodeDE temp = this.head;
                while (temp != null) {
                    if (temp.getDataDE().getCodePet().equals(code)) {
                        temp.getPrevious().setNextDE(temp.getNextDE());
                        if (temp.getNextDE() != null) {
                            temp.getNextDE().setPrevious(temp.getPrevious());
                        }
                        return;
                    }
                    temp = temp.getNextDE();
                }
                throw new ListSEException("El código " + code + " no existe en la lista");
            }
        }
        else {
            throw new ListSEException("No hay datos en la lista");
        }
    }

    //Obtener el promedio de edad de las mascotas de la lista
    public float averageAgePets() throws ListDEException {
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
            throw new ListDEException("La lista está vacía");
        }
    }

    //Generar un reporte que me diga cuantas mascotas hay de cada ciudad.
    public int getCountPetsByLocationCode(String code) throws ListDEException {
        int count = 0;
        if (this.head != null) {
            NodeDE temp = this.head;
            while (temp != null) {
                if (temp.getDataDE().getLocationPets().getCode().equals(code)) {
                    count++;
                }
                temp = temp.getNextDE();
            }
            return count;
        } else{
            throw new ListDEException("La lista está vacía");
        }
    }

    public int getCountPetsByDepartmentCode(String code) throws ListDEException{
        int count = 0;
        if (this.head != null) {
            NodeDE temp = this.head;
            while (temp != null) {
                if (temp.getDataDE().getLocationPets().getCode().equals(code)) {
                    count++;
                }
                temp = temp.getNextDE();
            }
            return count;
        }
        else{
            throw new ListDEException("La lista está vacía");
        }
    }

    public void getReportPetsByLocationGendersByAge(byte age, ReportPetsDTO report){
        if(head != null){
            NodeDE temp = this.head;
            while(temp!=null){
                if(temp.getDataDE().getAgePet() > age){
                    report.updateQuantityPets(temp.getDataDE().getLocationPets().getName(),
                            temp.getDataDE().getGenderPet());
                }
                temp = temp.getNextDE();
            }
        }
    }

    //Método que me permita decirle a una mascota determinada que adelante un número de posiciones dadas
    public void passPetByPosition(String codePet, int positions) throws ListDEException{
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
                    NodeDE temp2 = new NodeDE(temp.getNextDE().getDataDE());
                    temp2.setPrevious(temp);
                    temp.setNextDE(temp2);
                    if(positions >= count+1){
                        addPetByPosition(temp2.getDataDE(), positions);
                    }
                }
            }
            else{
                throw new ListDEException("La posición ingresada es mayor o igual al tamaño de la lista.");
            }
        }
        else{
            throw new ListDEException("La lista se encuentra vacía.");
        }
    }

    //Método que me permita decirle a una mascota determinada que pierda un numero de posiciones dadas
    public void afterwardsPetsPositions(String codePet, int positions) throws ListDEException {
        if (head != null) {
            if (positions < size) {
                if (head.getDataDE().getCodePet() == codePet) {
                    NodeDE node = new NodeDE(head.getNextDE().getDataDE());
                    addPetByPosition(node.getDataDE(),positions + 1 );
                    head = head.getNextDE();
                    head.setPrevious(null);
                } else {
                    int count = 1;
                    NodeDE temp = head;
                    while (temp.getNextDE() != null && temp.getNextDE().getDataDE().getCodePet() != codePet) {
                        temp = temp.getNextDE();
                        count++;
                    }
                    if (temp.getNextDE() == null) {
                        throw new ListDEException("No se encontró un nodo con la identificación proporcionada.");
                    } else {
                        NodeDE temp2 = new NodeDE(temp.getNextDE().getDataDE());
                        temp.setNextDE(temp.getNextDE().getNextDE());
                        if (temp.getNextDE() != null) {
                            temp.getNextDE().setPrevious(temp);
                        }
                        addPetByPosition(temp2.getDataDE(), count + 1 + positions);
                    }
                }
            } else {
                throw new ListDEException("La posición proporcionada excede el tamaño de la lista");
            }
        } else {
            throw new ListDEException("La lista se encuentra vacía.");
        }
    }

    //Obtener un informe de mascotas por rango de edades
    public int rangePetsByAge(int min, int max) throws ListDEException{
        if (head == null) {
            throw new ListDEException("La lista se encuentra vacía.");
        }
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

    //Implementar un método que me permita enviar al final de la lista a las mascotas que su nombre inicie con una letra dada
    public void petsByLetter(char initial) throws ListDEException{

        if (this.head == null) {
            throw new ListDEException("La lista está vacía");
        }

        ListDE listCP = new ListDE();
        NodeDE temp = this.head;

        while (temp != null){
            if (temp.getDataDE().getNamePet().charAt(0) != Character.toUpperCase(initial)){
                listCP.addPet(temp.getDataDE());
            }
            temp = temp.getNextDE();
        }

        temp = this.head;

        while (temp != null){
            if (temp.getDataDE().getNamePet().charAt(0) == Character.toUpperCase(initial)){
                listCP.addPet(temp.getDataDE());
            }
            temp = temp.getNextDE();
        }

        this.head = listCP.getHead();
    }

    public void addPetByPosition(Pet pet, int position) throws ListDEException{
        if (position < 0 || position > size) {
            throw new ListDEException("Posición inválida");
        }
        NodeDE newNode = new NodeDE(pet);
        if (position == 0){
            newNode.setNextDE(head);
            if (head != null){
                head.setPrevious(newNode);
            }
            head = newNode;
        } else {
            NodeDE temp = head;
            for (int i = 0; i < position - 1; i++){
                temp = temp.getNextDE();
            }
            newNode.setNextDE(temp.getNextDE());
            if (temp.getNextDE()!=null){
                temp.getNextDE().setPrevious(newNode);
            }
            temp.setNextDE(newNode);
            newNode.setPrevious(temp);
        }
        size++;
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

    public void changesPetExtremes() throws ListDEException{
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
        else
        {
            throw new ListDEException("No es posible cambiar de extremos.");
        }
    }

    //Ejercicio sustentación: Doblemente enlazada
    /*
    Explicación:
    Si hay datos o no esté vacío
        Si en la cabeza coincide con la identificación de la mascota almacenada con el código a comparar y eliminar
        si es así, que actualice el nodo (la cabeza) que apunte al siguiente nodo en la lista
            si la cabeza hay datos
            que el nodo previo se actualice a nulo (al ser el primero, atrás no hay nada)
        si no, si el primer nodo, o en la cabeza no está el nodo a eliminar se empieza a buscar en los nodos
            llamo a un ayudante y que se posicione en la cabeza
            mientras hayan datos o no sea nulo
                que el ayudante compruebe si en ese nodo contiene el código de la mascota a eliminar
                que el ayudante tome igual el nodo anterior y actualice al nodo siguiente para llegar al nodo actual
                que se pase el ayudante
                    si ahí en el nodo siguiente donde está el ayudante hay datos o no sea nulo
                    que el ayudante actualice al nodo previo para que apunte al nodo actual
                    que se rompa la iteración de búsqueda al eliminar el nodo con el código a eliminar
                que el ayudante tome el siguiente nodo (o se pase al final).
     */
    public void deleteKamikazeByPosition(String codePet) {
        if (this.head != null) {
            if (this.head.getDataDE().getCodePet().equals(codePet)) {
                this.head = this.head.getNextDE();
                if (this.head != null) {
                    this.head.setPrevious(null);
                }
            } else {
                NodeDE temp = this.head;
                while (temp != null) {
                    if (temp.getDataDE().getCodePet().equals(codePet)) {
                        temp.getPrevious().setNextDE(temp.getNextDE());
                        if (temp.getNextDE() != null) {
                            temp.getNextDE().setPrevious(temp.getPrevious());
                        }
                        return;
                    }
                    temp = temp.getNextDE();
                }
            }
        }
    }
}





