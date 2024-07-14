package com.tedu.element;

import com.tedu.manager.ElementManager;
import com.tedu.manager.GameLoad;

import javax.swing.*;
import java.awt.*;

public class PlayFile extends ElementObj {
    public static int atk = 1;
    private int hp = 1;
    private int Vx = 10;
    private String direction;
    PlayFile(){}
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
        this.setW(30);
        this.setH(30);
        return this;
    }
    @Override
    public void setHp(int i){
        hp = i;
    }

    @Override
    public int getHp(){
        return hp;
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
            case"up":this.setY(this.getY()-this.Vx);break;
            case"down":this.setY(this.getY()+this.Vx);break;
            case"left":this.setX(this.getX()-this.Vx);break;
            case"right":this.setX(this.getX()+this.Vx);break;
        }
    }
public int getAtk(){
    return atk;}
public void setAtk(int atk){
    PlayFile.atk = atk;}

}

