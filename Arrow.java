import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;


public class Arrow extends Rectangle{
	//Player player = new Player(50,450);
	//Initializes variables
	String dir;
	double charge;
	double pX;
	double pY;
	ArrayList<Arrow> Arrows = new ArrayList<Arrow>();
	int upCount = 0;
	int downCount = 0;
	double upVel = -6;
	double downVel = 0.5;
	double gravity = 0.07;
	double velY = 0;
	double velX = 0;
	static int addY =0;
	String lastDir = "";
	static int arrowSize = 8;

	//Arrow Constructor
	public Arrow(int x, int y,String dir, double charge){
		
		super(x,y,arrowSize,arrowSize);
		this.dir = dir;
		this.charge = charge;
		upVel = -(charge);
		
		
			
	}
	
	
	//Draws arrows
	public void draw(Graphics g){
		
		
		g.setColor(Color.ORANGE);
		if (dir == "r"){
			g.fillRect((int)x,(int)y,arrowSize,arrowSize);	
		}
		if(dir == "l"){
			g.fillRect((int)x,(int)y,arrowSize,arrowSize);	
		}
		if(dir == "u"){
			g.fillRect((int)x,(int)y,arrowSize,arrowSize);	
		}
		if(dir == "d"){
			g.fillRect((int)x,(int)y+addY,arrowSize,arrowSize);	
		}
	}
	
	//Moves arrows in different ways depending on direction of shot
	public void update(){
		x+=velX;;
		y+=velY;
		velY+=gravity;
		if (dir == "r"){
			addY= 0;
			velX =charge+1;
		}
		if (dir == "l"){
			addY= 0;
			velX=-charge;
		}
		if (dir == "u"){
			addY= 0;
			lastDir = "u";
			upCount++;
			//Every few frames, the amount of which the arrow goes up by decreases
			//This simulates gravity as the arrow appears to slow down as it goes upwards
			if (upCount == 12){
				upVel++;
				upCount = 0;
			}
			velY=upVel;
			//Once velY is positive, it means the arrow is moving downwards
			//This is why the direction is switched the down
			if (velY > 0){
				dir = "d";
			}
			
			
		}
		if (dir == "d"){
			//Normally if shot downwards, it falls as much as you charged
			//But if it's falling downwards due to shooting up, it should fall gradually due to gravity
			//So if the last direction was up, it will slowly get faster every few frames
			if (lastDir.equals("u")){
				upCount++;
				if (upCount == 12){
					downVel++;
					upCount = 0;
				}
				velY=downVel;
			}
			else{
				velY=7+charge/2;
				addY = (int)7.5;
			}
			
		}
	}
	//Returns direction for other classes to use
	public String getDir(){
		return dir;
	}
			
}
