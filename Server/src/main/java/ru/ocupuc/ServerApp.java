package ru.ocupuc;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

public class ServerApp {
    public static void main (String[] arg) {

        RPCServer rpcServer = new RPCServer("127.0.0.1", 8090, 1, 1);
        rpcServer.start();


        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setTitle("Hello Server!");
        new Lwjgl3Application(new MyGame(), config);
    }
}
