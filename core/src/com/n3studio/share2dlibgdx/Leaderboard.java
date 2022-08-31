package com.n3studio.share2dlibgdx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
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

import java.util.Random;

import handler.CheckBoxHandler;
import handler.FontSizeHandler;
import handler.ImageTextButtonHandler;
import handler.LabelHandler;

public class Leaderboard extends BaseScreen {
    private Stage stage;

    Viewport fitViewport;
    OrthographicCamera camera;

    float gameWidth, gameHeight;

    Skin skin;

    String roms[];

    game game;

    Table scrollTable;

    String namePlayerUn;
    String namePlayer = "";

    int helpVariable;
    CheckBox checkBox;
    boolean check;
    String mod = "";
    Texture bacT;


    public Leaderboard(final game gam, final String namePlayerUn, final String namePlayer) {
        this.game = gam;
        this.namePlayer = namePlayer;
        this.namePlayerUn = namePlayerUn;
        gameWidth = Gdx.graphics.getWidth();
        gameHeight = Gdx.graphics.getHeight();

        camera = new OrthographicCamera();
        fitViewport = new FitViewport(1280, 720, camera);
//        fitViewport = new ExtendViewport(1280, 720, 1280, 720, camera);
        this.stage = new Stage(fitViewport);
        stage.getViewport().setCamera(camera);
        skin = new Skin(Gdx.files.internal("uiskin.json"));

//
//
//        final Label text = LabelHandler.INSTANCE.createLabel(reallyLongString, 40, Color.WHITE);
//        text.setAlignment(Align.center);
//        text.setWrap(true);
//        final Label text2 = LabelHandler.INSTANCE.createLabel("This is a short string!", 40, Color.WHITE);
//        text2.setAlignment(Align.center);
//        text2.setWrap(true);
//        final Label text3 = LabelHandler.INSTANCE.createLabel(reallyLongString, 40, Color.WHITE);
//        text3.setAlignment(Align.center);
//        text3.setWrap(true);
//        scrollTable.add(text);
//        scrollTable.row();
//        scrollTable.add(text2);
//        scrollTable.row();
//        scrollTable.add(text3);
//        scrollTable.row();
//        scroller.setTransform(true);
//        scroller.setBounds(100, 500, gameWidth, gameHeight + 100);
//        scroller.setSmoothScrolling(true);
//        scroller.setPosition(500, 0);

        Label text = LabelHandler.INSTANCE.createLabel("Выберите комнату или создайте свою", 40, Color.WHITE);
        text.setPosition(30, 620);

//        Texture myTexture = new Texture(Gdx.files.internal("7.png"));
//        TextureRegion myTextureRegion = new TextureRegion(myTexture);
//        TextureRegionDrawable myTexRegionDrawable = new TextureRegionDrawable(myTextureRegion);
        ImageTextButton back = ImageTextButtonHandler.INSTANCE.createButtonWay("back.png", "", 60, Color.WHITE, false);
        back.setPosition(1000, 600);
        back.setSize(250, 100);
        back.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.loaded.dispose();
                game.setScreen(game.lobby);
            }
        });

        game.loaded.checkStartPlay();

        ImageTextButton newGame = ImageTextButtonHandler.INSTANCE.createButtonWay("butL (3).png", "Новая игра", 35, Color.WHITE, true);
        newGame.setSize(400, 100);
        newGame.setPosition(800, 160);
        newGame.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.loaded.dispose();
                if (check) {
                    mod = "!!";
                }
                System.gc();
                game.setScreen(new com.n3studio.share2dlibgdx.Online_Game_Screen(game, namePlayer, namePlayerUn, mod + (new Random().nextInt(900000) + 100000 + "" + System.currentTimeMillis()), true, check));
                mod = "";
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

        checkBox = CheckBoxHandler.INSTANCE.createCheckBox("", skin, 8, Color.WHITE);
        checkBox.setPosition(1130, 180);
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
                game.loaded.toast("Вы переключили режим Онлайн игры");
            }
        });
        ImageTextButton loading = ImageTextButtonHandler.INSTANCE.createButtonWay("loading.png", "", 35, Color.WHITE, false);
        loading.setSize(100, 100);
        loading.setPosition(30, 500);
        loading.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                helpVariable = 0;
                scrollTable.clear();
            }
        });

        this.stage.addActor(table);
        this.stage.addActor(text);
        this.stage.addActor(newGame);
        this.stage.addActor(back);
        this.stage.addActor(checkBox);
        this.stage.addActor(loading);

        bacT = new Texture("bacT.png");
        Gdx.input.setInputProcessor(this.stage);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(this.stage);
        game.loaded.checkStartPlay();
        helpVariable = 0;
        scrollTable.clear();
        checkBox.setChecked(false);
    }

    boolean y = false;

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0, 0, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        fitViewport.apply();
        game.batch.begin();
        game.batch.draw(bacT, 0, 0, 1280, 720);
        game.batch.end();
        if (!game.loaded.isOnline() && !y) {
            y = true;
            game.loaded.toast("Нет подключения к интрнету");
            game.setScreen(game.lobby);
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
        try {
            roms = game.loaded.checkStartPlay2().replace("null", "").split(" ");
            if (roms.length > helpVariable) {
                for (int i = 0; i < roms.length; i++) {
                    ImageTextButton button = new ImageTextButton(roms[i], imageTextButton("buttonBer.png", 25));
                    final int finalI = i;
                    button.addListener(new ChangeListener() {
                        @Override
                        public void changed(ChangeEvent event, Actor actor) {
                            game.loaded.dispose();
                            if (roms[finalI].contains("!!")) {
                                check = true;
                            }
                            game.setScreen(new Online_Game_Screen(game, namePlayer, namePlayerUn, roms[finalI], false, check));
                        }
                    });
                    scrollTable.add(button).size(420, 100).padBottom(20);
                    scrollTable.row();
                }
                helpVariable = roms.length;
            }
            if (roms.length < helpVariable) {
                helpVariable = 0;
                scrollTable.clear();
            }


        } catch (Exception e) {

        }

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
        bacT.dispose();
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
 