package ru.ocupuc;


import com.fasterxml.jackson.annotation.JsonProperty;
import ru.ocupuc.enums.MessageType;

import java.util.List;

public class Wrapper {
    private MessageType type;

    @JsonProperty("pacmans")
    private List<Pacman> pacman;

   private int length;

    @JsonProperty("data")
    private  short[] data;

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

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public short[] getData() {
        return data;
    }

    public void setData(short[] data) {
        this.data = data;
    }
}

