package org.hse.mainbuilder;

public class Unit {
    Long id;
    String text;
    Coordinates coordinates;

    public Unit(Long id, String text, Coordinates coordinates) {
        this.id = id;
        this.text = text;
        this.coordinates = coordinates;
    }
}

class Coordinates {
    int x1, y1; // upper-left corner
    int x2, y2; // down-right corner

    public Coordinates(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }
}