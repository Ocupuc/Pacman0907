package ru.ocupuc;

import com.badlogic.gdx.math.Vector2;
import ru.ocupuc.dto.MovementDTO;

import java.util.Arrays;

import static ru.ocupuc.ServerData.*;

public class MovementManager {


    public void addMovementDTO(MovementDTO dto) {
  if (dto.getId()==null) {
      System.out.println(dto.toString());
      return;
  }

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

            int newX = pacman.getX();
            int newY = pacman.getY();

            boolean aPressed = dto.isaPressed();
            boolean dPressed = dto.isdPressed();
            boolean wPressed = dto.iswPressed();
            boolean sPressed = dto.issPressed();

            // Check if both 'a' and 'd' keys are pressed and no other keys are pressed
            if (aPressed && dPressed && !wPressed && !sPressed) {
                if (pacman.isMovingRight()) {
                    if (!pacmanField.isWall(newX + 1, newY)) {
                        newX += 1;
                    } else {
                        pacman.setMovingRight(false);
                    }
                } else {
                    if (!pacmanField.isWall(newX - 1, newY)) {
                        newX -= 1;
                    } else {
                        pacman.setMovingRight(true);
                    }
                }
            }
            // Check if both 'w' and 's' keys are pressed and no other keys are pressed
            else if (wPressed && sPressed && !aPressed && !dPressed) {
                if (pacman.isMovingUp()) {
                    if (!pacmanField.isWall(newX, newY + 1)) {
                        newY += 1;
                    } else {
                        pacman.setMovingUp(false);
                    }
                } else {
                    if (!pacmanField.isWall(newX, newY - 1)) {
                        newY -= 1;
                    } else {
                        pacman.setMovingUp(true);
                    }
                }
            }
            // If 'w' and 'a' keys are pressed and no other keys are pressed
            else if (wPressed && aPressed && !dPressed && !sPressed) {
                if (!pacmanField.isWall(newX, newY + 1)) {
                    newY += 1;
                } else if (!pacmanField.isWall(newX - 1, newY)) {
                    newX -= 1;
                }
            }
            // Single key pressed
            else {
                if (aPressed) {
           //         if (!pacmanField.isWall(newX - 1, newY)) {
                        newX -= 1;
                    }
       //         }
                if (dPressed) {
//                    if (!pacmanField.isWall(newX + 1, newY)) {
                        newX += 1;
//                    }
                }
                if (wPressed) {
      //              if (!pacmanField.isWall(newX, newY + 1)) {
                        newY += 1;
         //           }
                }
                if (sPressed) {
   //                 if (!pacmanField.isWall(newX, newY - 1)) {
                        newY -= 1;
     //               }
                }
            }

            // Check if the new position is a wall
            if (!pacmanField.isWall(newX, newY)) {
                pacman.setX(newX);
                pacman.setY(newY);
            }
            if(pacmanField.isPill(newX, newY)) {
                pacmanField.setEmpty(newX,newY);
            }
        }
    }

}
