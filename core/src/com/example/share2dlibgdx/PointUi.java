package com.example.share2dlibgdx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import java.util.ArrayList;

import handler.ImageTextButtonHandler;
import handler.LabelHandler;


public class PointUi {
    Label label;
    Label label2;
    Label WinLabel;
    Label timer;
    ArrayList<ImageButton> imageButtonsApples;
    Group group;
    Group group2;
    ImageTextButton end;
    int testLevel2;
    int testLevel1;
    String testNamePlayer2;

    int level1, level2;
    String namePlayer1, namePlayer2;

    boolean g = false;

    DeterminantSize size;

    int f = 7;
    int f2 = 7;

    public Label Timer() {
        timer = LabelHandler.INSTANCE.createLabel("00:00", 60, Color.BLACK);
        timer.setX((float) (1280 / 1.5));
        timer.setY(720 - 100);
        return timer;
    }

    public Label WinPlay() {
        WinLabel = LabelHandler.INSTANCE.createLabel("", 100, Color.WHITE);
        WinLabel.setX(350);
        WinLabel.setY(1280 / 2);
        return WinLabel;
    }

    public Label FirstPointUi() {
        label = LabelHandler.INSTANCE.createLabel("namePlayer", 60, Color.WHITE);
        label.setX(0);
        label.setY(720 - 100);
        return label;
    }

    public Label SecondPointUi() {
        label2 = LabelHandler.INSTANCE.createLabel("namePlayersss", 60, Color.WHITE);
        label2.setX(0);
        label2.setY(720 - 200);
        return label2;
    }

    public Group apples() {
        group = new Group();
        for (int i = 0; i < 8; i++) {
            Texture myTexture = new Texture(Gdx.files.internal("appleBlue.png"));
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
            group2.addActor(imageButton);
//            myTexture.dispose();
        }
        return group2;
    }

    public ImageTextButton endButton() {

        end = ImageTextButtonHandler.INSTANCE.createButtonWay("back.png", "", 60, Color.WHITE, false);
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
        if (testLevel2 < level2) {
            testLevel2 = level2;
            group.getChild(8 - level2).setVisible(false);
            group.getChild(7).setVisible(false);
            try {
                group.getChild(8 - level2 + 1).setVisible(false);
            } catch (Exception e) {

            }
//            for (int i = 0; i < level2; i++) {
//                group.getChild(8-i).setVisible(false);
//            }
        }

        if (testLevel1 < level1) {
            testLevel1 = level1;
//            group2.getChild(8 - level1).setVisible(false);
            group2.getChild(8 - level1).setVisible(false);
            group2.getChild(7).setVisible(false);
            try {
                group2.getChild(8 - level1 + 1).setVisible(false);
            } catch (Exception e) {

            }


        }
    }

    public void WhoWin(String namePlayer) {

        WinLabel.setX(360 - 360 / namePlayer.length());
        WinLabel.setY(440);
        WinLabel.setText("Win: " + namePlayer);
    }


}
