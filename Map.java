import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.imageio.ImageIO;


public class Map {
	//Initializes variables
	static BufferedImage img = null;
	static BufferedImage img2 = null;
	static BufferedImage img3 = null;
	Color border = new Color(200,100,0);
	Color plat = new Color(1,200,50);
	Scanner scFile;
	String mapSelect = "map01.txt";
	int input;
	String[][] list2;
	int[][] map = new int[18][25];
	


	public Map(){
		//Loads Images
		try {
			
			img = ImageIO.read(new File("space.jpg"));
			img2 = ImageIO.read(new File("metal.png"));
			
			
		}
		 catch (IOException e) {
				e.printStackTrace();
				
		}
		
			 
	}
	//Returns map to be used in other classes
	public int[][] giveMap(){
		return map;
	}
	//Sets different images depending on level
	public void setImg(String mapSelect){
		try {
		 if (mapSelect.equals("map01.txt")){
			img = ImageIO.read(new File("space.jpg"));
			img2 = ImageIO.read(new File("metal.png"));

		 }	
		 if (mapSelect.equals("map02.txt")){
				img = ImageIO.read(new File("forest.jpg"));
				img2 = ImageIO.read(new File("leaf.png"));
				img3 = ImageIO.read(new File("log.png"));
		 }
		 if (mapSelect.equals("map03.txt")){
				img = ImageIO.read(new File("spinx.jpg"));
				img2 = ImageIO.read(new File("pyramid.png"));
				img3 = ImageIO.read(new File("sandy.png"));
				
		 }
		 if (mapSelect.equals("map04.txt")){
				img = ImageIO.read(new File("doublebg.png"));
				img2 = ImageIO.read(new File("cloud.png"));
				
		 }
			
		}
		 catch (IOException e) {
				e.printStackTrace();
				
		}
	}
	//Sets map in this class depending on map chosen in the Game class
	public void setMap01(){
		mapSelect = "map01.txt";
		reloadMap(mapSelect);
		setImg(mapSelect);
	}
	public void setMap02(){
		mapSelect = "map02.txt";
		reloadMap(mapSelect);
		setImg(mapSelect);
	}
	public void setMap03(){
		mapSelect = "map03.txt";
		reloadMap(mapSelect);
		setImg(mapSelect);
	}
	public void setMap04(){
		mapSelect = "map04.txt";
		reloadMap(mapSelect);
		setImg(mapSelect);
	}
	
	//Draws map
	public void drawMap(Graphics g){
		
		
		//Draws background
		g.drawImage(img,0,0,800,600,null);
		//Depending on what number the tile is, a different sprite will be drawn
		for (int i = 0; i < map[1].length; i++){
			for (int q = 0; q < map.length; q++){
				if (map[q][i] == 0){
					g.drawImage(img2,i*32,q*32,32,32,null);
				}
				else if(map[q][i] == 1) {
					
				}
				else if(map[q][i] == 2) {
					g.drawImage(img3,i*32,q*32,32,32,null);
				}
				
				

			}
		}}
	
	
	//Reloads map
	public void reloadMap(String mapSelect){
		//Will read from a different file according to the argument
		try {
				

				scFile = new Scanner(new File(mapSelect));
				//The first for loop handles the rows, while the second handles the columns
				//It reads through every number in the grid in the text file
				for(int i=0;i<18;i++){
					for(int z=0;z<25;z++){
						input = scFile.nextInt();
						map[i][z] = input;
						System.out.println(map[i][z]);
					}
				}
				
			}
			 catch (IOException e) {

					e.printStackTrace();
				}	
			 
	}
	   
	
}
