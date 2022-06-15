package com.example.share2dlibgdx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class AnimationBluetooth {
    SpriteBatch batch;
    Animation<Texture> animation;
    float elapsed;
//    Texture texture[];

    public AnimationBluetooth(SpriteBatch batch) {
        this.batch = batch;
//        texture = new Texture[33];
//        texture[0] = new Texture("загруженное.png");
//        texture[1] = new Texture("загруженное (1).png");
//        texture[2] = new Texture("загруженное (2).png");
//        texture[3] = new Texture("загруженное (3).png");
//        texture[4] = new Texture("загруженное (4).png");
//        texture[5] = new Texture("загруженное (5).png");
//        texture[6] = new Texture("загруженное (6).png");
//        texture[7] = new Texture("загруженное (7).png");
//        texture[8] = new Texture("загруженное (8).png");
//        texture[9] = new Texture("загруженное (9).png");
//        texture[10] = new Texture("загруженное (10).png");
//        texture[11] = new Texture("загруженное (11).png");
//        texture[12] = new Texture("загруженное (12).png");
//        texture[13] = new Texture("загруженное (13).png");
//        texture[14] = new Texture("загруженное (14).png");
//        texture[15] = new Texture("загруженное (15).png");
//        texture[16] = new Texture("загруженное (16).png");
//        texture[17] = new Texture("загруженное (17).png");
//        texture[18] = new Texture("загруженное (18).png");
//        texture[19] = new Texture("загруженное (19).png");
//        texture[20] = new Texture("загруженное (20).png");
//        texture[21] = new Texture("загруженное (21).png");
//        texture[22] = new Texture("загруженное (22).png");
//        texture[23] = new Texture("загруженное (23).png");
//        texture[24] = new Texture("загруженное (24).png");
//        texture[25] = new Texture("загруженное (25).png");
//        texture[26] = new Texture("загруженное (26).png");
//        texture[27] = new Texture("загруженное (27).png");
//        texture[28] = new Texture("загруженное (28).png");
//        texture[29] = new Texture("загруженное (29).png");
//        texture[30] = new Texture("загруженное (30).png");
//        texture[31] = new Texture("загруженное (31).png");
//        texture[32] = new Texture("загруженное (32).png");
        animation = new Animation<Texture>(0.15f, TexturesClass.i.textures);
        animation.setPlayMode(Animation.PlayMode.LOOP);
    }


    public void render() {
        elapsed += Gdx.graphics.getDeltaTime();
        Gdx.gl.glClearColor(0, 0.1f, 0.22f, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Texture currentFrame = animation.getKeyFrame(elapsed, true);
        batch.begin();
        batch.draw(currentFrame, 50, 50);
        batch.end();
    }

    public void dispose() {
        for (int i = 0; i < TexturesClass.i.textures.length - 1; i++) {
            TexturesClass.i.textures[i].dispose();
        }
    }
}