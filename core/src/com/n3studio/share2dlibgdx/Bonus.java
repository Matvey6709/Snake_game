package com.n3studio.share2dlibgdx;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;

public class Bonus {

    float x, y;
    float sizeX, sizeY;
    int view;
    Texture bonus;
    Touch touch = new Touch();
    Batch batch;

    public Bonus(Batch batch) {
        this.batch = batch;
        bonus = TexturesClass.textureFast;
        deleteBonus();
        getBonusFast();
    }

    public void render(Snake snake, float snake2X, float snake2Y) {
        batch.draw(bonus, x, y, sizeX, sizeY);
        if (touch.touchBox(snake, x, y)) {
            deleteBonus();
            getBonusFast();
            System.out.println("Yes");
        } else if (checkBonus(snake.cells.get(0).x, snake.cells.get(0).y, snake2X, snake2Y)) {
            deleteBonus();
            getBonusFast();
            System.out.println("No");
        }
    }

    public void getBonusFast() {
        x = MathUtils.random(1, 1180 / 33 - 1) * 33;
        ;
        y = MathUtils.random(1, 1180 / 33 - 1) * 33;
        ;

        sizeX = 33;
        sizeY = 33;
        bonus = TexturesClass.textureFast;
    }

    public void deleteBonus() {
        x = -100;
        y = -100;
    }

    public boolean checkBonus(float snakeX, float snakeY, float snake2X, float snake2Y) {
        return touch.touchBonus(x, y, snakeX, snakeY, snake2X, snake2Y, 33);
    }

}
