package com.example.share2dlibgdx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.example.share2dlibgdx.ui.ActivitySwipeDetector;
import com.example.share2dlibgdx.ui.JoystickArrows;

import handler.FontSizeHandler;
import handler.LabelHandler;

public class Game2_Screen implements Screen {
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
    float speed = 0.1f;

    public Game2_Screen(game game) {
        this.game = game;
    }

    @Override
    public void show() {
        grass = new Texture(Gdx.files.internal("grass.png"));
        camera = new OrthographicCamera();
        fitViewport = new FitViewport(1280, 720, camera);

        snake = new Snake(game.batch, size, size, game, speed);

        bread = new Bread(game.batch, size);
        bread.spawn();

        touch = new Touch(snake, bread);
        joystickArrows = new JoystickArrows(100, 100, 10);

        stage = new Stage(fitViewport);
        Skin skin = new Skin(Gdx.files.internal("uiskin.json"));

        TextButton v = new TextButton("", setStyle(skin, 60));
        v.setSize(1280, 720);
        v.setPosition(0, 0);
        v.addListener(new ActivitySwipeDetector(snake));
        stage.addActor(v);

        label = LabelHandler.INSTANCE.createLabel("Points: 0", 50, Color.BLACK);

        TextButton back = new TextButton("", skin);
        back = new TextButton("назад", setStyle(skin, back, 40));
        back.setPosition(1080, 620);
        back.setSize(180, 80);
        back.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                SoundPlayer.stopMusic(Asset.MEMO_SOUND);
                game.setScreen(game.lobby);

            }
        });
        stage.addActor(back);
        stage.addActor(joystickArrows.joystick());
        stage.addActor(label);
        Gdx.input.setInputProcessor(stage);

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
        initMusic();
    }

    public TextButton.TextButtonStyle setStyle(Skin skin, int size) {
        TextButton textButton = new TextButton("", skin);
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.font = FontSizeHandler.INSTANCE.getFont(size, Color.WHITE);

        return style;
    }

    private void initMusic() {
        SoundPlayer.init();
        SoundPlayer.playMusic(Asset.MEMO_SOUND, false);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        fitViewport.apply();

        game.batch.begin();
//        for (int x = 0; x < Gdx.graphics.getWidth() / grass.getWidth(); x++) {
//            for (int y = 0; y < Gdx.graphics.getHeight() / grass.getHeight(); y++) {
//                game.batch.draw(grass, grass.getWidth() * x, grass.getHeight() * y);
//            }
//        }
        cells = new Cell[Gdx.graphics.getWidth() / size][Gdx.graphics.getHeight() / size];
        for (int rowGrass = 0; rowGrass < cells.length; rowGrass++) {
            for (int colGrass = 0; colGrass < cells[rowGrass].length; colGrass++) {
                game.batch.draw(Asset.instance().getSprite(randomGrass(rowGrass, colGrass)), rowGrass * size, colGrass * size, size, size);
            }
        }

        if (touch.touchScreen() != 0) {
            gameOver++;
            snake.cells.clear();
            snake.increase(3);
            snake.level = 1;

            label.setText("Points: 0");
            for (int j = 0; j < snake.cells.size(); j++) {
                snake.cells.get(j).x = 660;
                snake.cells.get(j).y = 363;
            }
        }


        snake.render3(Gdx.graphics.getDeltaTime());
        initMeal();

        for (int i = 1; i < snake.cells.size(); i++) {
            boolean g = touch.touchPlays(snake, snake.cells.get(i).x, snake.cells.get(i).y);
            if (g) {
                gameOver++;
//                SoundPlayer.playSound(Asset.CRASH_SOUND, false);
                snake.cells.clear();
                snake.increase(3);
                snake.level = 1;
                label.setText("Points: 0");
                for (int j = 0; j < snake.cells.size(); j++) {
                    snake.cells.get(j).x = 660;//660
                    snake.cells.get(j).y = 363;//363
                }
                break;
            }
        }


        game.batch.end();

        stage.draw();
        stage.act(Gdx.graphics.getDeltaTime());

        game.batch.begin();
        bread.render2();
        game.batch.end();

//        if (gameOver >= 10) {
//            SoundPlayer.stopMusic(Asset.MEMO_SOUND);
//            SoundPlayer.playMusic(Asset.MEMO_SOUND, false);
//            gameOver = 0;
//        }
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
        grass.dispose();
        snake.dispose();
    }

    public void initMeal() {
        if (touch.touchBred()) {
            SoundPlayer.playSound(Asset.EAT_FOOD_SOUND, false);
            label.setText("Points: " + snake.level);
            bread.resetBreadTexture();
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
        gameOver++;
        snake.cells.clear();
        snake.increase(3);
        snake.level = 1;

        label.setText("Points: 0");
        for (int j = 0; j < snake.cells.size(); j++) {
            snake.cells.get(j).x = 660;//660
            snake.cells.get(j).y = 363;//363
        }
    }
}