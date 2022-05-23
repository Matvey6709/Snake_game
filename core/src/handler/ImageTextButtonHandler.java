package handler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class ImageTextButtonHandler {

    public static ImageTextButtonHandler INSTANCE = new ImageTextButtonHandler();

    float w;
    float h;

    public ImageTextButton createButtonWay(String way, final String text, final int size, final Color color, boolean l) {
        ImageTextButton end = new ImageTextButton("", imageTextButtonStyle(way, size));
        ImageTextButton.ImageTextButtonStyle style = new ImageTextButton.ImageTextButtonStyle();
        style.font = FontSizeHandler.INSTANCE.getFont(size, Color.WHITE);
        style.checked = end.getStyle().checked;
        style.up = end.getStyle().up;
        style.down = end.getStyle().down;
        final ImageTextButton imageTextButton = new ImageTextButton(text, style);
        if (l) {
            imageTextButton.addListener(new InputListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
//                imageTextButton.setWidth(imageTextButton.getWidth()*0.5f);
                    imageTextButton.setHeight(imageTextButton.getHeight() * 0.5f);
                    return true;
                }

                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
//                imageTextButton.setWidth(imageTextButton.getWidth()*2);
                    imageTextButton.setHeight(imageTextButton.getHeight() * 2);

                }
            });
        }

        return imageTextButton;
    }

    public ImageTextButton createButtonSprite(Sprite sprite, final String text, final int size, final Color color) {
        ImageTextButton end = new ImageTextButton("", imageTextButtonStyle(sprite, size));
        ImageTextButton.ImageTextButtonStyle style = new ImageTextButton.ImageTextButtonStyle();
        style.font = FontSizeHandler.INSTANCE.getFont(size, Color.WHITE);
        style.checked = end.getStyle().checked;
        style.up = end.getStyle().up;
        style.down = end.getStyle().down;
        final ImageTextButton imageTextButton = new ImageTextButton(text, style);
//        imageTextButton.addListener(new InputListener() {
//            @Override
//            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
////                imageTextButton.setWidth(imageTextButton.getWidth()*0.5f);
//                imageTextButton.setHeight(imageTextButton.getHeight() * 0.5f);
//                return true;
//            }
//
//            @Override
//            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
////                imageTextButton.setWidth(imageTextButton.getWidth()*2);
//                imageTextButton.setHeight(imageTextButton.getHeight() * 2);
//
//            }
//        });
        return imageTextButton;
    }

    public ImageTextButton.ImageTextButtonStyle imageTextButtonStyle(String way, int size) {
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

    public ImageTextButton.ImageTextButtonStyle imageTextButtonStyle(Sprite sprite, int size) {
        TextureRegion myTextureRegion = new TextureRegion(sprite);
        TextureRegionDrawable myTexRegionDrawable = new TextureRegionDrawable(myTextureRegion);
        ImageTextButton.ImageTextButtonStyle style = new ImageTextButton.ImageTextButtonStyle();
        style.up = myTexRegionDrawable;
        style.down = myTexRegionDrawable;
        style.checked = myTexRegionDrawable;
        style.font = FontSizeHandler.INSTANCE.getFont(size, Color.WHITE);
        return style;
    }
}
