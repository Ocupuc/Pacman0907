package ru.ocupuc;

import ru.ocupuc.dto.MapDTO;
import ru.ocupuc.enums.CellType;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MapToDTOConverter {
    public static final MapDTO convert(PacmanField field) {

        List<Short> shorts = new ArrayList<>();
        for (int i = 0; i < field.getHeight(); i++) {
            List<Short> lineList =  field.getByLineNumber(i)
                    .stream()
                    .map(MapCell::getCellType)
                    .map(CellType::getCode)
                    .collect(Collectors.toList());

            shorts.addAll(lineList);
        }
        int[] ints = shorts
                .stream()
                .mapToInt(i -> i)
                .toArray();

        short inShort[] = new short[ints.length];

        for(int i = 0; i < ints.length; i++)
        {
            inShort[i] = (short)ints[i];
        }

        return new MapDTO(field.getWidth(), inShort);
    }
}