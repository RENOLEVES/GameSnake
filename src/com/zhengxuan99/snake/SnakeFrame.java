package com.zhengxuan99.snake;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class SnakeFrame extends Frame {
    static final int GAME_WIDTH = 500, GAME_HEIGHT = 500;

    Dimension ScreenSize = getToolkit().getScreenSize();
    int ScreenWidth = (int) ScreenSize.getWidth();
    int ScreenHeight = (int) ScreenSize.getHeight();


    public SnakeFrame() {
        setSize(GAME_WIDTH, GAME_HEIGHT);
        setResizable(false);
        setTitle("Snake");
        setLocation(ScreenWidth/2-250,ScreenHeight/2-250);
        setVisible(true);

        addKeyListener(new MyKeyListener());
    }

    //双缓冲
    Image offScreenImage = null;

    @Override
    public void update(Graphics g) {
        if (offScreenImage == null) {
            offScreenImage = this.createImage(GAME_WIDTH, GAME_HEIGHT);
        }
        Graphics gOffscreen = offScreenImage.getGraphics();
        Color c = gOffscreen.getColor();
        gOffscreen.setColor(Color.WHITE);
        gOffscreen.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
        gOffscreen.setColor(c);
        paint(gOffscreen);
        g.drawImage(offScreenImage, 0, 0, null);
    }


    private class MyKeyListener implements KeyListener {

        boolean bU = false;
        boolean bD = false;
        boolean bL = false;
        boolean bR = false;

        @Override
        public void keyTyped(KeyEvent e) {}

        @Override
        public void keyPressed(KeyEvent e) {
            int Key =e.getKeyCode();

            switch (Key){
                case KeyEvent.VK_UP:
                    bU = true;
                case KeyEvent.VK_DOWN:
                    bD = true;
                case KeyEvent.VK_LEFT:
                    bL = true;
                case KeyEvent.VK_RIGHT:
                    bR = true;
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {

        }
    }


}

enum Dir{UP,DOWN,LEFT,RIGHT}

class Snake{
    int x,y;
    static final int WIDTH = 20, HEIGHT = 20;
    Dir dir = Dir.RIGHT;
    Rectangle rect = new Rectangle();

    public Snake(int x, int y, Dir dir, Rectangle rect) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.rect = rect;

        rect.x = x;
        rect.y = y;
        rect.width = WIDTH;
        rect.height = HEIGHT;
    }
}
