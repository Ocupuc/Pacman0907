package ru.ocupuc;

public class StarterServer {
    public static void main(String[] args) throws Exception {
        //тест парсинга поля
//        PacmanField pacmanField = new PacmanField("D:\\libGDX\\Pacman0907\\Server\\src\\main\\resources\\pacman_field.txt");
//        System.out.println(pacmanField.getField());

        int port = 8090;

        //       Json json = new Json();


        new NettyServer(port).run();
    }
}
