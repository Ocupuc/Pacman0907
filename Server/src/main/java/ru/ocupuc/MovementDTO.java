package ru.ocupuc;

public class MovementDTO {

    private String id;

    private boolean wPressed;
    private boolean sPressed;
    private boolean aPressed;
    private boolean dPressed;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MovementDTO that)) return false;

        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "MovementDTO{" +
                "id='" + id + '\'' +
                ", wPressed=" + wPressed +
                ", sPressed=" + sPressed +
                ", aPressed=" + aPressed +
                ", dPressed=" + dPressed +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean iswPressed() {
        return wPressed;
    }

    public void setwPressed(boolean wPressed) {
        this.wPressed = wPressed;
    }

    public boolean issPressed() {
        return sPressed;
    }

    public void setsPressed(boolean sPressed) {
        this.sPressed = sPressed;
    }

    public boolean isaPressed() {
        return aPressed;
    }

    public void setaPressed(boolean aPressed) {
        this.aPressed = aPressed;
    }

    public boolean isdPressed() {
        return dPressed;
    }

    public void setdPressed(boolean dPressed) {
        this.dPressed = dPressed;
    }
}
