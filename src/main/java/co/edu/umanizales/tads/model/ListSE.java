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
    creo un ayudante y que se posicione en la cabeza
     inicializo un contador en cero al igual que la edad (irán aumentando)
     mientras hayan datos o no sea null
        que aumente el contador
        ahora que se sume la edad de los niños con el promedio a calcular
        que el ayudante tome el siguiente nodo (o se pase al final)
        se declara una variable que contendría la suma de las edades
        si el contador fue mayor a 0
            que se divida la edad por lo obtenido en el contador (la cantidad de niños)
        que me retorne el promedio ya calculado.
     */
    public float averageAge() throws ListSEException {
        if (head == null) {
            throw new ListSEException("La lista está vacía. No se puede calcular el promedio de edades.");
        }
        Node temp = head;
        int count = 0;
        int ages = 0;
        while(temp != null) {
            count++;
            ages = ages + temp.getData().getAge();
            temp = temp.getNext();
        }
        float average = 0;
        if (count > 0) {
            average = ages / (float)count;
        }
        return average;
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
    public void passPosition(String identification, int position, ListSE listSE) throws ListSEException {
        Node temp = this.head;
        int count = 1;
        while (temp != null && !temp.getData().getIdentification().equals(identification)) {
            temp = temp.getNext();
            count++;
        }
        if (temp != null) {
            int difference = count - position;
            Kid kid = temp.getData();
            listSE.deleteKidByIdentification(temp.getData().getIdentification());
            if (difference > 0) {
                listSE.addInPosition(difference, kid);
            }
            else {
                addToStart(kid);
            }
        }
        else{
            throw new ListSEException("No se encontró ningún niño con la identificación especificada.");
        }
    }

    /**
    -Ejercicio 8: Método que me permita decirle a un niño determinado que pierda un numero de posiciones dadas
     Explicación:
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
    public void backPositions (String identification,int position) throws ListSEException {
        Node temp = this.head;
        int count = 1;
        while (temp != null && !temp.getData().getIdentification().equals(identification)) {
            temp = temp.getNext();
            count++;
        }
        int sum = position + count;
        if (temp != null) {
            Kid kid = temp.getData();
            deleteKidByIdentification(temp.getData().getIdentification());
            addInPosition(sum, kid);
        }
        else{
            throw new ListSEException("No se encontró ningún niño con la identificación especificada.");
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

    /**
    -Método añadir por posición
     Explicación:
     Si hay datos en la cabeza o no está vacía la lista
        si la posición es la primera (1)
        si es la primera, que se llame el método añadir al inicio y se inserte el niño
     si no
        llamo a un ayudante y que se posicione en la cabeza
        inicializo un contador en 1
        mientras hayan datos y que el contador es menor que el valor de la posición -1
        que se ejecute hasta llegar a la posición anterior deseada
        que el ayudante tome el siguiente nodo
        que el contador se incremente
     si siguen habiendo datos en el siguiente nodo donde está el ayudante
        que se cree un nuevo nodo y metemos al niño ahí
        que se establezca el enlace del nuevo nodo al siguiente nodo después de la posición deseada (allí se parará el ayudante)
        que el ayudante se establezca en enlace del nodo anterior a la posición deseada al nuevo nodo
     */
    public void addInPosition(int position, Kid kid) throws ListSEException {
        if (size<position) throw  new ListSEException("Se ingresó una posición mas grande que la lista.");

        if (head!=null) {
            if (position == 1) {
                addToStart(kid);
            } else {
                Node temp = head;
                int cont =1;
                while (temp != null && cont<position-1)
                {
                    temp = temp.getNext();
                    cont++;
                }
                if (temp != null) {
                    Node newNode = new Node(kid);
                    newNode.setNext(temp.getNext());
                    temp.setNext(newNode);

                }
            }
        }
    }

    /**
    -Método de intercambiar extremos
     Explicación:
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

    /**
    -Método de eliminar niños por identificación
     Explicación
        si en la cabeza hay datos o no esté vacía la lista
          si el elemento a eliminar se encuentra en la cabeza de la lista (en este caso la identificación)
            que elimine la cabeza moviendo el enlace al siguiente nodo
        si no
            llamo a un ayudante y que se posicione en la cabeza
            mientras en el enlace siguiente exista algo
                verificar si el siguiente nodo contiene el elemento a eliminar
                si se encontró, que se elimine el siguiente nodo con su identificación
            que el ayudante tome el siguiente nodo (o se pase al final)
     */
    public void deleteKidByIdentification(String identification) {
        if (this.head != null) {
            if (this.head.getData().getIdentification().equals(identification)) {
                this.head = this.head.getNext();
            } else {
                Node temp = this.head;
                while (temp.getNext() != null) {
                    if (temp.getNext().getData().getIdentification().equals(identification)) {
                        temp.setNext(temp.getNext().getNext());
                        return;
                    }
                    temp = temp.getNext();
                }
            }
        }
    }


    /**
    La implementación de los throw es con el objetivo de lograr capturar algún error y logre
    finalizar correctamente los métodos, sin ser tan abrupto.
     La idea, es mostrar  un error o una situación inusual que ocurre durante la ejecución de un programa.
     y tome medidas adecuadas para manejar el error
     */

}

