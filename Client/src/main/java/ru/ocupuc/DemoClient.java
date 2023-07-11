package ru.ocupuc;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;


public class DemoClient {

    private final String ip;
    private final int port;
    private final int workerThreads; // Поток вычислений для бизнес-обработки

    public DemoClient(String ip, int port, int workerThreads) {
        this.ip = ip;
        this.port = port;
        this.workerThreads = workerThreads;
    }

    public void start() {
        EventLoopGroup workerGroup = new NioEventLoopGroup(workerThreads);
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(workerGroup).channel(NioSocketChannel.class).handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                    ChannelPipeline p = ch.pipeline();
                    p.addLast(new StringDecoder());
                    p.addLast(new StringEncoder());
                    p.addLast(new DemoClientHandler());
                }
            });
            bootstrap.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                    .option(ChannelOption.SO_KEEPALIVE, true);
            ChannelFuture future = bootstrap.connect(ip, port).sync();
                        future.channel().writeAndFlush("Hello Netty Server ,I am a common client");
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            workerGroup.shutdownGracefully();
        }

    }
//    public static void main(String[] args) {
//        DemoClient client=new DemoClient("127.0.0.1",8090,1);
//        client.start();
//    }
}