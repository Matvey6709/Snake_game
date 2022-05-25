package com.example.share2dlibgdx;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import datamanager.Player;
import datamanager.ServerUpdate;

public class Bluetooth_Game_Screen extends Сlassic_Game_Screen {
    ServerUpdate update;
    AnimationBluetooth animationBluetooth;
    PointUi pointUi;

    String gg = "";
    float timeB, stopGames;
    int startGame = 0, keyMy;
    boolean end = false, create;

    public Bluetooth_Game_Screen(final game game, boolean create) {
        super(game, true);
        o = true;
        this.create = create;
        update = new ServerUpdate();
        update.testBl(game.batch, false, game);
        animationBluetooth = new AnimationBluetooth(game.batch);
        keyMy = MathUtils.random(1, 60);

    }


    @Override
    public void render(float delta) {
        super.render(delta);
        if (startGame > 3) {
        } else {
            animationBluetooth.render();
            stopGames += delta;
            if (stopGames > 15) {
                SoundPlayer.stopMusic(Asset.MEMO_SOUND);
                game.setScreen(game.lobby);
                game.bluetoothLoaded.restartGame();
                game.loaded.toast("У противника слабый bluetooth, вы подключились к тому, кто ещё не создал сервер или не кто не хочет подключаться к вам");
                game.bluetoothLoaded.stopT();
            }
        }

        timeB += delta;
        if (timeB > .25 && game.bluetoothLoaded.getS()) {
            game.bluetoothLoaded.send(snake.cells.get(0).x + " " + snake.cells.get(0).y + " " + snake.level + " " + bread.x + " " + bread.y + " " + keyMy + " " + snake.NamePlayer);
            timeB = 0;
        }

        if (!gg.equals(game.bluetoothLoaded.getMs())) {
            stopGames = 0;
            gg = game.bluetoothLoaded.getMs();

            startGame++;

            if (gg.indexOf("-stop-") <= 0) {
                Player player = new Player();
                player.setCords(gg);
                player.setLevel("3");
                player.setName("");
                player.setAppleX(0);
                player.setAppleY(0);
                update.renderBl(player, snake);
                pointUi.render(game.batch, snake.level, update.getLevel(), snake.NamePlayer, update.getNamePlayer2());
            }
            switch (touch.touchScreen()) {
                case 1:
                    snake.cells.get(0).x = 0;
                    break;
                case 5:
                    snake.cells.get(0).y = 716;
                    break;
                case 2:
                    snake.cells.get(0).y = 0;
                    break;
                case 3:
                    snake.cells.get(0).x = 0;
                    snake.cells.get(0).y = 0;
                    break;
                case 4:
                    snake.cells.get(0).x = 1254;
                    break;

                case 6:
                    snake.cells.get(0).x = 1280;
                    snake.cells.get(0).y = 1280;
                    break;
            }
            for (int i = 0; i < update.getLevel2(); i++) {
                boolean g = touch.touchPlays(snake, update.cells.get(i).x, update.cells.get(i).y);
                if (g) {
                    if (create) {
                        for (int j = 0; j < snake.cells.size(); j++) {
                            snake.cells.get(j).x = 99;
                            snake.cells.get(j).y = 363;
                        }
                    } else {
                        for (int j = 0; j < snake.cells.size(); j++) {
                            snake.cells.get(j).x = 1221;
                            snake.cells.get(j).y = 363;
                        }
                    }
                    break;
                }
            }
            if (snake.level >= 8) {
                pointUi.WhoWin(snake.NamePlayer);
                end = true;
            } else if (update.getLevel() >= 8 && update.getNamePlayer() != null) {
                pointUi.WhoWin(update.getNamePlayer2());
                end = true;

            }

            if (gg.equals("-stop-" + update.getKeyEnemy())) {
                SoundPlayer.stopMusic(Asset.MEMO_SOUND);
                game.loaded.toast("Ваш противник вышел из игры");
                game.bluetoothLoaded.stopT();
                game.bluetoothLoaded.restartGame();
            }
        } else if (startGame > 3) {
            stopGames += delta;
            if (stopGames > 10) {
                SoundPlayer.stopMusic(Asset.MEMO_SOUND);
                game.bluetoothLoaded.stopT();
                game.bluetoothLoaded.restartGame();
            }
        }
        if (startGame > 3) {
            game.batch.begin();
            update.render2();
            game.batch.end();
        }
        if (!game.bluetoothLoaded.isEnabled()) {
            game.loaded.toast("Вы выключили bluetooth");
            game.bluetoothLoaded.stopT();
            game.bluetoothLoaded.restartGame();
        }

        if (end) {
            snake.transfer.tr = -1;
        }
    }

    @Override
    public void show() {
        o = true;
        super.show();
        back.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                SoundPlayer.stopMusic(Asset.MEMO_SOUND);
                game.bluetoothLoaded.send("-stop-" + keyMy);
                game.bluetoothLoaded.stopT();
                game.bluetoothLoaded.restartGame();
            }
        });
        snake.cells.clear();
        snake.increase(3);
        snake.level = 1;
        snake.NamePlayer = game.lobby.namePlayer;
        if (create) {
            for (int j = 0; j < snake.cells.size(); j++) {
                snake.cells.get(j).x = 99;
                snake.cells.get(j).y = 363;
            }
        } else {
            for (int j = 0; j < snake.cells.size(); j++) {
                snake.cells.get(j).x = 1221;
                snake.cells.get(j).y = 363;
            }
        }
        pointUi = new PointUi();

        stage.addActor(pointUi.FirstPointUi());
        stage.addActor(pointUi.SecondPointUi());
        stage.addActor(pointUi.apples());
        stage.addActor(pointUi.apples2());
        stage.addActor(pointUi.WinPlay());
        stage.addActor(v);
        snake.transfer.tr = 0;

    }

    @Override
    public void dispose() {
        animationBluetooth.dispose();
        update.dispose();
    }
}
