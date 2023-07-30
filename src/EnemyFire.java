/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dinabodic;

import java.awt.Graphics;
import javaPlay.GameObject;
import javaPlay.Sprite;

/**
 *
 * @author Rafael Diniz/Tememidia/PUC-Rio (2014)
 */
public class EnemyFire extends GameObject
{
    private Sprite sprite;
    public EnemyFire(Sprite sprite)
    {
        this.sprite = sprite;
    }
    public EnemyFire(EnemyFire fire)
    {
        if (fire != null)
        {
            this.sprite = fire.sprite;
            // this.currFrame = fire.currFrame;
            this.x = fire.x;
            this.y = fire.y;
        }
    }
    @Override
    public void step(long timeElapsed)
    {
        x--;
        x--;
        // currFrame = (currFrame + 1) % MAX_FRAMES;
    }
    @Override
    public void draw(Graphics g)
    {
        sprite.setCurrAnimFrame(0);
        sprite.draw(g, x, y);
    }
}
