package ru.ocupuc;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class KeyboardTracker implements InputProcessor {

    private Network network;

    private boolean wPressed = false;
    private boolean sPressed = false;
    private boolean aPressed = false;
    private boolean dPressed = false;

    public KeyboardTracker(Network network) {
        this.network = network;
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Keys.W:
                wPressed = true;
                break;
            case Keys.S:
                sPressed = true;
                break;
            case Keys.A:
                aPressed = true;
                break;
            case Keys.D:
                dPressed = true;
                break;
            default:
                return false;
        }
        sendStateToServer();
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Keys.W:
                wPressed = false;
                break;
            case Keys.S:
                sPressed = false;
                break;
            case Keys.A:
                aPressed = false;
                break;
            case Keys.D:
                dPressed = false;
                break;
            default:
                return false;
        }
        sendStateToServer();
        return true;
    }

    public void sendStateToServer() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode rootNode = mapper.createObjectNode();
        rootNode.put("type", "state");
        rootNode.put("leftPressed", aPressed);
        rootNode.put("rightPressed", dPressed);
        rootNode.put("upPressed", wPressed);
        rootNode.put("downPressed", sPressed);
        String state = "";
        try {
            state = mapper.writeValueAsString(rootNode);
            network.sendMessage(state);
            System.out.println(state);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    // Остальные методы интерфейса InputProcessor оставлены пустыми, так как они не используются
    @Override public boolean keyTyped(char character) { return false; }
    @Override public boolean touchDown(int screenX, int screenY, int pointer, int button) { return false; }
    @Override public boolean touchUp(int screenX, int screenY, int pointer, int button) { return false; }
    @Override public boolean touchDragged(int screenX, int screenY, int pointer) { return false; }
    @Override public boolean mouseMoved(int screenX, int screenY) { return false; }
    @Override public boolean scrolled(float amountX, float amountY) { return false; }
}
