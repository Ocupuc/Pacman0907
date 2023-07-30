package ru.ocupuc;

import java.util.*;
import java.nio.file.*;
import java.io.IOException;
import com.badlogic.gdx.math.Vector2;
import ru.ocupuc.enums.FieldType;

public class PacmanField {

    private int width;
    private int height;
    private Map<Vector2, FieldType> field;



    public PacmanField(String filename) {
        field = new HashMap<>();

        try {
            List<String> lines = Files.readAllLines(Paths.get(filename));
            boolean sizeFound = false;
            boolean wallsFound = false;
            for (String line : lines) {
                if (line.trim().equals("// Size")) {
                    sizeFound = true;
                } else if (sizeFound) {
                    String[] dimensions = line.split(";");
                    if (dimensions.length == 2 && isValidFormat(line)) {
                        try {
                            width = Integer.parseInt(dimensions[0]);
                            height = Integer.parseInt(dimensions[1]);
                            sizeFound = false;  // size obtained, reset flag
                        } catch (NumberFormatException e) {
                            continue;
                        }
                    }
                } else if (line.trim().equals("// Walls")) {
                    wallsFound = true;
                } else if (wallsFound) {
                    String[] coordinates = line.split(";");
                    if (coordinates.length == 2 && isValidFormat(line)) {
                        try {
                            float x = Float.parseFloat(coordinates[0]);
                            float y = Float.parseFloat(coordinates[1]);
                            Vector2 position = new Vector2(x, y);
                            field.put(position, FieldType.WALL);
                        } catch (NumberFormatException e) {
                            continue;
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Fill the field with pills if there are no walls
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Vector2 position = new Vector2(x, y);
                field.putIfAbsent(position, FieldType.PILL);
            }
        }
    }

    public boolean isValidFormat(String line) {
        return line.matches("^\\d+;\\d+$");
    }

    public boolean isWall(Vector2 position) {
        return field.getOrDefault(position, FieldType.EMPTY) == FieldType.WALL;
    }

    public boolean isPill(Vector2 position) {
        return field.getOrDefault(position, FieldType.EMPTY) == FieldType.PILL;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Map<Vector2, FieldType> getField() {
        return field;
    }

    // ... Other code
}