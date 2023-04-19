package co.edu.umanizales.tads.model;

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
    Posiciono el ayudante en la cabeza
    hago un recorrido hasta diez nodos
    si hay datos de los nodos
        que el ayudante pase hasta el siguiente hasta que llegue a la cabeza
        que ayudante se quede en la cabeza.
     */
    public void passByPosition(Kid kid) {
        Node temp = head;

        for (int i = 0; i < 10; i++) {
            if (temp.getNext() != null) {
                temp = temp.getNext();
            }
            head.setNext(temp);
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
}
