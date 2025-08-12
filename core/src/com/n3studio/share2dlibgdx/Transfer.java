package com.n3studio.share2dlibgdx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import com.n3studio.share2dlibgdx.ui.Joystick3;

import java.util.LinkedList;

public class Transfer {
    public int tr = -1;
    LinkedList<com.n3studio.share2dlibgdx.Cell> cells;
    DeterminantSize size = new DeterminantSize();
    int speed = 33;
    Joystick3 joystick3;
    com.n3studio.share2dlibgdx.Snake snake;

    public Transfer(LinkedList<com.n3studio.share2dlibgdx.Cell> cells, Joystick3 joystick3) {
        this.cells = cells;
        this.joystick3 = joystick3;
        speed = 33;
        tr = 0;
    }

    public Transfer(LinkedList<com.n3studio.share2dlibgdx.Cell> cells, Snake snake) {
        this.cells = cells;
        this.snake = snake;
        speed = snake.sizeX;
    }

    public void trInit() {

        if (Gdx.input.isKeyPressed(Input.Keys.UP) && tr != 3) {
            tr = 0;
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && tr != 2) {
            tr = 1;

        } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && tr != 1) {
            tr = 2;

        } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN) && tr != 0) {
            tr = 3;

        }
    }

    public void trBody() {
        for (int i = cells.size() - 1; i > 0; i--) {
            com.n3studio.share2dlibgdx.Cell nextBody = cells.get(i - 1);
            com.n3studio.share2dlibgdx.Cell body = cells.get(i);
            body.x = nextBody.getX();
            body.y = nextBody.getY();
            body.rotate = nextBody.rotate;
        }

        switch (tr) {
            case 1:
                cells.get(0).x += speed;
                cells.get(0).rotate = 0;
                break;
            case 0:
                cells.get(0).rotate = 90;
                cells.get(0).y += speed;
                break;
            case 3:
                cells.get(0).y -= speed;
                cells.get(0).rotate = 270;
                break;
            case 2:
                cells.get(0).x -= speed;
                cells.get(0).rotate = 180;
                break;
        }
    }


    public void trBody2(float delta) {
        if (tr != -1) {
            for (int i = cells.size() - 1; i > 0; i--) {
                com.n3studio.share2dlibgdx.Cell nextBody = cells.get(i - 1);
                Cell body = cells.get(i);
                body.x = nextBody.getX();
                body.y = nextBody.getY();
            }

            cells.get(0).x += joystick3.getValueX() * 10 * size.getKX();
            cells.get(0).y += joystick3.getValueY() * 10 * size.getKY();
        }
    }

}
