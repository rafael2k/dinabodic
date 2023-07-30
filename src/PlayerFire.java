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
 * @author Rafael Diniz/Telemidia/PUC-Rio (2014)
 */
public class PlayerFire extends GameObject
{
    private Sprite sprite;

    public PlayerFire(Sprite sprite)
    {
        this.sprite = sprite;
    }
    public PlayerFire(PlayerFire fire)
    {
        if (fire != null)
        {
            this.sprite = fire.sprite;
            this.x = fire.x;
            this.y = fire.y;
        }
    }
    @Override
    public void step(long timeElapsed)
    {
        x++;
        x++;
    }
    @Override
    public void draw(Graphics g)
    {
        sprite.setCurrAnimFrame(0);
        sprite.draw(g, x, y);
    }
}
