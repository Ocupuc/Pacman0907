package ru.ocupuc;

import ru.ocupuc.enums.CellType;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class PacmanField {

    private int width;
    private int height;
    private List<MapCell> field;

    public PacmanField(int width, int height, List<MapCell> field) {
        this.width = width;
        this.height = height;
        this.field = field;
    }

    public List<MapCell> getByLineNumber(int lineNumber) {
        return field.stream()
                .filter(c -> c.getY() == lineNumber)
                .sorted(Comparator.comparingInt(MapCell::getX))
                .collect(Collectors.toList());
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean isWall(int newX, int newY) {
        MapCell mapCell = field.get(field.indexOf(new MapCell(newX, newY, null)));
        return mapCell.getCellType() == CellType.WALL;
    }
}
