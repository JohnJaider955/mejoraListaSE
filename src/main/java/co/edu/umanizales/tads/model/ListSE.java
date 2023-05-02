package co.edu.umanizales.tads.model;

import co.edu.umanizales.tads.controller.dto.ReportKidsDTO;
import lombok.Data;

@Data
public class ListSE {
    private Node head;
    private int size;
    //private int size;

    /*
    Algoritmo de adicionar al final
    Entrada
        un niño
    si hay datos
    si
        llamo a un ayudante y le digo que se posicione en la cabeza
        mientras en el brazo exista algo
            pasese al siguiente
        va estar ubicado en el ùltimo

        meto al niño en un costal (nuevo costal)
        y le digo al ultimo que tome el nuevo costal
    no
        metemos el niño en el costal y ese costal es la cabeza
     */
    public void add(Kid kid) {
        if (head != null) {
            Node temp = head;
            while (temp.getNext() != null) {
                temp = temp.getNext();
            }
            /// Parado en el último
            Node newNode = new Node(kid);
            temp.setNext(newNode);
        } else {
            head = new Node(kid);
        }

        size++;
    }

    /*
    -Ejercicio número 1: Invertir lista.
    Explicación:
    Si hay datos
    si
        creo una lista copia temporal
        llamo a un ayudante y le digo que se posicione en la cabeza
        mientras hayan datos o no sea null
            posiciono la lista copia al inicio junto al ayudante con los datos
            que el ayudante tome el siguiente nodo (o se pase al final)

            que se posicione en la cabeza la copia.
     */
    public void invert() {
        if (this.head != null) {
            ListSE listCP = new ListSE();
            Node temp = this.head;
            while (temp != null) {
                listCP.addToStart(temp.getData());
                temp = temp.getNext();
            }
            this.head = listCP.getHead();
        }
    }

    /*
    -Ejercicio número 2: Niños al inicio y niñas al final.
    Explicación:
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
    public void orderBoysToStart() {
        if (this.head != null) {
            ListSE listCp = new ListSE();
            Node temp = this.head;
            while (temp != null) {
                if (temp.getData().getGender() == 'M') {
                    listCp.addToStart(temp.getData());
                } else {
                    listCp.add(temp.getData());
                }

                temp = temp.getNext();
            }
            this.head = listCp.getHead();
        }
    }

    /*
    -Ejercicio número 3: Intercalar niño, niña, niño, niña
    Explicación:
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
    public void intercalateBoysGirls(){
        ListSE listBoy = new ListSE();
        ListSE listGirl = new ListSE();
        Node temp = this.head;
        while (temp != null){
            if(temp.getData().getGender()=='M'){
                listBoy.add(temp.getData());
            }
            if(temp.getData().getGender()=='F'){
                listGirl.add(temp.getData());
            }
            temp = temp.getNext();
        }
        ListSE newListBoysGirls = new ListSE();
        Node boyNode = listBoy.getHead();
        Node girlNode = listGirl.getHead();
        while (boyNode != null || girlNode != null){
            if (boyNode != null){
                newListBoysGirls.add(boyNode.getData());
                boyNode = boyNode.getNext();
            }
            if (girlNode != null){
                newListBoysGirls.add(girlNode.getData());
                girlNode = girlNode.getNext();
            }
        }
        this.head = newListBoysGirls.getHead();
    }

    /*
    -Ejercicio número 4: Dada una edad eliminar a los niños de la edad dada
    Explicación:
    Si hay datos
    si
        creo una lista copia
        llamo a un ayudante y le digo que se posicione en la cabeza
        mientras hayan datos
            si el valor de edad almacenado en el nodo es menor o igual que la edad del primer
            nodo de la lista original
            posiciono la lista copia al inicio junto al ayudante con los datos
            que el ayudante tome el siguiente nodo (o se pase al final)

        que se posicione en la cabeza la copia.
     */
    public void deleteByAge(byte age) {
        if (this.head != null) {
            ListSE listCP = new ListSE();
            Node temp = this.head;
            while (temp != null) {
                if (temp.getData().getAge() <= age) {
                    listCP.addToStart(temp.getData());
                }
                temp = temp.getNext();
            }
            this.head = listCP.getHead();
        }
    }

    /*
    -Ejercicio 5: Obtener el promedio de edad de los niños de la lista
    Explicación:
    si hay datos
        llamo a un ayudante y que se posicione en la cabeza
        se inicializa las variables contador y age en 0 para añadir las cantidades (num de nodos y suma de edades)
        mientras en el nodo exista algo
        que se incremente el contador y se agrega la edad del nodo actual
        que el ayudante me actualice pasándose al siguiente
        que se calcule el promedio de edad dividiendo la edad entre el contador devolviéndome un valor tipo float
     */
    public float averageAge(){
        if (head != null){
            Node temp = head;
            int count = 0;
            int ages = 0;
            while(temp.getNext() != null) {
                count++;
                ages = ages + temp.getData().getAge();
                temp = temp.getNext();
            }
            return (float) ages/count;
        }
        else{
            return (float) 0;
        }
    }

    /*
    -Ejercicio 9: Obtener un informe de niños por rango de edades
    Explicación:
    llamo a un ayudante y que se posicione en la cabeza
    inicializo un contador en cero
        mientras hayan datos
            que verifique si la edad del nodo actual está dentro del rango especificado
            si dentro encuentra una edad dentro del rango, que se añada al contador
            que el ayudante me actualice pasándose al siguiente
    que me retorne el contador devolviéndome su valor final
     */
    public int rangeByAge(int min, int max){
        Node temp = head;
        int count = 0;
            while(temp != null){
                if(temp.getData().getAge() >= min && temp.getData().getAge() <= max){
                    count++;
                }
                temp = temp.getNext();
            }
        return count;
    }


    /*
    -Ejercicio 10: Implementar un método que me permita enviar al final de la lista a los niños que
    su nombre inicie con una letra dada
    Explicación:
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
    public void boysByLetter(char initial){

        ListSE listCP = new ListSE();
        Node temp = this.head;

        while (temp != null){
            if (temp.getData().getName().charAt(0) != Character.toUpperCase(initial)){
                listCP.add(temp.getData());
            }
            temp = temp.getNext();
        }
        temp = this.head;
        while (temp != null){
            if (temp.getData().getName().charAt(0) == Character.toUpperCase(initial)){
                listCP.add(temp.getData());
            }
            temp = temp.getNext();
        }
        this.head = listCP.getHead();
    }

    /*
    -Ejercicio 7: Método que me permita decirle a un niño determinado que adelante un número de posiciones dadas
     */
    public void passByPosition(String identification, int positions){
        if (head != null){
            if(positions<size){
                if(head.getData().getIdentification()==identification){

                }
                else{
                    int count = 1;
                    Node temp = head;
                    while(temp.getNext().getData().getIdentification()!=identification){
                        temp = temp.getNext();
                        count++;
                        if(temp.getNext()!=null){
                            return;
                        }
                    }
                    Node temp2=new Node(temp.getNext().getData());
                    temp.setNext(temp.getNext().getNext());
                    if(positions >= count+1){
                        addToStart(temp2.getData());
                    }
                }
            }
        }
    }

    /*
    si hay datos en la cabeza y si en el siguiente nodo también hay datos
    si
        llamo a un ayudante y le digo que se posicione en la cabeza
        que el ayudante recorra la lista hasta que llegue al último (mientras hayan datos)
            que el ayudante tome el siguiente nodo (o se pase al final)
            se guarda la cabeza en una variable copia de niños
            que se reemplace la cabeza con el objeto de datos del último nodo (el primero niño ahora es último)
            que se reemplace el último nodo (el último ahora es el primero)
     */
    public void changesExtremes() {
        if (this.head != null && this.head.getNext() != null) {
            Node temp = this.head;
            while (temp.getNext() != null) {
                temp = temp.getNext();
            }

            Kid copy = this.head.getData();
            this.head.setData(temp.getData());
            temp.setData(copy);
        }
    }

    /* Adicionar al inicio
    si hay datos
    si
        meto al niño en un costal (nuevocostal)
        le digo a nuevo costal que tome con su brazo a la cabeza
        cabeza es igual a nuevo costal
    no
        meto el niño en un costal y lo asigno a la cabeza
     */
    public void addToStart(Kid kid) {
        if (head != null) {
            Node newNode = new Node(kid);
            newNode.setNext(head);
            head = newNode;
        } else {
            head = new Node(kid);
        }
        size++;
    }

    /*
    Si la lista tiene aunque sea un elemento para agregar, se crea un nuevo nodo que contiene un niño
    si no contiene datos la cabeza, que se añada a un nuevo nodo
    llamo a un ayudante y le digo que se posicione en la cabeza
    mientras en el brazo exista algo que se pase
        metemos el niño en el costal y ese costal es la cabeza
     */
    public void addToFinal (Kid kid) {
        Node newNode = new Node(kid);
        if (head == null) {
            head = newNode;
            return;
        }
        Node temp = head;
        while (temp.getNext() != null) {
            temp = temp.getNext();
        }
        temp.setNext(newNode);
    }

    public int getCountKidsByLocationCode(String code) {
        int count = 0;
        if (this.head != null) {
            Node temp = this.head;
            while (temp != null) {
                if (temp.getData().getLocation().getCode().equals(code)) {
                    count++;
                }
                temp = temp.getNext();
            }
        }
        return count;
    }

    public int getCountKidsByDepartmentCode(String code) {
        int count = 0;
        if (this.head != null) {
            Node temp = this.head;
            while (temp != null) {
                if (temp.getData().getLocation().getCode().equals(code)) {
                    count++;
                }
                temp = temp.getNext();
            }
        }
        return count;
    }

    public void getReportKidsByLocationGendersByAge(byte age, ReportKidsDTO report){
        if(head != null){
            Node temp = this.head;
            while(temp!=null){
                if(temp.getData().getAge() > age){
                    report.updateQuantity(temp.getData().getLocation().getName(),
                            temp.getData().getGender());
                }
                temp = temp.getNext();
            }
        }
    }

}
