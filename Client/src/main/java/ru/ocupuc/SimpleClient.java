package ru.ocupuc;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.nio.charset.StandardCharsets;

public class SimpleClient {
    public static void main(String[] args) throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap()
                    .group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<Channel>() {
                        @Override
                        protected void initChannel(Channel ch) {
                            ch.pipeline()
                                    .addLast(new StringDecoder(StandardCharsets.UTF_8))
                                    .addLast(new StringEncoder(StandardCharsets.UTF_8))
                                    .addLast(new SimpleChannelInboundHandler<String>() {
                                        @Override
                                        protected void channelRead0(ChannelHandlerContext ctx, String msg) {
                                            System.out.println("Received message: " + msg);
                                        }
                                    });
                        }
                    });

            Channel channel = bootstrap.connect("localhost", 8090).sync().channel();

            // Send a test message to the server
            String testMessage = "{\"type\":\"state\",\"leftPressed\":true,\"rightPressed\":false,\"upPressed\":false,\"downPressed\":false,\"angle\":0.0}";
            channel.writeAndFlush(Unpooled.copiedBuffer(testMessage, StandardCharsets.UTF_8));

            // Wait until the connection is closed.
            channel.closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
    }
}
