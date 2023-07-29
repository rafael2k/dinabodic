/*
 * GameCanvas
 */

package javaPlay;

import java.awt.Container;
import java.awt.DisplayMode;
import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

/**
 * @author VisionLab/PUC-Rio
 */
public class GameCanvas extends JFrame
{
    private final int defaultScreenWidth = 1280;
    private final int defaultScreenHeight = 720;
    // private final Boolean fullscreen = false;
    private final Boolean fullscreen = true;
    private Graphics g;
    private BufferStrategy bf;
    
    private int renderScreenStartX;
    private int renderScreenStartY;

    public GameCanvas(GraphicsConfiguration gc)
    {
        super(gc);
        
        if (fullscreen)
            setUndecorated(true);
        
        setResizable(false);
        setVisible(true);
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(defaultScreenWidth, defaultScreenHeight);
                  
        if (fullscreen)
            GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().setFullScreenWindow(this);
                
        // thiss improve the game performance
        setIgnoreRepaint(true);

        createBufferStrategy(2);

        renderScreenStartX = this.getContentPane().getLocationOnScreen().x;
        renderScreenStartY = this.getContentPane().getLocationOnScreen().y;

        bf = getBufferStrategy();
    }

    public int getRenderScreenStartX()
    {
        return renderScreenStartX;
    }

    public int getRenderScreenStartY()
    {
        return renderScreenStartY;
    }

    public Graphics getGameGraphics()
    {        
        g = bf.getDrawGraphics();
        return g;
    }

    public void swapBuffers()
    {
        bf.show();
        g.dispose();       
        Toolkit.getDefaultToolkit().sync();
    }
}
