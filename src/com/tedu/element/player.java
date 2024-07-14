package com.tedu.element;

import com.tedu.manager.ElementManager;
import com.tedu.manager.GameElement;
import com.tedu.manager.GameLoad;

import java.awt.*;

import static com.tedu.manager.KeyNumber.*;
import static java.util.Collections.max;

public class player extends ElementObj {
    private boolean left = false;
    private boolean right = false;
    public boolean down = true; // 检测是否碰地面
    private String direction = "down";
    private String formatDirection = "right";
    private boolean isOffence = false;
    private boolean up = false;
    private int time = 0;
    private int speed = 0; // 向下速度为正
    int maxGravity = 2; // 重力

    private int hp = 3;
    private int point = 0;
    private String weapon = "bullet1";

    @Override
    public String getDirection() {
        return direction;
    }

    @Override
    public int getSpeed() {
        return speed;
    }

    @Override
    public void setDown(boolean b) {
        this.down = b;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    @Override
    public int getHp() {
        return hp;
    }

    @Override
    public void setHp(int hp) {
        this.hp = hp;
    }

    private int localTime = 0;

    @Override
    public void showElement(Graphics g) {
        int w = (time / 6) % 3;
        g.drawImage(this.getIcon().getImage(), this.getX(), this.getY(), this.getH() + this.getX(),
                this.getW() + this.getY(),
                this.getIcon().getIconWidth() / 6 * w, this.getIcon().getIconHeight() / 4 * GameLoad.direction.get(formatDirection),
                this.getIcon().getIconWidth() / 6 * (w + 1), this.getIcon().getIconHeight() / 4 * (GameLoad.direction.get(formatDirection) + 1), null);
        time++;
    }

    @Override
    public ElementObj createElement(String str) {
        this.setX(80);
        this.setY(672-40);
        this.setW(40);
        this.setH(40);
        this.setIcon(GameLoad.imgMap.get(str));
        return this;
    }

    @Override
    public int getPoint() {
        return point;
    }

    @Override
    public void setPoint(int point) {
        this.point = point;
    }

    @Override
    public boolean isDown() {
        return down;
    }

    @Override
    public boolean isUp() {
        return up;
    }

    public void keyClick(boolean keyType, int keyCode) {
        if (keyType) {
            switch (keyCode) {
                case A:
                case LEFT:
                    if (true) {
                        this.left = true;
                        this.right = false;
                        direction = "left";
                        formatDirection = "left";
                    }
                    break;//A
                case W:
                case UP:
                    if (!this.down)
                        break; // 如果已经跳起来还没落地，就不再跳了
                    this.up = true;
                    this.resetSpeed();
                    this.down = false;
                    direction = "up";
                    break;//W
                case D:
                case RIGHT:
                    if (true) {
                        this.right = true;
                        this.left = false;
                        direction = "right";
                        formatDirection = "right";
                    }
                    break;//D
                case SPACE:
                    isOffence = true;
            }
        } else {
            switch (keyCode) {
                case A:
                    this.left = false;
                    break;//A
                case D:
                    this.right = false;
                    break;//D
                case SPACE:
                    isOffence = false;
            }
        }
    }

    @Override
    public boolean isShouldDown() {
        return shouldDown;
    }

    @Override
    public void setLeft(boolean left) {
        this.left = left;
    }

    @Override
    public void setRight(boolean right) {
        this.right = right;
    }

    @Override
    public void setUp(boolean up) {
        this.up = up;
    }

    public boolean isLeft() {
        return left;
    }

    public boolean isRight() {
        return right;
    }

    @Override
    public void resetSpeed() {
        this.speed = -10 * maxGravity; // 设置跳一次为8次重力之后归零的速度
    }

    // 先移动再碰撞检测
    protected void move() {
        if (this.left && this.getX() - 5 >= 0) {
            this.setX(this.getX() - 5);
        }
        if (this.right && this.getX() + 5 <= 1280 - this.getW() - 10) {
            this.setX(this.getX() + 5);
        }
        this.setY(this.getY() + speed);
        speed += maxGravity;

    }

    protected void shout(long gobalTime) {
        if (!isOffence || gobalTime - localTime <= 10) {
            if (!isOffence && gobalTime - localTime > 10) {
                localTime++;
            }
        } else {
            localTime += 10;
            ElementObj element = new PlayFile().createElement(this.toString());
            ElementManager.getManager().addElement(element, GameElement.PLAYFILE);
        }
    }

    @Override
    public String toString() {
        int x = this.getX();
        int y = this.getY() - 10;
//        switch (this.formatDirection) {
//            case "left":
//                y += 15;
//                break;
//            case "right":
//                x += 30;
//                y += 15;
//                break;
//        }
        return "x:" + x + ",y:" + y + ",direction:" +
                this.formatDirection + ",type:" + this.weapon;
    }

    @Override
    public void peng() {
        if (this.getDirection() != "") {
            // 垂直碰撞检测
            this.setY(this.getY() - (this.getSpeed() - maxGravity));
            speed = 0;
            this.down = true; // 落地了

            // 水平碰撞检测
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

    //    @Override
//    public void setLive(boolean live){
//        this.hp--;
//        if(this.hp>0){
//            return;
//        }
//        super.setLive(live);
//    }
    @Override
    public int getAtk() {
        return this.getPoint() / 2 + 2;
    }
}
