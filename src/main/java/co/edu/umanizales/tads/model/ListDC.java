package co.edu.umanizales.tads.model;

import co.edu.umanizales.tads.exception.ListDEException;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Data
public class ListDC {
    private NodeDE head;
    int size;
    private List<Pet> petsCircular = new ArrayList<>();

    /**
     -Añadir mascota.
      si en la cabeza hay datos
      llamo a un ayudante y que se posicione en la cabeza
      si en el siguiente hay algo diferente a la cabeza
      que el ayudante se pase al siguiente nodo
      que se cree un nuevo nodo con el objeto led que se proporcionó previamente
      que el ayudante se pase al nodo siguiente y se establezca como nuevo nodo
      que se establezca el nodo anterior del nuevo nodo con el ayudante
      ahora que se establezca el nuevo nodo de la mascota como la cabeza de la lista
      que se establezca el nodo anterior de la cabeza de la lista como nueva mascota.
     */
    public void addPetCircular(Pet pet) throws ListDEException {
        if (this.head != null) {
            NodeDE temp = this.head;
            while (temp.getNextDE() != this.head) {
                if (temp.getDataDE().getCodePet().equals(pet.getCodePet())) {
                    throw new ListDEException("Ya existe una mascota");
                }
                temp = temp.getNextDE();
            }
            if (temp.getDataDE().getCodePet().equals(pet.getCodePet())) {
                throw new ListDEException("Ya existe una mascota");
            }
            NodeDE newPet = new NodeDE(pet);
            temp.setNextDE(newPet);
            newPet.setPrevious(temp);
            newPet.setNextDE(this.head);
            this.head.setPrevious(newPet);
        } else {
            this.head = new NodeDE(pet);
            this.head.setNextDE(this.head);
            this.head.setPrevious(this.head);
        }
        size++;
    }

    /**
    -Bañar mascota
    si la lista, empezando por la cabeza, está vacía que devuelva -1 para que me indique que no se
    puede bañar a ninguna mascota
    creo un tamaño de la lista de tipo entero
    importo y llamo el objeto random que me va ayudar a generar un número aleatorio a partir del tamaño
    1 de la lista
    llamo a un ayudante y que se posicione en la cabeza
    inicializo un contador en 1 que me va a llevar los conteos de los nodos recorridos
    previamente, se declaró un lado, ya que al ser random, si elegimos izquierda o derecha debe escoger al azar
    a la mascota para bañar
    si el lado especificado es 'l' (left - izquierda) que se actualice el nodo donde está parado el ayudante
    con el nodo anterior a ese.
    mientras, que me recorra la lista hasta llegar a la posición aleatoria
        si el lado especificado ahora es 'r' (right - derecha), que el ayudante se pase
        pero, si el lado es izquierdo, que el ayudante avance al nodo anterior a ese nodo
        que me incremente el contador
    ahora, es necesario actualizar los nodos donde está el ayudante para saber si ya está bañado o no
        así va a estar el ayudante pasándose por cada nodo para así actualizar
    ahora, que se obtenga la mascota asociada al nodo donde está ubicado el ayudante y se le asigna
    ahora, que me verifique si la mascota ha sido bañada
        si la mascota ya fue bañada que niegue a los que no han sido bañados
        si no ha sido bañada la mascota que se marque con un true y me devuelva la posición aleatoria
    si no ha sido bañada, que me devuelva un 0 indicando que no se realizó ningún baño
     */
    public int bathPet(String side) {
        if (head == null) {
            return -1;
        }
        int size = getSize();
        Random random = new Random();
        int randomPosition = random.nextInt(size) + 1;
        NodeDE temp = head;
        int cont = 1;
        if (side.equals("l")) {
            temp = head.getPrevious();
        }
        while (cont < randomPosition) {
            if (side.equals("r")) {
                temp = temp.getNextDE();
            } else if (side.equals("l")) {
                temp = temp.getPrevious();
            }
            cont++;
        }
        if (side.equals("l")) {
            temp = temp.getNextDE();
        }
        Pet pet = temp.getDataDE();
        if (!pet.isBath()) {
            pet.setBath(true);
            return randomPosition;
        } else {
            return 0;
        }
    }

    /**
    -Añadir mascota al inicio
    si la cabeza es igual a nulo, o está vacía
        que se añada una mascota de forma circular siendo puntero de la lista y único elemento
    si no
        se crea un nuevo nodo y guardamos a la mascota allí
        se obtiene el nodo anterior al nodo de la cabeza actual
        llamo al ayudante y que se posicione allí con la cabeza y el nodo anterior
        ahora que se establezca el ayudante donde está parado y apuntando del nodo anterior apunta al nuevo nodo
        ahora que se establezca  el puntero previo con el ayudante del nuevo nodo que apunta al nodo anterior
        que en el nuevo nodo apunte a la cabeza
        el puntero previo de la cabeza actual ahora apunta al nuevo nodo
        que actualice la cabeza para que apunte al nuevo nodo
        que incremente el tamaño
     */
    public void addPetToStart(Pet pet) throws ListDEException {
        if (head == null) {
            addPetCircular(pet);
        } else {
            NodeDE newNode = new NodeDE(pet);
            NodeDE temp = head.getPrevious();
            if (temp == null) {
                throw new ListDEException("El nodo anterior de la cabeza es nulo.");
            }
            temp.setNextDE(newNode);
            newNode.setPrevious(temp);
            newNode.setNextDE(head);
            head.setPrevious(newNode);
            head = newNode;
            size++;
        }
    }

    /**
    -Añadir mascota al final
    si la cabeza es igual a nulo, o está vacía
        que se añada una mascota de forma circular siendo puntero de la lista y único elemento
    si no
        se crea un nuevo nodo y guardamos a la mascota allí
        se obtiene el nodo anterior al nodo de la cabeza actual
        llamo al ayudante y que se posicione allí con la cabeza y el nodo anterior
        ahora que se establezca el ayudante donde está parado y apuntando del nodo anterior apunta al nuevo nodo
        ahora que se establezca  el puntero previo con el ayudante del nuevo nodo que apunta al nodo anterior
        que en el nuevo nodo apunte a la cabeza
        el puntero previo de la cabeza actual ahora apunta al nuevo nodo
        que incremente el tamaño
     */
    public void addPetToEnd(Pet pet) throws ListDEException {
        if (head == null) {
            addPetCircular(pet);
        } else {
            NodeDE newNode = new NodeDE(pet);
            NodeDE temp = head.getPrevious();
            if (temp == null) {
                throw new ListDEException("El nodo anterior de la cabeza es nulo.");
            }
            temp.setNextDE(newNode);
            newNode.setPrevious(temp);
            newNode.setNextDE(head);
            head.setPrevious(newNode);
            size++;
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
                addPetToStart(pet);
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
    public List<Pet> print() {
        List<Pet> pets = new ArrayList<>();
        if (head != null) {
            NodeDE temp = head;
            do {
                pets.add(temp.getDataDE());
                temp = temp.getNextDE();
            } while (temp != head);
        }
        return pets;
    }


        /**
         La implementación de los throw es con el objetivo de lograr capturar algún error y logre
         finalizar correctamente los métodos, sin ser tan abrupto.
         La idea, es mostrar un error o una situación inusual que ocurre durante la ejecución de un programa.
         y tome medidas adecuadas para manejar el error
         */
}

