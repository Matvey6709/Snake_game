package com.example.share2dlibgdx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.example.share2dlibgdx.ui.ActivitySwipeDetector;
import com.example.share2dlibgdx.ui.JoystickArrows;

import handler.FontSizeHandler;
import handler.ImageTextButtonHandler;
import handler.LabelHandler;

public class Сlassic_Game_Screen extends BaseScreen {
    public Snake snake;
    Bread bread;
    Touch touch;
    Texture grass;
    Viewport fitViewport;
    Label label;
    Stage stage;
    game game;
    OrthographicCamera camera;
    JoystickArrows joystickArrows;
    int gameOver = 0;

    private Cell[][] cells;
    int size = 33;
    float speed = 0.4f;
    ImageTextButton back;
    Texture bacG;
    boolean o = false;
    int spawnX = 660;
    int spawnY = 363;
    TextButton v;

    public Сlassic_Game_Screen(final game game, boolean o) {
        this.game = game;
        this.o = o;
        grass = new Texture(Gdx.files.internal("grass.png"));
        camera = new OrthographicCamera();
        fitViewport = new FitViewport(1280, 720, camera);

        snake = new Snake(game.batch, size, size, game, speed);

        bread = new Bread(game.batch, size);
        bread.spawn2();

        touch = new Touch(snake, bread);
        joystickArrows = new JoystickArrows(100, 100, 10);

        stage = new Stage(fitViewport);
        Skin skin = new Skin(Gdx.files.internal("uiskin.json"));

        v = new TextButton("", setStyle(skin, 60));
        v.setSize(1280, 720);
        v.setPosition(0, 0);
        v.addListener(new ActivitySwipeDetector(snake));
        stage.addActor(v);

        label = LabelHandler.INSTANCE.createLabel("Очки: 0", 50, Color.WHITE);
        label.setPosition(10, 620);

        back = ImageTextButtonHandler.INSTANCE.createButtonWay("back.png", "", 60, Color.WHITE, false);
        back.setPosition(980, 610);
        back.setSize(250, 100);
        back.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                SoundPlayer.stopMusic(Asset.MEMO_SOUND);
                System.gc();
                game.setScreen(game.lobby);
            }
        });
        stage.addActor(back);
        stage.addActor(joystickArrows.joystick());
        if (!o) {
            stage.addActor(label);
        }
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
        bacG = new Texture("bacT.png");
        if (!o) {
            for (int j = 0; j < snake.cells.size(); j++) {
                snake.cells.get(j).x = spawnX;//660
                snake.cells.get(j).y = spawnY;//363
            }
        }
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        initMusic();
        gameOver();
        snake.transfer.tr = -1;
    }

    public TextButton.TextButtonStyle setStyle(Skin skin, int size) {
        TextButton textButton = new TextButton("", skin);
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.font = FontSizeHandler.INSTANCE.getFont(size, Color.WHITE);

        return style;
    }

    private void initMusic() {
        SoundPlayer.playMusic(Asset.MEMO_SOUND, true);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        fitViewport.apply();

        game.batch.begin();
//        cells = new Cell[Gdx.graphics.getWidth() / size][Gdx.graphics.getHeight() / size];
//        for (int rowGrass = 0; rowGrass < cells.length; rowGrass++) {
//            for (int colGrass = 0; colGrass < cells[rowGrass].length; colGrass++) {
//                game.batch.draw(Asset.instance().getSprite(randomGrass(rowGrass, colGrass)), rowGrass * size, colGrass * size, size, size);
//            }
//        }
        game.batch.draw(bacG, 0, 0);


        snake.render3(Gdx.graphics.getDeltaTime());
        initMeal();

        if (!o) {
            if (touch.touchScreen() != 0) {
                gameOver();
            }
            for (int i = 1; i < snake.cells.size(); i++) {
                boolean g = touch.touchPlays(snake, snake.cells.get(i).x, snake.cells.get(i).y);
                boolean f = touch.touchPlayer(snake.cells.get(i).x, snake.cells.get(i).y, bread.x, bread.y);
                if (f) {
                    bread.spawn2();
                }
                if (g && snake.transfer.tr != -1) {
                    SoundPlayer.playSound(Asset.CRASH_SOUND, false);
                    gameOver();
                    break;
                }
            }
        }


        game.batch.end();

        stage.draw();
        stage.act(Gdx.graphics.getDeltaTime());

        game.batch.begin();
        if (o) {
            bread.render();
        } else {
            bread.render2();
        }
        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {
        fitViewport.update(width, height);
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
        game.batch.dispose();
        grass.dispose();
        snake.dispose();
        bacG.dispose();
        bread.dispose();
        System.out.println("dispose");
    }

    public void initMeal() {
        if (touch.touchBred()) {
            SoundPlayer.playSound(Asset.EAT_FOOD_SOUND, false);
            label.setText("Очки: " + snake.level);
            if (!o) {
                bread.resetBreadTexture();
            }

            bread.spawn2();
            snake.addLevel();
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

    public TextButton.TextButtonStyle setStyle(Skin skin, TextButton end, int size) {
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.font = FontSizeHandler.INSTANCE.getFont(size, Color.WHITE);
        style.checked = end.getStyle().checked;
        style.up = end.getStyle().up;
        style.down = end.getStyle().down;

        return style;
    }

    public void gameOver() {
        snake.cells.clear();
        snake.increase(3);
        snake.level = 1;

        label.setText("Очки: 0");
        for (int j = 0; j < snake.cells.size(); j++) {
            snake.cells.get(j).x = spawnX;//660
            snake.cells.get(j).y = spawnY;//363
        }
    }
}