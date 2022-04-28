package com.example.share2dlibgdx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import java.util.ArrayList;

import handler.FontSizeHandler;
import handler.LabelHandler;


public class PointUi {
    Label label;
    Label label2;
    Label WinLabel;
    Label timer;
    ArrayList<ImageButton> imageButtonsApples;
    Group group;
    Group group2;
    TextButton end;
    int testLevel2;
    int testLevel1;
    String testNamePlayer2;

    int level1, level2;
    String namePlayer1, namePlayer2;

    boolean g = false;

    DeterminantSize size;

    int f = 7;

    public Label Timer() {
        timer = LabelHandler.INSTANCE.createLabel("00:00", 60, Color.BLACK);
        timer.setX((float) (1280 / 1.5));
        timer.setY(720 - 100);
        return timer;
    }

    public Label WinPlay() {
        WinLabel = LabelHandler.INSTANCE.createLabel("", 100, Color.BLACK);
        WinLabel.setX(350);
        WinLabel.setY(1280 / 2);
        return WinLabel;
    }

    public Label FirstPointUi() {
        label = LabelHandler.INSTANCE.createLabel("namePlayer", 60, Color.BLACK);
        label.setX(0);
        label.setY(720 - 100);
        return label;
    }

    public Label SecondPointUi() {
        label2 = LabelHandler.INSTANCE.createLabel("namePlayersss", 60, Color.BLACK);
        label2.setX(0);
        label2.setY(720 - 200);
        return label2;
    }

    public Group apples() {
        group = new Group();
        for (int i = 0; i < 8; i++) {
            Texture myTexture = new Texture(Gdx.files.internal("apple.png"));
            TextureRegion myTextureRegion = new TextureRegion(myTexture);
            TextureRegionDrawable myTexRegionDrawable = new TextureRegionDrawable(myTextureRegion);
            ImageButton imageButton = new ImageButton(myTexRegionDrawable);
            imageButton.setX(350 + (i * 60));
            imageButton.setY(720 - 200);
            imageButton.setWidth(56);
            imageButton.setHeight(56);
            group.addActor(imageButton);
//            myTexture.dispose();
        }
        return group;
    }

    public Group apples2() {
        group2 = new Group();
        for (int i = 0; i < 8; i++) {
            Texture myTexture = new Texture(Gdx.files.internal("apple.png"));
            TextureRegion myTextureRegion = new TextureRegion(myTexture);
            TextureRegionDrawable myTexRegionDrawable = new TextureRegionDrawable(myTextureRegion);
            ImageButton imageButton = new ImageButton(myTexRegionDrawable);
            imageButton.setX(350 + (i * 60));
            imageButton.setY(720 - 100);
            imageButton.setWidth(56);
            imageButton.setHeight(56);
            imageButtonsApples.add(imageButton);
            group2.addActor(imageButton);
//            myTexture.dispose();
        }
        return group2;
    }

    public TextButton endButton() {
        Skin skin = new Skin(Gdx.files.internal("uiskin.json"));
        end = new TextButton("End", skin);
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.font = FontSizeHandler.INSTANCE.getFont(40, Color.WHITE);
        ;
        style.checked = end.getStyle().checked;
        style.up = end.getStyle().up;
        style.down = end.getStyle().down;
        end.setStyle(style);
        end.setSize(-400, -120);
        end.setPosition(-640, -720 / 2 - 350);
        return end;
    }

    public PointUi() {
        size = new DeterminantSize();
        imageButtonsApples = new ArrayList<>();
    }

    public void render(SpriteBatch batch, int level1, int level2, String namePlayer1, String namePlayer2) {
        this.level1 = level1;
        this.level2 = level2;
        this.namePlayer1 = namePlayer1;
        this.namePlayer2 = namePlayer2;


        if (!g && namePlayer2 != null) {
            label.setText(namePlayer1);
            if (!namePlayer2.equals(testNamePlayer2)) {
                label2.setText(namePlayer2);
                testNamePlayer2 = namePlayer2;
                g = true;
            }
        }

        if(testLevel2 < level2){
            testLevel2 = level2;
            group.getChild(f).clear();
            f--;
        }

        if (testLevel1 < level1) {
            testLevel1 = level1;
            group2.getChild(8 - level1).clear();

        }
    }

    public void WhoWin(String namePlayer) {

        WinLabel.setX(360 - 360 / namePlayer.length());
        WinLabel.setY(440);
        WinLabel.setText("Win: " + namePlayer);
    }


}
