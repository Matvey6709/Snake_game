package com.example.share2dlibgdx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.example.share2dlibgdx.ui.Joystick3;

import java.util.Locale;

import datamanager.ServerUpdate;

public class Game1_Screen implements Screen {

    final game game;
    //    OrthographicCamera camera;
    Camera camera;


    public Snake snake;
    Bread bread;
    Touch touch;

    float timeSet;
    ServerUpdate serverUpdate;
    Joystick3 joystick;
    Stage stage;
    boolean create = false;
    boolean t = false;
    int nulls = 0;
    DeterminantSize size;
    boolean f = false;
    PointUi pointUi;
    boolean wait;
    Texture grass;
    Texture cur;
    Viewport fitViewport;

    public Game1_Screen(final game gam, String namePlayer, String nameGame, boolean wait) {

        game = gam;
        this.wait = wait;

        camera = new OrthographicCamera();
//        camera.setToOrtho(true, 800, 480);
        pointUi = new PointUi();
//        game.fitViewport = new FitViewport(1280, 720, camera);
        cur = new Texture("pole3.png");
        joystick = new Joystick3(cur, cur);
        camera.position.set(400, 100, 0);
        fitViewport = new FitViewport(1280, 720, camera);
//        fitViewport.setScreenBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        stage = new Stage(fitViewport);
        stage.addActor(pointUi.FirstPointUi());//Поинт игрока 1
        stage.addActor(joystick);

        stage.addActor(pointUi.SecondPointUi());//Поинт игрока 2
        stage.addActor(pointUi.apples());
        stage.addActor(pointUi.apples2());
        stage.addActor(pointUi.WinPlay());
        stage.addActor(pointUi.endButton());
        stage.addActor(pointUi.Timer());
        pointUi.end.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                end();
                game.setScreen(new Lobby(game));
            }
        });

        Gdx.input.setInputProcessor(stage);


        size = new DeterminantSize();
        snake = new Snake(game.batch, joystick, size.getWidthGame(100), size.getHeightGame(100));
        snake.NamePlayer = namePlayer;
        snake.NameGame = nameGame;
        bread = new Bread(game.batch, size.getWidthGame(100) / 2);
        bread.spawn();
        touch = new Touch(snake, bread);
        serverUpdate = new ServerUpdate();
        serverUpdate.test(game.batch);
        grass = new Texture(Gdx.files.internal("grass.png"));


        game.loaded.requestData(snake.NameGame, snake.NamePlayer, snake);
        if (wait) {
            game.loaded.countPlayersGames(snake.NameGame);
            game.loaded.put(snake.NameGame, snake.NamePlayer, snake.vector2, snake.level + "", bread.x, bread.y);
            create = true;
        }
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

//        fitViewport.apply();


//        camera.position.set(400, 100, 0);
//        camera.update();
        fitViewport.apply();


//        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();

        try {
//               Сделать проверку на всякий случай подключились ли они(Можно поросить подвигать джостиком каждого игрока,
//               если все подключились, то данные джостика отоброзятся в FireBase => будут готовы, проверить можно будет путём провекри игроками чужих координат)
//               Цыфры 3... 2... 1... GO..
            if (!wait) {

                seconder(1);
                for (int x = 0; x < Gdx.graphics.getWidth() / grass.getWidth(); x++) {
                    for (int y = 0; y < Gdx.graphics.getHeight() / grass.getHeight(); y++) {
                        game.batch.draw(grass, grass.getWidth() * x, grass.getHeight() * y);
                    }
                }

                bread.render();
                snake.render(Gdx.graphics.getDeltaTime());
                if (timeSet > .50) {
                    timeSet = 0;
                    snake.PlayerClient();
                    game.loaded.put(snake.NameGame, snake.NamePlayer, snake.vector2, snake.level + "", bread.x, bread.y);
                    serverUpdate.render(game.loaded.requestData2(), snake);
                }
                timeSet += Gdx.graphics.getDeltaTime();
                serverUpdate.render2();
                init();
                pointUi.render(game.batch, snake.level, serverUpdate.getLevel(), snake.NamePlayer, serverUpdate.getNamePlayer());

                for (int i = 0; i < serverUpdate.getLevel(); i++) {
                    boolean g = touch.touchPlays(snake, serverUpdate.cells.get(i).x, serverUpdate.cells.get(i).y);
                    if (g) {
                        if (create) {
                            for (int j = 0; j < snake.level; j++) {
                                snake.cells.get(j).x = 1200;
                                snake.cells.get(j).y = 0;
                            }
//                            share.cells.get(0).x = 1200;(Это изменено)
                        } else {
                            for (int j = 0; j < snake.level; j++) {
                                snake.cells.get(j).x = 0;
                                snake.cells.get(j).y = 0;
                            }
//                            share.cells.get(0).x = 0;(Это изменено)
                        }
//                        share.cells.get(0).y = 0;
                        break;
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
                }


                if (snake.level >= 8) {
                    pointUi.WhoWin(snake.NamePlayer);
                    end = true;
                    pointUi.end.setSize(400, 120);
                    pointUi.end.setPosition(640, Gdx.graphics.getHeight() / 2 - 350);
                } else if (serverUpdate.getLevel() >= 9 && serverUpdate.getNamePlayer() != null) {
                    pointUi.WhoWin(serverUpdate.getNamePlayer());
                    end = true;
                    pointUi.end.setSize(200, 120);
                    pointUi.end.setPosition(360 - 360 / snake.NamePlayer.length(), 240);

                }
            } else {
                if (game.loaded.countPlayersGames2() == 2) {
                    wait = false;
                }
            }


        } catch (Exception e) {
        }
//            if (startPlay) {
//                try {
////               Сделать проверку на всякий случай подключились ли они(Можно поросить подвигать джостиком каждого игрока,
////               если все подключились, то данные джостика отоброзятся в FireBase => будут готовы, проверить можно будет путём провекри игроками чужих координат)
//
////               Цыфры 3... 2... 1... GO..
//                    seconder(1);
//                    for (int x = 0; x < Gdx.graphics.getWidth() / grass.getWidth(); x++) {
//                        for (int y = 0; y < Gdx.graphics.getHeight()/ grass.getHeight(); y++) {
//                            game.batch.draw(grass, grass.getWidth() * x, grass.getHeight() * y);
//                        }
//                    }
//
//                    bread.render();
//                    snake.render(Gdx.graphics.getDeltaTime());
//                    if (timeSet > .50) {
//                        timeSet = 0;
//                        snake.PlayerClient();
//                        game.loaded.put(snake.NameGame, snake.NamePlayer, snake.vector2, snake.level + "", bread.x, bread.y);
//                        serverUpdate.render(game.loaded.requestData(snake.NameGame, snake.NamePlayer, snake), snake);
//
//                    }
//                    timeSet += Gdx.graphics.getDeltaTime();
//                    serverUpdate.render2();
//                    init();
////                t = false;
//                    pointUi.render(game.batch, snake.level, serverUpdate.getLevel(), snake.NamePlayer, serverUpdate.getNamePlayer());
//
//                    for (int i = 0; i < serverUpdate.getLevel(); i++) {
////                    boolean g = touch.touchPlays(snake, serverUpdate.cells.get(i).x, serverUpdate.cells.get(i).y);
////                    if (g) {
////                        if (create) {
////                            for (int j = 0; j < snake.level; j++) {
////                                snake.cells.get(j).x = 1200;
////                                snake.cells.get(j).y = 0;
////                            }
//////                            share.cells.get(0).x = 1200;(Это изменено)
////                        } else {
////                            for (int j = 0; j < snake.level; j++) {
////                                snake.cells.get(j).x = 0;
////                                snake.cells.get(j).y = 0;
////                            }
//////                            share.cells.get(0).x = 0;(Это изменено)
////                        }
//////                        share.cells.get(0).y = 0;
////                        break;
////                    }
//                        switch (touch.touchScreen()){
//
//                            case 1:
//                                snake.cells.get(0).x = 0;
//                                break;
//                            case 5:
//                                snake.cells.get(0).y = 720;
//                                break;
//                            case 2:
//                                snake.cells.get(0).y = 0;
//                                break;
//                            case 3:
//                                snake.cells.get(0).x = 0;
//                                snake.cells.get(0).y = 0;
//                                break;
//                            case 4:
//                                snake.cells.get(0).x = 1280;
//                                break;
//
//                            case 6:
//                                snake.cells.get(0).x = 1280;
//                                snake.cells.get(0).y = 1280;
//                                break;
//                        }
//                    }
//
//                    if (snake.level >= 8) {
//                        pointUi.WhoWin(snake.NamePlayer);
//                        end = true;
//                        pointUi.end.setSize(400, 120);
//                        pointUi.end.setPosition(640, Gdx.graphics.getHeight() / 2 - 350);
//                    }
//
//                    else if (serverUpdate.getLevel() >= 9 && serverUpdate.getNamePlayer() != null) {
//                        pointUi.WhoWin(serverUpdate.getNamePlayer());
//                        end = true;
//                        pointUi.end.setSize(400, 120);
//                        pointUi.end.setPosition(640, Gdx.graphics.getHeight() / 2 - 350);
//                    }
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            } else if (game.loaded.checkStartPlay() != null && !t) {
//                snake.NameGame = game.loaded.checkStartPlay();
//
//                game.loaded.create(snake.NameGame, snake);
//
//                startPlay = true;
//                System.out.println("Идёт поиск подходящей комнаты");
//            }
//            else if (game.loaded.checkStartPlay() == null && !t) {
//                nulls++;
//                System.out.println("Ждёт");
//            }
//            if (nulls == 200) {
//                if (!t) {
//                    snake.PlayerClient();
//                    snake.NameGame = new Random().nextInt(900000)+100000+""+System.currentTimeMillis();
//                    snake.cells.get(0).x = 1200;
//                    snake.cells.get(0).y = 0;
//                    create = true;
//                    snake.youFirst = true;
//                    game.loaded.create(snake.NameGame, snake);
//                    game.loaded.put(snake.NameGame, snake.NamePlayer, snake.vector2, snake.level + "", bread.x, bread.y);
//                    t = true;
//                    System.out.println("Комната не найдена, создаём собственную комнату");
//                }
//                if (game.loaded.countPlayersGames(snake.NameGame) == 2 && !startPlay) {
//                    startPlay = true;
//                    System.out.println("Игрок подключился к вашей комнате");
//                }
//            }

        game.batch.end();
        stage.draw();
        stage.act(Gdx.graphics.getDeltaTime());

    }

    @Override
    public void resize(int width, int height) {
        fitViewport.update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

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
        game.loaded.dispose();

    }


    boolean end = false;

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
        }
    }

    int time;

    public void seconder(float delta) {
        time += delta;
        int hours = time / 3600;
        int minutes = (time % 3600) / 60;
        int secs = time % 60;
//        player.setStr(minutes*5+".0 0.0");
        String time2 = String.format(Locale.getDefault(), "%d:%02d:%02d", hours, minutes, secs);
        pointUi.timer.setText(time2);
    }

}