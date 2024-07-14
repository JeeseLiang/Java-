package com.tedu.element;

import com.tedu.manager.GameLoad;

import java.awt.*;

public class Prize extends ElementObj{
    @Override
    public void showElement(Graphics g) {
        g.drawImage(this.getIcon().getImage(),
                this.getX(), this.getY(),
                this.getW(), this.getH(),null);
    }
    public Prize(){}
    @Override
    public ElementObj createElement(String str){
        System.out.println(str);
        String[] split = str.split(",");
        for(String s:split){
            String[] split2 = s.split(":");
            switch (split2[0]){
                case"x":this.setX(Integer.parseInt(split2[1]));break;
                case"y":this.setY(Integer.parseInt(split2[1]));;break;
                case"type":this.setIcon(GameLoad.imgMap.get(split2[1]));
            }
        }
        this.setW(30);
        this.setH(30);
        return this;
    }
}
