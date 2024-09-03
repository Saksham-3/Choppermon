package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;

public class Player extends Entity{
	
	GamePanel gp;
	KeyHandler keyH;
	
	public final int screenX;
	public final int screenY;
	
	public Player(GamePanel gp,KeyHandler keyH) {
		
		this.gp = gp;
		this.keyH = keyH;
		
		screenX = gp.screenWidth /2 - (gp.tileSize/2);
		screenY = gp.screenHeight /2- (gp.tileSize/2);
		
		solidArea = new Rectangle(8,16,16,20);
		
		
		setDefaultValues();
		getPlayerImage();
	}
	
	public void setDefaultValues() {
		
		worldX = gp.tileSize * 23;
		worldY = gp.tileSize * 21;
		speed = 4;
		direction = "down";
		
	}
	
	public void getPlayerImage() {
		try {

			stand = ImageIO.read(getClass().getResourceAsStream("/player/sprite_stand_1.png"));
			up1 = ImageIO.read(getClass().getResourceAsStream("/player/sprite_up_1.png"));
			up2 = ImageIO.read(getClass().getResourceAsStream("/player/sprite_up_2.png"));
			down1 = ImageIO.read(getClass().getResourceAsStream("/player/sprite_down_1.png"));
			down2 = ImageIO.read(getClass().getResourceAsStream("/player/sprite_down_2.png"));
			left1 = ImageIO.read(getClass().getResourceAsStream("/player/sprite_left_1.png"));
			left2 = ImageIO.read(getClass().getResourceAsStream("/player/sprite_left_2.png"));
			right1 = ImageIO.read(getClass().getResourceAsStream("/player/sprite_right_1.png"));
			right2 = ImageIO.read(getClass().getResourceAsStream("/player/sprite_right_2.png"));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void update() {
		
		isMoving = false; // Reset the flag to false at the start of the update
		    
		if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {
			 isMoving = true; // Set the flag to true if any movement key is pressed
		if (keyH.upPressed == true) {
			direction = "up";
		}
		else if (keyH.downPressed == true) {
			direction = "down";
		}
		else if (keyH.leftPressed == true) {
			direction = "left";
		}
		else if (keyH.rightPressed == true) {
			direction = "right";
		}
		
		
		// tile collision
		collisionOn = false;
		gp.cChecker.checkTile(this);
		
		if (collisionOn == false) {
			switch (direction) {
			case "up":
				worldY -= speed;
				break;
			case "down":
				worldY += speed;
				break;
			case "left":
				worldX -= speed;
				break;
			case "right":
				worldX += speed;
				break;
			}
		}
		spriteCounter++;
		
		if (spriteCounter > 11) {
			if (spriteNum == 1) {
				spriteNum = 2;
			}
			else if (spriteNum == 2) {
				spriteNum = 1;
			}
			spriteCounter = 0;
		}
	}
	}
	public void draw(Graphics2D g2) {
		
	BufferedImage image = null;
	
	 if (isMoving) {
	        // Select sprite based on direction and spriteNum when moving
	        switch (direction) {
	            case "up":
	                image = (spriteNum == 1) ? up1 : up2;
	                break;
	            case "down":
	                image = (spriteNum == 1) ? down1 : down2;
	                break;
	            case "left":
	                image = (spriteNum == 1) ? left1 : left2;
	                break;
	            case "right":
	                image = (spriteNum == 1) ? right1 : right2;
	                break;
	        }
	    } else {
	        // Use standing still image when not moving
	        image = stand;
	    }
	
	g2.drawImage(image,screenX,screenY, gp.tileSize, gp.tileSize, null);
	
	
	
	}

}
