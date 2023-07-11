package ru.ocupuc;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class DemoClientHandler extends SimpleChannelInboundHandler<String>{

    private enum State {
        AWAITING_SIZE,
        AWAITING_MAP,
        AWAITING_PACMAN_POSITION
    }

    private State currentState = State.AWAITING_SIZE;

    @Override
    public void channelActive(ChannelHandlerContext ctx) {

    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String s) throws Exception {
        switch (currentState) {
            case AWAITING_SIZE:
                MapSize mapSize = MapSize.parseFromString(s);
                System.out.println("Received size:");
                System.out.println("mapSizeX = " + mapSize.mapSizeX());
                System.out.println("mapSizeY = " + mapSize.mapSizeY());
                currentState = State.AWAITING_MAP;
                break;

            case AWAITING_MAP:
                MapGame gameMap = new MapGame();
                gameMap.parseFromString(s);
                System.out.println("Received map:");
                gameMap.getMap().forEach((point, type) -> {
                    System.out.println("(" + point.x + "," + point.y + ") = " + type);
                });
                currentState = State.AWAITING_PACMAN_POSITION;
                break;

            case AWAITING_PACMAN_POSITION:
                PacmanPosition pacmanPosition = PacmanPosition.parseFromString(s);
                System.out.println("Received Pacman position:");
                System.out.println("Pacman is at (" + pacmanPosition.getX() + ";" + pacmanPosition.getY() + ")");
                currentState = State.AWAITING_SIZE;  // Если нужно, можно переходить обратно к состоянию ожидания размера
                break;
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
