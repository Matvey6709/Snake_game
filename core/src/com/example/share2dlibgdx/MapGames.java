package com.example.share2dlibgdx;

import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;

public class MapGames {
    int level = 1;
    ArrayList<Cell> boxes;
    Texture box;
    game game;

    public MapGames(int level, game game) {
        this.level = level;
        this.game = game;
        box = new Texture("box.png");
        boxes = new ArrayList<>();
    }

    public void mapRendering() {
        for (int i = 0; i < boxes.size() - 1; i++) {
            game.batch.draw(box, boxes.get(i).x, boxes.get(i).y, 33, 33);

        }


    }

    public void dispose() {
        box.dispose();
    }


}
