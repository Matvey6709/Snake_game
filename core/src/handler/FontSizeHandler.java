package handler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

import java.util.HashMap;
import java.util.Map;

public class FontSizeHandler {

    final String FONT_CHARS = "абвгдеёжзийклмнопрстуфхцчшщъыьэюяabcdefghijklmnopqrstuvwxyzАБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789][_!$%#@|\\/?-+=()*&.;:,{}\"´`'<>";

    public static FontSizeHandler INSTANCE = new FontSizeHandler("LRpixel.ttf");

    private final Map<Integer, BitmapFont> fonts = new HashMap<>();

    private final String fontPath;

    public FontSizeHandler(String fontPath) {
        this.fontPath = fontPath;
    }

    public BitmapFont getFont(final int size, Color color) {
        if (this.fonts.containsKey(size)) return this.fonts.get(size);
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Roboto-Black.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = size;
        parameter.characters = FONT_CHARS;

        BitmapFont font = generator.generateFont(parameter);

        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        font.setColor(color);

        this.fonts.put(size, font);
        return font;
    }
}