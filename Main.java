
import java.awt.*; 
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.*;
//Main
public class Main{
	
	public static void main ( String[] args )  {
		//Creates JFrame
		JFrame app = new JFrame("Box Archers");  
		//Creates JPanel
	    Game game = new Game();
	    //Sets size of the JFrame
        app.setSize(800,600);   
        //Allows JFrame to be seen
        app.setVisible( true ); 
        //Make the window centered
        app.setLocationRelativeTo(null);
        //Makes the window not being able to be resized
        app.setResizable(false);
        //Allows the window to close
        app.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        //Add the created JPanel
        Container c = app.getContentPane();
        c.setLayout(new BorderLayout());
        app.add(game, BorderLayout.CENTER);
        c.addKeyListener(game);
        app.addKeyListener(game);

      
  }
	
	
}

