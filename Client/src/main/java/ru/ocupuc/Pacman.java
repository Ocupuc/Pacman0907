package ru.ocupuc;

public class Pacman {
    private String id;
    private int x;
    private int y;
    private int score;
    private int animationCount;
    public  void animate(){
        animationCount++;
        if (animationCount>2){
            animationCount = 0;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pacman pacman)) return false;

        return id.equals(pacman.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
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

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getAnimationCount() {
        return animationCount;
    }
}