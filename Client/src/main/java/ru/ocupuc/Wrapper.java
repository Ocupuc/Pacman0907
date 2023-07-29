package ru.ocupuc;


import com.fasterxml.jackson.annotation.JsonProperty;

public class Wrapper {
    private MessageType type;

    @JsonProperty("data")
    private Pacman pacman;

    // getters and setters
    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public Pacman getPacman() {
        return pacman;
    }

    public void setPacman(Pacman pacman) {
        this.pacman = pacman;
    }
}

