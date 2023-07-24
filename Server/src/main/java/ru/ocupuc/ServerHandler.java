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
                Panzer panzer = GameLoop.panzers.get(ctx.channel().id().asLongText());
                panzer.setLeftPressed(message.get("leftPressed").asBoolean());
                panzer.setRightPressed(message.get("rightPressed").asBoolean());
                panzer.setUpPressed(message.get("upPressed").asBoolean());
                panzer.setDownPressed(message.get("downPressed").asBoolean());
                break;
            default:
                throw new RuntimeException("Unknown WS object type: " + type);
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Panzer panzer = new Panzer();
        panzer.setId(ctx.channel().id().asLongText());
        GameLoop.panzers.put(ctx.channel().id().asLongText(), panzer);

        ObjectNode sessionKeyNode = mapper.createObjectNode();
        sessionKeyNode.put("class", "sessionKey");
        sessionKeyNode.put("id", ctx.channel().id().asLongText());
        ctx.writeAndFlush(sessionKeyNode.toString());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        ObjectNode evictNode = mapper.createObjectNode();
        evictNode.put("class", "evict");
        evictNode.put("id", ctx.channel().id().asLongText());
        ctx.writeAndFlush(evictNode.toString());

        GameLoop.panzers.remove(ctx.channel().id().asLongText());
    }
}
