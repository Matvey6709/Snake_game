package datamanager;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.example.share2dlibgdx.Cell;
import com.example.share2dlibgdx.DeterminantSize;
import com.example.share2dlibgdx.Snake;

import java.util.ArrayList;

public class ServerUpdate {
    public ArrayList<Cell> cells;
    Player players;
    DeterminantSize size;
    public int level;
    public String namePlayer = "";
    SpriteBatch batch;
    Texture texture;
    Texture blueApple;
    int appleX;
    int appleY;

    public void test(SpriteBatch batch) {
        this.batch = batch;
        cells = new ArrayList<>();
        size = new DeterminantSize();
        cells.add(new Cell(0, 0, size.getWidthGame(100), size.getHeightGame(100)));
        cells.add(new Cell(0, 0, size.getWidthGame(100), size.getHeightGame(100)));

        texture = new Texture("BlueS2.png");
        blueApple = new Texture("appleBlue.png");
        //dded
    }

    public void render(Player players, Snake share) {
        this.players = players;
        try {
            if (players != null) {
                String[] corXY = players.getCords().split("h");
                for (int i = cells.size() - 1; i > 0; i--) {
                    Cell nextBody = cells.get(i - 1);
                    Cell body = cells.get(i);
                    body.x = nextBody.getX();
                    body.y = nextBody.getY();
                }
                cells.get(0).x = Float.parseFloat(corXY[0]);
                cells.get(0).y = Float.parseFloat(corXY[1]);

                appleX = players.getAppleX();
                appleY = players.getAppleY();

                if (cells.size() - 1 < Integer.parseInt(players.getLevel()) && players != null) {
                    addLevel();
                }
            }
        } catch (Exception e) {
//            e.printStackTrace();
        }

    }

    public void render2() {
        for (int i = 0; i < cells.size() - 1; i++) {
            batch.draw(texture, cells.get(i).x, cells.get(i).y, cells.get(i).sizeX, cells.get(i).sizeY);
        }
        batch.draw(blueApple, appleX, appleY, 100, 100);
    }

    public void addLevel() {
        cells.add(new Cell(0, 0, size.getWidthGame(100), size.getHeightGame(100)));
    }

    public void dispose() {
        texture.dispose();
        batch.dispose();
        blueApple.dispose();
    }

    public int getLevel() {
        return cells.size();
    }

    public String getNamePlayer() {
        if(players != null){
            return players.getName();
        }
        return null;
    }
}
