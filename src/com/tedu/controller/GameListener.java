package com.tedu.controller;

import com.tedu.element.ElementObj;
import com.tedu.manager.ElementManager;
import com.tedu.manager.GameElement;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GameListener implements KeyListener {
    private ElementManager em = ElementManager.getManager();
    private Set<Integer> set = new HashSet<Integer>();
    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if(set.contains(key)){
            return;
        }
        set.add(key);
        List<ElementObj> play = em.getElementByKey(GameElement.PLAY);
        for(ElementObj obj:play){
            obj.keyClick(true,e.getKeyCode());
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        set.remove(e.getKeyCode());
        List<ElementObj> play = em.getElementByKey(GameElement.PLAY);
        List<ElementObj> start = em.getElementByKey(GameElement.START);
        for(ElementObj obj:play){
            obj.keyClick(false,e.getKeyCode());
        }
        for(ElementObj obj:start){
            obj.keyClick(false,e.getKeyCode());
        }
    }
}
