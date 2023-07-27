package ru.ocupuc;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import io.netty.channel.Channel;
import lombok.Data;


public class Pacman implements Json.Serializable {
    private String id;
    private Channel channel;

    private int x;
    private int y;
    private int speed = 1;

    private boolean leftPressed;
    private boolean rightPressed;
    private boolean upPressed;
    private boolean downPressed;

    public Pacman() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }



    public void act() {
        if (isUpPressed() && !isDownPressed()) {
            y += speed;
        } else if (isDownPressed() && !isUpPressed()) {
            y -= speed;
        }

        if (isLeftPressed() && !isRightPressed()) {
            x -= speed;
        } else if (isRightPressed() && !isLeftPressed()) {
            x += speed;
        }
    }

    @Override
    public void write(Json json) {
        json.writeValue("x", x);
        json.writeValue("y", y);
        json.writeValue("id", id);
    }

    @Override
    public void read(Json json, JsonValue jsonData) {
        this.x = jsonData.getInt("x");
        this.y = jsonData.getInt("y");
        this.id = jsonData.getString("id");
        // add more fields as necessary
    }

    public boolean isUpPressed() {
        return upPressed;
    }

    public void setUpPressed(boolean upPressed) {
        this.upPressed = upPressed;
    }

    public boolean isDownPressed() {
        return downPressed;
    }

    public void setDownPressed(boolean downPressed) {
        this.downPressed = downPressed;
    }

    public boolean isLeftPressed() {
        return leftPressed;
    }

    public void setLeftPressed(boolean leftPressed) {
        this.leftPressed = leftPressed;
    }

    public boolean isRightPressed() {
        return rightPressed;
    }

    public void setRightPressed(boolean rightPressed) {
        this.rightPressed = rightPressed;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }
}