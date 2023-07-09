package ru.ocupuc;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

public class ClientApp {
    public static void main(String[] arg) {


        DemoClient client = new DemoClient("127.0.0.1", 8090, 1);
        client.start();


        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setTitle("Hello Client!");
        new Lwjgl3Application(new MyGame(), config);
    }
}
