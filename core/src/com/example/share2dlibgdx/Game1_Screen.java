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

import java.util.Random;

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
    //    Joystick3 joystick;
//    SpriteBatch batch;
    boolean startPlay = false;
    boolean t = false;
    int nulls = 0;
    DeterminantSize size;
    boolean f = false;
    PointUi pointUi;
    boolean create = false;
    Texture grass;

    public Game1_Screen(final game gam, String namePlayer) {
        game = gam;


        camera = new OrthographicCamera();
//        camera.setToOrtho(true, 800, 480);
        pointUi = new PointUi();
        game.fitViewport = new FitViewport(1280, 720, camera);
//        game.fitViewport = new StretchViewport(1280, 720, camera);
        game.stage = new Stage();
        game.stage.addActor(game.joystick);
        game.stage.addActor(pointUi.FirstPointUi());//Поинт игрока 1
        game.stage.addActor(pointUi.SecondPointUi());//Поинт игрока 2
        game.stage.addActor(pointUi.apples());
        game.stage.addActor(pointUi.apples2());
        game.stage.addActor(pointUi.WinPlay());
        game.stage.addActor(pointUi.endButton());
        pointUi.end.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new Lobby(game));
            }
        });

        Gdx.input.setInputProcessor(game.stage);

        game.fitViewport.apply();
//        System.out.println(camera.viewportWidth);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);

        size = new DeterminantSize();
        snake = new Snake(game.batch, game.joystick, size.getWidthGame(100), size.getHeightGame(100));
        snake.NamePlayer = namePlayer;
        bread = new Bread(game.batch, size.getWidthGame(100));
        bread.spawn();
        touch = new Touch(snake, bread);
        serverUpdate = new ServerUpdate();
        serverUpdate.test(game.batch);
        grass = new Texture(Gdx.files.internal("grass.png"));
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
//        camera.rotate(delta*10);
        Gdx.gl.glClearColor(0, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.update();

        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();


        if (startPlay) {
            try {
//               Сделать проверку на всякий случай подключились ли они(Можно поросить подвигать джостиком каждого игрока,
//               если все подключились, то данные джостика отоброзятся в FireBase => будут готовы, проверить можно будет путём провекри игроками чужих координат)

//               Цыфры 3... 2... 1... GO..

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
                    game.loaded.put(snake.NameGame, snake.NamePlayer, snake.vector2, snake.level + "");
                    serverUpdate.render(game.loaded.requestData(snake.NameGame, snake.NamePlayer, snake), snake);

                }
                timeSet += Gdx.graphics.getDeltaTime();
                serverUpdate.render2();
                init();
//                t = false;
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
                    switch (touch.touchScreen()){

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
                }
                else if (serverUpdate.getLevel() >= 9 && serverUpdate.getNamePlayer() != null) {
                    pointUi.WhoWin(serverUpdate.getNamePlayer());
                    end = true;
                    pointUi.end.setSize(400, 120);
                    pointUi.end.setPosition(640, Gdx.graphics.getHeight() / 2 - 350);
                }


            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (game.loaded.checkStartPlay() != null && !t) {
            snake.NameGame = game.loaded.checkStartPlay();
            startPlay = true;
            System.out.println("Идёт поиск подходящей комнаты");
        }
        else if (game.loaded.checkStartPlay() == null) {
            nulls++;
            System.out.println("Ждёт");
        }
        if (nulls == 200) {
            if (!t) {
                snake.PlayerClient();
                snake.NameGame = new Random().nextInt(900000)+100000+""+System.currentTimeMillis();
                snake.cells.get(0).x = 1200;
                snake.cells.get(0).y = 0;
                create = true;
                game.loaded.put(snake.NameGame, snake.NamePlayer, snake.vector2, snake.level + "");
                t = true;
                System.out.println("Комната не найдена, создаём собственную комнату");
            }
            if (game.loaded.countPlayersGames(snake.NameGame) == 2 && !startPlay) {
                startPlay = true;
                System.out.println("Игрок подключился к вашей комнате");
            }
        }
        game.batch.end();
        camera.update();
        game.stage.act(Gdx.graphics.getDeltaTime());
        game.stage.draw();
    }

    @Override
    public void resize(int width, int height) {

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
        game.stage.dispose();
        bread.dispose();
        game.joystick.dispose();
        grass.dispose();

    }


    boolean end = false;

    public boolean end() {

        return end;
    }

    public void init() {
        if (touch.touchBred()) {
            bread.spawn();
            snake.addLevel();
        }
    }
}