package com.example.share2dlibgdx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import handler.CheckBoxHandler;
import handler.FontSizeHandler;
import handler.LabelHandler;

public class LeaderboardBluetooth implements Screen {

    private Stage stage;

    Viewport fitViewport;
    OrthographicCamera camera;

    float gameWidth, gameHeight;

    Skin skin;

    String roms[];

    game game;

    Table scrollTable;

//    String namePlayerUn;
//    String namePlayer = "";

    int helpVariable;
    CheckBox checkBox;
    boolean check;
    String mod = "";

    public LeaderboardBluetooth(final game gam) {
        this.game = gam;
//        this.namePlayer = namePlayer;
//        this.namePlayerUn = namePlayerUn;

        gameWidth = Gdx.graphics.getWidth();
        gameHeight = Gdx.graphics.getHeight();

        camera = new OrthographicCamera();
        fitViewport = new FitViewport(1280, 720, camera);

        this.stage = new Stage(fitViewport);

        skin = new Skin(Gdx.files.internal("uiskin.json"));

        Label text = LabelHandler.INSTANCE.createLabel("Выберите устройство \nили \nсоздайте свою ", 50, Color.WHITE);
        text.setPosition(30, 540);

        Texture myTexture = new Texture(Gdx.files.internal("7.png"));
        TextureRegion myTextureRegion = new TextureRegion(myTexture);
        TextureRegionDrawable myTexRegionDrawable = new TextureRegionDrawable(myTextureRegion);
        ImageButton back = new ImageButton(myTexRegionDrawable);
        back.setPosition(1090, 600);
        back.setSize(120, 120);
        back.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.loaded.dispose();
                game.setScreen(game.lobby);
            }
        });

        game.loaded.checkStartPlay();

        ImageTextButton newGame = new ImageTextButton("Создать сервер", imageTextButton("BlakButton.png", 35));
        newGame.setSize(400, 100);
        newGame.setPosition(800, 160);
        newGame.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.bluetoothLoaded.listen();
                game.setScreen(new Game4_Screen(game, true));
            }
        });

        ImageTextButton listen = new ImageTextButton("Доступные устройства", imageTextButton("BlakButton.png", 35));
        listen.setSize(400, 100);
        listen.setPosition(800, 360);
        listen.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                scrollTable.clear();
                for (int i = 0; i < game.bluetoothLoaded.getListDevice().size(); i++) {
                    ImageTextButton button = new ImageTextButton(game.bluetoothLoaded.getListDevice().get(i), imageTextButton("buttonBer.png", 25));
                    final int finalI = i;
                    button.addListener(new ChangeListener() {
                        @Override
                        public void changed(ChangeEvent event, Actor actor) {
                            game.loaded.dispose();
                            game.bluetoothLoaded.itemB(finalI);
                            game.setScreen(new Game4_Screen(game, false));
                        }
                    });
                    scrollTable.add(button).size(420, 100).padBottom(20);
                    scrollTable.row();
                }
            }
        });


        scrollTable = new Table();
        final ScrollPane scroller = new ScrollPane(scrollTable);
//        scroller.setSmoothScrolling(true);
//        scroller.setTransform(true);
        scroller.setScale(1f);
        scroller.setBounds(0, 0, 450, 450);

        final Table table = new Table();
//        table.setFillParent(true);
        table.add(scroller).fill().expand();
        table.setBounds(0, 50, 450, 400);

        checkBox = CheckBoxHandler.INSTANCE.createCheckBox("Другой мод", skin, 8, Color.BLACK);
        checkBox.setPosition(1100, 100);
        checkBox.setTransform(true);
        checkBox.getImage().setScaling(Scaling.fill);
        checkBox.setSize(50, 50);
        checkBox.getImageCell().size(50, 50);
        checkBox.getImage().setSize(50, 50);
        checkBox.getLabel().setFontScale(3f, 3f);
        checkBox.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (!check) {
                    check = true;
                } else {
                    check = false;
                }
            }
        });

        this.stage.addActor(table);
        this.stage.addActor(text);
        this.stage.addActor(newGame);
        this.stage.addActor(listen);
        this.stage.addActor(back);
        this.stage.addActor(checkBox);


        Gdx.input.setInputProcessor(this.stage);

        this.stage.getViewport().setCamera(camera);
//        for (int i = 0; i < game.bluetoothLoaded.getListDevice().size(); i++) {
//            ImageTextButton button = new ImageTextButton(game.bluetoothLoaded.getListDevice().get(i), imageTextButton("buttonBer.png", 25));
//            final int finalI = i;
//            button.addListener(new ChangeListener() {
//                @Override
//                public void changed(ChangeEvent event, Actor actor) {
//                    game.loaded.dispose();
//
//                }
//            });
//            scrollTable.add(button).size(420, 100).padBottom(20);
//            scrollTable.row();
//        }

    }

    @Override
    public void show() {

    }

    String gg = "";

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.3f, 0, 1, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
//        camera.update();

        fitViewport.apply();
        if (!game.bluetoothLoaded.isEnabled()) {
            game.setScreen(game.lobby);
            game.loaded.toast("Вы выключили bluetooth");
            game.bluetoothLoaded.stopT();
        }
//        try {
//            roms = game.loaded.checkStartPlay2().replace("null", "").split(" ");
//            if (roms.length > helpVariable) {
//                scrollTable.clear();
//                for (int i = 0; i < roms.length; i++) {
//                    ImageTextButton button = new ImageTextButton(roms[i], imageTextButton("buttonBer.png", 25));
//                    final int finalI = i;
//                    button.addListener(new ChangeListener() {
//                        @Override
//                        public void changed(ChangeEvent event, Actor actor) {
//                            game.loaded.dispose();
//                            if (roms[finalI].contains("!!")) {
//                                check = true;
//                            }
//                            game.setScreen(new Game1_Screen(game, namePlayer, namePlayerUn, roms[finalI], false, check));
//                        }
//                    });
//                    scrollTable.add(button).size(420, 100).padBottom(20);
//                    scrollTable.row();
//                }
//                helpVariable = roms.length;
//            }
//
//
//        } catch (Exception e) {
//
//        }

        this.stage.act();
        this.stage.draw();
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
        stage.dispose();
        skin.dispose();
    }

    public ImageTextButton.ImageTextButtonStyle imageTextButton(String way, int size) {
        Texture myTexture = new Texture(Gdx.files.internal(way));//buttonBer
        TextureRegion myTextureRegion = new TextureRegion(myTexture);
        TextureRegionDrawable myTexRegionDrawable = new TextureRegionDrawable(myTextureRegion);
        ImageTextButton.ImageTextButtonStyle style = new ImageTextButton.ImageTextButtonStyle();
        style.up = myTexRegionDrawable;
        style.down = myTexRegionDrawable;
        style.checked = myTexRegionDrawable;
        style.font = FontSizeHandler.INSTANCE.getFont(size, Color.WHITE);
        return style;
    }

    public TextButton.TextButtonStyle setStyle(TextButton end, int size) {
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.font = FontSizeHandler.INSTANCE.getFont(size, Color.WHITE);
        style.checked = end.getStyle().checked;
        style.up = end.getStyle().up;
        style.down = end.getStyle().down;

        return style;
    }
}
