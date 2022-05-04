package com.example.share2dlibgdx;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
        this.sizeX = sizeX / 3;
        this.sizeY = sizeY / 3;
        increase(level);
        transfer = new Transfer(cells, joystick3);
        texture = new Texture("redS.png");
    }

    private String getBodyType(int index) {
        if (index == cells.size()) return "snake_body";
        if (index == 0) return "snake_head";
        else return "snake_tail";
    }

    public Snake(SpriteBatch batch, int sizeX, int sizeY) {
        this.batch = batch;
        this.sizeX = sizeX / 3;
        this.sizeY = sizeY / 3;
        increase(level);
        transfer = new Transfer(cells);
        texture = new Texture("redS.png");
    }

    public void render(float delta) {
        ShareInit(delta);
        timeSet += delta;
        for (int i = 0; i < cells.size() - 1; i++) {
            batch.draw(texture, cells.get(i).x, cells.get(i).y, cells.get(i).sizeX, cells.get(i).sizeY);
        }
//        System.out.println(cells.size());
    }

    public void render2(float delta) {
        ShareInit2(delta);
        timeSet += delta;
        for (int i = 0; i < cells.size() - 1; i++) {
            batch.draw(Asset.instance().getSprite(getBodyType(i)), cells.get(i).x, cells.get(i).y, cells.get(i).sizeX, cells.get(i).sizeY);
        }
//        System.out.println(cells.size());
    }

    public void ShareInit(float delta) {
        if (timeSet > .10) {
            transfer.trBody2(delta);//передвижение
            timeSet = 0;
        }
    }

    public void ShareInit2(float delta) {
        transfer.trInit();
        if (timeSet > .20) {//40
            transfer.trBody();//передвижение
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
        cells.add(new Cell(-100, -100, sizeX, sizeY));
        cells.add(new Cell(-100, -100, sizeX, sizeY));
        cells.add(new Cell(-100, -100, sizeX, sizeY));
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
