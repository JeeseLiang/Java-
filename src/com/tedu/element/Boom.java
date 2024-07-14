package com.tedu.element;

import com.tedu.manager.GameLoad;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Boom extends ElementObj{
    private int localTime = 0;
    private Image img;

    ////g.drawImage(this.getIcon().getImage(),
    //    //                this.getX(), this.getY(),
    //    //                this.getW(), this.getH(),null);
    @Override
    public void showElement(Graphics g) {
        int x = (localTime) % 17;
        g.drawImage(new ImageIcon("image/boom/bomb_bang"+x+".png").getImage(), this.getX(), this.getY(),
                this.getH(), this.getW(), null);
        localTime++;
        if(localTime==17){
            this.setLive(false);
        }
    }
    @Override
    public ElementObj createElement(String str){
        String[] arr = str.split(",");
        ImageIcon icon = new ImageIcon("image/boom/bomb_bang"+0+".png");
        this.setX(Integer.parseInt(arr[0]));
        this.setY(Integer.parseInt(arr[1]));
        this.setW(40);
        this.setH(40);
        this.setIcon(icon);
        return this;
    }
}
