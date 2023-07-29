/*
 * GameEngine
 */

package javaPlay;

import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author VisionLab/PUC-Rio
 */
public class GameEngine 
{
    public int TARGET_FPS = 60;
    private GameCanvas canvas;
    private Mouse mouse;
    private Keyboard keyboard;
    private long lastTime;
    private boolean engineRunning;
    private Hashtable gameStateControllers;
    private GameStateController currGameState;
    private GameStateController nextGameState;
    private static GameEngine instance;

    private GameEngine()
    {
        GraphicsEnvironment graphEnv = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice graphDevice = graphEnv.getDefaultScreenDevice();
        GraphicsConfiguration graphicConf = graphDevice.getDefaultConfiguration();

        canvas = new GameCanvas(graphicConf);

        mouse = new Mouse();
        keyboard = new Keyboard();

        canvas.addMouseListener(mouse);
        canvas.addMouseMotionListener(mouse);
        canvas.addKeyListener(keyboard);

        lastTime = System.currentTimeMillis();
        engineRunning = true;
        gameStateControllers = new Hashtable();
        currGameState = null;
        nextGameState = null;
        instance = null;
    }

    public static GameEngine getInstance()
    {
        if(instance == null)
        {
            instance = new GameEngine();
        }
        return instance;
    }

    public GameCanvas getGameCanvas()
    {
        return canvas;
    }

    public Mouse getMouse()
    {
        return mouse;
    }

    public Keyboard getKeyboard()
    {
        return keyboard;
    }

    public void addGameStateController(int id, GameStateController gs)
    {
        gameStateControllers.put(id, gs);

        gs.load();
    }

    public void removeGameStateController(int id)
    {
        GameStateController gs = (GameStateController)gameStateControllers.get(id);

        gs.unload();
    }

    public void setStartingGameStateController(int id)
    {
        currGameState = (GameStateController)gameStateControllers.get(id);
    }

    public void setNextGameStateController(int id)
    {
        nextGameState = (GameStateController)gameStateControllers.get(id);
    }

    public void requestShutdown()
    {
        engineRunning = false;
    }

    /* old run */
/*    public void run()
    {
        if(currGameState == null)
        {
            return;
        }

        currGameState.start();

        long currentTime;

        while(engineRunning == true)
        {
            currentTime = System.currentTimeMillis();

            currGameState.step(currentTime - lastTime);
            
            currGameState.draw(canvas.getGameGraphics());

            canvas.swapBuffers();

            if(nextGameState != null)
            {
                currGameState.stop();
                nextGameState.start();

                currGameState = nextGameState;
                nextGameState = null;
            }
        }

        canvas.dispose();
    }
    */
    public void run()
    {
        if(currGameState == null)
        {
            return;
        }

        currGameState.start();
        
        long lastLoopTime = System.currentTimeMillis();
        int wait_time = 1000 / TARGET_FPS;

        while(engineRunning == true)
        {
            // We'll run the main loop at TARGET_FPS ideally
            long delta = System.currentTimeMillis() - lastLoopTime;
            lastLoopTime = System.currentTimeMillis();

            currGameState.step(delta);
            
            currGameState.draw(canvas.getGameGraphics());

            canvas.swapBuffers();

            if(nextGameState != null)
            {
                currGameState.stop();
                nextGameState.start();

                currGameState = nextGameState;
                nextGameState = null;
            }
            
            long sleep_time = lastLoopTime + wait_time - System.currentTimeMillis();
            
            if (sleep_time > 0)
            {
                //System.out.println("keeping up the target framerate...\n");
                try 
                {
                    Thread.sleep(sleep_time);
                } 
                catch (InterruptedException ex) 
                {
                    Logger.getLogger(GameEngine.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else
            {
                //System.out.println("Not keeping up the target framerate...\n");
            }
            
        }

        canvas.dispose();
    }
}
