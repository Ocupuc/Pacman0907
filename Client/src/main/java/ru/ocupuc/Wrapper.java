package ru.ocupuc;


import com.fasterxml.jackson.annotation.JsonProperty;
import ru.ocupuc.enums.MessageType;

import java.util.List;

public class Wrapper {
    private MessageType type;

    @JsonProperty("data")
    private List<Pacman> pacman;

    // getters and setters
    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public List<Pacman> getPacman() {
        return pacman;
    }

    public void setPacman(List<Pacman> pacman) {
        this.pacman = pacman;
    }
}

