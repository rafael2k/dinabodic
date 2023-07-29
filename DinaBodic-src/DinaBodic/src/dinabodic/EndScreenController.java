/*
 * EndScreenController
 */

package dinabodic;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.util.logging.Level;
import java.util.logging.Logger;
import javaPlay.GameEngine;
import javaPlay.GameStateController;
import javaPlay.Keyboard;
import javaPlay.Mouse;
import javaPlay.Sprite;

/**
 * 
 * @author VisionLab/PUC-Rio (2009)
 * @author Rafael Diniz/Telemidia/PUC-Rio (2014)
 */
public class EndScreenController implements GameStateController
{
    private Sprite endScreenWin;
    private Sprite endScreenLose;

    public void load()
    {
        try
        {
            endScreenWin = new Sprite("finalback.jpg", 1, 1280, 720);
            endScreenLose = new Sprite("finalbacklost.jpg", 1, 1280, 720);
        }
        catch (Exception ex)
        {
            Logger.getLogger(EndScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void unload()
    {

    }

    public void start()
    {
    }

    public void step(long timeElapsed)
    {
      
        Mouse m = GameEngine.getInstance().getMouse();

        if(m.isLeftButtonPressed() == true)
        {
            Point p = m.getMousePos();

            if((p.x >= 238) && (p.y >= 510) && (p.x <= 434) && (p.y <= 580))
            {
                GameEngine.getInstance().requestShutdown();
                // System.exit(0);
            }
        } 
        
    }

    public void draw(Graphics g)
    {
        if (Global.lifes > 0)
            endScreenWin.draw(g, 0, 0);
        else
            endScreenLose.draw(g, 0, 0);
        
        g.setColor(Color.RED);
        g.setFont(new Font("Arial", Font.BOLD, 18));
        g.drawString("VIDAS: "+String.valueOf(Global.lifes), 40, 60);
        g.drawString("Pontos: "+String.valueOf(Global.score), 40, 80);
    }

    public void stop()
    {

    }
}
