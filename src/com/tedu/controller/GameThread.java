package com.tedu.controller;

import com.tedu.element.*;
import com.tedu.manager.ElementManager;
import com.tedu.manager.GameElement;
import com.tedu.manager.GameLoad;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.tedu.manager.GameLoad.mapId;

/*
 *  游戏主线程，控制游戏加载，游戏关卡，游戏运行时自优化
 *   游戏判定，游戏地图切换，资源释放和重新读取
 * */
public class GameThread extends Thread {

    private ElementManager em;

    public GameThread() {
        em = ElementManager.getManager();
    }

    //主线程
    public static long gobalTime = 0L;

    @Override
    public void run() {

        while (true) {
            //开始    加载
            gameStart(GameLoad.mapId);
            ;
            //进行
            gameRun();
            //结束    场景回收
            if (gameEnd()) {
                break;
            }
            try {
                sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }


    public void gameStart(int mapId) {
        GameLoad.loadImg();
        if(!GameLoad.isStart){
            GameLoad.loadStart();
            GameLoad.isStart = true;
        }
        GameLoad.MapLoad(mapId);
        GameLoad.loadEmery(mapId);
        GameLoad.loadPlayer();
    }

    private void gameRun() {
        //游戏进行时任务：
        //玩家移动、碰撞、死亡
        //游戏暂停
        while (true) {
            gobalTime++;
            Map<GameElement, List<ElementObj>> all = em.getGameElement();
            List<ElementObj> emery = em.getElementByKey(GameElement.EMERY);
            List<ElementObj> playerFile = em.getElementByKey(GameElement.PLAYFILE);
            List<ElementObj> maps = em.getElementByKey(GameElement.MAP);
            List<ElementObj> player = em.getElementByKey(GameElement.PLAY);
            List<ElementObj> eneryFile = em.getElementByKey(GameElement.ENERYFILE);
            List<ElementObj> prize = em.getElementByKey(GameElement.PRIZE);
            gameModelUse(all);

            gameBoomCheck(emery, playerFile);
            gameBoomCheck(eneryFile,player);

            gameBoomCheck(maps,playerFile);
            gameBoomCheck(maps,eneryFile);

            gameDisappearedCheck(player,prize);
            gameBoomCheck(player,emery);

            gamePushCheck(emery, maps);
            gamePushCheck(player, maps);

            if(!checkLive()){
                em.addElement(new GameOver().createElement(""),GameElement.GAMEOVER);
                try {
                    sleep(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                resetLevel();
            }

            if(checkNextLevel()){
                System.out.println(checkNextLevel());
                System.out.println("aoao");
                em.addElement(new NextGame().createElement(""),GameElement.NEXTGAME);
                try {
                    sleep(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                mapId++;
                resetLevel();
            }

            try {
                sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }

    private boolean checkNextLevel(){
        ElementObj player = em.getElementByKey(GameElement.PLAY).get(0);
        System.out.println(GameLoad.N);
        return player.getPoint()==GameLoad.N;
    }
    private boolean checkLive() {
        ElementObj player = em.getElementByKey(GameElement.PLAY).get(0);
        return player.getHp() > 0;
    }

    private void resetLevel(){
        gobalTime=0;
        PlayFile.atk = 1;
        for(GameElement ge:GameElement.values()){
            em.clearGameElementByKey(ge);
        }
        if(mapId==24){
            em.addElement(new GameEnd().createElement(""),GameElement.GAMEEND);
            try {
                sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.exit(0);
        }
        gameStart(mapId);
    }

    public void gameDisappearedCheck(List<ElementObj>listA,List<ElementObj>listB){
        for(int i=0;i<listA.size();i++){
            ElementObj lA = listA.get(i);
            for(int j=0;j<listB.size();j++){
                ElementObj lB = listB.get(j);
                if(listA.get(i).isBoom(listB.get(j))){
                    if(lA instanceof player){
                        lA.setPoint(lA.getPoint()+1);
                        PlayFile.atk++;
                    }
                    if(lB instanceof player) {
                        lB.setPoint(lB.getPoint()+1);
                        PlayFile.atk++;
                    }
                    if(lA instanceof Prize)listA.get(i).setLive(false);
                    if(lB instanceof Prize)listB.get(j).setLive(false);
                    if(lA instanceof player && lB instanceof Emery || lA instanceof Emery && lB instanceof player){
                        lA.setHp(lA.getHp()-1);
                        lB.setHp(lB.getHp()-1);
                        if (lA.getHp() == 0) {
                            listA.get(i).setLive(false);
                        }
                        if (lB.getHp() == 0) {
                            listB.get(j).setLive(false);
                        }
                    }
                    return;
                }
            }
        }
    }
    public void gameBoomCheck(List<ElementObj> listA, List<ElementObj> listB) {
        for (int i = 0; i < listA.size(); i++) {
            ElementObj lA = listA.get(i);
            for (int j = 0; j < listB.size(); j++) {
                ElementObj lB = listB.get(j);
                if (listA.get(i).isBoom(listB.get(j))) {
                    lA.setHp(lA.getHp() - lB.getAtk());
                    lB.setHp(lB.getHp() - lA.getAtk());
                    if (lA.getHp() <= 0) {
                        listA.get(i).setLive(false);
                        if (lA instanceof Emery) {
                            GameLoad.loadBoom(lA.getX() + "," + lA.getY());
                            GameLoad.loadPrize("x:" + lA.getX() + "," + "y:" + (lA.getY() + 20) + ",type:coin1");
                        }
                    }
                    if (lB.getHp() <= 0) {
                        listB.get(j).setLive(false);
                        if (lB instanceof Emery) {
                            GameLoad.loadBoom(lB.getX() + "," + lB.getY());
                            GameLoad.loadPrize("x:" + lB.getX() + "," + "y:" + (lB.getY() + 20) + ",type:coin1");
                        }
                    }
                    return;
                }
            }
        }
    }

    public void gamePushCheck(List<ElementObj> listA, List<ElementObj> listB) {
        if(listA.size()==0 || listB.size()==0)return;
        for (int i = 0; i < listA.size(); i++) {
            ElementObj o1 = listA.get(i);
            for (int j = 0; j < listB.size(); j++) {
                ElementObj o2 = listB.get(j);
                if (o1.isBoom(o2))
                {   //碰撞
                    o1.peng();
                    o2.peng();
                    break;
                }
            }
        }
    }

    public void gameModelUse(Map<GameElement, List<ElementObj>> all) {
        for (GameElement ge : GameElement.values()) {
            List<ElementObj> list = all.get(ge);
            for (int i = 0; i < list.size(); i++) {
                ElementObj obj = list.get(i); //读取为基类
                if (!obj.isLive()) {
                    //死亡效果
                    list.remove(i--);
                    continue;
                }
                obj.model(gobalTime);
            }
        }
    }

    private boolean gameEnd() {
        return false;
    }
}
