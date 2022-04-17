package com.example.share2dlibgdx;

public class Touch {
    Snake share;
    Bread bread;
    int widthScreen = 1280;
    int heightScreen = 720;

    public Touch(Snake share, Bread bread) {
        this.share = share;
        this.bread = bread;
    }

    public boolean touchPlays(Snake share, float share2X, float share2Y){
        if(share.cells.get(0).x+100 > share2X && share.cells.get(0).x < share2X+100 && share.cells.get(0).y+100 >share2Y && share.cells.get(0).y < share2Y+100){
            return true;
        }
        return false;
    }


    public boolean touchBred(){
        if(share.cells.get(0).x+100 > bread.x && share.cells.get(0).x < bread.x+100 && share.cells.get(0).y+100 > bread.y && share.cells.get(0).y < bread.y+100){
            return true;
        }
        return false;
    }

    public int touchScreen(){
        if(share.cells.get(0).x > widthScreen && share.cells.get(0).y < heightScreen){
            System.out.println("1");
//            Правая граница
            return 1;
        }
        if(share.cells.get(0).x < -50 && share.cells.get(0).y > 0){
            System.out.println("4");
//            Левая граница
            return 4;
        }
        if(share.cells.get(0).x > 0 && share.cells.get(0).y < -50){
            System.out.println("5");
//            Нижняя граница
            return 5;
        }
        if(share.cells.get(0).x < widthScreen-50 && share.cells.get(0).y > heightScreen){
            System.out.println(2);
//            Вверхняя граница
            return 2;
        }

        if(share.cells.get(0).x > widthScreen && share.cells.get(0).y > heightScreen){
            System.out.println("3");
//            Вверхний правый угол
            return 3;
        }
        if(share.cells.get(0).x < 0 && share.cells.get(0).y < 0){
            System.out.println("6");
//            нижний левый угол
            return 6;
        }
        return 0;
    }
}
