package com.example.share2dlibgdx;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

import java.util.Random;

public class Bread {
    int x;
    int y;
    int size;
    Random random;
    Texture texture;
    SpriteBatch batch;
    DeterminantSize determinantSize;
    int foodType;
    private String[] foodTypes = {
            "green_icing_green_sprinkles", "blue_icing", "chocolate_icing", "chocolate_icing_chocolate_drizzle", "dark_red_icing",
            "orange_icing_chocolate_shaving", "pink_icing_sprinkles", "orange_icing_chocolate_shaving", "pink_icing_sprinkles",
            "pink_icing_white_drizzle", "red_icing_white_sprinkles",
            "white_icing", "white_icing_sprinkles", "yellow_icing_chocolate_drizzle"};

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

    public void spawn2() {
        x = MathUtils.random(1, 1180 / 33 - 1) * 33;
        y = MathUtils.random(1, 620 / 33 - 1) * 33;
        System.out.println(x);
        System.out.println(y);
    }


    public void render() {
        batch.draw(texture, x, y, size, size);
    }

    public void render2() {
        batch.draw(Asset.instance().getSprite(foodTypes[foodType]), x, y, size, size);
    }

    public void resetBreadTexture() {
        foodType = MathUtils.random(foodTypes.length - 1);

    }

    public void dispose() {
        texture.dispose();
        batch.dispose();
    }

}
