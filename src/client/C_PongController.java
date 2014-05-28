package client;
import common.*;

import java.awt.event.KeyEvent;

import common.GameObject;
/**
 * Pong controller, handles user interactions
 */
public class C_PongController
{
  private C_PongModel model
  private C_PongView  view;
  private NetObjectWriter writer; //for the output to server

  /**
   * Constructor
   * @param aPongModel Model of game on client
   * @param aPongView  View of game on client
   */
  public C_PongController( C_PongModel aPongModel, C_PongView aPongView)
  {
    model  = aPongModel;
    view   = aPongView;
    view.setPongController( this );  // View talks to controller
  }
  
public void setWriter(NetObjectWriter writer){
	  
	  this.writer = writer; //set the writer
  }

  /**
   * Decide what to do for each key pressed
   * @param keyCode The keycode of the key pressed
   */
  public void userKeyInteraction(int keyCode )
  {
    // Key typed includes specials, -ve
    // Char is ASCII value
    switch ( keyCode )              // Character is
    {
      case -KeyEvent.VK_LEFT:        // Left Arrow
        break;
      case -KeyEvent.VK_RIGHT:       // Right arrow
        break;
      case -KeyEvent.VK_UP:          // Up arrow
        writer.put("1");  //if user presses up write "1" to server
        break;
      case -KeyEvent.VK_DOWN:        // Down arrow
    	  writer.put("0");  //if user presses down write "0" to server
        break;
    }
  }


}

