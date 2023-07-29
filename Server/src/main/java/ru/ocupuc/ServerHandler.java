package ru.ocupuc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

public class ServerHandler extends SimpleChannelInboundHandler<String> {
    private static final ObjectMapper mapper = new ObjectMapper();
    //TODO
    private static final ConcurrentMap<String, Pacman> pacmans = new ConcurrentHashMap<>();
    private static final ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel incoming = ctx.channel();
        channels.add(incoming);

        String id = incoming.id().asShortText();
        Pacman pacman = new Pacman(id, 0, 0); // или инициализировать случайные координаты
        System.out.println("Pacman: " + pacman);
        pacmans.put(id, pacman);

        PacmanDTO pacmanDTO = new PacmanDTO(pacman.getId(), pacman.getX(), pacman.getY());

        ServerMessage message = new ServerMessage(MessageType.MY_PACMAN, pacmanDTO);
        String messageJson = mapper.writeValueAsString(message);
        incoming.writeAndFlush(messageJson);



    }


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) {



        List<PacmanDTO> allPacmanDTOs = pacmans.values().stream()
                .map(p -> new PacmanDTO(p.getId(), p.getX(), p.getY()))
                .collect(Collectors.toList());

        ServerMessage allPacmansMessage = new ServerMessage(MessageType.ENEMY_PACMANS, allPacmanDTOs);

        String allPacmansMessageJson = null;
        try {
            allPacmansMessageJson = mapper.writeValueAsString(allPacmansMessage);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        for (Channel channel : channels) {
            channel.writeAndFlush(allPacmansMessageJson);
        }
    }



    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelInactive " +  ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}



