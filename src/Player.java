/*
 * Game Object: Player
 */

package dinabodic;

import java.awt.Graphics;
import java.io.File;
import java.util.ArrayList;
import javaPlay.GameObject;
import javaPlay.Scene;
import javaPlay.Sprite;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 * @author VisionLab/PUC-Rio (2009)
 * Rafael Diniz/Telemidia/PUC-Rio (2014)
 */
public class Player extends GameObject
{
    public int startX = 32;
    public int startY = Global.HEIGHT / 2;
    private Sprite sprite;
    private Sprite fireSprite;
    private Scene scene;
    private int currFrame;
    private ArrayList fires;
    private Clip fireClip;
     
    /*  animation with 8 frames - file "tankplayer.png"
    private final int[] UP_FRAMES = {0, 1};
    private final int[] LEFT_FRAMES = {2, 3};
    private final int[] RIGHT_FRAMES = {6, 7};
    private final int[] DOWN_FRAMES = {4, 5};
    private final int SHOW_STEP = 1;
    */

    /* animation with 40 frames - file "walkingplayer.png" */
    /* old stuff */
    /*
    private final int[] UP_FRAMES =    { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
    private final int[] LEFT_FRAMES =  {10,11,12,13,14,15,16,17,18,19};
    private final int[] RIGHT_FRAMES = {20,21,22,23,24,25,26,27,28,29};
    private final int[] DOWN_FRAMES =  {30,31,32,33,34,35,36,37,38,39};*/
    
    // private final int[] UP_FRAMES =    { 0, 1, 2};
    private final int[] UP_FRAMES =    { 0, 4, 5, 6};
    private final int[] LEFT_FRAMES =  { 0};
    private final int[] RIGHT_FRAMES = { 0};
    private final int[] DOWN_FRAMES =  { 0, 1, 2, 3};
    private final int SHOW_STEP = 10;

    private int animIndex;
    private int currMoveAnim;
    private int stepCounter;
    long lastFireTime;
    
    private final int MOVE_ANIM_UP = 0;
    private final int MOVE_ANIM_DOWN = 1;
    private final int MOVE_ANIM_LEFT = 2;
    private final int MOVE_ANIM_RIGHT = 3;
    private final int MOVE_ANIM_IDLE = 4;

    public void setSprite(Sprite sprite, Sprite fireSprite)
    {
        this.sprite = sprite;
        this.fireSprite = fireSprite;
        animIndex = 0;
        currFrame = UP_FRAMES[animIndex];
        currMoveAnim = MOVE_ANIM_UP;
        stepCounter = 0;
        fires = new ArrayList();
    }

    public void setClip(Clip clip)
    {
        this.fireClip = clip;
    }
    
    public void setScene(Scene scene)
    {
        this.scene = scene;
    }
    
    public ArrayList getFire()
    {
        if (fires != null)
            return fires;
        else
            return null;
    }

    public void moveUp()
    {
        y--;
        y--;
        currMoveAnim = MOVE_ANIM_UP;
    }

    public void moveDown()
    {
        y++;
        y++;
        currMoveAnim = MOVE_ANIM_DOWN;
    }

    public void moveLeft()
    {
        x--;
        x--;
        currMoveAnim = MOVE_ANIM_LEFT;
    }

    public void moveRight()
    {
        x++;
        x++;
        currMoveAnim = MOVE_ANIM_RIGHT;
    }
    
    public void moveIdle()
    {
        currMoveAnim = MOVE_ANIM_IDLE;
    }

    public void fire()
    {
        // take care of ENTER's bursts... 300ms between two shots...
        long currentFireTime = System.currentTimeMillis();
        if ( (currentFireTime - lastFireTime) > 150 )
        {
            PlayerFire fire = new PlayerFire(fireSprite);
            fire.x = x + 48;
            fire.y = y + 32;
            fires.add(fire);
            scene.addOverlay(fire);
            if (fireClip.isRunning())
                fireClip.stop(); 
            fireClip.setFramePosition(0);            
            fireClip.start();
            
            
        }
        lastFireTime = currentFireTime;
    }

    
    public void step(long timeElapsed)
    {
        switch(currMoveAnim)
        {
            case MOVE_ANIM_UP:
               if (++stepCounter >= SHOW_STEP)
               {
                  animIndex++;
                  if (animIndex >= UP_FRAMES.length)
                      animIndex = UP_FRAMES.length - 1;
                  currFrame = UP_FRAMES[animIndex];
                  stepCounter = 0;
               }
               break;
            case MOVE_ANIM_DOWN:
               if (++stepCounter >= SHOW_STEP)
               {
                  animIndex++;
                  if (animIndex >= DOWN_FRAMES.length)
                      animIndex = DOWN_FRAMES.length - 1;
                  currFrame = DOWN_FRAMES[animIndex];
                  stepCounter = 0;
               }
               break;
            case MOVE_ANIM_LEFT:
                
               if (++stepCounter >= SHOW_STEP)
               {
                  
                  animIndex++;
                  if (animIndex >= LEFT_FRAMES.length)
                      animIndex = LEFT_FRAMES.length - 1;
                  currFrame = LEFT_FRAMES[animIndex];
                  stepCounter = 0;
                   
               }
                
               break;
            case MOVE_ANIM_RIGHT:
                
               if (++stepCounter >= SHOW_STEP)
               {
                  animIndex++;
                  if (animIndex >= RIGHT_FRAMES.length)
                      animIndex = RIGHT_FRAMES.length - 1;
                  currFrame = RIGHT_FRAMES[animIndex];
                  stepCounter = 0;
               }
                
               break;
            case MOVE_ANIM_IDLE:
               if (++stepCounter >= SHOW_STEP)
               {
                  animIndex = 0;
                  currFrame = UP_FRAMES[animIndex];
                  stepCounter = 0;
               }
               break;
        }
        
    }

    public void draw(Graphics g)
    {
        sprite.setCurrAnimFrame(currFrame);
        sprite.draw(g, x, y);
    }
}
