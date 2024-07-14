package com.tedu.game;

import com.tedu.controller.GameListener;
import com.tedu.controller.GameThread;
import com.tedu.show.GameJFrame;
import com.tedu.show.GameMainPanel;

public class GameStart {

    public static void main(String[] args) {
        GameJFrame gameFrame = new GameJFrame();
        GameMainPanel mainFrame = new GameMainPanel();
        GameListener listener = new GameListener();
        GameThread thread = new GameThread();

        gameFrame.setJPanel(mainFrame);
        gameFrame.setKeyListener(listener);
        gameFrame.setThread(thread);
        gameFrame.start();
    }
}
