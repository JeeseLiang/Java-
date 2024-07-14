package com.tedu.element;

import javax.swing.*;
import java.awt.*;

/*
*所有元素的基类
* */
public abstract class ElementObj {
    private int x;
    private int y;
    private int w;
    private int h;

private int atk = 1;
    private boolean left;
    private boolean right;
    private boolean up;
    private boolean down;

    public boolean isUp() {
        return false;
    }

    public boolean isDown() {
        return false;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    private ImageIcon icon;
    private boolean live = true;
    public boolean shouldDown;

    public int speed;

    public void resetSpeed() {
        this.speed = 10;
    }

    public boolean isShouldDown() {
        return false;
    }
    public boolean shouldJump(){
        return false;
    }

    public boolean isLeft() {
        return false;
    }

    public boolean isRight() {
        return false;
    }

    public void setShouldDown(boolean shouldDown) {
        this.shouldDown = shouldDown;
    }

    public ElementObj(){}

    public ElementObj(int x, int y, int w, int h, ImageIcon icon) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.icon = icon;
    }
    public abstract void showElement(Graphics g);

    public int getX() {
        return x;
    }

    public boolean isLive() {
        return live;
    }
    public void setDown(boolean b){}

    public void setHp(int i){
        return;
    }
    public int getHp(){
        return 0;
    }
    public void setLive(boolean live) {
        this.live = live;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getW() {
        return w;
    }

    public void setW(int w) {
        this.w = w;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }

    public ImageIcon getIcon() {
        return icon;
    }
    //使用父类添加键盘事件
    //不强制性实现
    public void keyClick(boolean keyType,int keyCode){}
    //死亡方法
    public void death(){}
    public void setIcon(ImageIcon icon) {
        this.icon = icon;
    }
    public void model(long gobalTime){
        changeDirection();
        move();
        shout(gobalTime);
    }
    public String getDirection() {
        return "";
    }
    public void setDirection(String str) {
    }
    public int getSpeed() {
        return 0;
    }
    protected void move(){}
    protected void changeDirection(){}
    protected void shout(long gobalTime){}
    public ElementObj createElement(String str){
        return null;
    }
    //返回元素的碰撞体型对象
    public Rectangle getRectangle(){
        return new Rectangle(x,y,w,h);
    }
    //碰撞检测
    public boolean isBoom(ElementObj obj){
        return this.getRectangle().intersects(obj.getRectangle());
    }

    public boolean isVecBoom(ElementObj obj){
        return obj.getX()>x &&obj.getX()<x+w || x>obj.getX() && x<obj.getX()+obj.getW() && !isHorBoom(obj);
    }
    public boolean isHorBoom(ElementObj obj){
        return (obj.getY()>=y && obj.getY()<=y+h) || (y>=obj.getY() && y<=obj.getY()+obj.getH()) ;
    }
    public void setPoint(int i){
    }
    public int getPoint(){return 0;}

    public void peng() {
    }

    public int getAtk(){
        return atk;
    }
}
