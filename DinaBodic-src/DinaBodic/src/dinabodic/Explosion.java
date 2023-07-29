 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dinabodic;

import java.awt.Graphics;
import javaPlay.GameObject;
import javaPlay.Scene;
import javaPlay.Sprite;

/**
 *
 * @author Rafael Diniz/Telemidia/PUC-Rio (2014)
 */
public class Explosion extends GameObject
{
    private Sprite sprite;
    private int currFrame;
    private Boolean end;
    private int currChangeTime;
    private final int CHANGE_FRAME_TIME = 1000;
    private Scene scene;
    private final int MAX_FRAMES = 5;

    public void setSprite(Sprite sprite)
    {
        this.sprite = sprite;
        currFrame = 0;
        currChangeTime = 0;
        end = false;
    }
    
    public void setScene(Scene scene)
    {
        this.scene = scene;
    }

    public Boolean explosionEnded()
    {
        return end;
    }
    
    @Override 
    public void step(long timeElapsed)
    {
        
        currChangeTime -= timeElapsed;
        if(currChangeTime <= 0)
        {
            currChangeTime = CHANGE_FRAME_TIME;
            currFrame = currFrame + 1;
            
            if(currFrame == MAX_FRAMES)
            {
                scene.removeOverlay(this);
                end = true;
            }
        }
    }
    
    @Override
    public void draw(Graphics g)
    {
        sprite.setCurrAnimFrame(currFrame);
        sprite.draw(g, x, y);
    }
}
