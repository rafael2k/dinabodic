/*
 * Main
 */

package dinabodic;

import javaPlay.GameEngine;

public class Main 
{
    public static void main(String[] args) 
    {   
        GameEngine.getInstance().addGameStateController(Global.START_MENU_CONTROLLER_ID, new StartMenuController());

        GameEngine.getInstance().addGameStateController(Global.SIMPLE_CONTROLLER_ID, new SimpleController());

        GameEngine.getInstance().addGameStateController(Global.END_SCREEN_CONTROLLER_ID, new EndScreenController());

        GameEngine.getInstance().setStartingGameStateController(Global.START_MENU_CONTROLLER_ID);

        GameEngine.getInstance().run();        
    }
}
