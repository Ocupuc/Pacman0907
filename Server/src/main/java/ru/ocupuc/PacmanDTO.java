package ru.ocupuc;

public class PacmanDTO {
    private final String type = "pacman";
    private String id;
    private int x;
    private int y;

    public PacmanDTO(Pacman pacman) {
        this.id = pacman.getId();
        this.x = pacman.getX();
        this.y = pacman.getY();
    }

    public String getType() {
        return type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}