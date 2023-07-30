package ru.ocupuc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static ru.ocupuc.enums.CellType.PILL;
import static ru.ocupuc.enums.CellType.WALL;

public class FieldParser {


    public static PacmanField parse(String fileName) {

        try {
            int width =0;
            int height = 0;

            List<MapCell> field = new ArrayList<>();

            List<String> lines = Files.readAllLines(Paths.get(fileName));
            boolean sizeFound = false;
            boolean wallsFound = false;
            for (String line : lines) {
                if (line.trim().equals("// Size")) {
                    sizeFound = true;
                } else if (sizeFound) {
                    String[] dimensions = line.split(";");
                    if (dimensions.length == 2 && isValidFormat(line)) {

                        width = Integer.parseInt(dimensions[0]);
                        height = Integer.parseInt(dimensions[1]);
                        sizeFound = false;  // size obtained, reset flag

                        for (int x = 0; x < width; x++) {        //сразу заполняе пилюлями как только полчили размер
                            for (int y = 0; y < height; y++) {
                                field.add(new MapCell(x, y, PILL));
                            }
                        }
                    }

                } else if (line.trim().equals("// Walls")) {
                    wallsFound = true;
                } else if (wallsFound) {
                    String[] coordinates = line.split(";");
                    if (coordinates.length == 2 && isValidFormat(line)) {

                        int x = Integer.parseInt(coordinates[0]);
                        int y = Integer.parseInt(coordinates[1]);
                        MapCell mapCell = new MapCell(x, y, WALL);
                        field.remove(mapCell);
                        field.add(mapCell);

                    }
                }

            }
            return new PacmanField(width, height,field);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean isValidFormat(String line) {
        return line.matches("^\\d+;\\d+$");
    }
}
