/*
 * Sprite
 */

package javaPlay;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.util.ArrayList;
import javax.imageio.ImageIO;


/**
 * @author VisionLab/PUC-Rio
 */
public class Sprite
{    
    private Image image;
    private int animFrameCount;
    private int currAnimFrame;
    private int animFrameWidth;
    private int animFrameHeight;
    private Boolean multiFileMode;
    private ArrayList fileList;

    public Sprite(String filename, int animFrameCount, int animFrameWidth,
            int animFrameHeight) throws Exception
    {
        multiFileMode = false;
        image = ImageIO.read(new File(filename));

        this.animFrameCount = animFrameCount;
        this.animFrameWidth = animFrameWidth;
        this.animFrameHeight = animFrameHeight;
        
        this.currAnimFrame = 0;
    }

    public Sprite(String filenamePrefix, int animFrameCount, int animFrameWidth,
            int animFrameHeight, Boolean multiFileModeIndicator) throws Exception
    {
        multiFileMode = true;
        fileList = new ArrayList();
        
        for (int i = 0; i < animFrameCount; i++)
        {
            // System.out.println(filenamePrefix + "-" + i + ".png");
            image = ImageIO.read(new File(filenamePrefix + "-" + i + ".png"));
            fileList.add(image);
        }

        this.animFrameCount = animFrameCount;
        this.animFrameWidth = animFrameWidth;
        this.animFrameHeight = animFrameHeight;
        
        this.currAnimFrame = 0;
    }

    
    public void setCurrAnimFrame(int frame)
    {
        currAnimFrame = frame;
    }

    public void draw(Graphics g, int x, int y)
    {
        GameCanvas canvas = GameEngine.getInstance().getGameCanvas();

        int xpos = canvas.getRenderScreenStartX() + x;
        int ypos = canvas.getRenderScreenStartY() + y;

        if (multiFileMode == false)
        {
            g.drawImage(image, xpos, ypos, xpos + animFrameWidth, ypos + animFrameHeight,
                currAnimFrame * animFrameWidth, 0, (currAnimFrame + 1) * animFrameWidth, animFrameHeight, null);
        }
        else
        {
            image = (Image) fileList.get(currAnimFrame);
            g.drawImage(image, xpos, ypos, null);
        }
    }

    private Sprite(Image image, int animFrameCount,
            int currAnimFrame, int animFrameWidth, int animFrameHeight)
    {
        this.image = image;
        this.animFrameCount = animFrameCount;
        this.currAnimFrame = currAnimFrame;
        this.animFrameWidth = animFrameWidth;
        this.animFrameHeight = animFrameHeight;
    }

    public Sprite clone()
    {
        return new Sprite(image, animFrameCount, currAnimFrame,
                animFrameWidth, animFrameHeight);
    }
}
