package cn.ncufz.planeGame;


import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Date;

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

    Explode bao;

    Date startTime = new Date();
    Date endTime;
    int period;

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        Color c = g.getColor();
        Font font = g.getFont();

        g.drawImage(bg, 0, 0, null);

        plane.drawSelf(g);

        for (int i = 0; i < shells.length; i++) {
            shells[i].draw(g);

            //飞机和炮弹的碰撞检测！！！
            boolean peng = shells[i].getRect().intersects(plane.getRect());
//            boolean  peng = shells[i].getRect().intersects(plane.getRect());

            if (peng) {
                System.out.println("发生碰撞了");
                plane.live = false;

                if (bao == null) {
                    bao = new Explode(plane.x, plane.y);

                    endTime = new Date();

                    period = (int) ((endTime.getTime() - startTime.getTime()) / 1000);
                }

                bao.draw(g);
            }

            //计时功能开启，给出提示

            if (!plane.live){

                g.setColor(Color.RED);
                Font f = new Font("微软雅黑",Font.BOLD,22);
                g.setFont(f);
                g.drawString("时间："+period+"秒",250,250);
            }

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
