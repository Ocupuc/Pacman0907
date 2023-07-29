package ru.ocupuc;

import java.util.HashSet;
import java.util.Set;

import static ru.ocupuc.ServerData.movementDTOs;
import static ru.ocupuc.ServerData.pacmans;

public class MovementManager {


    public void addMovementDTO(MovementDTO dto) {
    //    System.out.println(dto.toString());
        movementDTOs.remove(dto);
        movementDTOs.add(dto);
    //    movementDTOs.forEach(System.out::println);
    //    System.out.println("--------------------------------");
    }



    public void move() {
        for (MovementDTO movementDTO : movementDTOs) {
            String id = movementDTO.getId();
            Pacman pacman = pacmans.get(id);
            if (movementDTO.isdPressed()) {
                pacman.setX(pacman.getX() + 1);
            }
        }
    }
}
