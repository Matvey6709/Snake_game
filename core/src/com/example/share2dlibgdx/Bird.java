package com.example.share2dlibgdx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

public class Bird {
    float x, y, targetX, targetY;
    int sizeX, sizeY;
    Texture bird;


    public Bird(float x, float y, int sizeX, int sizeY) {
        this.x = x;
        this.y = y;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
    }

    boolean flyY = false;

    public void flyX() {
        targetY = MathUtils.random(1, 620);
        flyY = true;
    }

    float fly;

    public void render(SpriteBatch batch) {
        batch.begin();
        batch.draw(bird, x, y, sizeX, sizeY);
        batch.end();
        fly += Gdx.graphics.getDeltaTime();
        if (flyY) {

        }
    }

    public void dispose() {
        bird.dispose();
    }
}
