package com.example.share2dlibgdx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class Levels_Game_Screen extends Сlassic_Game_Screen {

    MapGames mapGames;
    Touch touch;
    Skin skin;
    int level;

    public Levels_Game_Screen(game game, int level) {
        super(game, false);
        this.level = level;
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        mapGames = new MapGames(1, game);
        switch (level) {
            case 0:
                for (int i = 0; i < 720 / 33 * 1.5; i++) {
                    if (i >= (720 / 33) / 2 - 5 && i <= (720 / 33) / 2 + 5) {
                        continue;
                    }
                    mapGames.boxes.add(new Cell(627, 33 * i, 33, 33));
                }
                break;
            case 1:
                for (int i = 0; i < 720 / 33; i++) {
                    if (i >= (720 / 33) / 2 - 3 && i <= (720 / 33) / 2 + 3) {
                        continue;
                    }
                    mapGames.boxes.add(new Cell(627, 33 * i, 33, 33));
                }
                for (int i = 0; i < 1280 / 33 * 1.5; i++) {
                    if (i >= (1280 / 33) / 2 - 3 && i <= (1280 / 33) / 2 + 3) {
                        continue;
                    }
                    mapGames.boxes.add(new Cell(33 * i, 363, 33, 33));
                }
                spawnX = 99;
                spawnY = 99;
                for (int j = 0; j < snake.cells.size(); j++) {
                    snake.cells.get(j).x = spawnX;//660
                    snake.cells.get(j).y = spawnY;//363
                }
                break;
            case 2:
                game3 = true;
                break;
            case 3:
                for (int i = 0; i < 40; i++) {
                    if (i >= 10 && i <= 15) {
                        continue;
                    }
                    mapGames.boxes.add(new Cell(660, 33 * i, 33, 33));
                }
                break;
            case 4:
                for (int i = 0; i < 25; i++) {
                    if (i >= 10 && i <= 15) {
                        continue;
                    }
                    mapGames.boxes.add(new Cell(660, 33 * i, 33, 33));
                }
                break;
        }

        touch = new Touch(snake);

    }

    @Override
    public void show() {
        super.show();
        game.lobby.m2.sprite.setAlpha(1);
        game.loaded.dialog("Задача", "Вы должны набрать 20 очков", "Хорошо");
        snake.transfer.tr = -1;
        mapGames.boxes.clear();
        game3 = false;

        switch (level) {
            case 0:
                for (int i = 0; i < 720 / 33 * 1.5; i++) {
                    if (i >= (720 / 33) / 2 - 5 && i <= (720 / 33) / 2 + 5) {
                        continue;
                    }
                    mapGames.boxes.add(new Cell(627, 33 * i, 33, 33));
                }
                break;
            case 1:
                for (int i = 0; i < 720 / 33; i++) {
                    if (i >= (720 / 33) / 2 - 3 && i <= (720 / 33) / 2 + 3) {
                        continue;
                    }
                    mapGames.boxes.add(new Cell(627, 33 * i, 33, 33));
                }
                for (int i = 0; i < 1280 / 33 * 1.5; i++) {
                    if (i >= (1280 / 33) / 2 - 3 && i <= (1280 / 33) / 2 + 3) {
                        continue;
                    }
                    mapGames.boxes.add(new Cell(33 * i, 363, 33, 33));
                }
                spawnX = 99;
                spawnY = 99;
                for (int j = 0; j < snake.cells.size(); j++) {
                    snake.cells.get(j).x = spawnX;//660
                    snake.cells.get(j).y = spawnY;//363
                }
                break;
            case 2:
                game3 = true;
                break;
        }
    }

    boolean game3;

    @Override
    public void render(float delta) {
        super.render(delta);
        game.batch.begin();
        mapGames.mapRendering();

        if (game3) {
            game.lobby.m2.sprite.setAlpha(0);
        }
        for (int i = 0; i < mapGames.boxes.size() - 1; i++) {
            if (touch.touchBox(snake, mapGames.boxes.get(i).x, mapGames.boxes.get(i).y)) {
                gameOver();
                break;
            }
            if (bread.x == mapGames.boxes.get(i).x && bread.y == mapGames.boxes.get(i).y) {
                bread.spawn2();
                break;
            }
        }

        if (snake.level == 21) {
            game.loaded.toast("Вы прошли этот уровень");
            game.setScreen(game.lobby);
        }

        game.batch.end();
        stage.draw();
        game.batch.begin();
        bread.render2();
        game.batch.end();
    }


    @Override
    public void dispose() {
        super.dispose();
        mapGames.dispose();
        skin.dispose();
    }
}
