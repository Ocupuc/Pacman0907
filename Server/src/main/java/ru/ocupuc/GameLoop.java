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
    private final Json json;
    private float lastRender = 0;

    static final ObjectMap<String, Pacman> pacmen = new ObjectMap<>();
    private final Array<Pacman> stateToSend = new Array<>();
    static final ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    public GameLoop(Json json) {
        this.json = json;
    }

    @Override
    public void render() {
        lastRender += Gdx.graphics.getDeltaTime();
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

    private void sendToEverybody(String json) {
        for (Channel channel : channels) {
            if (channel.isActive()) {
                channel.writeAndFlush(json);
            }
        }
    }

    public static void sendToEverybodyExcept(Pacman exception, String json) {
        for (ObjectMap.Entry<String, Pacman> pacmanEntry : pacmen) {
            Pacman pacman = pacmanEntry.value;
            if (!pacman.equals(exception) && pacman.getChannel().isActive()) {
                pacman.getChannel().writeAndFlush(json);
            }
        }
    }
}

