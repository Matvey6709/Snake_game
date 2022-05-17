package datamanager;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.example.share2dlibgdx.Asset;
import com.example.share2dlibgdx.Cell;
import com.example.share2dlibgdx.DeterminantSize;
import com.example.share2dlibgdx.Snake;

import java.util.LinkedList;

public class ServerUpdate {
    public LinkedList<Cell> cells;
    Player players;
    DeterminantSize size;
    public int level = 1;
    public String namePlayer = "";
    SpriteBatch batch;
    Texture texture;
    Texture blueApple;
    int appleX;
    int appleY;
    float x2;
    float y2;


    public void test(SpriteBatch batch, boolean create) {
        this.batch = batch;
        cells = new LinkedList<>();
        size = new DeterminantSize();
        for (int i = 0; i < 35; i++) {
            cells.add(new Cell(-100, -100, size.getWidthGame(100) / 3, size.getHeightGame(100) / 3));
        }


        texture = new Texture("BlueS2.png");
        //
        blueApple = new Texture("appleBlue.png");


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
//                if (cells.get(0).x != Float.parseFloat(corXY[0]) || cells.get(0).y != Float.parseFloat(corXY[1])) {
////                    for (int i = cells.size() - 1; i > 0; i--) {
////                        Cell nextBody = cells.get(i - 1);
////                        Cell body = cells.get(i);
////                        body.x = nextBody.getX();
////                        body.y = nextBody.getY();
////                    }
//                } else {
////                    for (int i = cells.size() - 1; i > 0; i--) {
////                        cells.get(i).x = -50;
////                        cells.get(i).y = -50;
////                    }
//                }

//                cells.get(0).x = Float.parseFloat(corXY[0]);
//                cells.get(0).y = Float.parseFloat(corXY[1]);


//                if (level < Integer.parseInt(players.getLevel()) && players != null) {
//                    addLevel();
//                    cells.add(new Cell(0, 0, size.getWidthGame(100) / 3, size.getHeightGame(100) / 3));
//                    cells.add(new Cell(0, 0, size.getWidthGame(100) / 3, size.getHeightGame(100) / 3));
//                }
            }
        } catch (Exception e) {
//            e.printStackTrace();
        }
    }

    public void render2() {

        for (int i = 0; i < cells.size() - 1; i++) {
            batch.draw(Asset.instance().getSprite(getBodyType(i)), cells.get(i).x, cells.get(i).y, cells.get(i).sizeX, cells.get(i).sizeY);
        }

        batch.draw(blueApple, appleX, appleY, 100 / 2, 100 / 2);
    }

    public void render3() {

        for (int i = 0; i < cells.size() - 1; i++) {
            batch.draw(texture, cells.get(i).x, cells.get(i).y, cells.get(i).sizeX, cells.get(i).sizeY);
        }

        batch.draw(blueApple, appleX, appleY, 100 / 2, 100 / 2);
    }

    public void addLevel() {
        cells.add(new Cell(0, 0, size.getWidthGame(100) / 3, size.getHeightGame(100) / 3));
        level++;
    }


    public void dispose() {
        texture.dispose();
        batch.dispose();
        blueApple.dispose();
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

    private String getBodyType(int index) {
        if (index == cells.size()) return "snake_body";
        if (index == 0) return "snake_head";
        else return "snake_tail";
    }
}
