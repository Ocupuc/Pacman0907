package ru.ocupuc;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.ocupuc.dto.MapDTO;
import ru.ocupuc.enums.MessageType;

import java.util.List;

public class ServerMessage {
    private MessageType type;
    private List<Object> pacmans;
    private int length;


    private  short[] data;

    public ServerMessage(MessageType type, List<Object> pacmans, int length, short[] data) {
        this.type = type;
        this.pacmans = pacmans;
        this.length = length;
        this.data = data;
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public Object getPacmans() {
        return pacmans;
    }

    public void setPacmans(List<Object> pacmans) {
        this.pacmans = pacmans;
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

