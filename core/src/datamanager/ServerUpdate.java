package datamanager;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.example.share2dlibgdx.Cell;
import com.example.share2dlibgdx.DeterminantSize;
import com.example.share2dlibgdx.Snake;
import com.example.share2dlibgdx.TexturesClass;
import com.example.share2dlibgdx.game;

import java.util.LinkedList;

public class ServerUpdate {
    public LinkedList<Cell> cells;
    Player players;
    DeterminantSize size;
    public String namePlayer = "";
    SpriteBatch batch;
    public int appleX, appleY, keyEnemy = -1, level = 1;
//    Texture headY, bodyY, texture, blueApple;


    public void test(SpriteBatch batch, boolean create) {
        this.batch = batch;
        cells = new LinkedList<>();
        size = new DeterminantSize();
        for (int i = 0; i < 35; i++) {
            cells.add(new Cell(-100, -100, size.getWidthGame(100) / 3, size.getHeightGame(100) / 3));
        }


//        texture = new Texture("BlueS2.png");
//        blueApple = new Texture("appleBlue.png");
//        headY = new Texture("headY.png");
//        bodyY = new Texture("bodyE.png");
    }

    game game;

    public void testBl(SpriteBatch batch, game game) {
        this.batch = batch;
        this.game = game;
        cells = new LinkedList<>();
        size = new DeterminantSize();
        level = 1;
        for (int i = 0; i < 4; i++) {
            cells.add(new Cell(-100, -100, size.getWidthGame(100) / 3, size.getHeightGame(100) / 3));
        }


//        texture = new Texture("BlueS2.png");
//        blueApple = new Texture("appleBlue.png");
//        headY = new Texture("headY.png");
//        bodyY = new Texture("bodyE.png");
    }

    public void render(Player players, Snake share) {
        this.players = players;
        try {
            if (players != null) {

                appleX = players.getAppleX();
                appleY = players.getAppleY();
                String[] corXY = players.getCords().split(" ");
                level = Integer.parseInt(players.getLevel());
                for (int i = 0; i < corXY.length; i += 2) {
                    cells.get(i).x = Float.parseFloat(corXY[i]);
                    cells.get(i).y = Float.parseFloat(corXY[i + 1]);
                }
            }
        } catch (Exception e) {
        }
    }


    public void renderBl(Player players, Snake share) {
        this.players = players;
        try {
            if (players != null) {
                String[] corXY = players.getCords().split(" ");
                for (int i = cells.size() - 1; i > 0; i--) {
                    Cell nextBody = cells.get(i - 1);
                    Cell body = cells.get(i);
                    body.x = nextBody.getX();
                    body.y = nextBody.getY();
                }
                cells.get(0).x = Float.parseFloat(corXY[0]);
                cells.get(0).y = Float.parseFloat(corXY[1]);

                appleX = Integer.parseInt(corXY[3]);
                appleY = Integer.parseInt(corXY[4]);

                keyEnemy = Integer.parseInt(corXY[5]);

                namePlayer = corXY[6];

                if (level < Integer.parseInt(corXY[2]) && players != null) {
                    addLevel();
                    cells.add(new Cell(0, 0, size.getWidthGame(100) / 3, size.getHeightGame(100) / 3));
                    cells.add(new Cell(0, 0, size.getWidthGame(100) / 3, size.getHeightGame(100) / 3));
                }
                if (level > Integer.parseInt(corXY[2]) && players != null) {
                    level--;
                    cells.remove(cells.size() - 1);
                    cells.remove(cells.size() - 1);
                    cells.remove(cells.size() - 1);
                }
            }
        } catch (Exception e) {
        }
    }

    public void render2() {
        batch.draw(TexturesClass.i.blueApple, appleX, appleY, 33, 33);
        for (int i = 0; i < cells.size() - 1; i++) {
            batch.draw(getBodyType2(i), cells.get(i).x, cells.get(i).y, cells.get(i).sizeX, cells.get(i).sizeY);
        }


    }

    public void render3() {
        batch.draw(TexturesClass.i.blueApple, appleX, appleY, 33, 33);
        for (int i = 0; i < cells.size() - 1; i++) {
            batch.draw(TexturesClass.i.texture, cells.get(i).x, cells.get(i).y, cells.get(i).sizeX, cells.get(i).sizeY);
        }


    }

    public void addLevel() {
        cells.add(new Cell(0, 0, size.getWidthGame(100) / 3, size.getHeightGame(100) / 3));
        level++;
    }


    public void dispose() {
        TexturesClass.i.texture.dispose();
        batch.dispose();
        TexturesClass.i.blueApple.dispose();
        TexturesClass.i.headY.dispose();
        TexturesClass.i.bodyY.dispose();
    }

    public int getLevel() {
        return level;
    }

    public int getLevel2() {
        return level * 3;
    }

    public String getNamePlayer() {
        if (players != null) {
            return players.getName();
        }
        return null;
    }

    public String getNamePlayer2() {
        if (players != null) {
            return namePlayer;
        }
        return null;
    }

    private String getBodyType(int index) {
        if (index == cells.size()) return "snake_body";
        if (index == 0) return "snake_head";
        else return "snake_tail";
    }

    private Texture getBodyType2(int index) {
        if (index == 0) return TexturesClass.i.headY;
        else return TexturesClass.i.bodyY;
    }

    public int getKeyEnemy() {
        return keyEnemy;
    }
}
