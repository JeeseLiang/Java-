package com.tedu.element;

import com.tedu.manager.GameLoad;

import javax.swing.*;
import java.awt.*;

public class GameStart extends ElementObj{
    @Override
    public void showElement(Graphics g) {
        if(this.getIcon().getImage()!=null){
        g.drawImage(this.getIcon().getImage(),
                this.getX(), this.getY(),
                this.getW(),this.getH(),null);
        }

    }
    @Override
    public ElementObj createElement(String str) {
        this.setX(0);
        this.setY(0);
        this.setW(1280);
        this.setH(780);
        this.setIcon(GameLoad.imgMap.get("Start"));
        return this;
    }
    public void keyClick(boolean keyType, int keyCode) {
        if(!keyType) this.setLive(false);
    }
}
