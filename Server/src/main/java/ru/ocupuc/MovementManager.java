package ru.ocupuc;

import ru.ocupuc.dto.MovementDTO;

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
        for (MovementDTO dto : movementDTOs) {
            String id = dto.getId();
            Pacman pacman = pacmans.get(id);
            if (pacman == null) {
                continue;
            }
            if (dto.isdPressed()) {
                pacman.setX(pacman.getX() + 1);
            }
            if (dto.iswPressed()) {
                pacman.setY(pacman.getY() + 1);
            }
            if (dto.isaPressed()) {
                pacman.setX(pacman.getX() - 1);
            }
            if (dto.issPressed()) {
                pacman.setY(pacman.getY() - 1);
            }
        }
    }
}
