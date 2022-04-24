package com.example.share2dlibgdx;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.example.share2dlibgdx.ui.Joystick3;

import java.util.ArrayList;

public class Snake {
    Transfer transfer;
    public SpriteBatch batch;
    ArrayList<Cell> cells = new ArrayList<>();
    float timeSet;
    int level = 1;
    Joystick3 joystick3;
    int sizeX;
    int sizeY;
    Texture texture;
    public boolean youFirst = false;


    public Snake(SpriteBatch batch, Joystick3 joystick3, int sizeX, int sizeY) {
        this.batch = batch;
        this.joystick3 = joystick3;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        increase(level);
        transfer = new Transfer(cells, joystick3);
        texture = new Texture("redS.png");
    }

    public void render(float delta) {
        ShareInit();
        timeSet += delta;
        for (int i = 0; i < cells.size() - 1; i++) {
            batch.draw(texture, cells.get(i).x, cells.get(i).y, cells.get(i).sizeX, cells.get(i).sizeY);
        }
    }

    public void ShareInit() {
//        transfer.trInit();
        if (timeSet > .50) {
            transfer.trBody2();//передвижение
            timeSet = 0;
        }
    }

    public void increase(int level) {
        for (int i = 0; i < level + 1; i++) {
            cells.add(new Cell(0, 0, sizeX, sizeY));
        }
    }

    public void dispose() {
        texture.dispose();
        batch.dispose();
    }

    public void addLevel() {
        level++;
        cells.add(new Cell(0, 0, sizeX, sizeY));
    }

//    Player's Client


    String NameGame = "1";
    public String NamePlayer = "test2";//Matvey-Pixel24 gg-my phone
    String vector2 = "";

    public void PlayerClient() {
        vector2 = "";
//        for (int i = 0; i < cells.size() - 1; i++) {
//            vector2 += cells.get(i).x + " " + cells.get(i).y + " ";
//        }
        vector2 = cells.get(0).x + "h" + cells.get(0).y;
    }

}
