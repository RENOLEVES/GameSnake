package com.zhengxuan99.snake;

public class Main {
    public static void main(String[] args) {
        SnakeFrame sf = new SnakeFrame();
        while (true) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            sf.repaint();
        }
    }
}
