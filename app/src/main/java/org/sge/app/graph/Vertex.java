package org.sge.app.graph;

import javafx.beans.property.*;

public class Vertex extends SelectableData{
    public Vertex() {

    }
    public Vertex(double x, double y){
        setX(x);
        setY(y);
    }

    private final DoubleProperty x = new SimpleDoubleProperty();

    public double getX() {
        return x.get();
    }
    public DoubleProperty xProperty() {
        return x;
    }
    public void setX(double x) {
        this.x.set(x);
    }

    private final DoubleProperty y = new SimpleDoubleProperty();

    public double getY() {
        return y.get();
    }
    public DoubleProperty yProperty() {
        return y;
    }
    public void setY(double y) {
        this.y.set(y);
    }
}
