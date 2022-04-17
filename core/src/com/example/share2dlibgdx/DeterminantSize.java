package com.example.share2dlibgdx;

import com.badlogic.gdx.Gdx;

public class DeterminantSize {
    float height = Gdx.graphics.getHeight();
    float width = Gdx.graphics.getWidth();

    public int getWidthGame(int sizeX) {
//        return Integer.parseInt(String.format( "%.0f",(1280/width)*sizeX));
        return sizeX;
    }

    public int getHeightGame(int sizeY) {
//        return Integer.parseInt(String.format( "%.0f",(720/height)*sizeY));
        return sizeY;
    }

    public int getWidthGame2(int sizeX) {
//        return Integer.parseInt(String.format( "%.0f",(1280/width)*sizeX));
        return sizeX;
    }

    public int getHeightGame2(int sizeY) {
//        return Integer.parseInt(String.format( "%.0f",(720/height)*sizeY));
        return sizeY;
    }

    public float getKX(){
//        return 1280/width;
        return 1;
    }

    public float getKY(){
//        return 720/width;
        return 1;
    }
    public float getKY2(){
//        return 720/height;
        return 1;
    }
}
