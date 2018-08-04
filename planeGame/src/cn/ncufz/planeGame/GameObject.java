package cn.ncufz.planeGame;

import java.awt.*;

/**
 * @author cyikns
 * @create 2018-08-04 9:33
 */
public class GameObject {

   Image image;
   double x,y;
   int width,height;
   int speed;

    //画自己
    protected void drawSelf(Graphics g){
        g.drawImage(image,(int) x,(int) y,null);
    }


    public GameObject() {
    }

    public GameObject(Image image, double x, double y) {
        this.image = image;
        this.x = x;
        this.y = y;
    }

    public GameObject(Image image, double x, double y, int width, int height, int speed) {
        this.image = image;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.speed = speed;
    }

    /**
     * 返回物体所在的矩形，便于后续的碰撞检测
     * @return
     */
    public Rectangle getRec(){
        return new Rectangle((int) x,(int) y,width,height);
    }
}
