package ru.ocupuc;

import java.util.HashMap;
import java.util.Map;


public class Field {
    private static final int SIZE_X = 5;
    private static final int SIZE_Y = 5;
    private Map<Point, Object> grid;
    private Pacman pacman;

    public Field() {
        this.grid = new HashMap<>();
        // Размещаем стены по периметру
        for(int i = 0; i < 5; i++) {
            for(int j = 0; j < 5; j++) {
                if(i == 0 || j == 0 || i == 4 || j == 4) {
                    grid.put(new Point(i, j), new Wall());
                }
            }
        }
        // Помещаем Pacman в свободную клетку
        pacman = new Pacman(new Point(2, 2));
        grid.put(pacman.getPosition(), pacman);
    }

    // Проверяем, может ли Pacman переместиться на заданную позицию
    public boolean canMoveTo(Point position) {
        return !grid.containsKey(position);
    }

    // Перемещаем Pacman на заданную позицию
    public void movePacman(Point newPosition) {
        if(canMoveTo(newPosition)) {
            grid.remove(pacman.getPosition());
            pacman.setPosition(newPosition);
            grid.put(pacman.getPosition(), pacman);
        } else {
            System.out.println("Cannot move to wall!");
        }
    }

    public void processCommand(String command) {
        Point currentPosition = pacman.getPosition();
        Point newPosition = null;

        switch (command) {
            case "UP":
                newPosition = new Point(currentPosition.x, currentPosition.y + 1);
                break;
            case "DOWN":
                newPosition = new Point(currentPosition.x, currentPosition.y - 1);
                break;
            case "LEFT":
                newPosition = new Point(currentPosition.x - 1, currentPosition.y);
                break;
            case "RIGHT":
                newPosition = new Point(currentPosition.x + 1, currentPosition.y);
                break;
        }

        if (newPosition != null) {
            movePacman(newPosition);
        } else {
            System.out.println("Invalid command: " + command);
        }
    }

    // Получить текущую сетку игры
    public Map<Point, Object> getGrid() {
        return this.grid;
    }

    public Point getPacmanPosition() {
        return pacman.getPosition();
    }

    public int getSizeX() {
        return SIZE_X;
    }

    public int getSizeY() {
        return SIZE_Y;
    }
}
