package ru.ocupuc;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.io.IOException;

public class ServerHandler extends SimpleChannelInboundHandler<String> {

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) {
        try {
            JsonNode message = mapper.readTree(msg);

            if (message.has("type")) {
                String type = message.get("type").asText();
                switch (type) {
                    case "state":
                        Pacman pacman = GameLoop.pacmen.get(ctx.channel().id().asLongText());

                        if (pacman != null && message.has("leftPressed") && message.has("rightPressed")
                                && message.has("upPressed") && message.has("downPressed")) {
                            // Обновляем состояние пакмана на основе полученных данных
                            pacman.setLeftPressed(message.get("leftPressed").asBoolean());
                            pacman.setRightPressed(message.get("rightPressed").asBoolean());
                            pacman.setUpPressed(message.get("upPressed").asBoolean());
                            pacman.setDownPressed(message.get("downPressed").asBoolean());


                            // Сериализуем обновленного пакмана в формат JSON
                            PacmanDTO updatedPacmanDTO = new PacmanDTO(pacman);
                            String updatedPacmanJson = mapper.writeValueAsString(updatedPacmanDTO);

                            // Отправляем обновленного пакмана обратно клиенту
                            ctx.writeAndFlush(updatedPacmanJson);
                            System.out.println(updatedPacmanJson);
                        }
                        break;
                    default:
                        throw new RuntimeException("Unknown WS object type: " + type);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
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

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}



