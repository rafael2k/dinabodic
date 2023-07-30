/*
 * SimpleController
 */

package dinabodic;

import java.awt.Graphics;
import java.awt.Point;
import java.io.File;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javaPlay.GameEngine;
import javaPlay.GameStateController;
import javaPlay.Keyboard;
import javaPlay.Scene;
import javaPlay.Sprite;
import javaPlay.TileInfo;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 *
 * @author VisionLab/PUC-Rio (2009)
 * @author Rafael Diniz/Telemidia/PUC-Rio (2014)
 */
public class SimpleController implements GameStateController
{
    private Sprite playerSprite;
    private Sprite playerFireSprite;
    private Sprite enemySprite;
    private Sprite enemyFireSprite;
    private Sprite explosionEnemySprite;
    private Sprite explosionPlayerSprite;
    private Player player;
    private Clip backgroundClip;
    private Clip explosionClip;
    private ArrayList enemies;
    private ArrayList enemiesInactive;
    private ArrayList enemiesFire;
    private ArrayList explosions;
    
    private Scene scene;    
    private int numberOfEnemies = 12;
    private final int TILE_WALL = 1;
    private final int TILE_EXIT = 3;    

    private int[] midx;
    private int[] midy;
    private final int UP_MID = 0;
    private final int DOWN_MID = 1;
    private final int LEFT_MID = 2;
    private final int RIGHT_MID = 3;

    private Score score;
    
    public void load()
    {
        try
        {

            // playerSprite = new Sprite("mainship.png", 3, 128, 128);
            
            // playerSprite = new Sprite("mainship-bd.png", 1, 128, 72);
            playerSprite = new Sprite("mainship", 7, 128, 72, true);
            
            // playerFireSprite = new Sprite("playerfire.png", 7, 32, 32);
            playerFireSprite = new Sprite("missil_player.png", 1, 57, 32);
            
            // enemyFireSprite = new Sprite("enemyfire.png", 7, 32, 32);
            enemyFireSprite = new Sprite("tiro_enemy.png", 1, 44, 25);
            
            explosionEnemySprite = new Sprite ("explosionlo", 5, 171, 96, true);
            explosionPlayerSprite = new Sprite ("explosionhi", 5, 228, 128, true);
            
            scene = new Scene();
            scene.loadFromFile("scene.scn");
            
            player = new Player();
            player.setSprite(playerSprite, playerFireSprite);
            player.setScene(scene);
            
            scene.addOverlay(player);
            
            enemies = new ArrayList();
            enemiesInactive = new ArrayList();
            enemiesFire = new ArrayList();
            explosions = new ArrayList();
         
            
            for (int i = 1; i <= numberOfEnemies; i++)
            {
                // System.out.println("enemy-"+i+".png");
                enemySprite = new Sprite("enemy-"+i+".png", 1, 114, 64);
                Enemy enemy = new Enemy();
                enemy.setSprite(enemySprite, enemyFireSprite);
                enemy.setScene(scene);
                enemy.setPlayer(player);
                enemy.setController(this);
                enemy.setStartPoint(Global.WIDTH, (i * 128) % (Global.HEIGHT - 128));
                // scene.addOverlay(enemy);
                enemiesInactive.add(enemy);
            }

            score = new Score();
            scene.addOverlay(score);
            
            midx = new int[4];
            midy = new int[4];
            
            // load the playerfire audio effect
            Clip fireClip = null;
            try {
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("tiro.wav").getAbsoluteFile());
                fireClip = AudioSystem.getClip();
                fireClip.open(audioInputStream);
            } catch(Exception ex) {
                System.out.println("Error with playing sound.");
                ex.printStackTrace();
            }
            player.setClip(fireClip);
            
            // load the explosion effect
            try {
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("explosion.wav").getAbsoluteFile());
                explosionClip = AudioSystem.getClip();
                explosionClip.open(audioInputStream);
            } catch(Exception ex) {
                System.out.println("Error with playing sound.");
                ex.printStackTrace();
            }
            
            // load the background music
            try {
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("trilha.wav").getAbsoluteFile());
                backgroundClip = AudioSystem.getClip();
                backgroundClip.open(audioInputStream);
                // clip.start();
            } catch(Exception ex) {
                System.out.println("Error with playing sound.");
                ex.printStackTrace();
            }
            
            
                        
        }
        catch (Exception ex)
        {
            Logger.getLogger(SimpleController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void unload()
    {
 
    }

    public void start()
    {
        player.x = player.startX;
        player.y = player.startY;
        backgroundClip.loop(backgroundClip.LOOP_CONTINUOUSLY);
        
    }

    public void step(long timeElapsed)
    {
        Keyboard k = GameEngine.getInstance().getKeyboard();

        if (k.keyDown(Keyboard.UP_KEY)    == false &&
            k.keyDown(Keyboard.DOWN_KEY)  == false &&
            k.keyDown(Keyboard.LEFT_KEY)  == false &&
            k.keyDown(Keyboard.RIGHT_KEY) == false)
        {
            player.moveIdle();
        } 

        if(k.keyDown(Keyboard.LEFT_KEY) == true)
        {
            player.moveLeft();
        }
        if(k.keyDown(Keyboard.RIGHT_KEY) == true)
        {
            player.moveRight();
        }
        if(k.keyDown(Keyboard.UP_KEY) == true)
        {
            player.moveUp();
        }
        if(k.keyDown(Keyboard.DOWN_KEY) == true)
        {
            player.moveDown();
        }
                
        if(k.keyDown(Keyboard.SPACE_KEY) == true)
        {
            player.fire();
        }

        if (numberOfEnemies == 0 && explosions.size() == 0)
        {
            backgroundClip.stop();
            GameEngine.getInstance().setNextGameStateController
                                    (Global.END_SCREEN_CONTROLLER_ID);
        }
        
        if ((enemies.size() < 4) && (enemiesInactive.size() > 0))
        {
            Enemy enemy = (Enemy) enemiesInactive.get(0);
            enemies.add(enemy);
            scene.addOverlay(enemy);
            enemiesInactive.remove(enemy);
        }
        
        // call the step() for the explosions
        for (int i = 0; i < explosions.size(); i ++)
        {
            Explosion explosion = (Explosion) explosions.get(i);
            explosion.step(300);
            if (explosion.explosionEnded())
                explosions.remove(explosion);
            
        }
     
        player.step(timeElapsed);

        Point playerMin = new Point(player.x + 20, player.y + 10);
        Point playerMax = new Point(player.x + 96, player.y + 48);

        for(int i = 0; i < enemiesFire.size(); i++)
        {
            Point fireMin = null;
            Point fireMax = null;
            
            EnemyFire fire = (EnemyFire) enemiesFire.get(i);
            fire.step(timeElapsed); // here we make the shot walk...

            fireMin = new Point(fire.x, fire.y);
            fireMax = new Point(fire.x + Global.FIRE_WIDTH, fire.y + Global.FIRE_HEIGHT);
            
            if( isColliding(playerMin, playerMax, fireMin, fireMax) == true )
            {
                Explosion explosion = new Explosion();
                explosion.x = player.x;
                explosion.y = player.y;
                explosion.setSprite(explosionPlayerSprite);
                explosion.setScene(scene);
                explosions.add(explosion);
                scene.addOverlay(explosion);
                
                // update Score
                score.decLife();
                // back to start point
                player.x = player.startX;
                player.y = player.startY;

                if (explosionClip.isRunning())
                    explosionClip.stop(); 
                explosionClip.setFramePosition(0);
                explosionClip.start();
                
                // Shutdown if Score = 0              
                if (score.getLife()== 0)
                {
                     backgroundClip.stop();
                     GameEngine.getInstance().setNextGameStateController
                                    (Global.END_SCREEN_CONTROLLER_ID);
                }

                return;
            }
            
            if ( fire.x  < -32 || fire.y < -32 )
            {
                scene.removeOverlay(fire);
                enemiesFire.remove(fire);
                fire = null;
            }
        }
        
        // here we verify the collision between player and enemy 
        for(int i = 0 ; i < enemies.size() ; i++)
        {
            
            Enemy enemy = (Enemy) enemies.get(i);
            enemy.step(timeElapsed);

            Point enemyMin = new Point(enemy.x, enemy.y);
            Point enemyMax = new Point(enemy.x + (Global.ENEMY_WIDTH-1), enemy.y + (Global.ENEMY_HEIGHT-1));
                        
            if(isColliding(playerMin, playerMax, enemyMin, enemyMax) == true)
            {
                Explosion explosion = new Explosion();
                explosion.x = player.x;
                explosion.y = player.y;
                explosion.setSprite(explosionPlayerSprite);
                explosion.setScene(scene);
                explosions.add(explosion);
                scene.addOverlay(explosion);
                
                // update Score
                score.decLife();
                // back to start point
                player.x = player.startX;
                player.y = player.startY;
                
                if (explosionClip.isRunning())
                    explosionClip.stop(); 
                explosionClip.setFramePosition(0);
                explosionClip.start();
                
                // Shutdown if Score = 0
                if (score.getLife() == 0)
                {
                    backgroundClip.stop();   
                    GameEngine.getInstance().setNextGameStateController
                                    (Global.END_SCREEN_CONTROLLER_ID);
                }

                return;
            }

        }
        
        ArrayList playerFire = null;
        // no lets make the shots walk and verify for collision between the enemies and player fire
        playerFire = player.getFire();
        if (playerFire != null)
        {
            for(int i = 0 ; i < playerFire.size() ; i++)
            {
                PlayerFire fire = (PlayerFire) playerFire.get(i);
                fire.step(0);
                if (fire.x > Global.WIDTH || fire.y > Global.HEIGHT)
                {
                    scene.removeOverlay(fire);
                    playerFire.remove(fire);
                    fire = null;
                }
                else
                {
                    Point fireMin = new Point(fire.x, fire.y);
                    Point fireMax = new Point(fire.x + (Global.FIRE_WIDTH-1), fire.y + (Global.FIRE_HEIGHT-1));
                    for(int j = 0 ; j < enemies.size(); j++)
                    {
                        Enemy enemy = (Enemy) enemies.get(j);
                        
                        Point enemyMin = new Point(enemy.x, enemy.y);
                        Point enemyMax = new Point(enemy.x + (Global.ENEMY_WIDTH-1), enemy.y + (Global.ENEMY_HEIGHT-1));

                        // collision between enemy and player
                        if(isColliding(enemyMin, enemyMax, fireMin, fireMax) == true)
                        {
                            Explosion explosion = new Explosion();
                            explosion.x = enemy.x;
                            explosion.y = enemy.y;
                            
                            explosion.setSprite(explosionEnemySprite);
                            explosion.setScene(scene);
                      
                            explosions.add(explosion);
                            
                            score.setScore(+5);
                            
                            scene.removeOverlay(enemy);
                            enemies.remove(enemy);
                            numberOfEnemies--;
                            
                            scene.removeOverlay(fire);
                            playerFire.remove(fire);
                            
                            scene.addOverlay(explosion);
                            
                            if (explosionClip.isRunning())
                                explosionClip.stop(); 
                            explosionClip.setFramePosition(0);
                            explosionClip.start();
                            
                            fire = null;
                            enemy = null;
                        }
                    }
                }
            }
        }
        
        /*
        Vector tiles = scene.getTilesFromRect(playerMin, playerMax);      

        for(int i = 0 ; i < tiles.size() ; i++)
        {
            TileInfo tile = (TileInfo)tiles.elementAt(i);

            if((tile.id == TILE_WALL) && (isColliding(playerMin, playerMax,
                    tile.min, tile.max) == true))
            {
                int centerx = playerMin.x/2 + playerMax.x/2;
                int centery = playerMin.y/2 + playerMax.y/2;

                midx[UP_MID] = tile.min.x/2 + tile.max.x/2;
                midy[UP_MID] = tile.min.y;

                midx[DOWN_MID] = tile.min.x/2 + tile.max.x/2;
                midy[DOWN_MID] = tile.max.y;

                midx[LEFT_MID] = tile.min.x;
                midy[LEFT_MID] = tile.min.y/2 + tile.max.y/2;

                midx[RIGHT_MID] = tile.max.x;
                midy[RIGHT_MID] = tile.min.y/2 + tile.max.y/2;

                int dx = midx[UP_MID] - centerx;
                int dy = midy[UP_MID] - centery;
                int nearestMid = 0;
                double nearestDist = Math.sqrt(dx*dx + dy*dy);

                for(int j = 1 ; j < 4 ; j++)
                {
                    dx = midx[j] - centerx;
                    dy = midy[j] - centery;
                    double dist = Math.sqrt(dx*dx + dy*dy);

                    if(dist < nearestDist)
                    {
                        nearestDist = dist;
                        nearestMid = j;
                    }
                }

                switch(nearestMid)
                {
                    case UP_MID:
                        player.y = tile.min.y - 32;
                        break;
                    case DOWN_MID:
                        player.y = tile.max.y;
                        break;
                    case LEFT_MID:
                        player.x = tile.min.x - 32;
                        break;
                    case RIGHT_MID:
                        player.x = tile.max.x;
                        break;
                }
            }
            else if(tile.id == TILE_EXIT)
            {
                GameEngine.getInstance().setNextGameStateController
                        (Global.END_SCREEN_CONTROLLER_ID);
            }
        }
                */
    }

    public void draw(Graphics g)
    {        
        scene.draw(g);
    }

    public void stop()
    {
 
    }

    public void addEnemyFire(EnemyFire enemyFire)
    {
        enemiesFire.add(enemyFire);
    }

    public void removeEnemyFire(EnemyFire enemyFire)
    {
        enemiesFire.remove(enemyFire);
        enemyFire = null;
    }

    
    private boolean isColliding(Point min1, Point max1, Point min2, Point max2)
    {
        if(min1.x > max2.x || max1.x < min2.x)
        {
            return false;
        }
        if(min1.y > max2.y || max1.y < min2.y)
        {
            return false;
        }

        return true;
    }
}
