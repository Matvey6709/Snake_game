package com.example.share2dlibgdx;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class TestG extends Actor {
    public TestG() {

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(new Texture("grass.png"), 200, 200, 400, 400);
    }


}
