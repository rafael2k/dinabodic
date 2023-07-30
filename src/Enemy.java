/*
 * ObjectGame: Enemy
 */

package dinabodic;

import java.awt.Graphics;
import java.awt.Point;
import java.util.Random;
import javaPlay.GameObject;
import javaPlay.GameEngine;
import javaPlay.Sprite;
import javaPlay.Scene;
/**
 * @author VisionLab/PUC-Rio (2009)
 * @author Rafael Diniz/Telemidia/PUC-Rio (2014)
 */
public class Enemy extends GameObject
{
    private Sprite sprite;
    private Sprite fireSprite;
    private Player player;
    private SimpleController controller;
    private Scene scene;
    private Random random;
    private long timeCalled;
    

    public Enemy()
    {
        timeCalled = 0;
        random = new Random();
    }
    
    public void setController(SimpleController controller)
    {
        this.controller = controller;
    }
    public void setSprite(Sprite sprite, Sprite fireSprite)
    {
        this.sprite = sprite;
        this.fireSprite = fireSprite;
    }
    public void setScene(Scene scene)
    {
        this.scene = scene;
    }
    public void setPlayer(Player player)
    {
        this.player = player;
    }
   
    public void setStartPoint(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    public void step(long timeElapsed)
    {
        x--;
         // here is our enemy AI
        if (player.x < x)
        {
            if (player.y - 192 < y)
                y--;
            if (player.y + 192 > y)
                y++;
        }
 
        if (x < 0)
        {
            x = Global.WIDTH;            
        }
        
        this.timeCalled++;
        //  One new fire each 4 * fps == 4s, when the game is running ideally
        if(this.timeCalled > (4 * GameEngine.getInstance().TARGET_FPS))
        {
            // System.out.println("New enemy fire added!");
            this.timeCalled = 0;
            EnemyFire fire = new EnemyFire(fireSprite);
            fire.x = this.x;
            fire.y = this.y;
            controller.addEnemyFire(fire);
            scene.addOverlay(fire);            
        }
        
    }

    public void draw(Graphics g)
    {
        sprite.setCurrAnimFrame(0);
        sprite.draw(g, x, y);
    }    
}
