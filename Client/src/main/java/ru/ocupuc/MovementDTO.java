package ru.ocupuc;

public class MovementDTO {

    private String id;

    private boolean wPressed;
    private boolean sPressed;
    private boolean aPressed;
    private boolean dPressed;

    public MovementDTO() {
    }

    public MovementDTO(String id, boolean wPressed, boolean sPressed, boolean aPressed, boolean dPressed) {
        this.id = id;
        this.wPressed = wPressed;
        this.sPressed = sPressed;
        this.aPressed = aPressed;
        this.dPressed = dPressed;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setwPressed(boolean wPressed) {
        this.wPressed = wPressed;
    }

    public void setsPressed(boolean sPressed) {
        this.sPressed = sPressed;
    }

    public void setaPressed(boolean aPressed) {
        this.aPressed = aPressed;
    }

    public void setdPressed(boolean dPressed) {
        this.dPressed = dPressed;
    }

    public String getId() {
        return id;
    }

    public boolean iswPressed() {
        return wPressed;
    }

    public boolean issPressed() {
        return sPressed;
    }

    public boolean isaPressed() {
        return aPressed;
    }

    public boolean isdPressed() {
        return dPressed;
    }
}
