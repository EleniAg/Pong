package server;
import java.net.*;
import java.util.Scanner;

import server.Player;
import server.S_PongModel;
import static common.Global.*;
import common.*;

/**
 * Start the game server
 *  The call to makeActiveObject() in the model
 *   starts the play of the game
 */
class Server
{
  private NetObjectWriter p0, p1; 
  private int PortNumber = 50000; //set  port number to 50000
  private ServerSocket SS;  // create socket SS
  public static void main( String args[] )
  {
   ( new Server() ).start();
  }

  /**
   * Start the server
   */
  public void start()
  {
    DEBUG.set( true );
    DEBUG.trace("Pong Server");
    DEBUG.set( false );               // Otherwise lots of debug info
    try 
    {
    SS = new ServerSocket(PortNumber); //try and initialise SS using port number
    } catch ( Exception e )
    {
    
    }
    while (true){ //constant loop for multiple games
    S_PongModel model = new S_PongModel();

    makeContactWithClients( model );

    S_PongView  view  = new S_PongView(p0, p1 );
                        new S_PongController( model, view );

    model.addObserver( view );       // Add observer to the model
    model.makeActiveObject();        // Start play
    }
    }

  /**
   * Make contact with the clients who wish to play
   * Players will need to know about the model
   * @param model  Of the game
   */
  public void makeContactWithClients( S_PongModel model )
  {
	  try {
		  Socket player0 = SS.accept(); //create a socket called player0 from the server socket
		  
		  p0 = new NetObjectWriter(player0);  // initialise p0 with the socket
		  
		  Player Player = new Player(0, model, player0); //create a player with number 0 and pass model and socket
		  Thread thread0 = new Thread(Player); //create a thread with the player
		  thread0.setPriority( Thread.NORM_PRIORITY);
		  thread0.start();  //start thread
		  
		  
  } catch(Exception e) {

	 
  }
	  
	  try {
		  Socket player1 = SS.accept(); //create a socket called player1 from the server socket
		  
		  p1 = new NetObjectWriter(player1);  // initialise p1 with the socket
		  
		  Player Playert1 = new Player(1, model, player1); //create a player with number 1 and pass model and socket
		  Thread thread1 = new Thread(Playert1); //create a thread with the player
		  thread1.setPriority( Thread.NORM_PRIORITY);
		  thread1.start(); // start thread
		  
		  
  } catch(Exception e) {

	 
  }
  }
}

/**
 * Individual player run as a separate thread to allow
 * updates to the model when a player moves there bat
 */
class Player extends Thread
{
	Socket socket; // socket for connections
	int playerNumber; //player number
	S_PongModel model; // model
	 
  /**
   * Constructor
   * @param player Player 0 or 1
   * @param model Model of the game
   * @param s Socket used to communicate the players bat move
   */
  public Player( int player, S_PongModel model, Socket s  )
  {
	  playerNumber = player;
	  this.model = model;
	  socket = s;
  }


  /**
   * Get and update the model with the latest bat movement
   */
  public void run()                             // Execution
  {
	  try {
		  NetObjectReader input = new NetObjectReader(socket); //try create a reader with the socket
		  while (true){  //constant loop
			  String updatemsg = (String) input.get();		// create string from the reader.get	 
			  Scanner sc = new Scanner(updatemsg);			//create a scanner using the string								
			  double movement;   // a double to identify movement
			  movement = 2;  // movement can't be 1 or 0
			  for(int i = 0; i < 1; i++)	{										
				  movement = sc.nextDouble(); // get the doble from the string
			  }
			  sc.close();  //close the scanner
			  if (movement == 0){ // if movement is 0
				  
				  model.getBat(playerNumber).moveY(10); //move 10 to the Y direction for the current player
				  
			  }
			  
		if (movement == 1){ // if momvement is 1
				  
				  model.getBat(playerNumber).moveY(-10); // move 10 to the opposite direction for the current player
				  
			  }
		  }
			  
		  
	  } catch (Exception e) {
		  // TODO Auto-generated catch block
		  e.printStackTrace();
	  }

  }
}
