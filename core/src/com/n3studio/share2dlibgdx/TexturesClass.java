package com.n3studio.share2dlibgdx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class TexturesClass {
    public static TexturesClass i = new TexturesClass();
    public static Texture bacG, grass, snakebody, snakehead, snakeheadMy, snakebodyMy, snaketailMy, textureS,
            circle, curJoystick, myTexture1, myTexture2, myTexture3, myTexture4, headY, bodyY, texture,
            blueApple, myTexture, myTextureB, textureFast, snakehead_black, snakebody_black, snaketail_black, eat_pink, bluetoothImage;
    Texture textures[];


    public void init() {
        grass = new Texture(Gdx.files.internal("grass.png"));
        bacG = new Texture("bacT.png");
        textureS = new Texture("redS.png");
        snakebody = new Texture("snakebody.png");
        snakehead = new Texture("snakehead.png");
        snakeheadMy = new Texture("snakeHeadMy.png");
        snakebodyMy = new Texture("snakeBodyMy.png");
        snaketailMy = new Texture("snakeTailMy.png");
        snakehead_black = new Texture("snakehead_black.png");
        snakebody_black = new Texture("snakebody_black.png");
        snaketail_black = new Texture("snaketail_black.png");

        eat_pink = new Texture("eat_pink.png");

        myTexture1 = new Texture(Gdx.files.internal("arrowLeft.png"));
        myTexture2 = new Texture(Gdx.files.internal("arrowRight.png"));
        myTexture3 = new Texture(Gdx.files.internal("arrowUp.png"));
        myTexture4 = new Texture(Gdx.files.internal("arrowDown.png"));

        texture = new Texture("BlueS2.png");
        blueApple = new Texture("appleBlue.png");
        headY = new Texture("headY.png");
        bodyY = new Texture("bodyE.png");

        textures = new Texture[33];
        textures[0] = new Texture("загруженное.png");
        textures[1] = new Texture("загруженное (2).png");
        textures[2] = new Texture("загруженное (2).png");
        textures[3] = new Texture("загруженное (3).png");
        textures[4] = new Texture("загруженное (4).png");
        textures[5] = new Texture("загруженное (5).png");
        textures[6] = new Texture("загруженное (6).png");
        textures[7] = new Texture("загруженное (7).png");
        textures[8] = new Texture("загруженное (8).png");
        textures[9] = new Texture("загруженное (9).png");
        textures[10] = new Texture("загруженное (10).png");
        textures[11] = new Texture("загруженное (11).png");
        textures[12] = new Texture("загруженное (12).png");
        textures[13] = new Texture("загруженное (13).png");
        textures[14] = new Texture("загруженное (14).png");
        textures[15] = new Texture("загруженное (15).png");
        textures[16] = new Texture("загруженное (16).png");
        textures[17] = new Texture("загруженное (17).png");
        textures[18] = new Texture("загруженное (18).png");
        textures[19] = new Texture("загруженное (19).png");
        textures[20] = new Texture("загруженное (20).png");
        textures[21] = new Texture("загруженное (21).png");
        textures[22] = new Texture("загруженное (22).png");
        textures[23] = new Texture("загруженное (23).png");
        textures[24] = new Texture("загруженное (24).png");
        textures[25] = new Texture("загруженное (25).png");
        textures[26] = new Texture("загруженное (26).png");
        textures[27] = new Texture("загруженное (27).png");
        textures[28] = new Texture("загруженное (28).png");
        textures[29] = new Texture("загруженное (29).png");
        textures[30] = new Texture("загруженное (30).png");
        textures[31] = new Texture("загруженное (31).png");
        textures[32] = new Texture("загруженное (32).png");

        myTextureB = new Texture(Gdx.files.internal("appleBlue.png"));
        myTexture = new Texture(Gdx.files.internal("apple.png"));

        textureFast = new Texture("bonusFast.png");

        bluetoothImage = new Texture("BluetoothImage.png");
    }
}
