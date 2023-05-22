package co.edu.umanizales.tads.model;

import co.edu.umanizales.tads.controller.dto.ReportPetsDTO;
import co.edu.umanizales.tads.exception.ListDEException;
import co.edu.umanizales.tads.exception.ListSEException;
import lombok.Data;

import java.util.List;

import java.util.ArrayList;

@Data
public class ListDE {
    private NodeDE head;
    private int size;

    private List<Pet> pets = new ArrayList<>();

    //Adicionar
    /**
     si hay datos
        llamo a un ayudante y que se posicione en la cabeza
        mientras en el siguiente nodo, o en el brazo exista algo
            que el ayudante se pase al siguiente nodo
            que se cree un nuevo nodo con el objeto led que se proporcionó previamente
        que el ayudante se pase al nodo siguiente y se establezca como nuevo nodo
        que se establezca el nodo anterior del nuevo nodo con el ayudante

     si está vacía, que se establezca como primer nodo siendo la cabeza

     que se incremente el tamaño de la lista
     */
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
    /**
    Si hay datos
    si
        creo una lista copia temporal
        llamo a un ayudante y le digo que se posicione en la cabeza
        mientras hayan datos o no sea null
            posiciono la lista copia al inicio junto al ayudante con los datos
            que el ayudante tome el siguiente nodo (o se pase al final)

            que se posicione en la cabeza la copia.
     */
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
    /**
    si hay datos
    sí
        creo una lista copia temporal
        llamo a un ayudante y le digo que se posicione en la cabeza
        mientras hayan datos o no sea null
            compruebo si el género es masculino, si es así que se agregue al inicio junto al ayudante
            si es femenino, que se agregue al final
            que el ayudante tome el siguiente nodo (o se pase al final)
            que se posicione en la cabeza la copia.
     */
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
    /**
    Se crean dos nuevas listas enlazadas para separar los nodos de la lista original según el género
    llamo un ayudante y que se posicione en la cabeza original
    mientras hayan datos o no sea null
        que ayudante compruebe si el nodo actual es masculino o femenino y que se agregue a la lista que corresponda
        que se almacenen en el nodo actual
    que se cree una nueva lista vacía para almacenar los nodos de la lista original en un orden deseado
    que se creen unos nodos temporales y que se posicionen en la cabeza de las listas de niños y niñas previas
    mientras hayan datos o no sean nulos en cada nodo de niños y niñas
        que se agregue el nodo a la nueva lista
    que se posicione en la cabeza de la lista original a la nueva lista de niños intercalados
     */
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
    /**
    Si hay datos
        si el primer nodo coincide o es igual con el código dado previamente
        Si es así, que actualice el primer nodo para que apunte al siguiente nodo en la lista
        si aún hay datos en la lista
            si hay algún nodo restante, que se establezca el nodo anterior del nuevo primer nodo como nulo
        si no
            llamo a un ayudante y que se posicione en la cabeza
            mientras hayan datos o no sea null
                verificar si el código del nodo actual, donde está parado el ayudante coincide
                que el ayudante también tome el nodo previo
                 y si código coincide, que se actualicen los enlaces de los nodos
                Si en el siguiente nodo al nodo actual hay datos
                    si lo hay, que se establece el nodo anterior del siguiente nodo (donde está el ayudante)
                    como el anterior del nodo actual
                si el nodo es eliminado, que retorne o se salga del método
            que el ayudante tome el siguiente nodo (o se pase al final)
     */
    public void deletePetByIdentification(String code) throws ListDEException {
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
                throw new ListDEException("El código " + code + " no existe en la lista");
            }
        }
        else {
            throw new ListDEException("No hay datos en la lista");
        }
    }

    //Obtener el promedio de edad de las mascotas de la lista
    /**
     creo un ayudante y que se posicione en la cabeza
     inicializo un contador en cero al igual que la edad (irán aumentando)
     mientras hayan datos o no sea null
     que aumente el contador
     ahora que se sume la edad de los niños con el promedio a calcular
     que el ayudante tome el siguiente nodo (o se pase al final)
     se declara una variable que contendría la suma de las edades
     si el contador fue mayor a 0
     que se divida la edad por lo obtenido en el contador (la cantidad de mascotas)
     que me retorne el promedio ya calculado.
     */
    public float averageAgePets() throws ListDEException {
        if (head == null) {
            throw new ListDEException("La lista está vacía. No se puede calcular el promedio de edades.");
        }
        NodeDE temp = head;
        int count = 0;
        int ages = 0;
        while(temp != null) {
            count++;
            ages = ages + temp.getDataDE().getAgePet();
            temp = temp.getNextDE();
        }
        float average = 0;
        if (count > 0) {
            average = ages / (float)count;
        }
        return average;
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
    /**
     llamo a un ayudante y que se posicione en la cabeza
     inicializo un contador en 1 (teniendo en cuenta que la lista tenga alguien en la posición)
     mientras en la cabeza hayan datos y que verifique si la identificación que se dio previamente no es igual a la
     identificación específica proporcionada
     si las identificaciones no son iguales, que continúe buscando.
     si ambas condiciones se cumplen, significa que el nodo donde está parado el ayudante no es nulo y su
     identificación no coincide con la edad buscada, entonces que el ayudante se pase al siguiente nodo y que
     se incremente el contador
     si en el nodo siguiente donde está parado ahora el ayudante hay datos
     se calcula la diferencia entre la posición actual del nodo y la posición a la que se quiere mover
     se obtiene una variable del niño donde salió del nodo actual obteniendo sus datos
     ahora que se elimine el nodo actual de la lista gracias a su identificación
     si la diferencia calculada es mayor a 0, que se agregue el niño en la posición del resultado de
     calcular la posición actual del nodo y la posición de la lista
     si no
     si la diferencia es menor a 0, que se agregue el niño al inicio de la lista.
     */
    public void passPetPosition(String codePet, int position, ListDE listDE) throws ListDEException{
        NodeDE temp = this.head;
        int count = 1;
        while (temp != null && !temp.getDataDE().getCodePet().equals(codePet)) {
            temp = temp.getNextDE();
            count++;
        }
        if (temp != null) {
            int difference = count - position;
            Pet pet = temp.getDataDE();
            listDE.deletePetByIdentification(temp.getDataDE().getCodePet());
            if (difference > 0) {
                listDE.addPetInPosition(difference, pet);
            } else {
                listDE.addPetsToStart(pet);
            }
        } else {
            throw new ListDEException("No se encontró ningún niño con la identificación especificada.");
        }
    }


    //Método que me permita decirle a una mascota determinada que pierda un numero de posiciones dadas
    /**
     llamo a un ayudante y le digo que se posicione en la cabeza
     inicializo un contador en 1 (teniendo en cuenta que la lista tenga alguien en la posición)
     mientras en la cabeza hayan datos y que verifique si la identificación que se dio previamente no es igual a la
     identificación específica proporcionada
     si las identificaciones no son iguales, que continúe buscando.
     si ambas condiciones se cumplen, significa que el nodo donde está parado el ayudante no es nulo y su
     identificación no coincide con la edad buscada, entonces que el ayudante se pase al siguiente nodo y que
     se incremente el contador
     se calcula la suma de la posición actual del nodo encontrado y la posición a retroceder
     si en el nodo siguiente donde está parado ahora el ayudante hay datos
     se obtiene una variable del niño donde salió del nodo actual obteniendo sus datos
     que se elimine el nodo actual de la lista por su identificación
     que se agregue el niño en la posición que da el total de la suma en la lista.
     */
    public void backPetPositions(String codePet, int position) throws ListDEException {
        NodeDE temp = this.head;
        int count = 1;
        while (temp != null && !temp.getDataDE().getCodePet().equals(codePet)) {
            temp = temp.getNextDE();
            count++;
        }
        int sum = position + count;
        if (temp != null) {
            Pet pet = temp.getDataDE();
            deletePetByIdentification(temp.getDataDE().getCodePet());
            addPetInPosition(sum, pet);
        } else {
            throw new ListDEException("No se encontró ningún niño con la identificación especificada.");
        }
    }

    //Obtener un informe de mascotas por rango de edades
    /**
    llamo a un ayudante y que se posicione en la cabeza
    inicializo un contador en cero
        mientras hayan datos
            que verifique si la edad del nodo actual está dentro del rango especificado
            si dentro encuentra una edad dentro del rango, que se añada al contador
            que el ayudante me actualice pasándose al siguiente
    que me retorne el contador devolviéndome su valor final
     */
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
    /**
    Creo una lista copia temporal
    llamo a un ayudante que se posicione en la cabeza
    mientras hayan datos
        que se compruebe si  el primer carácter del nombre del nodo actual es diferente a la letra initial mediante el método charAt
        si es así, el nodo actual se agrega al final de la lista copia
    que el ayudante se posicione en la cabeza
    mientras hayan datos
        que realice el paso similar al primer bucle, pero esta vez agrega todos los nodos que comienzan con la letra initial al final de la lista copia
    que se posicione en la cabeza la copia
     */
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

    /**
    Si en la cabeza hay datos o no está vacía la lista
        si la posición es la primera (1)
        si es la primera, que se llame el método añadir al inicio y se inserte la mascota
     si no
        llamo a un ayudante y que se posicione en la cabeza
        inicializo un contador en 1
        mientras hayan datos y que el contador es menor que el valor de la posición -1
        que se ejecute hasta llegar a la posición anterior deseada
        que el ayudante tome el siguiente nodo
        que el contador se incremente
     si siguen habiendo datos en el siguiente nodo donde está el ayudante
        que se cree un nuevo nodo y metemos a la mascota ahí
        que se establezca el enlace del nuevo nodo al siguiente nodo después de la posición deseada (allí se parará el ayudante)
        ahora que se establezca referencia del nodo anterior para el nuevo nodo
        si en el siguiente nodo hay algo
            que se actualice el nodo donde está el ayudante con el enlace previo
        que se actualice las referencias del nodo actual y el nuevo nodo
     */
    public void addPetInPosition(int position, Pet pet) throws ListDEException {
        if (size < position) {
            throw new ListDEException("Se ingresó una posición más grande que la lista.");
        }

        if (head != null) {
            if (position == 1) {
                addPetsToStart(pet);
            } else {
                NodeDE temp = head;
                int count = 1;
                while (temp != null && count < position - 1) {
                    temp = temp.getNextDE();
                    count++;
                }
                if (temp != null) {
                    NodeDE newNode = new NodeDE(pet);
                    newNode.setNextDE(temp.getNextDE());
                    newNode.setPrevious(temp);
                    if (temp.getNextDE() != null) {
                        temp.getNextDE().setPrevious(newNode);
                    }
                    temp.setNextDE(newNode);
                }
            }
        }
    }

    //Para que me pueda imprimir más de dos elementos de la lista
    public List<Pet> print(){
        pets.clear();
        if(head != null){
            NodeDE temp = head;
            while(temp != null){
                pets.add(temp.getDataDE());
                temp = temp.getNextDE();
            }
        }
        return pets;
    }


    /**
     La implementación de los throw es con el objetivo de lograr capturar algún error y logre
     finalizar correctamente los métodos, sin ser tan abrupto.
     La idea, es mostrar  un error o una situación inusual que ocurre durante la ejecución de un programa.
     y tome medidas adecuadas para manejar el error
     */
}





