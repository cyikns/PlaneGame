package cn.ncufz.planeGame;

import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * 飞机
 *
 * @author cyikns
 * @create 2018-08-04 9:54
 */
public class Plane extends GameObject {

    int speed = 3;
    boolean left, up, right, down;

    @Override
    protected void drawSelf(Graphics g) {
        g.drawImage(image, (int) x, (int) y, null);

        if (left) {
            x -= speed;
        }
        if (up) {
            y -= speed;
        }
        if (right) {
            x += speed;
        }
        if (down) {
            y += speed;
        }
    }

    public Plane(Image image, double x, double y) {
        super(image, x, y);
    }

    //增加方向
    public void addDirection(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                left = true;
                break;
            case KeyEvent.VK_UP:
                up = true;
                break;
            case KeyEvent.VK_RIGHT:
                right = true;
                break;
            case KeyEvent.VK_DOWN:
                down = true;
                break;
        }


    }

    //减去方向
    public void minusDirection(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                left = false;
                break;
            case KeyEvent.VK_UP:
                up = false;
                break;
            case KeyEvent.VK_RIGHT:
                right = false;
                break;
            case KeyEvent.VK_DOWN:
                down = false;
                break;
        }
    }

}
