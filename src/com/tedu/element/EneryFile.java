package com.tedu.element;

import com.tedu.manager.ElementManager;
import com.tedu.manager.GameLoad;

import javax.swing.*;
import java.awt.*;

public class EneryFile extends ElementObj {

    private int attack = 1;
    private int speed = 5;
    private String direction;
    EneryFile(){}
    @Override
    public ElementObj createElement(String str){
        String[] split = str.split(",");
        for(String s:split){
            String[] split2 = s.split(":");
            switch (split2[0]){
                case"x":this.setX(Integer.parseInt(split2[1]));break;
                case"y":this.setY(Integer.parseInt(split2[1]));break;
                case"direction":this.direction = split2[1];break;
                case"type":this.setIcon(GameLoad.imgMap.get(split2[1]));
            }
        }
        this.setW(40);
        this.setH(40);
        return this;
    }
    @Override
    public void setHp(int i){
        attack = i;
    }

    @Override
    public int getHp(){
        return attack;
    }
    @Override
    public void showElement(Graphics g) {
        g.drawImage(this.getIcon().getImage(),
                this.getX(), this.getY(),
                this.getW(), this.getH(),null);
    }
    @Override
    protected void move(){
        if(this.getX()<0 || this.getY()<0 || this.getX()>=1280||this.getY()>=720){
            this.setLive(false);
            return;
        }
        switch (this.direction){
            case"up":this.setY(this.getY()-this.speed);break;
            case"down":this.setY(this.getY()+this.speed);break;
            case"left":this.setX(this.getX()-this.speed);break;
            case"right":this.setX(this.getX()+this.speed);break;
        }
    }
}

