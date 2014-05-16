package client;

import common.*;
import static common.Global.*;

import java.net.Socket;
import java.util.Scanner;
/**
 * Individual player run as a separate thread to allow
 * updates immediately the bat is moved
 */
class Player extends Thread
{
	C_PongModel model;
	Socket s;

  /**
   * Constructor
   * @param model - model of the game
   * @param s - Socket used to communicate with server
   */
  public Player( C_PongModel model, Socket s  )
  {
    // The player needs to know this to be able to work
	  this.model = model;
	  this.s = s;
  }


  /**
   * Get and update the model with the latest bat movement
   * sent by the server
   */
  public void run()                             // Execution
  {
    // Listen to network to get the latest state of the
    //  game from the server
    // Update model with this information, Redisplay model
    DEBUG.trace( "Player.run" );
    try {
		  NetObjectReader input = new NetObjectReader(s);  // try make a reader with socket s
		  
		  while(true){    // constant loop
			  String update = (String) input.get(); //create string from the reader.get
			  Scanner scanner = new Scanner(update);  //create a scanner with this string									
			  double[] positions = new double[6];	  //create an arraylist of doubles
			  for(int i = 0; i < 6; i++)	{			  							
				  positions[i] = scanner.nextDouble();	  //populate arraylist using doubles from scanner
			  }
			  scanner.close();  //close scanner
			  GameObject bats[] = new GameObject[2];  // create new arraylist with gameobjects
			  bats[0] = new GameObject(positions[0], positions[1], BAT_WIDTH, BAT_HEIGHT); // using the positions arraylist.. 
			  bats[1] = new GameObject(positions[2], positions[3], BAT_WIDTH, BAT_HEIGHT);  // ..set the objects' values
			  GameObject ball = new GameObject(positions[4], positions[5], BALL_SIZE, BALL_SIZE); // create a game object for the ball setting values with the positions arraylist
			  model.setBall(ball); //set the model's ball using the ball made
			  model.setBats(bats);  //same for bats
			  model.modelChanged();  // call modelChanged method on the model
			  
			  
		  }
	  } catch(Exception e) {

	  }


	  DEBUG.trace( "Player.run" );
}
  }

