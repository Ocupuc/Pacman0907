package ru.ocupuc;

public class StarterServer {
    public static void main(String[] args) throws Exception {

        int port = 8090;

        //       Json json = new Json();


        new NettyServer(port).run();
    }
}
