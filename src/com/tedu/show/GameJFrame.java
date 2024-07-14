package com.tedu.show;


//主要实现功能：关闭，显示，最大最小化
//需要嵌入面板，启动主程序等

import javax.swing.*;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class GameJFrame extends JFrame {
    public static int GameX = 1280;
    public static int GameY = 776;
    private JPanel jpanel = null;
    private KeyListener keyListener = null;
    private MouseMotionListener mouseMotionListener = null;
    private MouseListener mouseListener = null;
    private Thread thread = null;


    public GameJFrame(){
        init();
    }

    public void init(){
        this.setSize(GameX,GameY);
        this.setTitle("游戏标题");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
    }

    public void setJPanel(JPanel jpanel) {
        this.jpanel = jpanel;
    }

    public void setKeyListener(KeyListener keyListener) {
        this.keyListener = keyListener;
    }

    public void setMouseMotionListener(MouseMotionListener mouseMotionListener) {
        this.mouseMotionListener = mouseMotionListener;
    }

    public void setMouseListener(MouseListener mouseListener) {
        this.mouseListener = mouseListener;
    }

    public void setThread(Thread thread) {
        this.thread = thread;
    }
    public void addLayout(){
        //this.setLayout(manager);//布局格式，添加控件
    }
    //启动方法
    public void start(){
        if(jpanel!=null){
            this.add(jpanel);
        }
        if(keyListener!=null){
            this.addKeyListener(keyListener);
        }
        if(mouseListener!=null){
            this.addMouseListener(mouseListener);
        }
        if(mouseMotionListener!=null){
            this.addMouseMotionListener(mouseMotionListener);
        }
        if(thread!=null){
            thread.start();
        }
        this.setVisible(true);
        this.setResizable(false);
        if(this.jpanel instanceof Runnable){
            new Thread((Runnable)this.jpanel).start();
        }
    }
}

