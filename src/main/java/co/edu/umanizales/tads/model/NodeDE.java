package co.edu.umanizales.tads.model;

import lombok.Data;

@Data
public class NodeDE {

    private Pet dataDE;

    private NodeDE nextDE;

    private NodeDE previous;
    public NodeDE(Pet data) {
        this.dataDE = data;
    }
}
