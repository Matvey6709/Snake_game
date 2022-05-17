package com.example.share2dlibgdx;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Toast;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;

import java.io.FileNotFoundException;
import java.io.IOException;

import datamanager.InterfaceDataLoaded;
import datamanager.Player;

public class AndroidLauncher extends AndroidApplication {

    FireBaseDataBase base;
    InterfaceDataLoaded loaded;
    final AndroidLauncher context = this;
    public static final int GET_FROM_GALLERY = 3;
    Texture tex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        loaded = new InterfaceDataLoaded() {
            @Override
            public void create() {
                base = new FireBaseDataBase();
                base.Create();
            }

            @Override
            public Player requestData(String nameGame, String namePlayer, Snake share) {
                return base.requestData(nameGame, namePlayer, share);
            }

            @Override
            public Player requestData2() {
                return base.requestData2();
            }


            @Override
            public void put(String nameGame, String namePlayer, String vector2, String level, int appleX, int appleY) {
                base.putData(nameGame, namePlayer, vector2, level, appleX, appleY);
            }

            @Override
            public String checkStartPlay() {
                return base.checkStartPlay();
            }

            @Override
            public String checkStartPlay2() {
                return base.checkStartPlay2();
            }

            @Override
            public int countPlayersGames(String nameGame) {
                return base.countPlayersGames(nameGame);
            }

            @Override
            public int countPlayersGames2() {
                return base.countPlayersGames2();
            }

            @Override
            public void dispose() {
                base.dispose();
            }

            @Override
            public void dispose2() {
                base.dispose2();
            }

            @Override
            public boolean isOnline() {
                return isNetworkConnected();
            }

            @Override
            public void isOnline2(String nameGame, String namePlayer) {
                base.isOnline2(nameGame, namePlayer);
            }

            @Override
            public void toast(String text) {
                handler.post(() -> Toast.makeText(context, text, Toast.LENGTH_SHORT).show());
            }

            @Override
            public void delete(String nameGame) {
                base.delete(nameGame);
            }

            @Override
            public void isExistsGame(String nGame, String namePlayer2) {
                base.isExistsGame(nGame, namePlayer2);
            }

            @Override
            public boolean isExistsGame2() {
                return base.isExistsGame2();
            }

            @Override
            public boolean isNamePlayer(String namePlayer) {
                return false;
            }

            @Override
            public void putNamePlayer(String namePlayer) {

            }

            @Override
            public void setExistsGame(boolean existsGame) {
                base.setExistsGame(existsGame);
            }

            @Override
            public void Photo() {
                startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI), GET_FROM_GALLERY);
            }

            @Override
            public Texture getPhoto() {
                return tex;
            }

            @Override
            public void setPhoto() {
                tex = null;
            }

        };
//        Toast.makeText(this, "Чтобы избежать ошибок, придумайте разные имена игроков", Toast.LENGTH_LONG).show();
        initialize(new game(loaded), config);
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if (ni == null) {
            return false;
        } else
            return true;
    }

    Bitmap bitmap;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        System.out.println(1);
        //Detects request codes
        if (requestCode == GET_FROM_GALLERY && resultCode == Activity.RESULT_OK) {
            Uri selectedImage = data.getData();
            bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            System.out.println(bitmap.getWidth());
            if (bitmap.getWidth() > 1600 || bitmap.getHeight() > 1600) {
                bitmap = Bitmap.createScaledBitmap(bitmap, 1600,
                        1600, false);
            }
            Gdx.app.postRunnable(new Runnable() {
                @Override
                public void run() {
                    tex = new Texture(bitmap.getWidth(), bitmap.getHeight(), Pixmap.Format.RGBA8888);
                    GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, tex.getTextureObjectHandle());
                    GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);
                    GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0);
                    bitmap.recycle();
                }
            });

        }
    }


}

