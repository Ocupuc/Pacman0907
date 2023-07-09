package ru.ocupuc;

public class Pacman {
    private Point position;

    public Pacman(Point initialPosition) {
        this.position = initialPosition;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "Pacman at " + position.toString();
    }
}

