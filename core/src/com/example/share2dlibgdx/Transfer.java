package com.example.share2dlibgdx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.example.share2dlibgdx.ui.Joystick3;

import java.util.ArrayList;

public class Transfer {
    public int tr = 0;
    ArrayList<Cell> cells = new ArrayList<>();
    DeterminantSize size = new DeterminantSize();
    int speed = 33;
    Joystick3 joystick3;
    Snake snake;

    public Transfer(ArrayList<Cell> cells, Joystick3 joystick3) {
        this.cells = cells;
        this.joystick3 = joystick3;
        speed = 33;
    }

    public Transfer(ArrayList<Cell> cells, Snake snake) {
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
        switch (tr) {
            case 1:
                for (int i = cells.size() - 1; i > 0; i--) {
                    Cell nextBody = cells.get(i - 1);
                    Cell body = cells.get(i);
                    body.x = nextBody.getX();
                    body.y = nextBody.getY();
                    body.rotate = nextBody.rotate;
                }
                cells.get(0).x += speed;
                cells.get(0).rotate = 0;
                break;
            case 0:
                for (int i = cells.size() - 1; i > 0; i--) {
                    Cell nextBody = cells.get(i - 1);
                    Cell body = cells.get(i);
                    body.x = nextBody.getX();
                    body.y = nextBody.getY();
                    body.rotate = nextBody.rotate;
                }
                cells.get(0).rotate = 90;
                cells.get(0).y += speed;
                break;
            case 3:
                for (int i = cells.size() - 1; i > 0; i--) {
                    Cell nextBody = cells.get(i - 1);
                    Cell body = cells.get(i);
                    body.x = nextBody.getX();
                    body.y = nextBody.getY();
                    body.rotate = nextBody.rotate;
                }
                cells.get(0).y -= speed;
                cells.get(0).rotate = 270;
                break;
            case 2:
                for (int i = cells.size() - 1; i > 0; i--) {
                    Cell nextBody = cells.get(i - 1);
                    Cell body = cells.get(i);
                    body.x = nextBody.getX();
                    body.y = nextBody.getY();
                    body.rotate = nextBody.rotate;
                }
                cells.get(0).x -= speed;
                cells.get(0).rotate = 180;
                break;
        }
    }

    float timeSet;

    public void trBody2(float delta) {
        for (int i = cells.size() - 1; i > 0; i--) {
            Cell nextBody = cells.get(i - 1);
            Cell body = cells.get(i);
            body.x = nextBody.getX();
            body.y = nextBody.getY();
        }
        timeSet += delta;
        float getValueX = 0;
        float getValueY = 0;
        if (timeSet > .10) {
            getValueX = joystick3.getValueX();
            getValueY = joystick3.getValueY();
//            timeSet = 0;
        }
        cells.get(0).x += getValueX * 10 * size.getKX();
        cells.get(0).y += getValueY * 10 * size.getKY();
    }
}
