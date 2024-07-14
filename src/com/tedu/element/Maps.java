package com.tedu.element;

import com.tedu.manager.GameLoad;

import javax.swing.*;
import java.awt.*;

public class Maps extends ElementObj{
    private int hp = 100000;
    private String name;
    private String direction = "";

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    @Override
    public void showElement(Graphics g) {
        g.drawImage(this.getIcon().getImage(),
                this.getX(), this.getY(),
                this.getW(),this.getH(),null);
    }
    @Override
    public ElementObj createElement(String str) {
        String []arr=str.split(",");
        int x=Integer.parseInt(arr[1]);
        int y=Integer.parseInt(arr[2]);
        this.setH(32);
        this.setW(32);
        this.setX(x);
        this.setY(y);
        this.setIcon(GameLoad.imgMap.get(arr[0]));
        return this;
    }

    @Override
    public void setLive(boolean live){
        this.hp--;
        if(this.hp>0){
            return;
        }
        super.setLive(live);
    }
}
