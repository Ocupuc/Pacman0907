package ru.ocupuc;

import ru.ocupuc.enums.MessageType;

import java.util.List;

public class ServerMessage {
    private MessageType type;
    private List<Object> data;

    public ServerMessage(MessageType type, List<Object> data) {
        this.type = type;
        this.data = data;
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public Object getData() {
        return data;
    }

    public void setData(List<Object> data) {
        this.data = data;
    }
}