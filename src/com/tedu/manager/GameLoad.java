package com.tedu.manager;

import com.tedu.controller.GameThread;
import com.tedu.element.*;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class GameLoad {
    private static ElementManager em = ElementManager.getManager();
    private static Properties  properties = new Properties();
    public static Map<String, ImageIcon> imgMap = new HashMap<>();
    public static Map<String,Integer>direction = new HashMap<>();
    public static int mapId = 21;
    public static boolean isStart = false;
    public static int N;

    public static void MapLoad(int mapId){
        String mapName = "com/tedu/text/"+mapId+".map";
        ClassLoader classLoad = GameLoad.class.getClassLoader();
        InputStream maps = classLoad.getResourceAsStream(mapName);
        try {
            properties.clear();
            properties.load(maps);
            Enumeration<?> names = properties.propertyNames();
            while(names.hasMoreElements()){
                String key = names.nextElement().toString();
                properties.getProperty(key);
                String[] arrs = properties.getProperty(key).split(";");
                for(int i=0;i< arrs.length;i++){
                    ElementObj elementObj = new Maps().createElement(key+","+arrs[i]);
                    em.addElement(elementObj,GameElement.MAP);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void loadPrize(String str){em.addElement(new Prize().createElement(str),GameElement.PRIZE);}
    public static void loadEmery(int mapId){
        ClassLoader classLoader = GameLoad.class.getClassLoader();

        // 资源文件的路径，相对于类路径
        String resourcePath = "com/tedu/text/MapEnery"+mapId+".pro";
        // 通过类加载器获取输入流
        try (InputStream inputStream = classLoader.getResourceAsStream(resourcePath);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            // 读取文件内容
            String line = reader.readLine();
            String[] arr=line.split(";");
            Random r = new Random();
            N = arr.length;
            for(int i=0;i<arr.length-1;i++){
                String[] tep = arr[i].split(",");
                int X =  Integer.parseInt(tep[0])+r.nextInt(Integer.parseInt(tep[1])-Integer.parseInt(tep[0]));
                int Y = Integer.parseInt(tep[2])-40;
                int left = Integer.parseInt(tep[0]);
                int right = Integer.parseInt(tep[1]);
                int Em = r.nextInt(4)+1;
                em.addElement(new Emery().createElement("emery"+Em+","+
                        X+","+ Y+","+left+","+right),
                        GameElement.EMERY);
            }
            String[] tep = arr[arr.length-1].split(",");
            int X =  Integer.parseInt(tep[0])+r.nextInt(Integer.parseInt(tep[1])-Integer.parseInt(tep[0]));
            int Y = Integer.parseInt(tep[2]) - 80;
            em.addElement(new Emery().createElement("Boss"+","+
                    X+","+Y +","+Integer.parseInt(tep[0])+","+Integer.parseInt(tep[1]))
                    ,GameElement.EMERY);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void loadStart(){
        em.addElement(new GameStart().createElement(""),GameElement.START);
    }
    public static void loadImg(){
        ClassLoader classLoader = GameLoad.class.getClassLoader();
        InputStream gameData = classLoader.getResourceAsStream("com/tedu/text/Gamedata.pro");
        properties.clear();
        try {
            properties.load(gameData);
            Set<Object> set =  properties.keySet();
            for(Object o:set){
                String url = properties.getProperty(o.toString());
                imgMap.put(o.toString(),new ImageIcon(url));

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void loadPlayer(){
        direction.put("down",0);
        direction.put("left",1);
        direction.put("right",2);
        direction.put("up",3);
        em.addElement(new player().createElement("player2"),GameElement.PLAY);
    }
    public static void loadBoom(String str){
        em.addElement(new Boom().createElement(str),GameElement.BOOM);
    }

}

