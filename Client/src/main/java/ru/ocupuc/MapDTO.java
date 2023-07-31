package ru.ocupuc;

public class MapDTO {
    private final int length;
    private final short[] data;

    public MapDTO(int length, short[] data) {
        this.length = length;
        this.data = data;
    }

    public int getLength() {
        return length;
    }

    public short[] getData() {
        return data;
    }
}
