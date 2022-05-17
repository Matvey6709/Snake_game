package com.example.share2dlibgdx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class Game3_Screen extends Game2_Screen {

    MapGames mapGames;
    Touch touch;
    Skin skin;

    public Game3_Screen(game game, int level) {
        super(game);
    }

    @Override
    public void show() {
        super.show();
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        mapGames = new MapGames(1, game);

        for (int i = 0; i < 25; i++) {
            if (i >= 10 && i <= 15) {
                continue;
            }
            mapGames.boxes.add(new Cell(660, 33 * i, 33, 33));
        }
        touch = new Touch(snake);
        Dialog dialog = new Dialog("Hi", skin);
        dialog.setTransform(true);
        dialog.setScale(3);
        dialog.setSize(200, 100);
        dialog.setPosition(375, 200);
//        dialog.show(stage);
        stage.addActor(dialog);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        game.batch.begin();
        mapGames.mapRendering();
        for (int i = 0; i < mapGames.boxes.size() - 1; i++) {
            if (touch.touchBox(snake, mapGames.boxes.get(i).x, mapGames.boxes.get(i).y)) {
                gameOver();
            }
            if (bread.x == mapGames.boxes.get(i).x || bread.y == mapGames.boxes.get(i).y) {
                bread.spawn();
            }
        }
        if (snake.level == 20) {
            game.loaded.toast("Вы прошли этот уровень");
            game.setScreen(game.lobby);
        }

        game.batch.end();
        stage.draw();
    }


    @Override
    public void dispose() {
        super.dispose();
        mapGames.dispose();
    }
}
