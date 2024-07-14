package com.tedu.show;

import com.tedu.element.ElementObj;
import com.tedu.manager.ElementManager;
import com.tedu.manager.GameElement;
import com.tedu.manager.GameLoad;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;

//元素的显示和页面更新（多线程）
public class GameMainPanel extends JPanel implements Runnable{
    private ElementManager em;
    public GameMainPanel(){
        init();
    };

    public void init(){
        em = ElementManager.getManager();
    }

    @Override
    public void paint(Graphics g){
        super.paint(g);
        int x = GameLoad.mapId - 20;
        if(GameLoad.imgMap.get("background"+x)!=null){
            g.drawImage(GameLoad.imgMap.get("background"+x).getImage(),0,0,1280,740,null);
            if(!em.getElementByKey(GameElement.PLAY).isEmpty()){
                Font font = new Font("Arial", Font.PLAIN, 40);
                g.setFont(font);
                for(int i=0;i<em.getElementByKey(GameElement.PLAY).get(0).getHp();i++){
                    g.drawImage(GameLoad.imgMap.get("heart").getImage(),
                            30+i*30, 30,
                            70,50,null);
                }
                g.drawImage(GameLoad.imgMap.get("coin1").getImage(),
                        930+6*30, 30,
                        40,40,null);
                g.drawString("* "+em.getElementByKey(GameElement.PLAY).get(0).getPoint(),950+7*30,60);
            }
        }

        Map<GameElement, List<ElementObj>> all = em.getGameElement();
        for (GameElement ge: GameElement.values()) {
            List<ElementObj> list = all.get(ge);
            for (int i = 0; i < list.size(); i++) {
                ElementObj obj = list.get(i); //读取为基类
                obj.showElement(g);
            }
        }
    }

    @Override
    public void run() {
        while (true){
            this.repaint();
            try{
                Thread.sleep(20);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}
