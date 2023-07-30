/*
 * Game Object: Score
 */

package dinabodic;

import java.awt.Graphics;
import javaPlay.GameObject;
import java.awt.Color;
import java.awt.Font;
import java.lang.String;

/**
 *
 * @author VisionLab/PUC-Rio (2009)
 * @author Rafael Diniz/Telemidia/PUC-Rio (2014)
 */
public class Score extends GameObject
{

    public void setScore(int n)
    {
        Global.score += n;
    }

    public int getScore()
    {
        return Global.score;
    }

    public int decLife()
    {
        return --Global.lifes;
    }
    
    public int getLife()
    {
        return Global.lifes;
    }    
        
    public void step(long timeElapsed)
    {

    }
    public void draw(Graphics g)
    {
        g.setColor(Color.RED);
        g.setFont(new Font("Arial", Font.BOLD, 18));
        g.drawString("VIDAS: "+String.valueOf(Global.lifes), 40, 40);
        g.drawString("Pontos: "+String.valueOf(Global.score), 40, 60);
    }
}