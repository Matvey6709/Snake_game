package handler;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

public class FieldHandler {
    public static FieldHandler INSTANCE = new FieldHandler();

    public TextField createField(final String text, final int size, final Color color) {
        TextField.TextFieldStyle style = new TextField.TextFieldStyle();
        style.font = FontSizeHandler.INSTANCE.getFont(size, color);
        style.fontColor = color;
        TextField textField = new TextField(text, style);
        return textField;
    }
}
