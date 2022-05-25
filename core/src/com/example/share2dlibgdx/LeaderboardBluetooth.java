package com.example.share2dlibgdx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import handler.FontSizeHandler;
import handler.ImageTextButtonHandler;
import handler.LabelHandler;

public class LeaderboardBluetooth extends BaseScreen {

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
    Texture bacT;
    Texture level;
    HelpText helpText1;

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

        Label text = LabelHandler.INSTANCE.createLabel("Выберите устройство на котором слушают\nили создайте свой слушатель", 30, Color.WHITE);
        text.setPosition(30, 620);

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

        ImageTextButton newGame = ImageTextButtonHandler.INSTANCE.createButtonWay("butL (3).png", "Слушать", 35, Color.WHITE, true);
        newGame.setSize(400, 100);
        newGame.setPosition(800, 160);
        newGame.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.bluetoothLoaded.listen();
                game.setScreen(new Bluetooth_Game_Screen(game, true));
            }
        });

        ImageTextButton listen = ImageTextButtonHandler.INSTANCE.createButtonWay("butL (3).png", "Устройства", 35, Color.WHITE, true);
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
                            game.setScreen(new Bluetooth_Game_Screen(game, false));
                        }
                    });
                    scrollTable.add(button).size(420, 100).padBottom(20);
                    scrollTable.row();
                }
            }
        });


        scrollTable = new Table();
        final ScrollPane scroller = new ScrollPane(scrollTable);
        scroller.setScale(1f);
        scroller.setBounds(0, 0, 450, 450);

        final Table table = new Table();
        table.add(scroller).fill().expand();
        table.setBounds(0, 50, 450, 400);

        final ImageTextButton helpText = ImageTextButtonHandler.INSTANCE.createButtonWay("helpText.png", "", 35, Color.WHITE, true);
        helpText.setSize(140, 120);
        helpText.setPosition(800, 590);


        this.stage.addActor(table);
        this.stage.addActor(text);
        this.stage.addActor(newGame);
        this.stage.addActor(listen);
        this.stage.addActor(back);
        this.stage.addActor(helpText);


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

        bacT = new Texture("bacT.png");
        helpText1 = new HelpText();
        helpText.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                helpText1.show();
            }
        });
    }

    @Override
    public void show() {

    }

    String gg = "";

    @Override
    public void render(float delta) {
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        fitViewport.apply();
        game.batch.begin();
        game.batch.draw(bacT, 0, 0, 1280, 720);
        game.batch.end();

        if (!game.bluetoothLoaded.isEnabled()) {
            game.setScreen(game.lobby);
            game.loaded.toast("Вы выключили bluetooth");
            game.bluetoothLoaded.stopT();
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
        level.dispose();
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

    public class HelpText {
        Image image;
        ImageTextButton closeLevel;
        Label label;
        Label labelTitle;


        public HelpText() {
            level = new Texture("options.png");
            image = new Image(level);
            image.setSize(500, 700);
            image.setPosition(380, 0);
            closeLevel = ImageTextButtonHandler.INSTANCE.createButtonWay("close.png", "", 15, Color.WHITE, false);
            closeLevel.setPosition(800, 626);
            closeLevel.setSize(50, 50);
            closeLevel.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    remove();

                }
            });
            label = LabelHandler.INSTANCE.createLabel("Чтобы поиграть с другом по Bluetooth,\nсначала одному из вас нужно\nнажать кнопку \"Слушать\", " +
                    "после,\nдругому, выбрать из предложенного\nсписка сопряжённых устройств,\nнужный телефон.", 25, Color.BLACK);
            label.setPosition(395, 300);

            labelTitle = LabelHandler.INSTANCE.createLabel("Инструкция", 40, Color.BLACK);
            labelTitle.setPosition(510, 620);

            stage.addActor(image);
            stage.addActor(closeLevel);
            stage.addActor(label);
            stage.addActor(labelTitle);
            image.setVisible(false);
            closeLevel.setVisible(false);
            label.setVisible(false);
            labelTitle.setVisible(false);
        }

        public void show() {
            image.setVisible(true);
            closeLevel.setVisible(true);
            label.setVisible(true);
            labelTitle.setVisible(true);
        }

        public void remove() {
            image.setVisible(false);
            closeLevel.setVisible(false);
            label.setVisible(false);
            labelTitle.setVisible(false);
        }
    }
}
