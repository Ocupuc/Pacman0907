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
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

import static ru.ocupuc.ServerData.channels;
import static ru.ocupuc.ServerData.pacmans;

public class ServerHandler extends SimpleChannelInboundHandler<String> {


    private static final ObjectMapper mapper = new ObjectMapper();
    //TODO



    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel incoming = ctx.channel();
        channels.add(incoming);

        String id = incoming.id().asShortText();
        int size = pacmans.size() ;
        Pacman pacman = new Pacman(id, 10*size, 0); // или инициализировать случайные координаты
        System.out.println("Pacman: " + pacman);
        pacmans.put(id, pacman);

        PacmanDTO pacmanDTO = new PacmanDTO(pacman.getId(), pacman.getX(), pacman.getY());

        ServerMessage message = new ServerMessage(MessageType.MY_PACMAN, Collections.singletonList(pacmanDTO));
        String messageJson = mapper.writeValueAsString(message);
        incoming.writeAndFlush(messageJson);


    }


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) {



//        List<Object> allPacmanDTOs = pacmans.values().stream()
//                .map(p -> new PacmanDTO(p.getId(), p.getX(), p.getY()))
//                .collect(Collectors.toList());
//
//        ServerMessage allPacmansMessage = new ServerMessage(MessageType.ENEMY_PACMANS, allPacmanDTOs);
//
//        String allPacmansMessageJson = null;
//        try {
//            allPacmansMessageJson = mapper.writeValueAsString(allPacmansMessage);
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
//        }
//
//        for (Channel channel : channels) {
//            channel.writeAndFlush(allPacmansMessageJson);
//        }
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



