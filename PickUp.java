import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;


public class PickUp extends Rectangle {

	
		//Initializes variables
		String dir;
		double charge;
		int counter = 0;
		double velY = 0;
		int upCount = 0;
		double upVel = -7;
	
		//Constructor
		public PickUp(int x, int y,String dir,double charge){
			super(x,y,8,8);
			this.dir = dir;
			this.charge = charge;
			
		}
		
		
		//Draws pickupable arrows
		public void draw(Graphics g){
			g.setColor(Color.GREEN);
			if (dir == "r"){
				g.fillRect((int)x,(int)y,8,8);
			}
			if(dir == "l"){
				g.fillRect((int)x,(int)y,8,8);	
			}
			if(dir == "u"){
				g.fillRect((int)x,(int)y,8,8);	
			}
			if(dir == "d"){
				g.fillRect((int)x,(int)y,8,8);	
			}
		}
		
		//Moves arrow out of the wall in certain ways depending on the direction it was shot from
		public void update(){
			//Counter is used so that it is only pushed out once and so that arrows don't just bounce off walls
			if (counter == 0){
				if (dir == "r"){
					x -= 20;
					y-=5;
				}
				
				if (dir == "l"){
					x += 35;
					y-=5;
				}
				
				if (dir == "u"){
					
					y += 35+charge;
				}
				
				if (dir == "d"){
					y -= 30+charge;
				}
				counter++;
			}
			
			
}
}
