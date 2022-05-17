package com.example.share2dlibgdx;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.example.share2dlibgdx.ui.Joystick3;

import java.util.ArrayList;

public class Snake {
    public Transfer transfer;
    public SpriteBatch batch;
    ArrayList<Cell> cells = new ArrayList<>();
    float timeSet;
    int level = 1;
    Joystick3 joystick3;
    int sizeX;
    int sizeY;
    Texture texture;
    public boolean youFirst = false;
    Texture snakebody;
    Texture snakehead;
    Texture snakeheadMy;
    Texture snakebodyMy;
    Texture snaketailMy;
    game game;
    float speed = 0.1f;


    public Snake(SpriteBatch batch, Joystick3 joystick3, int sizeX, int sizeY) {
        this.batch = batch;
        this.joystick3 = joystick3;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        increase(level);
        transfer = new Transfer(cells, joystick3);
        texture = new Texture("redS.png");
        snakebody = new Texture("snakebody.png");
        snakehead = new Texture("snakehead.png");
        snakeheadMy = new Texture("snakeHeadMy.png");
        snakebodyMy = new Texture("snakeBodyMy.png");
        snaketailMy = new Texture("snakeTailMy.png");
    }

    private String getBodyType(int index) {
        if (index == cells.size() - 2) return "snake_tail";
        if (index == 0) return "snake_head";
        else return "snake_body";
    }

    private Sprite getBodyType4(int index) {
        if (index == cells.size() - 2) {
            return game.lobby.m1.sprite;
        }
        if (index == 0) {
            return game.lobby.m3.sprite;
        } else {
            return game.lobby.m2.sprite;
        }
    }

    private Texture getBodyType2(int index) {
        if (index == cells.size()) return snakebody;
        if (index == 0) return snakehead;
        else return snakebody;
    }

    private Sprite getBodyType3(int index) {
        if (index == cells.size() - 1) return new Sprite(snakebodyMy);
        if (index == 0) return new Sprite(snakeheadMy);
        else return new Sprite(snakebodyMy);
    }

    public Snake(SpriteBatch batch, int sizeX, int sizeY) {
        this.batch = batch;
        this.sizeX = sizeX / 3;
        this.sizeY = sizeY / 3;
        increase(level);
        transfer = new Transfer(cells, this);
        texture = new Texture("redS.png");
        snakebody = new Texture("snakebody.png");
        snakehead = new Texture("snakehead.png");
        snakeheadMy = new Texture("snakeHeadMy2.png");
        snakebodyMy = new Texture("snakeBodyMy.png");
        snaketailMy = new Texture("snakeTailMy.png");
    }

    public Snake(SpriteBatch batch, int sizeX, int sizeY, game game, float speed) {
        this.game = game;
        this.batch = batch;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.speed = speed;
        increase(level);
        transfer = new Transfer(cells, this);
        snakebody = new Texture("snakebody.png");
        snakehead = new Texture("snakehead.png");
        snakeheadMy = new Texture("snakeHeadMy.png");
        snakebodyMy = new Texture("snakeBodyMy.png");
        snaketailMy = new Texture("snakeTailMy.png");

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
//            batch.draw(getBodyType2(i), cells.get(i).x, cells.get(i).y, cells.get(i).sizeX, cells.get(i).sizeY);
        }
//        System.out.println(cells.size());
    }

    public void render3(float delta) {
        ShareInit3(delta);
        timeSet += delta;

        for (int i = 0; i < cells.size() - 1; i++) {
            Sprite sprite = getBodyType4(i);
//            if (game.lobby.chekPhoto) {
//                sprite = getBodyType4(i);
//            }

            sprite.setOrigin(cells.get(i).sizeX / 2, cells.get(i).sizeY / 2);
            sprite.rotate(cells.get(i).rotate);
            sprite.setBounds(cells.get(i).x, cells.get(i).y, cells.get(i).sizeX, cells.get(i).sizeY);
            sprite.draw(batch);
            sprite.setRotation(0);
        }


//            batch.draw(sprite, cells.get(i).x, cells.get(i).y, cells.get(i).sizeX, cells.get(i).sizeY);

//            batch.draw(getBodyType2(i), cells.get(i).x, cells.get(i).y, cells.get(i).sizeX, cells.get(i).sizeY);

//        System.out.println(cells.size());
    }

    public void ShareInit(float delta) {
        if (timeSet > .20) {
            transfer.trBody2(delta);//передвижение
            timeSet = 0;
        }
    }

    public void ShareInit2(float delta) {
        transfer.trInit();
        if (timeSet > .40) {//40
            transfer.trBody();//передвижение
            timeSet = 0;
        }
    }

    public void ShareInit3(float delta) {
        transfer.trInit();
        if (timeSet > speed) {//40
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
        snakehead.dispose();
        snakebody.dispose();
        snakeheadMy.dispose();
        snakebodyMy.dispose();
        snaketailMy.dispose();
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
        for (int i = 0; i < cells.size() - 1; i++) {
            vector2 += cells.get(i).x + " " + cells.get(i).y + " ";
        }
//        vector2 = cells.get(0).x + "h" + cells.get(0).y;
    }

}
