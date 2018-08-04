package cn.ncufz.planeGame;


import java.awt.*;
import java.awt.event.*;

/**
 * 飞机游戏窗口类
 *
 * @author cyikns
 * @create 2018-08-04 0:39
 */
public class PlaneGameFrame extends Frame {


    Image planeImg = GameUtil.getImage("images/plane.png");
    Image bg = GameUtil.getImage("images/bg.jpg");

    Plane plane = new Plane(planeImg, 250, 250);
    //    Shell shell = new Shell();
    Shell[] shells = new Shell[56];

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        g.drawImage(bg, 0, 0, null);

        plane.drawSelf(g);

        for (int i = 0; i < shells.length; i++) {
            shells[i].draw(g);
        }
    }


    class PaintThread extends Thread {
        @Override
        public void run() {
            while (true) {
                repaint();
                try {
                    Thread.sleep(40);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //初始化游戏窗口
    public void launchFrame() {

        this.setTitle("飞机世界大战Java版");
        this.setVisible(true);

        this.setSize(Constant.GAME_WIDTH, Constant.GAME_HEIGHT);
        this.setLocation(200, 200);

        //监听窗口关闭
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        //开始线程
        new PaintThread().start();

        //增加键盘监听
        addKeyListener(new KeyMonitor());

        //初始化炮弹
        for (int i = 0; i < shells.length; i++) {
            shells[i] = new Shell();
        }
    }


    //键盘监听类
    class KeyMonitor extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            plane.addDirection(e);
        }

        @Override
        public void keyReleased(KeyEvent e) {

            plane.minusDirection(e);
        }
    }

    public static void main(String[] args) {
        PlaneGameFrame frame = new PlaneGameFrame();
        frame.launchFrame();

    }

    //解决闪屏问题，采用先在内存中画，然后复制到画布上
    private Image offScreenImage = null;

    public void update(Graphics g) {
        if (offScreenImage == null)
            offScreenImage = this.createImage(Constant.GAME_WIDTH, Constant.GAME_HEIGHT);//这是游戏窗口的宽度和高度

        Graphics gOff = offScreenImage.getGraphics();
        paint(gOff);
        g.drawImage(offScreenImage, 0, 0, null);
    }

}
