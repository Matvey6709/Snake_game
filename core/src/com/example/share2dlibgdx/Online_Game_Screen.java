package com.example.share2dlibgdx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.example.share2dlibgdx.ui.Joystick3;
import com.example.share2dlibgdx.ui.JoystickArrows;

import datamanager.ServerUpdate;
import handler.LabelHandler;

public class Online_Game_Screen implements Screen {
    final game game;

    public Snake snake;
    Bread bread;
    Touch touch;
    ServerUpdate serverUpdate;
    DeterminantSize size;

    boolean f = false, f2 = false, wait, check, create = false, end = false;
    float timeSet, stateTime;


    private static final int FRAME_COLS = 6, FRAME_ROWS = 5;
    Animation<TextureRegion> walkAnimation;
    Texture grass, cur, walkSheet, bacG;

    Camera camera;
    PointUi pointUi;
    Joystick3 joystick;
    Stage stage;
    Viewport fitViewport;
    Label label;
    JoystickArrows joystickArrows;
    Skin skin;


    public Online_Game_Screen(final game gam, String namePlayer, final String namePlayerUn, final String nameGame, boolean wait, boolean check) {
        game = gam;
        this.wait = wait;
        this.check = check;

        camera = new OrthographicCamera();
        pointUi = new PointUi();
        cur = new Texture("pole3.png");
        joystick = new Joystick3(cur, cur);
        camera.position.set(400, 100, 0);
        fitViewport = new FitViewport(1280, 720, camera);

        stage = new Stage(fitViewport);
        label = LabelHandler.INSTANCE.createLabel("Поиск соперника", 50, Color.WHITE);
        label.setPosition(160, 50);
        stage.addActor(pointUi.FirstPointUi());
        stage.addActor(joystick);
        stage.addActor(pointUi.SecondPointUi());
        stage.addActor(pointUi.apples());
        stage.addActor(pointUi.apples2());
        stage.addActor(pointUi.WinPlay());
        stage.addActor(pointUi.endButton());
        stage.addActor(pointUi.Timer());
        stage.clear();
        stage.addActor(label);


        Gdx.input.setInputProcessor(stage);
        size = new DeterminantSize();
        snake = new Snake(game.batch, joystick, size.getWidthGame(33), size.getHeightGame(33), game.lobby.m1.sprite, game.lobby.m2.sprite, game.lobby.m3.sprite);

        snake.NamePlayer = namePlayer;
        snake.NameGame = nameGame;
        snake.speed = .40f;
        bread = new Bread(game.batch, 33);
        bread.spawn();
        touch = new Touch(snake, bread);
        serverUpdate = new ServerUpdate();

        grass = new Texture(Gdx.files.internal("grass.png"));
        game.loaded.requestData(snake.NameGame, snake.NamePlayer, snake);
        game.loaded.isOnline2(nameGame, namePlayer);
        if (wait) {
            game.loaded.countPlayersGames(snake.NameGame);
            game.loaded.put(snake.NameGame, snake.vector2);
            game.loaded.putMeal(snake.NameGame, snake.NamePlayer, snake.level + "", bread.x, bread.y);
            create = true;
            walkSheet = new Texture(Gdx.files.internal("animation_sheet.png"));
            TextureRegion[][] tmp = TextureRegion.split(walkSheet,
                    walkSheet.getWidth() / FRAME_COLS,
                    walkSheet.getHeight() / FRAME_ROWS);
            TextureRegion[] walkFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
            int index = 0;
            for (int i = 0; i < FRAME_ROWS; i++) {
                for (int j = 0; j < FRAME_COLS; j++) {
                    walkFrames[index++] = tmp[i][j];
                }
            }
            walkAnimation = new Animation<TextureRegion>(0.025f, walkFrames);
            walkAnimation.setPlayMode(Animation.PlayMode.LOOP);
            stateTime = 0f;
        }
        serverUpdate.test(game.batch, create);
        joystickArrows = new JoystickArrows(100, 100, 10);
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        bacG = new Texture("bacT.png");
        SoundPlayer.playMusic(Asset.MEMO_SOUND, true);
    }


    @Override
    public void show() {

    }

    boolean y = false;
    float stopSpeed = 5;

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        if (!game.loaded.isOnline() && !y) {
            y = true;
            game.loaded.toast("Нет подключения к интрнету");
            game.bluetoothLoaded.restartGame();
            try {
                game.loaded.dispose();
                game.loaded.dispose2();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (game.loaded.isOnline() && y) {
            y = false;
        }
        stateTime += Gdx.graphics.getDeltaTime();

        fitViewport.apply();
        game.batch.begin();
        try {
//               todo Цыфры 3... 2... 1... GO..
            if (!check) {
                if (!wait) {
                    if (!f) {
                        f = true;
                        stage.clear();
                        stage.addActor(pointUi.FirstPointUi());//Поинт игрока 1
                        stage.addActor(joystick);
                        stage.addActor(pointUi.SecondPointUi());//Поинт игрока 2
                        stage.addActor(pointUi.apples());
                        stage.addActor(pointUi.apples2());
                        stage.addActor(pointUi.WinPlay());
                        stage.addActor(pointUi.endButton());
                        pointUi.end.addListener(new ChangeListener() {
                            @Override
                            public void changed(ChangeEvent event, Actor actor) {
                                game.loaded.delete(snake.NameGame);
                                end();
                                SoundPlayer.stopMusic(Asset.MEMO_SOUND);
                                game.bluetoothLoaded.restartGame();
                            }
                        });
                    }
                    if (!game.loaded.isExistsGame2()) {
                        try {
                            game.loaded.delete(snake.NameGame);
                            game.loaded.dispose();
                            game.loaded.dispose2();
                        } catch (Exception e) {
                        }
                        game.loaded.toast("Ваш противник вышел из игры");
                        SoundPlayer.stopMusic(Asset.MEMO_SOUND);
                        game.bluetoothLoaded.restartGame();
                    }
                    game.batch.draw(bacG, 0, 0, 1280, 720);
                    bread.render();
                    snake.render(Gdx.graphics.getDeltaTime());
                    if (timeSet > .40) {
                        timeSet = 0;
                        if (!f2) {
                            f2 = true;
                            game.loaded.isExistsGame(snake.NameGame, serverUpdate.getNamePlayer());
                            game.loaded.put(snake.NameGame, snake.vector2);
                            game.loaded.putMeal(snake.NameGame, snake.NamePlayer, snake.level + "", bread.x, bread.y);
                        }
                        snake.PlayerClient();
                        game.loaded.put(snake.NameGame, snake.vector2);
                        serverUpdate.render(game.loaded.requestData2(), snake);

                    }
                    timeSet += Gdx.graphics.getDeltaTime();
                    serverUpdate.render3();
                    init();
                    pointUi.render(game.batch, snake.level, serverUpdate.getLevel(), snake.NamePlayer, serverUpdate.getNamePlayer());

                    for (int i = 0; i < serverUpdate.getLevel2(); i++) {
                        boolean g = touch.touchPlays(snake, serverUpdate.cells.get(i).x, serverUpdate.cells.get(i).y);
                        if (g) {
                            if (create) {
                                for (int j = 0; j < snake.cells.size(); j++) {
                                    snake.cells.get(j).x = 1200;
                                    snake.cells.get(j).y = 360;
                                }
//                            share.cells.get(0).x = 1200;(Это изменено)
                            } else {
                                for (int j = 0; j < snake.cells.size(); j++) {
                                    snake.cells.get(j).x = 80;
                                    snake.cells.get(j).y = 360;
                                }
//                            share.cells.get(0).x = 0;(Это изменено)
                            }
//                        share.cells.get(0).y = 0;
                            break;
                        }
                    }

                    switch (touch.touchScreen()) {
                        case 1:
                            snake.cells.get(0).x = 0;
                            break;
                        case 5:
                            snake.cells.get(0).y = 720;
                            break;
                        case 2:
                            snake.cells.get(0).y = 0;
                            break;
                        case 3:
                            snake.cells.get(0).x = 0;
                            snake.cells.get(0).y = 0;
                            break;
                        case 4:
                            snake.cells.get(0).x = 1280;
                            break;

                        case 6:
                            snake.cells.get(0).x = 1280;
                            snake.cells.get(0).y = 1280;
                            break;
                    }

                    if (snake.level >= 8) {
                        pointUi.WhoWin(snake.NamePlayer);
                        end = true;
                        pointUi.end.setSize(250, 100);
                        pointUi.end.setPosition(360, Gdx.graphics.getHeight() / 2 - 350);
                    } else if (serverUpdate.getLevel() >= 8 && serverUpdate.getNamePlayer() != null) {
                        pointUi.WhoWin(serverUpdate.getNamePlayer());
                        end = true;
                        pointUi.end.setSize(250, 100);
                        pointUi.end.setPosition(360 - 360 / snake.NamePlayer.length(), 240);

                    }
                } else {
                    TextureRegion currentFrame = walkAnimation.getKeyFrame(stateTime, true);
                    game.batch.draw(currentFrame, 50, 50);
                    if (game.loaded.countPlayersGames2() == 2) {
                        wait = false;
                    }
                }
            } else if (check) {
                if (!wait) {
                    if (!f) {
                        f = true;
                        stage.clear();
                        stage.addActor(pointUi.FirstPointUi());//Поинт игрока 1
//                        stage.addActor(joystick);
                        stage.addActor(pointUi.SecondPointUi());//Поинт игрока 2
                        stage.addActor(pointUi.apples());
                        stage.addActor(pointUi.apples2());
                        stage.addActor(pointUi.WinPlay());
                        stage.addActor(pointUi.endButton());
//                        stage.addActor(speedButton);

                        pointUi.end.addListener(new ChangeListener() {
                            @Override
                            public void changed(ChangeEvent event, Actor actor) {
                                game.loaded.delete(snake.NameGame);
                                game.loaded.setExistsGame(true);
                                end();
                                SoundPlayer.stopMusic(Asset.MEMO_SOUND);
                                game.bluetoothLoaded.restartGame();
                            }
                        });
                        stage.addActor(joystickArrows.joystick());
                        joystickArrows.UP.addListener(new ChangeListener() {
                            @Override
                            public void changed(ChangeEvent event, Actor actor) {
                                if (!end) {
                                    if (snake.transfer.tr != 3) {
                                        snake.transfer.tr = 0;
                                    }
                                }

                            }
                        });
                        joystickArrows.DOWN.addListener(new ChangeListener() {
                            @Override
                            public void changed(ChangeEvent event, Actor actor) {
                                if (!end) {
                                    if (snake.transfer.tr != 0) {
                                        snake.transfer.tr = 3;
                                    }
                                }
                            }
                        });
                        joystickArrows.LEFT.addListener(new ChangeListener() {
                            @Override
                            public void changed(ChangeEvent event, Actor actor) {
                                if (!end) {
                                    if (snake.transfer.tr != 1) {
                                        snake.transfer.tr = 2;
                                    }
                                }
                            }
                        });
                        joystickArrows.RIGHT.addListener(new ChangeListener() {
                            @Override
                            public void changed(ChangeEvent event, Actor actor) {
                                if (!end) {
                                    if (snake.transfer.tr != 2) {
                                        snake.transfer.tr = 1;
                                    }
                                }
                            }
                        });
//                        for (int i = 1; i < snake.cells.size(); i++) {
//                            boolean g = touch.touchPlays(snake, snake.cells.get(i).x, snake.cells.get(i).y);
//                            if (g) {
//                                for (int j = 0; j < snake.cells.size(); j++) {
//                                    snake.cells.get(j).x = 660;
//                                    snake.cells.get(j).y = 363;
//                                }
//                                break;
//                            }
//                        }
                        if (!create) {
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
//                        cells = new Sprite[Gdx.graphics.getWidth() / 33][Gdx.graphics.getHeight() / 33];
//                        for (int rowGrass = 0; rowGrass < cells.length; rowGrass++) {
//                            for (int colGrass = 0; colGrass < cells[rowGrass].length; colGrass++) {
//                                Sprite cell = Asset.instance().getSprite(randomGrass(rowGrass, colGrass));
//                                cells[rowGrass][colGrass] = cell;
//                            }
//                        }
                    }

                    game.batch.draw(bacG, 0, 0, 1280, 720);

                    bread.render();
                    snake.render2(Gdx.graphics.getDeltaTime());

                    if (!game.loaded.isExistsGame2()) {
                        try {
                            game.loaded.delete(snake.NameGame);
                            game.loaded.dispose();
                            game.loaded.dispose2();
                        } catch (Exception e) {
                        }
                        game.loaded.toast("Ваш противник вышел из игры");
                        SoundPlayer.stopMusic(Asset.MEMO_SOUND);
                        game.bluetoothLoaded.restartGame();
                    }

                    if (timeSet > snake.speed) {
                        timeSet = 0;
                        if (!f2) {
                            f2 = true;
                            game.loaded.isExistsGame(snake.NameGame, serverUpdate.getNamePlayer());
                            game.loaded.put(snake.NameGame, snake.vector2);
                            game.loaded.putMeal(snake.NameGame, snake.NamePlayer, snake.level + "", bread.x, bread.y);
                        }
                        snake.PlayerClient();
                        game.loaded.put(snake.NameGame, snake.vector2);
                        serverUpdate.render(game.loaded.requestData2(), snake);
                        pointUi.render(game.batch, snake.level, serverUpdate.getLevel(), snake.NamePlayer, serverUpdate.getNamePlayer());

                    }

                    timeSet += Gdx.graphics.getDeltaTime();
                    serverUpdate.render2();
                    init();
                    for (int i = 0; i < serverUpdate.getLevel2(); i++) {
                        boolean g = touch.touchPlays(snake, serverUpdate.cells.get(i).x, serverUpdate.cells.get(i).y);
                        if (g) {
                            if (create) {
                                for (int j = 0; j < snake.cells.size(); j++) {
                                    snake.cells.get(j).x = 1221;
                                    snake.cells.get(j).y = 363;
                                }
                            } else {
                                for (int j = 0; j < snake.cells.size(); j++) {
                                    snake.cells.get(j).x = 99;
                                    snake.cells.get(j).y = 363;
                                }
                            }
                            break;
                        }
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

                    if (snake.level >= 8) {
                        pointUi.WhoWin(snake.NamePlayer);
                        end = true;
                        pointUi.end.setSize(250, 100);
                        pointUi.end.setPosition(360 - 360 / snake.NamePlayer.length(), 240);
                    } else if (serverUpdate.getLevel() >= 8 && serverUpdate.getNamePlayer() != null) {
                        pointUi.WhoWin(serverUpdate.getNamePlayer());
                        end = true;
                        pointUi.end.setSize(250, 100);
                        pointUi.end.setPosition(360 - 360 / snake.NamePlayer.length(), 240);

                    }
                } else {
                    TextureRegion currentFrame = walkAnimation.getKeyFrame(stateTime, true);
                    game.batch.draw(currentFrame, 50, 50);
                    if (game.loaded.countPlayersGames2() == 2) {
                        wait = false;
                    }
                }

            }
            if (end) {
                snake.transfer.tr = -1;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        game.batch.end();
        stage.draw();
        stage.act(stateTime);
        if (!wait) {
            game.batch.begin();
            bread.render();
            game.batch.end();
        }
    }

    @Override
    public void resize(int width, int height) {
        fitViewport.update(width, height, true);
    }

    @Override
    public void pause() {
        try {
            game.loaded.delete(snake.NameGame);
            game.loaded.dispose();
            game.loaded.dispose2();
        } catch (Exception e) {
        }

    }

    @Override
    public void resume() {
        game.loaded.toast("Вы вышли из игры, поэтому игра закончилась");
        SoundPlayer.stopMusic(Asset.MEMO_SOUND);
        game.bluetoothLoaded.restartGame();

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        snake.dispose();
        game.batch.dispose();
        serverUpdate.dispose();
        stage.dispose();
        bread.dispose();
        joystick.dispose();
        grass.dispose();
        cur.dispose();
        bacG.dispose();
        skin.dispose();
    }

    public void end() {
        game.loaded.dispose();
        game.loaded.dispose2();
        end = true;
//        this.dispose();
    }

    public void init() {
        if (touch.touchBred()) {
            bread.spawn();
            snake.addLevel();
            SoundPlayer.playSound(Asset.EAT_FOOD_SOUND, false);
            game.loaded.put(snake.NameGame, snake.vector2);
            game.loaded.putMeal(snake.NameGame, snake.NamePlayer, snake.level + "", bread.x, bread.y);
        }
    }

    private String randomGrass(int row, int col) {
        if (col % 2 == 0) {
            if (row % 2 != 0) return "grass_01";
            if (row % 2 == 0) return "grass_02";
        } else if (col % 2 != 0) {
            if (row % 2 != 0) return "grass_02";
            if (row % 2 == 0) return "grass_01";
        }
        return "grass_02";
    }
}