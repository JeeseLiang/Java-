package com.tedu.manager;

//元素管理器，存储所有数据
//提供给予视图和控制的

import com.tedu.element.ElementObj;

import java.util.*;

public class ElementManager {
//    private List<Object> listMap;
//    private List<Object> listPlay;
    //存储数据
    private Map<GameElement,List<ElementObj>> gameElement;

    public Map<GameElement, List<ElementObj>> getGameElement() {
        return gameElement;
    }

    //单例化
    private static ElementManager EM=null;

    public static synchronized ElementManager getManager() {
        if(EM==null) EM = new ElementManager(); //饿汉模式
        return EM;
    }
    //添加元素（由加载器调用）
    public void addElement(ElementObj obj,GameElement ge){
        gameElement.get(ge).add(obj);
    }
    //按照key返回集合
    public List<ElementObj> getElementByKey(GameElement ge){
        return gameElement.get(ge);
    }
//    //饱汉模式
//    //static只会执行一次
//    static {
//        EM = new ElementManager();
//    }
    //私有化构造方法
    private ElementManager(){
        init();
    };

    public void clearGameElementByKey(GameElement ge){
        gameElement.get(ge).clear();
    }
    public void init(){
        gameElement =new HashMap<GameElement,List<ElementObj>>();
        for(GameElement ge:GameElement.values()){
            gameElement.put(ge,new ArrayList<ElementObj>());
        }
    }

}
