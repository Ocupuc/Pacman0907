package ru.ocupuc;

import ru.ocupuc.enums.CellType;

public class MapCell {
    private final int x, y;
    private  CellType cellType;

    public MapCell(int x, int y, CellType cellType) {
        this.x = x;
        this.y = y;
        this.cellType = cellType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MapCell mapCell)) return false;

        if (x != mapCell.x) return false;
        return y == mapCell.y;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public CellType getCellType() {
        return cellType;
    }

    public void setCellType(CellType cellType) {
        this.cellType = cellType;
    }
}
