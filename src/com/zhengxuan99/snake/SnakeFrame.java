package com.zhengxuan99.snake;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SnakeFrame extends Frame {
    static final int GAME_WIDTH = 500, GAME_HEIGHT = 500;

    Snake snake = new Snake(50, 50, Dir.DOWN);

    List<Food> food = new ArrayList<Food>();
    Dimension ScreenSize = getToolkit().getScreenSize();
    int ScreenWidth = (int) ScreenSize.getWidth();
    int ScreenHeight = (int) ScreenSize.getHeight();

    public SnakeFrame() {
        setSize(GAME_WIDTH, GAME_HEIGHT);
        setResizable(false);
        setTitle("Snake");
        setLocation(ScreenWidth / 2 - 250, ScreenHeight / 2 - 250);
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


    private class MyKeyListener extends KeyAdapter {

        boolean bU = false;
        boolean bD = false;
        boolean bL = false;
        boolean bR = false;

        @Override
        public void keyPressed(KeyEvent e) {
            int Key = e.getKeyCode();

            switch (Key) {
                case KeyEvent.VK_UP:
                    bU = true;
                    bD = bL = bR = false;
                    break;
                case KeyEvent.VK_DOWN:
                    bD = true;
                    bU = bL = bR = false;
                    break;
                case KeyEvent.VK_LEFT:
                    bL = true;
                    bD = bU = bR = false;
                    break;
                case KeyEvent.VK_RIGHT:
                    bR = true;
                    bD = bL = bU = false;
                    break;
            }

            setMainDirection();
        }

        private void setMainDirection() {
            if (!bU && !bD && !bL && !bR) snake.setMoving(false);
            else {
                snake.setMoving(true);
                if (bL) snake.setDir(Dir.LEFT);
                if (bU) snake.setDir(Dir.UP);
                if (bR) snake.setDir(Dir.RIGHT);
                if (bD) snake.setDir(Dir.DOWN);
            }

        }

        @Override
        public void keyReleased(KeyEvent e) {
        }
    }

    public void paint(Graphics g) {

        snake.paint(g);

        for(int i= 0;i<food.size();i++){
            food.get(i).paint(g);
        }

        for(int i= 0;i<food.size();i++){
            food.get(i).collidewith(snake);
        }
    }
}

enum Dir {UP, DOWN, LEFT, RIGHT}

class Snake {
    int x, y;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWIDTH() {
        return WIDTH;
    }

    public int getHEIGHT() {
        return HEIGHT;
    }

    public static final int WIDTH = 30, HEIGHT = 30;
    private static final int SPEED = 10;
    Dir dir = Dir.RIGHT;
    Rectangle rect = new Rectangle();

    private boolean moving = false;

    boolean ismoving() {
        return moving;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    public Dir getDir() {
        return dir;
    }

    public void setDir(Dir dir) {
        this.dir = dir;
    }

    public Snake(int x, int y, Dir dir) {
        this.x = x;
        this.y = y;
        this.dir = dir;
    }

    public void move() {
        switch (dir) {
            case UP:
                y -= SPEED;
                break;
            case DOWN:
                y += SPEED;
                break;
            case LEFT:
                x -= SPEED;
                break;
            case RIGHT:
                x += SPEED;
                break;
        }
    }

    public void paint(Graphics g) {
        Color c = g.getColor();
        g.setColor(Color.BLACK);
        g.setColor(c);
        g.fillOval(x, y, WIDTH, HEIGHT);

        move();
    }
}

class Food {
    Random random = new Random();
    private int x;
    private int y;
    private final int  WIDTH=20 ,HEIGHT = 20;
    private boolean exist = true;
    SnakeFrame sf;


    public Food(int x, int y,SnakeFrame sf) {
        this.x = x;
        this.y = y;
        this.sf = sf;

        sf.food.add(this);
    }


    private void spawn() {
        if (!exist) {
            int r1 = random.nextInt(500);
            int r2 = random.nextInt(500);
            x = r1;
            y = r2;
        } 
    }

    public void paint(Graphics g) {

        if (!exist) {
            sf.food.remove(this);
            System.out.printf("陈工");
        }

        Color c = g.getColor();
        g.setColor(Color.YELLOW);
        g.setColor(c);
        g.fillOval(x, y, WIDTH, HEIGHT);

        spawn();
    }

    public void collidewith(Snake snake) {

        //TODO:用一个rect来记录food的位置
        Rectangle rect1 = new Rectangle(x,y,WIDTH,HEIGHT);
        Rectangle rect2 = new Rectangle(snake.getX(),snake.getY(),snake.getWIDTH(),snake.getHEIGHT());
        if(rect1.intersects(rect2)){
            this.die();
        }
    }

    private void die() {
        this.exist = false;
    }
}
