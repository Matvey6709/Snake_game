package com.example.share2dlibgdx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Random;

public class Bread {
    int x;
    int y;
    int size;
    Random random;
    Texture texture;
    SpriteBatch batch;
    DeterminantSize determinantSize;

    public Bread(SpriteBatch batch, int size) {
        this.batch = batch;
        this.size = size;
        random = new Random();
        texture = new Texture("apple.png");
        determinantSize = new DeterminantSize();
    }

    public void spawn() {
        x = 100 + random.nextInt(1180 - 100 + 1);
//        int number = 30 + random.nextInt(1280 - 30 + 1);
        y = 100 + random.nextInt(620 - 100 + 1);
//        System.out.println(x);
//        System.out.println(y);
    }


    public void render() {
        batch.draw(texture, x, y, size, size);
    }

    public void dispose(){
        texture.dispose();
        batch.dispose();
    }

}
