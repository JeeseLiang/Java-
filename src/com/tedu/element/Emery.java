package com.tedu.element;

import com.tedu.manager.ElementManager;
import com.tedu.manager.GameElement;
import com.tedu.manager.GameLoad;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

public class Emery extends ElementObj {
    private int hp = 10;
    private int speed = 1;
    private String direction = "right";
    private boolean randN = false;
    private int changeTime = 0;
    private int localTime = 0;
    private String type = "";
    private int div = 4;

    private int left;
    private int right;


    @Override
    public ElementObj createElement(String s){
        //System.out.println(s);
        String[] strs = s.split(",");
        String str = strs[0];
        this.setX(Integer.parseInt(strs[1]));
        this.setY(Integer.parseInt(strs[2]));
        this.setH(str.equals("Boss")?85:50);
        this.setW(str.equals("Boss")?85:50);
        this.setIcon(GameLoad.imgMap.get(str));
        if(str.equals("Boss")) hp = 100;
        type = str;
        left = Integer.parseInt(strs[3]);
        right = Integer.parseInt(strs[4]);
        return this;
    }
    @Override
    public void setHp(int hp) {
        this.hp = hp;
    }
    @Override
    public int getHp(){
        return hp;
    }

    @Override
    public void showElement(Graphics g) {

        int w = (localTime/5)%3;
        if(type.equals("Boss")){
            div = 6;
        }
        if(this.getIcon().getImage()!=null){
            g.drawImage(this.getIcon().getImage(), this.getX(), this.getY(), this.getH() + this.getX(),
                    this.getW() + this.getY(),
                    this.getIcon().getIconWidth()/div * w, this.getIcon().getIconHeight()/4 * GameLoad.direction.get(direction),
                    this.getIcon().getIconWidth()/div * (w+1), this.getIcon().getIconHeight()/4 * (GameLoad.direction.get(direction)+1),
                    null);
        }


        Font originalFont = g.getFont();
        g.setFont(new Font("Arial", Font.PLAIN, 20));

        String healthText = "HP: " + this.getHp();
        FontMetrics metrics = g.getFontMetrics();

        // 计算文本绘制位置
        int textWidth = metrics.stringWidth(healthText);
        int x = this.getX() + (this.getW() - textWidth) / 2;
        int y = this.getY() - metrics.getHeight() + 20;
        g.setColor(Color.RED);
        g.drawString(healthText, x, y);
        g.setFont(originalFont);

        localTime++;

    }
    protected void move() {
        changeTime++;
        if(this.getX()<left || this.getY()<0 || this.getX()>=right||this.getY()>=700){
            randN = !randN;
        }
        if (randN) {
            this.setX(this.getX() + this.speed);
            this.direction = "right";
        } else {
            this.setX(this.getX() - this.speed);
            this.direction = "left";
        }
        if(changeTime%50==0){
            Random r = new Random();
            if(r.nextBoolean()) randN =!randN;
        }
    }

    public int getSpeed() {
        return speed;
    }

    public String getDirection() {
        return direction;
    }

    //

    protected void shout(long gobalTime){
        if(type.equals("Boss") && gobalTime - localTime>=100){
            localTime+=10;
            Random r = new Random();
            if(r.nextInt(10)<3){
                ElementObj element = new EneryFile().createElement(this.toString());
                ElementManager.getManager().addElement(element, GameElement.ENERYFILE);
            }
        }
    }
    @Override
    public String toString(){
        int x = this.getX();
        int y = this.getY();
        switch (this.direction){
            case"left":y+=30;break;
            case"right":x+=50;y+=30;break;
        }
        return "x:"+x+",y:"+y+",direction:"+this.direction+",type:bullet3";
    }

    @Override
    public void peng(){
        if (this.getDirection() != "") {
            if (this.isDown()) {
                this.setY(this.getY() - this.getSpeed() + 1);
                this.setDown(false);
                this.resetSpeed();
            }
            if (this.isUp()) {
                this.setY(this.getY() + this.getSpeed());
                this.setUp(false);
                this.setDown(true);
            }
            if (this.isLeft()) {
                this.setX(this.getX() + 5);
                this.setDown(true);
            }
            if (this.isRight()) {
                this.setX(this.getX() - 5);
                this.setDown(true);
            }
        }
    }
}

