package com.example.share2dlibgdx;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.example.share2dlibgdx.ui.Joystick3;

import java.util.LinkedList;

public class Snake {
    game game;

    public Transfer transfer;
    public SpriteBatch batch;
    LinkedList<Cell> cells = new LinkedList<>();
    Joystick3 joystick3;
    int sizeX, sizeY, level = 1;
    public boolean youFirst = false;
    //    Texture snakebody, snakehead, snakeheadMy, snakebodyMy, snaketailMy, texture;
    Vector2 vector = new Vector2();

    float speed = .40f, timeSet;
    Sprite m1, m2, m3;

    public Snake(SpriteBatch batch, Joystick3 joystick3, int sizeX, int sizeY, Sprite m1, Sprite m2, Sprite m3) {
        this.batch = batch;
        this.joystick3 = joystick3;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.m1 = m1;
        this.m2 = m2;
        this.m3 = m3;
        increase(level);
        transfer = new Transfer(cells, joystick3);
//        texture = new Texture("redS.png");
//        snakebody = new Texture("snakebody.png");
//        snakehead = new Texture("snakehead.png");
//        snakeheadMy = new Texture("snakeHeadMy.png");
//        snakebodyMy = new Texture("snakeBodyMy.png");
//        snaketailMy = new Texture("snakeTailMy.png");
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


    private Sprite getBodyType3(int index) {
        if (index == cells.size() - 2) {
            return m1;
        }
        if (index == 0) {
            return m3;
        } else {
            return m2;
        }
    }

    public Snake(SpriteBatch batch, int sizeX, int sizeY) {
        this.batch = batch;
        this.sizeX = sizeX / 3;
        this.sizeY = sizeY / 3;
        increase(level);
        transfer = new Transfer(cells, this);
//        texture = new Texture("redS.png");
//        snakebody = new Texture("snakebody.png");
//        snakehead = new Texture("snakehead.png");
//        snakeheadMy = new Texture("snakeHeadMy2.png");
//        snakebodyMy = new Texture("snakeBodyMy.png");
//        snaketailMy = new Texture("snakeTailMy.png");
    }

    public Snake(SpriteBatch batch, int sizeX, int sizeY, game game, float speed) {
        this.game = game;
        this.batch = batch;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.speed = speed;
        increase(level);
        transfer = new Transfer(cells, this);
//        snakebody = new Texture("snakebody.png");
//        snakehead = new Texture("snakehead.png");
//        snakeheadMy = new Texture("snakeHeadMy.png");
//        snakebodyMy = new Texture("snakeBodyMy.png");
//        snaketailMy = new Texture("snakeTailMy.png");

    }

    public void render(float delta) {
        ShareInit(delta);
        timeSet += delta;
        for (int i = 0; i < cells.size() - 1; i++) {
            batch.draw(TexturesClass.i.textureS, cells.get(i).x, cells.get(i).y, cells.get(i).sizeX, cells.get(i).sizeY);
        }
//        System.out.println(cells.size());
    }

    public void render2(float delta) {
        ShareInit2(delta);
        timeSet += delta;
        for (int i = 0; i < cells.size() - 1; i++) {
//            batch.draw(Asset.instance().getSprite(getBodyType(i)), cells.get(i).x, cells.get(i).y, cells.get(i).sizeX, cells.get(i).sizeY);
            Sprite sprite = getBodyType3(i);
            sprite.setOrigin(cells.get(i).sizeX / 2, cells.get(i).sizeY / 2);
            sprite.rotate(cells.get(i).rotate);
            sprite.setBounds(cells.get(i).x, cells.get(i).y, cells.get(i).sizeX, cells.get(i).sizeY);
            sprite.draw(batch);
            sprite.setRotation(0);
        }
    }

    public void render3(float delta) {
        ShareInit3(delta);
        timeSet += delta;

        for (int i = 0; i < cells.size() - 1; i++) {
            Sprite sprite = getBodyType4(i);
            sprite.setOrigin(cells.get(i).sizeX / 2, cells.get(i).sizeY / 2);
            sprite.rotate(cells.get(i).rotate);
            sprite.setBounds(cells.get(i).x, cells.get(i).y, cells.get(i).sizeX, cells.get(i).sizeY);
            sprite.draw(batch);
            sprite.setRotation(0);
        }
        Sprite sprite = getBodyType4(0);
        sprite.setOrigin(cells.get(0).sizeX / 2, cells.get(0).sizeY / 2);
        sprite.rotate(cells.get(0).rotate);
        sprite.setBounds(cells.get(0).x, cells.get(0).y, cells.get(0).sizeX, cells.get(0).sizeY);
        sprite.draw(batch);
        sprite.setRotation(0);
    }

    public void ShareInit(float delta) {
        if (timeSet > .20) {
            transfer.trBody2(delta);//передвижение
            timeSet = 0;
        }
    }

    public void ShareInit2(float delta) {
        transfer.trInit();
        if (timeSet > speed) {//40
            transfer.trBody();//передвижение

            timeSet = 0;
        }
    }

    int l = 0;
    public void ShareInit3(float delta) {
        transfer.trInit();
        if (timeSet > .25) {//40
            transfer.trBody();//передвижение
//            l = 0;
//            for (int i = 1; i < cells.size() - 1; i++) {
//                vector.x = cells.get(i).x;
//                vector.y = cells.get(i).y;
//                vector.lerp(new Vector2(cells.get(l).x, cells.get(l).y), Gdx.graphics.getDeltaTime()*8f);
//                cells.get(i).x = vector.x;
//                cells.get(i).y = vector.y;
//                l++;
//            }
            timeSet = 0;
        }
    }

    public void increase(int level) {
        for (int i = 0; i < level + 1; i++) {
            cells.add(new Cell(0, 0, sizeX, sizeY));
        }
    }

    public void dispose() {
        TexturesClass.i.textureS.dispose();
        batch.dispose();
        TexturesClass.i.snakehead.dispose();
        TexturesClass.i.snakebody.dispose();
        TexturesClass.i.snakeheadMy.dispose();
        TexturesClass.i.snakebodyMy.dispose();
        TexturesClass.i.snaketailMy.dispose();
    }

    public void addLevel() {
        level++;
        cells.add(new Cell(cells.get(0).x, cells.get(0).y, sizeX, sizeY));
    }

    public void addLevel2() {
        cells.add(new Cell(cells.get(0).x, cells.get(0).y, sizeX, sizeY));
    }

//    Player's Client


    String NameGame = "1";
    public String NamePlayer = "Игрок";//Matvey-Pixel24 gg-my phone
    String vector2 = "";

    public void PlayerClient() {
        vector2 = "";
        for (int i = 0; i < cells.size() - 1; i++) {
            vector2 += cells.get(i).x + " " + cells.get(i).y + " ";
        }
//        vector2 = cells.get(0).x + "h" + cells.get(0).y;
    }

}
