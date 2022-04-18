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

    public void test(SpriteBatch batch) {
        this.batch = batch;
        cells = new ArrayList<>();
        size = new DeterminantSize();
        cells.add(new Cell(0, 0, size.getWidthGame(100), size.getHeightGame(100)));
        cells.add(new Cell(0, 0, size.getWidthGame(100), size.getHeightGame(100)));

        texture = new Texture("BlueS2.png");
    }

    public void render(Player players, Snake share) {
        this.players = players;
        try {
            if (players != null) {
                String[] corXY = players.getStr().split(" ");
                for (int i = cells.size() - 1; i > 0; i--) {
                    Cell nextBody = cells.get(i - 1);
                    Cell body = cells.get(i);
                    body.x = nextBody.getX();
                    body.y = nextBody.getY();
                }
                cells.get(0).x = Float.parseFloat(corXY[0]);
                cells.get(0).y = Float.parseFloat(corXY[1]);

                if (cells.size()-1 < players.getLevel() && players != null) {
                    addLevel();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Ошибка в ServerUpdate");
        }

    }

    public void render2() {
        for (int i = 0; i < cells.size() - 1; i++) {
            batch.draw(texture, cells.get(i).x, cells.get(i).y, cells.get(i).sizeX, cells.get(i).sizeY);
        }
//        batch.draw(texture, cells.get(0).x, cells.get(0).y, cells.get(0).sizeX, cells.get(0).sizeY);
    }

    public void addLevel() {
        cells.add(new Cell(0, 0, size.getWidthGame(100), size.getHeightGame(100)));
    }

    public void dispose() {
        texture.dispose();
        batch.dispose();
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
