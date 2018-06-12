package Entity.Entity.Enemies;

import Entity.Animation;
import Entity.Enemy;
import Entity.Sprite;
import TileMap.TileMap;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 *
 */
public class Slugger extends Enemy
{
    private BufferedImage[] sprites;

    public Slugger(TileMap tm) {
	super(tm);
	moveSpeed = 0.3;
	maxSpeed = 0.3;
	fallSpeed = 0.2;
	maxFallSpeed = 10.0;

	width = 30;
	health = 30;
	cwidth = 20;
	cheight = 20;

	health = 2;
	maxHealth = 2;
	damage = 1;

	// load sprites

	try {
	    Sprite spritesheet = new Sprite("Resources/Sprites/Enemies/slugger.png");

	    sprites = new BufferedImage[3];
	    for (int i = 0; i < sprites.length; i++) {
		sprites[i] = spritesheet.getImage().getSubimage(i * width, 0, width, height);
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	}

	animation = new Animation();
	animation.setFrames(sprites);
	animation.setDelay(300);
	right = true;
	facingRight = true;
    }

    private void getNextPosition() {
	if (left) {
	    dx -= moveSpeed;
	    if (dx < -maxSpeed) {
		dx = -maxSpeed;
	    }
	} else if (right) {
	    dx += moveSpeed;
	    if (dx > maxSpeed) {
		dx = maxSpeed;
	    }
	}

	// falling
	if (falling) {
	    dy += fallSpeed;
	}
    }

    public void update() {
	// update position
	getNextPosition();
	checkTileMapCollision();
	setPosition(xtemp, ytemp);

	// check flinching
	if (flinching) {
	    long elapsed = (System.nanoTime() - flinchTimer) / 1000000;
	    if (elapsed > 400) {
		flinching = false;
	    }
	}

	// if it hits a wall, go other direction
	if (right && dx == 0) {
	    right = false;
	    left = true;
	    facingRight = false;
	} else if (left && dx == 0) {
	    right = true;
	    left = false;
	    facingRight = true;
	}

	// update animation
	animation.update();
    }

    public void draw(Graphics2D g2d) {
	if (notOnScreen()) {
	    return;
	}
	setMapPosition();

	super.draw(g2d);
    }
}
