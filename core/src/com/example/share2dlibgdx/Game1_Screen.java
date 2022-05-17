package com.example.share2dlibgdx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.example.share2dlibgdx.ui.Joystick3;
import com.example.share2dlibgdx.ui.JoystickArrows;

import java.util.Locale;

import datamanager.ServerUpdate;
import handler.LabelHandler;

public class Game1_Screen implements Screen {

    final game game;
    //    OrthographicCamera camera;
    Camera camera;


    public Snake snake;
    Bread bread;
    Touch touch;

    float timeSet;
    float timeSet2;
    ServerUpdate serverUpdate;
    Joystick3 joystick;
    Stage stage;
    boolean create = false;
    boolean t = false;
    int nulls = 0;
    DeterminantSize size;
    boolean f = false;
    boolean f2 = false;
    PointUi pointUi;
    boolean wait;
    Texture grass;
    Texture cur;
    Viewport fitViewport;
    Label label;

    private static final int FRAME_COLS = 6, FRAME_ROWS = 5;

    Animation<TextureRegion> walkAnimation; // Must declare frame type (TextureRegion)
    Texture walkSheet;

    float stateTime;
    boolean check;

    private Sprite[][] cells;
    JoystickArrows joystickArrows;

    public Game1_Screen(final game gam, String namePlayer, final String namePlayerUn, final String nameGame, boolean wait, boolean check) {
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
        label = LabelHandler.INSTANCE.createLabel("Looking for an opponent", 50, Color.WHITE);
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
        stage.addActor(label);//Поинт игрока 1


        Gdx.input.setInputProcessor(stage);
        size = new DeterminantSize();
        snake = new Snake(game.batch, joystick, size.getWidthGame(33), size.getHeightGame(33));
        snake.NamePlayer = namePlayer;
        snake.NameGame = nameGame;
        bread = new Bread(game.batch, size.getWidthGame(100) / 2);
        bread.spawn();
        touch = new Touch(snake, bread);
        serverUpdate = new ServerUpdate();

        grass = new Texture(Gdx.files.internal("grass.png"));
        game.loaded.requestData(snake.NameGame, snake.NamePlayer, snake);
        game.loaded.isOnline2(nameGame, namePlayer);
//        game.loaded.isExistsGame(nameGame);
        if (wait) {
            game.loaded.countPlayersGames(snake.NameGame);
            game.loaded.put(snake.NameGame, snake.NamePlayer, snake.vector2, snake.level + "", bread.x, bread.y);
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

    }


    @Override
    public void show() {

    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());


        stateTime += Gdx.graphics.getDeltaTime();

        fitViewport.apply();
        game.batch.begin();
        try {
//               Сделать проверку на всякий случай подключились ли они(Можно попросить подвигать джостиком каждого игрока,
//               если все подключились, то данные джостика отоброзятся в FireBase => будут готовы, проверить можно будет путём провекри игроками чужих координат)
//               Цыфры 3... 2... 1... GO..
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
                        stage.addActor(pointUi.Timer());
                        pointUi.end.addListener(new ChangeListener() {
                            @Override
                            public void changed(ChangeEvent event, Actor actor) {
                                game.loaded.delete(snake.NameGame);
                                end();
                                game.setScreen(game.lobby);
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
                        game.setScreen(game.lobby);
                    }
                    seconder(1);
                    for (int x = 0; x < Gdx.graphics.getWidth() / grass.getWidth(); x++) {
                        for (int y = 0; y < Gdx.graphics.getHeight() / grass.getHeight(); y++) {
                            game.batch.draw(grass, grass.getWidth() * x, grass.getHeight() * y);
                        }
                    }
                    bread.render();
                    snake.render(Gdx.graphics.getDeltaTime());
                    if (timeSet > .40) {
                        timeSet = 0;
                        snake.PlayerClient();
                        game.loaded.put(snake.NameGame, snake.NamePlayer, snake.vector2, snake.level + "", bread.x, bread.y);
                        serverUpdate.render(game.loaded.requestData2(), snake);
                        if (!f2) {
                            f2 = true;
                            game.loaded.isExistsGame(snake.NameGame, serverUpdate.getNamePlayer());
                        }
                    }

                    timeSet += Gdx.graphics.getDeltaTime();
                    serverUpdate.render3();
                    init();
                    pointUi.render(game.batch, snake.level, serverUpdate.getLevel(), snake.NamePlayer, serverUpdate.getNamePlayer());
//                System.out.println(snake.level);
//                System.out.println(serverUpdate.getLevel());

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
                        pointUi.end.setSize(400, 120);
                        pointUi.end.setPosition(640, Gdx.graphics.getHeight() / 2 - 350);
                    } else if (serverUpdate.getLevel() >= 8 && serverUpdate.getNamePlayer() != null) {
                        pointUi.WhoWin(serverUpdate.getNamePlayer());
                        end = true;
                        pointUi.end.setSize(200, 120);
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
                        stage.addActor(pointUi.Timer());
                        pointUi.end.addListener(new ChangeListener() {
                            @Override
                            public void changed(ChangeEvent event, Actor actor) {
                                game.loaded.delete(snake.NameGame);
                                game.loaded.setExistsGame(true);
                                end();
                                game.setScreen(game.lobby);
                            }
                        });
                        stage.addActor(joystickArrows.joystick());
                        joystickArrows.UP.addListener(new ChangeListener() {
                            @Override
                            public void changed(ChangeEvent event, Actor actor) {
                                if (snake.transfer.tr != 3) {
                                    snake.transfer.tr = 0;
                                }
                            }
                        });
                        joystickArrows.DOWN.addListener(new ChangeListener() {
                            @Override
                            public void changed(ChangeEvent event, Actor actor) {
                                if (snake.transfer.tr != 0) {
                                    snake.transfer.tr = 3;
                                }
                            }
                        });
                        joystickArrows.LEFT.addListener(new ChangeListener() {
                            @Override
                            public void changed(ChangeEvent event, Actor actor) {
                                if (snake.transfer.tr != 1) {
                                    snake.transfer.tr = 2;
                                }
                            }
                        });
                        joystickArrows.RIGHT.addListener(new ChangeListener() {
                            @Override
                            public void changed(ChangeEvent event, Actor actor) {
                                if (snake.transfer.tr != 2) {
                                    snake.transfer.tr = 1;
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
                        cells = new Sprite[Gdx.graphics.getWidth() / 33][Gdx.graphics.getHeight() / 33];
                        for (int rowGrass = 0; rowGrass < cells.length; rowGrass++) {
                            for (int colGrass = 0; colGrass < cells[rowGrass].length; colGrass++) {
                                Sprite cell = Asset.instance().getSprite(randomGrass(rowGrass, colGrass));
                                cells[rowGrass][colGrass] = cell;
                            }
                        }
                    }

                    seconder(1);

                    for (int rowGrass = 0; rowGrass < cells.length; rowGrass++) {
                        for (int colGrass = 0; colGrass < cells[rowGrass].length; colGrass++) {
                            game.batch.draw(Asset.instance().getSprite(randomGrass(rowGrass, colGrass)), rowGrass * 33, colGrass * 33, 33, 33);
                        }
                    }

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
                        game.setScreen(game.lobby);
                    }
                    if (timeSet > .40) {
                        timeSet = 0;
                        snake.PlayerClient();
                        game.loaded.put(snake.NameGame, snake.NamePlayer, snake.vector2, snake.level + "", bread.x, bread.y);
                        serverUpdate.render(game.loaded.requestData2(), snake);
                        pointUi.render(game.batch, snake.level, serverUpdate.getLevel(), snake.NamePlayer, serverUpdate.getNamePlayer());
                        if (!f2) {
                            f2 = true;
                            game.loaded.isExistsGame(snake.NameGame, serverUpdate.getNamePlayer());
                        }
                    }

                    timeSet += Gdx.graphics.getDeltaTime();
                    serverUpdate.render2();
                    init();
//                System.out.println(snake.level);
//                System.out.println(serverUpdate.getLevel());

                    for (int i = 0; i < serverUpdate.getLevel2(); i++) {
                        boolean g = touch.touchPlays(snake, serverUpdate.cells.get(i).x, serverUpdate.cells.get(i).y);
                        if (g) {
                            if (create) {
                                for (int j = 0; j < snake.cells.size(); j++) {
                                    snake.cells.get(j).x = 1221;
                                    snake.cells.get(j).y = 363;
                                }
//                            share.cells.get(0).x = 1200;(Это изменено)
                            } else {
                                for (int j = 0; j < snake.cells.size(); j++) {
                                    snake.cells.get(j).x = 99;
                                    snake.cells.get(j).y = 363;
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
                        pointUi.end.setSize(400, 120);
                        pointUi.end.setPosition(360 - 360 / snake.NamePlayer.length(), 240);
                    } else if (serverUpdate.getLevel() >= 8 && serverUpdate.getNamePlayer() != null) {
                        pointUi.WhoWin(serverUpdate.getNamePlayer());
                        end = true;
                        pointUi.end.setSize(200, 120);
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


        } catch (Exception e) {
            e.printStackTrace();
        }

        game.batch.end();
        stage.draw();
        stage.act(stateTime);
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
        game.setScreen(new Lobby(game));
        game.loaded.toast("Вы вышли из игры, поэтому игра закончилась");
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
}