package ru.ocupuc.enums;


import java.util.Arrays;

public enum CellType {
    EMPTY ((short)0),
    WALL((short)1),
    PILL((short) 2);

    private final short code;

    CellType(short code) {
        this.code = code;
    }

    public short getCode() {
        return code;
    }


}
