package ru.ocupuc;

public class CommandProcessor {

    private Field field;

    public CommandProcessor(Field field) {
        this.field = field;
    }

    public void processCommand(String command) {
        Point currentPosition = field.getPacmanPosition();
        Point newPosition = null;

        switch (command) {
            case "W:true, S:false, A:false, D:false":
                newPosition = new Point(currentPosition.x, currentPosition.y + 1);
                break;
            case "W:false, S:true, A:false, D:false":
                newPosition = new Point(currentPosition.x, currentPosition.y - 1);
                break;
            case "W:false, S:false, A:true, D:false":
                newPosition = new Point(currentPosition.x - 1, currentPosition.y);
                break;
            case "W:false, S:false, A:false, D:true":
                newPosition = new Point(currentPosition.x + 1, currentPosition.y);
                break;
        }

        if (newPosition != null) {
            field.movePacman(newPosition);
        } else {
            System.out.println("Invalid command: " + command);
        }
    }
}
