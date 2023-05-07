package co.edu.umanizales.tads.model;

import co.edu.umanizales.tads.controller.dto.ReportKidsDTO;

import co.edu.umanizales.tads.exception.ListSEException;
import lombok.Data;

@Data
public class ListSE {
    private Node head;
    private int size;

    /**
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
    public void add(Kid kid) throws ListSEException {
        if(head != null){
            Node temp = head;
            while(temp.getNext() !=null)
            {
                if(temp.getData().getIdentification().equals(kid.getIdentification())){
                    throw new ListSEException("Ya existe un niño");
                }
                temp = temp.getNext();

            }
            if(temp.getData().getIdentification().equals(kid.getIdentification())){
                throw new ListSEException("Ya existe un niño");
            }
            /// Parado en el último
            Node newNode = new Node(kid);
            temp.setNext(newNode);
        }
        else {
            head = new Node(kid);
        }
        size ++;
    }

    /**
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
    public void invert() throws ListSEException{
        if (this.head != null) {
            ListSE listCP = new ListSE();
            Node temp = this.head;
            while (temp != null) {
                listCP.addToStart(temp.getData());
                temp = temp.getNext();
            }
            this.head = listCP.getHead();
        }
        else{
            throw new ListSEException("La lista está vacía");
        }
    }

    /**
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
    public void orderBoysToStart() throws ListSEException {
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
        else{
            throw new ListSEException("La lista está vacía");
        }
    }

    /**
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
    public void intercalateBoysGirls() throws ListSEException {
        ListSE listBoy = new ListSE();
        ListSE listGirl = new ListSE();
        Node temp = this.head;

        if (temp == null) {
            throw new ListSEException("La lista está vacía");
        }

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

    /**
    -Ejercicio número 4: Dada una edad eliminar a los niños de la edad dada
    Explicación:
    Si hay datos
        si la identificación del niño almacenado en el primer nodo es igual a la edad que se pasó al método delete
        Si es así, que actualice el primer nodo para que apunte al siguiente nodo en la lista
        entonces
            llamo a un ayudante y que se posicione en la cabeza
            mientras hayan datos o no sea null
                si el siguiente nodo del ayudante no es nulo y si su edad es igual a la edad que se
                pasó al método delete. Si es así, el bucle se detiene y el nodo temp apunta al nodo que se debe eliminar.
                que el ayudante tome el siguiente nodo
        si hay datos nuevamente o no sea null
            actualiza el siguiente nodo donde está el ayudante para que apunte al nodo que está después del nodo que se debe
            eliminar, lo que elimina el nodo que contiene la edad buscada.
     */
    public void deleteByAge(byte age) throws ListSEException {
        if(this.head!=null)
        {
            if(this.head.getData().getAge() == age) {
                this.head = this.head.getNext();
            }
            else {
                Node temp = this.head;
                while(temp!=null) {
                    if(temp.getNext() != null && temp.getNext().getData().getAge() == age) {
                        break;
                    }
                    temp= temp.getNext();
                }
                if(temp!= null) {
                    temp.setNext(temp.getNext().getNext());
                }
                else {
                    throw  new ListSEException("La edad "+ age + " no existe en la lista");
                }
            }
        }
        else {
            throw  new ListSEException("No hay datos en la lista");
        }
    }

    /**
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
    public float averageAge() throws ListSEException {
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
            throw new ListSEException("La lista está vacía");
        }
    }

    /**
    Ejercicio 6: Generar un reporte que me diga cuantos niños hay de cada ciudad.
     */
    public int getCountKidsByLocationCode(String code) throws ListSEException {
        int count = 0;
        if (this.head != null) {
            Node temp = this.head;
            while (temp != null) {
                if (temp.getData().getLocation().getCode().equals(code)) {
                    count++;
                }
                temp = temp.getNext();
            }
            return count;
        } else {
            throw new ListSEException("La lista está vacía");
        }
    }

    public int getCountKidsByDepartmentCode(String code) throws ListSEException{
        int count = 0;
        if (this.head != null) {
            Node temp = this.head;
            while (temp != null) {
                if (temp.getData().getLocation().getCode().equals(code)) {
                    count++;
                }
                temp = temp.getNext();
            }
            return count;
        }
        else{
            throw new ListSEException("La lista está vacía");
        }
    }
    /*
    No es necesario añadir una excepción, ya que este método simplemente recorre la
    lista enlazada y actualiza un informe ReportKidsDTO con información de género y ubicación
    de los niños cuya edad es mayor que un valor dado. Si la lista está vacía, no se realiza
    ninguna operación y no hay nada que manejar excepcionalmente.
     */
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

    /**
    -Ejercicio 7: Método que me permita decirle a un niño determinado que adelante un número de posiciones dadas
     Explicación:
     hay datos
        si las posiciones es menor al tamaño de la lista
            en este punto, el método intentará buscar un nodo en la lista que tenga una propiedad
            "identification" igual a la cadena de texto "identification" pasada como parámetro.
            si encuentra dicho nodo, no hace nada y simplemente termina.
                inicio un contador en 1
                llamo a un ayudante y que se posicione en la cabeza
                se itera sobre la lista hasta que se encuentra el nodo con la identificación especificada,
                que el ayudante tome el siguiente nodo
                que incremente el contador en cada iteración.
                si el siguiente nodo es null, significa que la identificación especificada no se encontró en la lista y se sale del método.
                si se encuentra el nodo con la identificación, se crea otro ayudante
                este nuevo ayudante contiene la misma información que el nodo que se va a mover
                si la posición especificada es mayor igual al número de nodos que se encontraron
                antes de encontrar el nodo con la identificación especificada, entonces el segundo
                ayudante se agrega al inicio de la lista
                de lo contrario que el nodo temporal se agregue a la lista de la posición específica
                por la cantidad de nodos que se encontró antes de encontrar el nodo con la identificación
                especificada
                que se añada el nodo a la lista en la posición calculada.
     */
    public void passByPosition(String identification, int positions) throws ListSEException{
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
                    Node temp2 =new Node(temp.getNext().getData());
                    temp.setNext(temp.getNext().getNext());
                    if(positions >= count+1){
                        addToStart(temp2.getData());
                    }
                    else{
                        addByPosition((count+1) - positions, temp2.getData());
                    }
                }
            }
            else{
                throw new ListSEException("La posición ingresada es mayor o igual al tamaño de la lista.");
            }
        }
        else{
            throw new ListSEException("La lista se encuentra vacía.");
        }
    }

    /**
    -Ejercicio 8: Método que me permita decirle a un niño determinado que pierda un numero de posiciones dadas
     Explicación:
        hay datos
            si las posiciones es menor al tamaño de la lista
            se verifica si el nodo con el identificador especificado es la cabeza de la lista.
            si lo es, se crea un nuevo nodo a partir del segundo nodo en la lista y se inserta
            después de la posición especificada. Luego, se establece el nuevo nodo como la nueva cabeza de la lista.
            inicializo un contador en 1
            llamo a un ayudante y que se posicione en la cabeza
            si el nodo con el identificador especificado no es la cabeza de la lista, se itera
            sobre la lista para encontrar el nodo con el identificador especificado
            en cada iteración, se actualiza el con el siguiente nodo de la lista
            si se encuentra el nodo con el identificador especificado, se llama un nuevo ayudante a
            partir del nodo que se encuentra después del nodo con el identificador especificado y
            se inserta después de la posición especificada
     */
    public void afterwardsPositions(String identification, int positions) throws ListSEException {
        if (head!=null){
            if(positions<size){
                if(head.getData().getIdentification()==identification){
                    Node node = new Node(head.getNext().getData());
                    addByPosition(positions+1, node.getData());
                    head = head.getNext();
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
                    addByPosition(count+1+positions, temp2.getData());
                }
            }
            else{
                throw new ListSEException("La posición proporcionada excede el tamaño de la lista");
            }
        }
        else{
            throw new ListSEException("La lista se encuentra vacía.");
        }
    }

    /**
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
    public int rangeByAge(int min, int max) throws ListSEException{
        if (head == null) {
            throw new ListSEException("La lista se encuentra vacía.");
        }
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

    /**
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
    public void kidsByLetter(char initial) throws ListSEException {

        if (this.head == null) {
            throw new ListSEException("La lista está vacía");
        }

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

    public void addByPosition(int position, Kid kid) throws ListSEException {
        if (position < 1 || position > size + 1) {
            throw new ListSEException("La posición proporcionada no es válida.");
        }
        if (head != null) {
            if (position == 1) {
                addToStart(kid);
            } else {
                Node temp = head;
                int cont = 1;
                while (temp != null && cont < position - 1)
                {
                    temp = temp.getNext();
                    cont++;
                }
                if (temp != null) {
                    Node newNode = new Node(kid);
                    newNode.setNext(temp.getNext());
                    temp.setNext(newNode);
                } else {
                    add(kid);
                }
            }
        } else {
            add(kid);
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
    public void changesExtremes() throws ListSEException{
        if (this.head != null && this.head.getNext() != null) {
            Node temp = this.head;
            while (temp.getNext() != null) {
                temp = temp.getNext();
            }

            Kid copy = this.head.getData();
            this.head.setData(temp.getData());
            temp.setData(copy);
        }
        else
        {
            throw  new ListSEException("No es posible cambiar de extremos.");
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
    public void addToStart(Kid kid) throws ListSEException {
        if (head != null) {
            Node newNode = new Node(kid);
            newNode.setNext(head);
            head = newNode;
        } else if(this.head == null) {
            head = new Node(kid);
        }
        else{
            throw new ListSEException("La lista está vacía");
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

}

