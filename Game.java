
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
//Game Screen
public class Game extends JPanel implements KeyListener, ActionListener{
	//Initializes variables
	Timer myTimer = new Timer(3,this);
	//Creates a 2-D Array tilemap 
	int[][] map;
	//Initializes the player onto the map
	int round = 1;
	int maxRounds = 6;
	boolean gameEnd = false;
	//menu
	boolean pause = false;
	boolean menuPress = false;
	int choice = 0;
	int level = 0;
	//end of menu 
	double pX;
	double pY;
	double pX2;
	boolean isCharging = false;
	boolean isCharging2 = false;
	double charge2;
	double charge = 0;
	double pY2;
	double velX1;
	double velX2;
	int page = 1;
	double velY1;
	double velY2;
	String aim;
	String aim2;
	Color lightblue = new Color(37, 84, 199);
	Color red = new Color(240,37,37);
	Font font = new Font("Tahoma",Font.BOLD,16);
	Font helpFont = new Font("Tahoma",Font.BOLD,16);
	Font defFont = new Font("Arial",Font.PLAIN,12);
	Font titleFont = new Font("Tahoma",Font.BOLD,54);
	Font stageFont = new Font("Tahoma",Font.BOLD,48);
	Font winFont = new Font("Tahoma",Font.BOLD,40);
	Font megaFont = new Font("Tahoma",Font.BOLD,64);
	int arrCount = 3;
	int arrCount2 = 3;
	Player player;
	Player2 player2;
	ArrayList<Arrow> Arrows = new ArrayList<Arrow>();
	ArrayList<PickUp> PickUp = new ArrayList<PickUp>();
	Graphics2D g;
	int life = 1;
	int life2 = 1;
	boolean cont = false;
	int p1Win = 0;
	int p2Win = 0; 
	int tileSize = 32;
	int aY;
	Map playMap;
	String mapSelect;
	int mapNum;
	int musicNum;
	boolean wantSound = true;
	//Images
	static BufferedImage img = null;
	static BufferedImage img2 = null;
	static BufferedImage img3 = null;
	static BufferedImage img4 = null;
	static BufferedImage img5 = null;
	static BufferedImage img6 = null;
	static BufferedImage img7 = null;
	//Sound
	AudioPlayer menuMusic  = new AudioPlayer("Sound\\MeleeMenu.wav");
	AudioPlayer map04Music = new AudioPlayer("Sound\\Cornered.wav");
	AudioPlayer map02Music = new AudioPlayer("Sound\\Gourmet.wav");
	AudioPlayer map01Music = new AudioPlayer("Sound\\Destination.wav");
	AudioPlayer map03Music = new AudioPlayer("Sound\\Corn.wav");
	AudioPlayer bluewin = new AudioPlayer("Sound\\bluewins.wav");
	AudioPlayer redwin = new AudioPlayer("Sound\\redwins.wav");
	AudioPlayer arrwall = new AudioPlayer("Sound\\arrwall.wav");
	AudioPlayer arrfire = new AudioPlayer("Sound\\arrfire.wav");
	AudioPlayer arrpickup = new AudioPlayer("Sound\\arrpickup.wav");
	AudioPlayer arrhit = new AudioPlayer("Sound\\arrhit.wav");
	//Constructor
	public Game() {
		//Keyboard things
		playMap = new Map();
		map = playMap.giveMap();
		player = new Player(50,450,map);
		player2 = new Player2(720,450,map);
		setFocusable(true);
		addKeyListener(this);
		myTimer.start();
		try {
			//Images Loading
			img = ImageIO.read(new File("1thumb.png"));
			img2 = ImageIO.read(new File("2thumb.png"));
			img3 = ImageIO.read(new File("3thumb.png"));
			img4 = ImageIO.read(new File("4thumb.png"));
			img5 = ImageIO.read(new File("bg.jpg"));
			img6 = ImageIO.read(new File("cubicfunction.png"));
			img7 = ImageIO.read(new File("cubicfunctionred.png"));
			//Sound

		}
		 catch (IOException e) {
				e.printStackTrace();
				
		}

	}
	//Paints stuff to the screen
	public void paintComponent(Graphics g) {
		
		//Apparently we just need it
		super.paintComponent(g);
		//Makes a graphics 2D
		Graphics2D g2d = (Graphics2D) g;
		
		//Main Menu
		g.setFont(megaFont);
		
		//Level 0 is the main menu
		if (level == 0){

			g.drawImage(img5,0,0,800,600,null);
			g.drawString("BOX ARCHERS", 175, 100);
			g.setFont(titleFont);
			g.setColor(Color.BLACK);
			//Depending on choice, the color will be switched to Red at a different time
			//This will mean a different drawString will be red depending on choice
			//Choice depends on the user's keyboard input
			//This simulates a moving menu
			if (choice == 0){
				g.setColor(Color.RED);
				//Menupress becomes true when Enter is pressed
				//Depending on the choice value when menupress is true, different things will happen
				//This simulates a menu
				if (menuPress){
					//Level is changed meaning the gamestate is changed
					//Different things will be drawn to the screen depending on the level
					level = 2;
					menuPress= false;
					resetRunner();
				}
			}
			g.drawString("Start Game", 250, 300);
			g.setColor(Color.BLACK);
			if (choice ==1){
				g.setColor(Color.RED);
				if (menuPress){
					level = 3;
					menuPress = false;
					
				}
				
			}
			g.drawString("Help", 330, 400);
			g.setColor(Color.BLACK);
			if (choice == 2){
				g.setColor(Color.RED);
				if (menuPress){
					page = 1;
					System.exit(0);
					menuPress = false;
					
				}
				
			}
			
			g.drawString("Quit", 330, 500);
			
		}
		
		//Game
		
		//Level 1 is the game itself
		if (level == 1){
			//Map is drawn
			playMap.drawMap(g);
			//If pause is true, pause menu will display. Else, game will run
			if (pause){
				//myTimer.stop();
				g.setColor(Color.WHITE);
				g.fillRect(250, 100, 300, 300);
				g.drawImage(img5,250,100,300,300,null);
				g.setFont(titleFont);
				g.setColor(Color.BLACK);
				g.drawString("PAUSE", 310, 150);
				if (choice == 0){
					g.setColor(Color.RED);
					if (menuPress){
						level = 1;
						page = 1;
						menuPress= false;
						pause = false;
					}
				}
				g.drawString("Resume", 290, 240);
				g.setColor(Color.BLACK);
				if (choice == 1){
					g.setColor(Color.RED);
					if (menuPress){
						choice = 0;
						page = 1;
						level = 0;
						p1Win = 0;
						p2Win = 0;
						round = 1;
						menuPress = false;
						resetRunner();
						pause = false;
					}
					
				}
				g.drawString("Menu", 325, 330);
			}
			else{
				//Draws players
				g.setColor(lightblue);
				g.setFont(font);
				g.drawString("P1: " + p1Win, 100,20);
				g.setColor(red);
				g.drawString("P2: " + p2Win, 650,20);
				g.setFont(defFont);
				g.setColor(lightblue);
				player.draw(g2d);
				g.setColor(red);
				player2.draw(g2d);
				//Sets brush stroke size
				((Graphics2D) g).setStroke(new BasicStroke(4));
				g.setColor(Color.ORANGE);
				//Charge bar changes size according to a variable that increases with time it is held down
				g.drawLine((int)pX-4,(int)pY-4,(int)pX-4+(int)charge*4,(int)pY-4);
				g.drawLine((int)pX2-4,(int)pY2-4,(int)pX2-4+(int)charge2*4,(int)pY2-4);
				cont = false;
				
				//If one of the players are hit with an arrow, life goes down 1 to 0
				//Round ends and score goes up for the corresponding player
				if (life < 1){
					if (!cont){
						g.setFont(font);
						g.setColor(red);
						p2Win++;
						//Game is stopped till someone presses C to continue
						
						if (p2Win >= maxRounds){
							g.drawString("PLAYER 2 WINS", 330,270);
							g.drawString("PRESS \"C\" TO CONTINUE", 300,300);
						}
						else{
							g.drawString("ROUND " + round + " FINISH", 330,270);
							g.drawString("PRESS \"C\" TO CONTINUE", 300,300);
						}
						
						
					}
				
				}
				if (life2 < 1){
					if (!cont){
						g.setFont(font);
						g.setColor(lightblue);
						p1Win++;
						if (p1Win >= maxRounds){
							
							g.drawString("PLAYER 1 WINS", 330,270);
							g.drawString("PRESS \"C\" TO CONTINUE", 300,300);
						}
						else{
							g.drawString("ROUND " + round + " FINISH", 330,270);
							g.drawString("PRESS \"C\" TO CONTINUE", 300,300);
						}
						
						
					}
					
				}
				//Draws arrows
				for(int i = 0; i<Arrows.size();i++){
					
					Arrows.get(i).draw(g);
				}
				//Draws pickupable arrows
				for(int i = 0; i<PickUp.size();i++){
					PickUp.get(i).draw(g);
				}
				g.setColor(Color.CYAN);
				//Draws arrows left to use 
				for(int i = 0; i < arrCount; i++){
					if (i == 0){
						g.fillRect((int)pX-5,(int)pY-8,4,4);
					}
					if (i == 1){
						g.fillRect((int)pX+5,(int)pY-8,4,4);
					}
					if (i == 2){
						g.fillRect((int)pX+15,(int)pY-8,4,4);
					}
					if (i == 3){
						g.fillRect((int)pX+25,(int)pY-8,4,4);
					}
					if (i == 4){
						g.fillRect((int)pX+35,(int)pY-8,4,4);
					}
					
				}
				g.setColor(Color.MAGENTA);
				for(int i = 0; i < arrCount2; i++){
					if (i == 0){
						g.fillRect((int)pX2-5,(int)pY2-8,4,4);
					}
					if (i == 1){
						g.fillRect((int)pX2+5,(int)pY2-8,4,4);
					}
					if (i == 2){
						g.fillRect((int)pX2+15,(int)pY2-8,4,4);
					}
					if (i == 3){
						g.fillRect((int)pX2+25,(int)pY2-8,4,4);
					}
					if (i == 4){
						g.fillRect((int)pX2+35,(int)pY2-8,4,4);
					}
				
			}
			g.setColor(Color.BLACK);

			
			//gameEnd is true if a score limit is reached. The game is reset
			if (gameEnd){
				level = 4;
				
			}
			
			}
		}
		
		//Level 2 is Stage Select
		if (level == 2){
			g.drawImage(img5,0,0,800,600,null);
			//When choice goes past a certain number, the page changes and new maps are displayed
			if (page == 1){
				g.setColor(Color.BLACK);
				g.setFont(stageFont);
				g.drawImage(img,30,30,300,200,null);
				g.drawImage(img2,30,330,300,200,null);
				g.drawString("First Destination", 370, 115);
				g.drawString("Vert Verts", 430, 415);
				
				//Depending on which choice is chosen, a different map will be loaded in the Map class
				if (choice == 0){
					g.setColor(Color.RED);
					if (menuPress){
						resetRunner();
						playMap.setMap01();
						mapNum = 1;
						level = 1;
						menuPress = false;
						
					}
				}
				
				//Sets brushstroke size
				((Graphics2D) g).setStroke(new BasicStroke(4));
				g.drawRect(30,30,300,200);
				
				g.setColor(Color.BLACK);
				
				if (choice == 1){
					
					g.setColor(Color.RED);
					if (menuPress){
						resetRunner();
						playMap.setMap02();
						mapNum = 2;
						level = 1;
						menuPress = false;
						
					}
				}
				g.drawRect(30,330,300,200);
				
				g.setColor(Color.BLACK);
				
				
			}
			if (page == 2){
				g.setColor(Color.BLACK);
				g.setFont(stageFont);
				g.drawImage(img3,30,30,300,200,null);
				g.drawImage(img4,30,330,300,200,null);
				g.drawString("Pyramid Scheme", 370, 115);
				g.drawString("Rainbow Road", 370, 415);
				if (choice == 2){
					g.setColor(Color.RED);
					if (menuPress){
						resetRunner();
						playMap.setMap03();
						mapNum = 3;
						level = 1;
						menuPress = false;
						
					}
				}
				
				((Graphics2D) g).setStroke(new BasicStroke(4));
				g.drawRect(30,30,300,200);
				g.setColor(Color.BLACK);
				
				if (choice == 3){
					
					g.setColor(Color.RED);
					if (menuPress){
						resetRunner();
						mapNum = 4;
						playMap.setMap04();
						level = 1;
						menuPress = false;
						
					}
				}
				g.drawRect(30,330,300,200);
				g.setColor(Color.BLACK);
				
				
			}
		}
		
		//Level 3 is the help menu
		if (level == 3){
			g.drawImage(img5,0,0,800,600,null);
			if (page == 1){
				g.drawString("HELP",300,100);
				g.setFont(helpFont);
				g.drawString("In this game, 2 players engage in battle by shooting arrows at each other.",10,150);
				g.drawString("Both players start off with 3 arrows. Each shot depletes 1 arrow and fired arrows can be picked",10,180);
				g.drawString("up by both players. A maximum of 5 arrows can be held.",10,210);
				g.drawString("Players have the ability to double jump and wall jump, as well as aim in all 4 directions.",10,270);
				g.setFont(stageFont);
				g.drawString("PRESS RIGHT FOR CONTROLS",30,400);
			
			
			
			}		
			if (page == 2){
				g.drawString("HELP",300,100);
				g.setFont(titleFont);
				g.drawString("PLAYER 1",50,200);
				g.drawString("PLAYER 2",450,200);
				g.setFont(helpFont);
				g.drawString("A - LEFT",125,250);
				g.drawString("D - RIGHT",125,275);
				g.drawString("W - JUMP",125,300);
				g.drawString("U - AIM UP",125,325);
				g.drawString("H - AIM LEFT",125,350);
				g.drawString("K - AIM RIGHT",125,375);
				g.drawString("J - AIM DOWN",125,400);
				g.drawString("SPACE - SHOOT",125,425);
				g.drawString("LEFT - LEFT",500,250);
				g.drawString("RIGHT - RIGHT",500,275);
				g.drawString("UP - JUMP",500,300);
				g.drawString("5 - AIM UP",500,325);
				g.drawString("3 - AIM LEFT",500,350);
				g.drawString("1 - AIM RIGHT",500,375);
				g.drawString("2 - AIM DOWN",500,400);
				g.drawString("0 - SHOOT",500,425);
			}
		}
		if (level == 4){
			if (p1Win >= maxRounds && p2Win >= maxRounds){
				g.setColor(Color.BLACK);
				g.fillRect(0,0,800,600);
				g.setColor(Color.WHITE);
				g.setFont(megaFont);
				g.drawString("NO CONTEST",207,300);
				g.setFont(winFont);
				g.drawString("PRESS ESC TO RETURN TO MENU",70,100);
			}
			else if (p1Win >= maxRounds){
				g.drawImage(img6,0,0,800,600,null);
				g.setColor(Color.WHITE);
				g.setFont(winFont);
				g.drawString("PRESS ESC TO RETURN TO MENU",70,100);
			}
			else if (p2Win >= maxRounds){
				g.drawImage(img7,0,0,800,600,null);
				g.setColor(Color.WHITE);
				g.setFont(winFont);
				g.drawString("PRESS ESC TO RETURN TO MENU",70,100);
				
			}
		}
	}
		
		
	//Update
	public void actionPerformed(ActionEvent e){
		if (wantSound){
			musicPlayer();
		}
		//Only updates if pause menu is not in effect
		if (!pause){
		
		deathCollision();
		arrowCollision();
		pickUpCollision();
		
		player.update();
		player2.update();
		pX = player.pX;
		pY = player.pY;
		pX2 = player2.pX;
		pY2 =  player2.pY;
		velX1 = player.velX;
		velX2 = player2.velX;
		velY1 = player.velY;
		velY2 = player2.velY;
		aim = player.aim;
		aim2 = player2.aim;
		//Updates arrows and pickupable arrows only for the amount of arrows that are actually present
		//Arrows and pickupable arrows are managed within ArrayLists
		//There are as many entries as there are arrows
		//Therefore, the for loop will only run the update function for as many arrows as there are
		for(int i = 0; i<Arrows.size();i++){
			Arrows.get(i).update();
		}
		for(int i = 0; i<PickUp.size();i++){
			PickUp.get(i).update();		}
		if (arrCount > 5){
			arrCount = 5;
		}
		if (arrCount2 > 5){
			arrCount2 = 5;
		}
		
		//While firing button is being held down, charge will increase. The distance the arrow is shot will
		//vary depending on how big this variable is
		if (isCharging){
			if (charge <= 10){
				charge+= 0.1;
			}
		}
		else{
			charge = 0;
		}
		if (isCharging2){
			if (charge2 <= 10){
				charge2+= 0.1;
			}
		}
		else{
			charge2 = 0;
		}
		}
		repaint();

		
	}
	
	
	//Takes in keyboard inputs from player
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		//======Moving the player around========
		if (key == KeyEvent.VK_A){
			
			if (level == 1){
				player.moveLeft();
			}
			if (level == 3){
				page = 1;
			}
		}
		else if (key == KeyEvent.VK_D){
			
			if (level == 1){
				player.moveRight();
			}
			if (level == 3){
				page = 2;
			}
		}
		if (key == KeyEvent.VK_W){
			//Jump
			
			//The up and down keys for both players will function differently depending on the level
			//If it's a gamestate other than Level 1, these keys will move choice up and down
			//Basically functioning the menus
			if ((level == 1 && pause)){
				choice--;
				if (choice < 0 && level == 1){
					choice = 1;
				}
				else if (choice < 0){
					choice = 1;
				}
			}
			if ((level == 0)){
				choice--;
				if (choice < 0){
					choice = 2;
				}
				else if (choice < 0){
					choice = 0;
				}
			}
			if (level == 2){
				choice--;
				if (choice < 0){
					choice = 0;
				}
				if (choice < 2){
					page = 1;
				}
			}
			if (!pause && level == 1){
				player.jump();
			}
		}
		if (key == KeyEvent.VK_S){
			//Jump
			//The up and down keys for both players will function differently depending on the level
			//If it's a gamestate other than Level 1, these keys will move choice up and down
			//Basically functioning the menus
			if ((level == 1 &&pause)){
				choice++;
				if (choice > 1 && level == 1){
					choice = 0;
				}
				else if (choice > 1){
					choice = 0;
				}
			}
			if ((level == 0)){
				choice++;
				//Wraps the choices around so that if choice goes past a certain point,
				//it will reappear at the other end
				//For example, if you are at the option at the very top and you press up,
				//Your choice will go the very bottom
				if (choice > 2){
					choice = 0;
				}
				
			}
			if (level == 2){
				choice++;
				if (choice > 1){
					page = 2;
				}
				if (choice > 3){
					choice = 3;
				}
			}
			
		}
		
		else if (key == KeyEvent.VK_SPACE){
			//Shoot
			if (level == 1){
				isCharging = true;
			}
			if (level == 3){
				page = 2;
			}
			
			
		}
		//======Moving PLAYER2 around========
		if (key == KeyEvent.VK_LEFT){
			
			if (level == 1){
				player2.moveLeft();
			}
			if (level == 3){
				page = 1;
			}
			
		}
		else if (key == KeyEvent.VK_RIGHT){
			
			if (level == 1){
				player2.moveRight();
			}
			if (level == 3){
				page = 2;
			}
			
		}
		if (key == KeyEvent.VK_UP){
			//The up and down keys for both players will function differently depending on the level
			//If it's a gamestate other than Level 1, these keys will move choice up and down
			//Basically functioning the menus
			if ((level == 1 && pause)){
				choice--;
				if (choice < 0 && level == 1){
					choice = 1;
				}
				else if (choice < 0){
					choice = 1;
				}
			}
			if ((level == 0)){
				choice--;
				if (choice < 0){
					choice = 2;
				}
				else if (choice < 0){
					choice = 0;
				}
			}
			if (level == 2){
				choice--;
				if (choice < 0){
					choice = 0;
				}
				if (choice < 2){
					page = 1;
				}
			}
			System.out.println("#Jump");
			if (!pause && level == 1){
				player2.jump();
			}
		}
		if (key == KeyEvent.VK_DOWN){
			//Jump
			//The up and down keys for both players will function differently depending on the level
			//If it's a gamestate other than Level 1, these keys will move choice up and down
			//Basically functioning the menus
			if ((level == 1 &&pause)){
				choice++;
				if (choice > 1 && level == 1){
					choice = 0;
				}
				
			}
			if ((level == 0)){
				choice++;
				if (choice > 2){
					choice = 0;
				}
				
			}
			if (level == 2){
				choice++;
				if (choice > 1){
					page = 2;
				}
				if (choice > 3){
					choice = 3;
				}
			}
			
		}
		else if (key == KeyEvent.VK_NUMPAD0){
			//Shoot
			isCharging2 = true;
			}

		
		//=========Shooting directions============
		if (key == KeyEvent.VK_H){
			//Aim Left
			System.out.println("#Left Aim");
			player.aimLeft();
		}
		else if (key == KeyEvent.VK_K){
			//Aim Right
			System.out.println("#Right Aim");
			player.aimRight();
		}
		if (key == KeyEvent.VK_U){
			//Aim Up
			System.out.println("#Up Aim");
			player.aimUp();
		}
		else if (key == KeyEvent.VK_J){
			//Aim Down
			System.out.println("#Down Aim");
			player.aimDown();
		}
		//======Shooting PLAYER2 around========
		if (key == KeyEvent.VK_NUMPAD1){
			//Aim Left
			System.out.println("#Left Aim");
			player2.aimLeft();
		}
		else if (key == KeyEvent.VK_NUMPAD3){
			//Aim Right
			System.out.println("#Right Aim");
			player2.aimRight();
		}
		if (key == KeyEvent.VK_NUMPAD5){
			//Aim Up
			System.out.println("#Up Aim");
			player2.aimUp();
		}
		else if (key == KeyEvent.VK_NUMPAD2){
			//Aim Down
			System.out.println("#Down Aim");
			player2.aimDown();
		}
		
		//Resets game after a round finishes
		if (key == KeyEvent.VK_C){
			
			if (life < 1 || life2 < 1){
				resetRunner();
				life = 1;
				life2 = 1;
				myTimer.start();
				cont = true;
				round++;
			}
			
			//Checks to see if the game is over
			if (p1Win >= maxRounds || p2Win >= maxRounds){
				gameEnd = true;
			}
			
			
			
		}
		
		//Used to select options in menus
		if (key == KeyEvent.VK_ENTER){
			if (level == 0 || (level == 1 && pause) || level == 2){
				menuPress = true;
			}
			
		}
		
		//Pauses the game
		if (key == KeyEvent.VK_ESCAPE){
			if (level == 1){
				choice = 0;
				pause = true;
				
			}
			
			//If ESC is pressed while in any of the menus or the win screen, it will return to the main menu
			if (level == 2){
				choice = 0;
				page = 1;
				level = 0;
				
			}
			if (level == 3){
				level = 0;
				page = 1;
				choice = 0;
				
			}
			if (gameEnd){
				resetRunner();
				choice = 0;
				pause = false;
				level = 0;
				p1Win = 0;
				p2Win = 0;
				gameEnd = false;
				round = 1;
				
			}
			
		}
	}
	//Takes in keyboard released from player
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		
		if (key == KeyEvent.VK_A){
			player.stopMoving();
		}
		else if (key == KeyEvent.VK_D){
			player.stopMoving();
		}
		//======PLAYER 2 STOP MOVING========
		if (key == KeyEvent.VK_LEFT){
			player2.stopMoving();
		}
		else if (key == KeyEvent.VK_RIGHT){
			player2.stopMoving();
		}
		if (key == KeyEvent.VK_SPACE){
			
			//Can only shoot as long as Player has more arrows
			if (arrCount > 0 && level == 1){
				arrfire.play();
				//Will add an arrow to the ArrayList with different properties depending on
				//The direction the player shot from
				if (aim.equals("u")){
					Arrows.add(new Arrow((int)pX+16,(int)pY-40-(int)velY1,aim,charge));
				}
				if (aim.equals("d")){
					Arrows.add(new Arrow((int)pX+16,(int)pY+44+(int)velY1,aim,charge));
				}
				if (aim.equals("l")){
					Arrows.add(new Arrow((int)pX-40-(int)velX1,(int)pY+16,aim,charge));
				}
				if (aim.equals("r")){
					Arrows.add(new Arrow((int)pX+64+(int)velX1,(int)pY+16,aim,charge));
				}
				arrCount -= 1;
			}
			isCharging = false;
			
		}
		if (key == KeyEvent.VK_NUMPAD0){
			//Shoot
			//Can only shoot as long as Player has more arrows
			if (arrCount2 > 0){
				arrfire.play();
				
				//Will add an arrow to the ArrayList with different properties depending on
				//The direction the player shot from
				if (aim2.equals("u")){
					Arrows.add(new Arrow((int)pX2+16,(int)pY2-40-(int)velY2,aim2,charge2));
				}
				if (aim2.equals("d")){
					Arrows.add(new Arrow((int)pX2+16,(int)pY2+44+(int)velY2,aim2,charge2));
				}
				if (aim2.equals("l")){
					Arrows.add(new Arrow((int)pX2-40-(int)velX2,(int)pY2+16,aim2,charge2));
				}
				if (aim2.equals("r")){
					Arrows.add(new Arrow((int)pX2+64+(int)velX2,(int)pY2+16,aim2,charge2));
				}
				arrCount2 -= 1;
			}
			isCharging2 = false;
		}
		
	}
	
	//What is this even? I'm not using it but apparently needed for KeyListener
	public void keyTyped(KeyEvent e) {
		//
		
	}
	
//Checks to see if player collides with an arrow
public void deathCollision(){
		//Declares rectangles
		Rectangle2D Player = new Rectangle2D.Double(pX,pY,tileSize+7,tileSize+7);
		Rectangle2D Player2 = new Rectangle2D.Double(pX2,pY2,tileSize+7,tileSize+7);
		//Goes through all the arows that exist
		for(int i = 0; i<Arrows.size();i++){
			//Rectangle becomes the specifc arrow
			Rectangle2D arrow = Arrows.get(i);
			//If both rects collide, life goes down by 1
			if (Player.intersects(arrow)){
				arrhit.play();
				life--;
				myTimer.stop();
				
			}
			if (Player2.intersects(arrow)){
				arrhit.play();
				life2--;
				myTimer.stop();
			}
			
		}

	}



//Checks to see if arrow collides with a wall
public void arrowCollision(){
	//Will go through all units within the 2D array of map
	for (int i = 0; i < map[1].length; i++){
		for (int q = 0; q < map.length; q++){
			//Will go through all arrows in the arrayList
			for(int z = 0; z<Arrows.size();z++){
				//Rectangle will become the specific arrow that the for loop is going through
				Rectangle2D arrow = Arrows.get(z);
				//If the specific map block is a 0 or a 2 (a wall block)
				if(map[q][i] == 0 || map[q][i] == 2){
					//Rectangle is location of the map block times tile size (32 because everything is 32x32)
					Rectangle2D terrain = new Rectangle2D.Double(i*tileSize,q*tileSize,tileSize,tileSize);
					//Checks if any points collide between the player and the map block
					if (terrain.contains(new Point((int)arrow.getX(),(int)arrow.getY())) //Top Left
							|| terrain.contains(new Point((int)arrow.getX(),(int)arrow.getY()+8)) //Bottom Left
							|| terrain.contains(new Point((int)arrow.getX()+8,(int)arrow.getY()+8)) //Bottom Right
							|| terrain.contains(new Point((int)arrow.getX()+8,(int)arrow.getY()))){ //Top Right
							aY = (int)arrow.getY();
							//Adds a new item to the PickUp arrayList
							PickUp.add(new PickUp((int)arrow.getX(),(int)arrow.getY(),Arrows.get(z).getDir(),charge));
							//Removes the arrow that hit the wall from the list
							Arrows.remove(Arrows.get(z));
							arrwall.play();
					}
					
				
				}
			}
		}
	}
}

//Checks to see if player collides with a pickupable arrow
public void pickUpCollision(){
	//Creates rectangles
	Rectangle2D Player = new Rectangle2D.Double(pX,pY,tileSize,tileSize);
	Rectangle2D Player2 = new Rectangle2D.Double(pX2,pY2,tileSize,tileSize);
	//Goes through all of the pickupable arrows in the list
	for(int i = 0; i<PickUp.size();i++){
		//Rectangle becomes the specifc pickupable arrow
		Rectangle2D pick = PickUp.get(i);
		//If player collides with pickupable arrow
		//Arrow Count for the player will go up, but only if it's less than 5 because 5 is the max
		if (Player.intersects(pick)){
			if (arrCount <=4){
				arrpickup.play();
				arrCount++;
				PickUp.remove(PickUp.get(i));
			}
		}
		
			if (Player2.intersects(pick)){
				if (arrCount2 <=4){
					
					if (PickUp.size() >0){
						arrpickup.play();
						arrCount2++;
						PickUp.remove(PickUp.get(i));
					}
					
			}
			
			
		}
	}
	

}

//Resets variables to initial state
public void reset(){
	player.reset();
	player2.reset();
	arrCount = 3;
	arrCount2 = 3;
	charge = 0;
	charge2 =0;
	
	
	for(int i = 0; i<Arrows.size();i++){
		Arrows.remove(Arrows.get(i));
	}
	for(int i = 0; i<PickUp.size();i++){
		PickUp.remove(PickUp.get(i));
	}

}

//For some reason reset() only resets the lists once...so we need this to run reset multiple times. No idea why..
public void resetRunner(){
	for (int i = 0; i < 12; i++){
		reset();
	}
	
}
public void musicPlayer(){
	if (level == 0 || level == 2 || level == 3){
		menuMusic.loop();
	}
	else{
		menuMusic.stop();
	}
	if(level == 1 && mapNum == 4){
		map04Music.loop();	
	}
	else{
		map04Music.stop();
	}
	if(level == 1 && mapNum == 2){
		map02Music.loop();	
	}
	else{
		map02Music.stop();
	}
	if(level == 1 && mapNum == 1){
		map01Music.loop();	
	}
	else{
		map01Music.stop();
	}
	if(level == 1 && mapNum == 3){
		map03Music.loop();	
	}
	else{
		map03Music.stop();
	}
	if(level == 4 && p1Win >= maxRounds){
		bluewin.loop();	
	}
	else{
		bluewin.stop();
	}
	if(level == 4 && p2Win >= maxRounds){
		redwin.loop();	
	}
	else{
		redwin.stop();
	}
	
	
}
}