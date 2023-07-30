/*
 * StartMenuController
 */

package dinabodic;

import java.awt.Graphics;
import java.awt.Point;
import java.util.logging.Level;
import java.util.logging.Logger;
import javaPlay.GameEngine;
import javaPlay.GameStateController;
import javaPlay.Mouse;
import javaPlay.Sprite;

/**
 *
 * @author VisionLab/PUC-Rio (2009)
 * @author Rafael Diniz/Telemidia/PUC-Rio (2014)
 */
public class StartMenuController implements GameStateController
{
    private Sprite menu;

    public void load()
    {
        try
        {
            menu = new Sprite("startmenu.jpg", 1, 1280, 720);
        }
        catch (Exception ex)
        {
            Logger.getLogger(StartMenuController.class.getName()).log(Level.SEVERE, null, ex);
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

            if((p.x >= 576) && (p.y >= 400) && (p.x <= 771) && (p.y <= 460))
            {
                GameEngine.getInstance().setNextGameStateController(Global.SIMPLE_CONTROLLER_ID);
            }
        }        
    }

    public void draw(Graphics g)
    {
        menu.draw(g, 0, 0);
    }

    public void stop()
    {
        
    }
}
