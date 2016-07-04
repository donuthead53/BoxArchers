
import java.awt.*; 
import java.awt.geom.Rectangle2D;
//Player 1
public class Player {
	double currY;
	double lastY;
	int yCount = 0;
	int lastJumpCount;
	boolean wallJump = true;
	boolean inAir = false;
	boolean arRight = false;
	int arrCountDraw = 0;
	int jumpCount = 2;
	boolean didShoot = false;
	static boolean initShoot = false;
	int arrCount = 3;
	//If they're pressing right
	boolean right = false;
	//If they're pressing left
	boolean left = false;
	//Collide left boolean
	boolean colLeft = false;
	//Collide bottom boolean
	boolean colBot = false;
	//Collide right boolean
	boolean colRight = false;
	//Collide top boolean
	boolean colTop = false;
	//Collide nothing
	boolean colNothing = true;
	//Aim variable
	static String aim = "r";
	//Their X position
	static double pX;
	//Their Y position
	static double pY;
	//Speed of player's X
	double velX = 0;
	//Speed of player's Y
	double velY = 0;
	//Jumping or not
	boolean jumping = false;
	//Gravity number
	double gravity = 0.07;
	//Tilesize 
	int tileSize = 32;
	//Creates a 2-D Array tilemap 
	int[][] map;
	//Bow Displacement variables
	int aX;
	int aY;
	int bowX=16;
	int bowY=16;
	int bowHeight = 16;
	int bowLength = 8;
	double collideXat = pX;
	double collideYat = pY;
	double terminalVel = 10;

	//Constructor for in take of starting position from Game.java
	public Player(int x, int y,int map[][]){
		//Position X = given x
		pX = x;
		//Position Y = give y
		pY =y;
		this.map = map;
	}
	//Paint 
	public void draw(Graphics2D g){
		//Draw player
		g.fillRect((int)(Math.round((pX))),(int)(Math.round((pY*100)/100)), tileSize, tileSize);
		g.setColor(Color.CYAN);
		//Draws bow
		Rectangle bow = new Rectangle((int)pX+bowX, ((int)pY+bowY), bowHeight, bowLength);
		g.fill(bow);
		g.draw(bow);
		
		
		 
		g.setColor(Color.BLACK);


		

		
		

	}
	//Updating the player
	public void update() {
		//Always add speed to player for smoother movement because it works better than positional moving
		//Add X speed to position X
		pX += velX;
		//Add Y speed to position Y
		pY += velY;
		//The power of gravity is always with you.
		if (velY<terminalVel){
			velY+=gravity;
		}
		else{
			velY=terminalVel;
		}
		Collision();
		//Checks the player's Y value every 2 frames. If there is a difference in the Y values
		//This means that the player is in the air, therefore inAir will become true
		if (yCount == 0){
			lastY = pY;
			yCount ++;
		}
		else{
			currY = pY;
			yCount--;
		}
		if (lastY > currY || lastY < currY){
			inAir = true;
		}
		else{
			inAir = false;
		}
		//Normally, the player will have 2 jumps, this next statement means that the player is on the ground
		//Therefore they have 2 jumps
		//The reason we also checked for left collision is because even when colliding with the ground block
		//The player still collides left because of the rectangles
		if (colLeft && !inAir){
			jumpCount = 2;
		}
		
		//If the player is touching with walls on the side and is in the air
		if (colLeft || colRight && inAir){
			//
			if (jumpCount < 3 && wallJump){
				jumpCount=1;
				wallJump = false;
			}
			colBot = true;
		}
			

	}
	
	//Moving
	public void moveLeft() {
		System.out.println("#Left Move");
		left = true;
		right = false;
		colRight=false;
		velX=-2;
	}
	public void moveRight() {
		System.out.println("#Right Move");
		right = true;
		left = false;
		colLeft=false;
		velX=2;
	}
	public void stopMoving(){
		velX=0;
		left = false;
		right = false;
		
	}
	//Key pressed 
	public void jump(){
		if (jumpCount > 0){
			jumpCount -= 1;
			velY=-5;
			colBot = false;
			wallJump = true;
		}
	}
	
	//Changes bow's position depending on aiming direction
	public void aimLeft(){
		aim = "l";
		bowHeight = 16;
		bowX=0;
		bowY=16;
		bowLength = 8;
	}
	public void aimRight(){
		aim = "r";
		arRight = true;
		bowY=16;
		bowX=16;
		bowHeight = 16;
		bowLength = 8;
	}
	public void aimDown(){
		aim = "d";
		bowY=16;
		bowX=8;
		bowHeight = 8;
		bowLength = 16;
	}
	public void aimUp(){
		aim = "u";
		bowY=0;
		bowX=8;
		bowHeight = 7;
		bowLength = 16;	
	}
	
	//Resets Player 1's variables
	public void reset(){
		aim = "r";
		velX = 0;
		velY = 0;
		bowY=16;
		bowX=16;
		bowHeight = 16;
		bowLength = 8;
		arrCount = 3;
		//life = 1;
		pX = 50;
		pY = 450;

	}
	
	//Runs reset() multiple times because the lists only get reset once for some reason
	public void resetRunner(){
		for (int i = 0; i < 12; i++){
			reset();
		}
	}

	

	//Runs collision for every map tile
	public void Collision(){
		Rectangle2D Player = new Rectangle2D.Double(pX,pY,tileSize,tileSize);
		for (int i = 0; i < map[1].length; i++){
			for (int q = 0; q < map.length; q++){
				if(map[q][i] == 2 || map[q][i] == 0){
					Rectangle2D terrain = new Rectangle2D.Double(i*tileSize,q*tileSize,tileSize,tileSize);
					checkIntersect(Player,terrain);
				}
			}
		}
		if (pY>=(511)){
			pY=511;
			velY=0;
		} 
		if (pY<=32){
			pY=33;
			velY=0;
		} 
	}
	
	//Checks collision between player and walls
	public void checkIntersect(Rectangle2D Player, Rectangle2D terrain){

		//Left Collision
		if (terrain.contains(new Point((int)(pX-2),(int)pY))
				|| terrain.contains(new Point((int)(pX-2),(int)pY+tileSize))){
			collideXat = pX;
			colLeft = true;
			pX-=velX;
			
		}
		else if(pX == collideXat||pX ==(pX-velX)){
			colLeft = false;
		}
		//Right Collision
		if (terrain.contains(new Point((int)(pX+tileSize+2),(int)pY))
				|| terrain.contains(new Point((int)(pX+tileSize+2),(int)pY+tileSize))){
			collideXat = pX;
			colRight = true;
			pX-=velX;
		}
		else if(pX == collideXat||pX ==(pX-velX)){
			colRight = false;
		}
		//Bottom Collision
		if (terrain.contains(new Point((int)(pX),(int)(pY+tileSize+velY)))
				|| terrain.contains(new Point((int)(pX+tileSize),(int)(pY+tileSize+velY)))){
			jumpCount = 2;
			colBot= true;
			velY=0;
			colTop = false;

		}
		//Top Collision
		if (terrain.contains(new Point((int)(pX),(int)(pY+velY)))
				|| terrain.contains(new Point((int)(pX+tileSize),(int)(pY+velY)))){
			pY-=velY;
			collideYat=pY;
			colTop= true;
			velY=0;
			
		}
		else if(velY>1){
			colTop = false;
			
		}
		if (!colTop && !colBot && !colLeft && !colRight){
			colNothing = true;
		}
		else{
			colNothing = false;
		}
	}
}
