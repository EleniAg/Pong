package client;

import client.Player;
import common.*;
import static common.Global.*;

import java.io.IOException;
import java.net.Socket;

/**
 * Start the client that will display the game for a player
 */
class Client
{
	Socket socket;  // socket for connection to server
  public static void main( String args[] )
  {
    ( new Client() ).start();
  }

  /**
   * Start the Client
   */
  public void start()
  {
    DEBUG.set( true );
    DEBUG.trace( "Pong Client" );
    DEBUG.set( false );
    C_PongModel       model = new C_PongModel();
    C_PongView        view  = new C_PongView();
    C_PongController  cont  = new C_PongController( model, view );

    makeContactWithServer( model, cont );

    model.addObserver( view );       // Add observer to the model
    view.setVisible(true);           // Display Screen
  }

  /**
   * Make contact with the Server who controls the game
   * Players will need to know about the model
   *
   * @param model Of the game
   * @param cont Controller (MVC) of the Game
   */
  public void makeContactWithServer( C_PongModel model,
                                     C_PongController  cont )
  {
    // Also starts the Player task that get the current state
    //  of the game from the server
	  try {
          socket = new Socket("localhost", 50000); // try connect with socket at port 50000 on localhost
          NetObjectWriter writer = new NetObjectWriter( socket );  // initialise writer with socket
          cont.setWriter(writer);  // set writer in the controller
          Player Player = new Player(model, socket);  // create new player using model and socket
     	  Thread thread = new Thread(Player);  //create a thread with Player and start it
     	  thread.setPriority( Thread.NORM_PRIORITY); 
     	  thread.start();   
                  
    }
	  
    catch (IOException e) {
        
    	
    }
 
}
  }

