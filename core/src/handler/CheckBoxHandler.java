package handler;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class CheckBoxHandler {

    public static CheckBoxHandler INSTANCE = new CheckBoxHandler();

    public CheckBox createCheckBox(final String text, Skin skin, final int size, final Color color) {
        CheckBox checkBox = new CheckBox("", skin);
        CheckBox.CheckBoxStyle style = checkBox.getStyle();
        style.font = FontSizeHandler.INSTANCE.getFont(size, color);
        style.fontColor = color;
        return new CheckBox(text, style);
    }
}
