package co.edu.umanizales.tads.model.listade;

import co.edu.umanizales.tads.model.Kid;
import lombok.Data;

@Data
public class Node {

    private Kid data;

    private Node next;

    private Node previous;

    public Node(Kid data) {
        this.data = data;
    }
}
