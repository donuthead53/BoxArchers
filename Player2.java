
import java.awt.*; 
import java.awt.geom.Rectangle2D;
//Complete copy of Player1 because both players behave in the exact same way, other than input keys
public class Player2 {
	int life = 1;
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
	static String aim = "l";
	//Their X position
	static double pX;
	//Their Y position
	static double pY;
	//Speed of player2's X
	double velX = 0;
	//Speed of player2's Y
	double velY = 0;
	//Jumping or not
	boolean jumping = false;
	//Gravity number
	double gravity = 0.07;
	int tileSize = 32;
	//Creates a 2-D Array tilemap 
	int[][] map;
	//Bow Displacement variables
	int aX;
	static int aY;
	int bowX=0;
	int bowY=16;
	int bowHeight = 16;
	int bowLength = 8;
	double collideXat = pX;
	double collideYat = pY;
	//Constructor for in take of starting position from Game.java
	public Player2(int x, int y, int map[][]){
		//Position X = given x
		pX = x;
		//Position Y = give y
		pY =y;
		this.map = map;
		
	}
	//Paint 
	public void draw(Graphics2D g){
		//Draw player2
		g.fillRect((int)(Math.round((pX))),(int)(Math.round((pY*100)/100)), tileSize, tileSize);
		g.setColor(Color.PINK);
		Rectangle bow = new Rectangle((int)pX+bowX, ((int)pY+bowY), bowHeight, bowLength);
		g.fill(bow);
		g.draw(bow);
		

		 
		g.setColor(Color.WHITE);

		
		g.setColor(Color.magenta);

		
		

	}
	//Updating the player2
	public void update() {
		//Always add speed to player2 for smoother movement because it works better than positional moving
		//Add X speed to position X
		pX += velX;
		//Add Y speed to position Y
		pY += velY;
		//The power of gravity is always with you.
		velY+=gravity;
		Collision();
		//arrowCollision();
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
		
		if (colLeft && !inAir){
			jumpCount = 2;
		}
		if (colLeft || colRight && inAir){
			
			if (jumpCount < 3 && wallJump){
				jumpCount=1;
				wallJump = false;
			}
			colBot = true;
		}
			
			
		
	}
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
	public int getALife(){
		return life;
	}
	public void reset(){
		aim = "l";
		velY = 0;
		velX = 0;
		bowX=0;
		bowY=16;
		bowHeight = 16;
		bowLength = 8;
		arrCount = 3;
		life = 1;
		pX = 720;
		pY = 450;
	}
	public void resetRunner(){
		for (int i = 0; i < 12; i++){
			reset();
		}
	}
	public void shoot(){

	}

	public void Collision(){
		Rectangle2D player2 = new Rectangle2D.Double(pX,pY,tileSize,tileSize);
		for (int i = 0; i < map[1].length; i++){
			for (int q = 0; q < map.length; q++){
				if(map[q][i] == 0 || map[q][i] == 2){
					Rectangle2D terrain = new Rectangle2D.Double(i*tileSize,q*tileSize,tileSize,tileSize);
					checkIntersect(player2,terrain);
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
	public void checkIntersect(Rectangle2D player2, Rectangle2D terrain){

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
			pY += 1;
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
