package com.tedu.show;

import com.tedu.element.ElementObj;
import com.tedu.manager.ElementManager;
import com.tedu.manager.GameElement;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;

//元素的显示和页面更新（多线程）
public class GameSecondPanel extends JPanel implements Runnable{
    private ElementManager em;
    public GameSecondPanel(){
        init();
    };

    public void init(){
        em = ElementManager.getManager();
    }
    @Override
    public void paint(Graphics g){
        super.paint(g);

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
