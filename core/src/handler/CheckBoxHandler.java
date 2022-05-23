package handler;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class CheckBoxHandler {

    public static CheckBoxHandler INSTANCE = new CheckBoxHandler();

    public CheckBox createCheckBox(final String text, Skin skin, final int size, final Color color) {
        CheckBox checkBox = new CheckBox("", skin);
        CheckBox.CheckBoxStyle style = checkBox.getStyle();
        style.font = FontSizeHandler.INSTANCE.getFont(size, color);
        style.fontColor = color;
        style.checkboxOff = new TextureRegionDrawable(new TextureRegion(new Texture("colorG (1).jpg")));
        return new CheckBox(text, style);
    }
}
