package co.edu.umanizales.tads.model.listade;

import co.edu.umanizales.tads.model.Kid;
import co.edu.umanizales.tads.model.Node;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ListDE {
    private Node head;
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
    public void add(Kid kid)
    {
        if(this.head!=null)
        {
            Node temp = this.head;
            while(temp.getNext()!=null)
            {
                temp = temp.getNext();
            }
            Node newBoy= new Node(kid);
            temp.setNext(newBoy);

        }
        else
        {
            this.head= new Node(kid);
        }
    }

}
