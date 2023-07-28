package ru.ocupuc;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.ObjectMap;
import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

public class GameLoop extends ApplicationAdapter {
    private static final float frameRate = 1 / 60f;
    private float lastRender = 0;

    static final ObjectMap<String, Pacman> pacmen = new ObjectMap<>();
    private final Array<Pacman> stateToSend = new Array<>();
    static final ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    private final Json json;

    public GameLoop(Json json) {
        this.json = json;
    }

    public void render() {
        lastRender += 1/60f; // DeltaTime for 60 FPS
        if (lastRender >= frameRate) {
            stateToSend.clear();
            for (ObjectMap.Entry<String, Pacman> pacmanEntry : pacmen) {
                Pacman pacman = pacmanEntry.value;
                pacman.act();
                stateToSend.add(pacman);
            }

            lastRender = 0;
            String stateJson = json.toJson(stateToSend);

            sendToEverybody(stateJson);
        }
    }

    public void sendToEverybody(String message) {
        for (Channel channel : channels) {
            if (channel.isActive()) {
                System.out.println("Sending message: " + message);
                channel.writeAndFlush(message);
            }
        }
    }

    public static void sendToEverybodyExcept(Pacman exception, String json) {
        for (ObjectMap.Entry<String, Pacman> pacmanEntry : pacmen) {
            Pacman pacman = pacmanEntry.value;
            if (!pacman.equals(exception) && pacman.getChannel().isActive()) {
                System.out.println("Sending exception message: " + json);
                pacman.getChannel().writeAndFlush(json);
            }
        }
    }
}
