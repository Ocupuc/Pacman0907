package ru.ocupuc;


import com.badlogic.gdx.utils.Json;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import java.util.logging.Level;

public class NettyServer {


    private final int port;
    private final Logger logger = Logger.getLogger(NettyServer.class.getName());
    private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

    private final DataSender dataSender = new DataSender();

    public NettyServer(int port) {
        this.port = port;
    }


    public void run() throws Exception {
        logger.log(Level.INFO, "Starting server on port " + port);
       executor.scheduleAtFixedRate(dataSender::send, 0, 1000 /15, TimeUnit.MILLISECONDS); // 60 FPS
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap()
                    .group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<Channel>() {
                        @Override
                        protected void initChannel(Channel ch) {
                            ch.pipeline().addLast(new StringDecoder());
                            ch.pipeline().addLast(new StringEncoder());
                            ch.pipeline().addLast(new ServerHandler());
                            logger.log(Level.INFO, "New client connected: " + ch.remoteAddress());
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            ChannelFuture future = bootstrap.bind("0.0.0.0",8090).sync();
            logger.log(Level.INFO, "Server started successfully.");
            future.channel().closeFuture().sync();
        } catch(Exception e) {
            logger.log(Level.SEVERE, "Error in server: " + e.getMessage(), e);
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
            logger.log(Level.INFO, "Server stopped.");
        }
    }

}
