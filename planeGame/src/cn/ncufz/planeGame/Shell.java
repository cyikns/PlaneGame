package cn.ncufz.planeGame;

import java.awt.*;

/**
 * 炮弹类
 *
 * @author cyikns
 * @create 2018-08-04 11:18
 */
public class Shell extends GameObject {

    double degree;

    public Shell() {
        x = 100;
        y = 100;
        width = 10;
        height = 10;
        speed = 4;

        degree = Math.random() * Math.PI * 2;
    }

    public void draw(Graphics g) {
        Color color = g.getColor();
        g.setColor(Color.ORANGE);
        g.fillOval((int) x, (int) y, width, height);

        x += speed * Math.cos(degree);
        y += speed * Math.sin(degree);


        if (x<width || x>Constant.GAME_WIDTH-width){
            degree = Math.PI-degree;
        }
        if (y<40 || y>Constant.GAME_HEIGHT-height){
            degree = -degree;
        }


        g.setColor(color);

    }
}
