package com.tedu.element;

import com.tedu.manager.GameLoad;

import java.awt.*;

public class NextGame extends ElementObj{
    @Override
    public void showElement(Graphics g) {
        g.drawImage(this.getIcon().getImage(),
                this.getX(), this.getY(),
                this.getW(),this.getH(),null);
    }
    @Override
    public ElementObj createElement(String str) {
        this.setX(0);
        this.setY(200);
        this.setW(1280);
        this.setH(300);
        this.setIcon(GameLoad.imgMap.get("victory"));
        return this;
    }
}
