package ru.ocupuc;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ServerHandler extends SimpleChannelInboundHandler<String> {

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        JsonNode message = mapper.readTree(msg);
        String type = message.get("type").asText();
        System.out.println(msg);

        switch (type) {
            case "state":
                Pacman pacman = GameLoop.pacmen.get(ctx.channel().id().asLongText());
                pacman.setLeftPressed(message.get("leftPressed").asBoolean());
                pacman.setRightPressed(message.get("rightPressed").asBoolean());
                pacman.setUpPressed(message.get("upPressed").asBoolean());
                pacman.setDownPressed(message.get("downPressed").asBoolean());
                break;
            default:
                throw new RuntimeException("Unknown WS object type: " + type);
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Pacman pacman = new Pacman();
        pacman.setId(ctx.channel().id().asLongText());
        pacman.setChannel(ctx.channel());
        GameLoop.pacmen.put(ctx.channel().id().asLongText(), pacman);

        // send the new player to the client
        PacmanDTO pacmanDTO = new PacmanDTO(pacman);
        ctx.writeAndFlush(mapper.writeValueAsString(pacmanDTO));

        // update other clients about the new player
        GameLoop.sendToEverybodyExcept(pacman, "New player joined: " + pacman.getId());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        // inform others about leaving player
        Pacman pacman = GameLoop.pacmen.get(ctx.channel().id().asLongText());
        GameLoop.sendToEverybodyExcept(pacman, "Player left: " + pacman.getId());

        GameLoop.pacmen.remove(ctx.channel().id().asLongText());
    }
}

