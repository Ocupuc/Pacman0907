package ru.ocupuc;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import ru.ocupuc.parse.PacmanPositionParse;

import java.util.LinkedList;
import java.util.Queue;

public class MyGame extends ApplicationAdapter {
    Network network = new Network((args)->{
    });
    KeyboardTracker keyboardTracker = new KeyboardTracker(network);

    private static final int CELL_SIZE = 50;  // размер клетки
    private static int gridWidth;   // ширина сетки
    private static int gridHeight;  // высота сетки
    private static MyGame instance;
    private static Queue<PacmanPositionParse> positionUpdates = new LinkedList<>();

    private Texture pacmanTexture;
    private int pacmanX;  // позиция Pacman'a по X в клетках
    private int pacmanY;  // позиция Pacman'a по Y в клетках

    public static synchronized MyGame getInstance() {
        if (instance == null) {
            instance = new MyGame();
        }
        return instance;
    }

    public static void setGridSize(int width, int height) {
        gridWidth = width;
        gridHeight = height;
    }

    public void updatePacmanPosition(int x, int y) {
        // Если игра еще не создана, сохраняем обновление для последующего использования
        positionUpdates.add(new PacmanPositionParse(x, y));
    }

    @Override
    public void create() {

        Gdx.input.setInputProcessor(keyboardTracker);
        instance = this;
        pacmanTexture = new Texture(Gdx.files.internal("pacman.png"));
        processPendingUpdates();
    }

    @Override
    public void render() {
        processPendingUpdates();
        ScreenUtils.clear(0f, 0f, 0.2f, 1.0f);
        SpriteBatch batch = new SpriteBatch();
        batch.begin();
        batch.draw(pacmanTexture, pacmanX * CELL_SIZE, pacmanY * CELL_SIZE, CELL_SIZE, CELL_SIZE);
        batch.end();
    }

    private void processPendingUpdates() {
        // Обрабатываем все ожидающие обновления позиции
        while (!positionUpdates.isEmpty()) {
            PacmanPositionParse position = positionUpdates.poll();
            this.pacmanX = position.x();
            this.pacmanY = position.y();
        }
    }

    @Override
    public void dispose() {
        pacmanTexture.dispose();
    }
}
