package ru.ocupuc;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class MyGame extends ApplicationAdapter {
    private static final int CELL_SIZE = 50;  // размер клетки
    private static int gridWidth;   // ширина сетки
    private static int gridHeight;  // высота сетки
    private static MyGame instance;

    private Texture pacmanTexture;
    private int pacmanX;  // позиция Pacman'a по X в клетках
    private int pacmanY;  // позиция Pacman'a по Y в клетках

    public static MyGame getInstance() {
        return instance;
    }

    public static void setGridSize(int width, int height) {
        gridWidth = width;
        gridHeight = height;
    }

    public void updatePacmanPosition(int x, int y) {
        this.pacmanX = x;
        this.pacmanY = y;
    }

    @Override
    public void create() {
        instance = this;
        pacmanTexture = new Texture(Gdx.files.internal("pacman.png"));
    }

    @Override
    public void render() {
        ScreenUtils.clear(0f, 0f, 0.2f, 1.0f);
        SpriteBatch batch = new SpriteBatch();
        batch.begin();
        batch.draw(pacmanTexture, pacmanX * CELL_SIZE, pacmanY * CELL_SIZE, CELL_SIZE, CELL_SIZE);
        batch.end();
    }

    @Override
    public void dispose() {
        pacmanTexture.dispose();
    }
}