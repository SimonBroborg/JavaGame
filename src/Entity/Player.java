package Entity;

import TileMap.TileMap;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 *
 */
@SuppressWarnings({ "MagicNumber", "unused", "OverlyComplexMethod", "FieldCanBeLocal" })
public class Player extends MapObject
{
    // player stuff
    private int health;
    private int maxHealth;
    private int fire;
    private int maxFire;
    private boolean dead;
    private boolean flinching;
    private long flinchTimer;

    // fireball
    private boolean firing;
    private int fireCost;
    private int fireBallDamage;
    private ArrayList<FireBall> fireBalls;

    //scratch
    private boolean scratching;
    private int scratchdamage;
    private int scratchRange;

    //gliding
    private boolean gliding;

    // animations
    private ArrayList<BufferedImage[]> sprites;

    // temp sprite
    private Sprite sprite;

    private final int[] numFrames = { 2, 8, 1, 2, 4, 2, 5 };

    // animation actions
    private static final int IDLE = 0;
    private static final int WALKING = 1;
    private static final int JUMPING = 2;
    private static final int FALLING = 3;
    private static final int GLIDING = 4;
    private static final int FIREBALL = 5;
    private static final int SCRATCHING = 6;

    public Player(final TileMap tm) {
	super(tm);

	width = 30;
	height = 30;
	cwidth = 20;
	cheight = 20;

	moveSpeed = 0.7;
	maxSpeed = 3;
	stopSpeed = 0.4;
	fallSpeed = 0.5;
	maxFallSpeed = 10;
	jumpStart = -10;
	stopJumpSpeed = 0.3;

	facingRight = true;

	health = 5;
	maxHealth = 5;
	fire = 2500;
	maxFire = 2500;

	fireCost = 200;
	fireBallDamage = 5;
	fireBalls = new ArrayList<>();

	scratchdamage = 8;
	scratchRange = 40;

	setAnimation();

    }

    private void setAnimation() {
	Sprite spritesheet = new Sprite("Resources/Sprites/Player/playersprites.png");

	sprites = new ArrayList<>();
	for (int i = 0; i < 7; i++) {
	    BufferedImage[] bi = new BufferedImage[numFrames[i]];

	    for (int j = 0; j < numFrames[i]; j++) {
		if (i != 6) {
		    bi[j] = spritesheet.getImage().getSubimage(j * width, i * height, width, height);
		} else {
		    bi[j] = spritesheet.getImage().getSubimage(j * width * 2, i * height, width * 2, height);
		}
	    }
	    sprites.add(bi);
	}

	animation.setFrames(sprites.get(IDLE));
	animation.setDelay(400);
    }


    public int getHealth() {
	return health;
    }

    public int getMaxHealth() {
	return maxHealth;
    }

    public int getFire() {
	return fire;
    }

    public int getMaxFire() {
	return maxFire;
    }

    public void setFiring() {
	this.firing = true;
    }

    public void setScratching() {
	this.scratching = true;
    }

    public void setGliding(final boolean gliding) {
	this.gliding = gliding;
    }

    private void getNextPosition() {
	// movement
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
	} else {
	    if (dx > 0) {
		dx -= stopSpeed;
		if (dx < 0) {
		    dx = 0;
		}
	    } else if (dx < 0) {
		dx += stopSpeed;
		if (dx > 0) {
		    dx = 0;
		}
	    }
	}

	// cannot move while attacking , except in air
	if ((currentAction == SCRATCHING || currentAction == FIREBALL) && !(jumping || falling)) {
	    dx = 0;
	}

	// jumping
	if (jumping && !falling) {
	    dy = jumpStart;
	    falling = true;
	}

	// falling
	if (falling) {

	    if (dy > 0 && gliding) {
		dy += fallSpeed * 0.1;
	    } else {
		dy += fallSpeed;
	    }
	    if (dy > 0) {
		jumping = false;
	    }
	    if (dy < 0 && !jumping) {
		dy += stopJumpSpeed;
	    }
	    if (dy > maxFallSpeed) {
		dy = maxFallSpeed;
	    }
	}

    }

    public void update() {
	// update position
	getNextPosition();
	checkTileMapCollision();
	setPosition(xtemp, ytemp);

	if (currentAction == SCRATCHING) {
	    if (animation.hasPlayedOnce()) {
		scratching = false;
	    }
	}
	if (currentAction == FIREBALL) {
	    if (animation.hasPlayedOnce()) {
		firing = false;
	    }
	}

	// fireball attack
	fire += 1;
	if (fire > maxFire) {
	    fire = maxFire;
	}
	if (firing && currentAction != FIREBALL) {
	    if (fire > fireCost) {
		fire -= fireCost;
		FireBall fb = new FireBall(tileMap, facingRight);
		fb.setPosition(x, y);
		fireBalls.add(fb);
	    }
	}

	// update fireballs
	for (int i = 0; i < fireBalls.size(); i++) {
	    fireBalls.get(i).update();
	    if (fireBalls.get(i).shouldRemove()) {
		fireBalls.remove(i);
		i--;
	    }
	}


	// set direction
	if (currentAction != SCRATCHING && currentAction != FIREBALL) {
	    if (right) facingRight = true;
	    if (left) facingRight = false;
	}
	runAnimation();
    }

    private void runAnimation() {
	// set animation
	if (scratching) {
	    if (currentAction != SCRATCHING) {
		currentAction = SCRATCHING;
		animation.setFrames(sprites.get(SCRATCHING));
		animation.setDelay(50);
		width = 60;
	    }
	} else if (firing) {
	    if (currentAction != FIREBALL) {
		currentAction = FIREBALL;
		animation.setFrames(sprites.get(FIREBALL));
		animation.setDelay(100);
		width = 30;
	    }
	} else if (dy > 0) {
	    if (gliding) {
		if (currentAction != GLIDING) {
		    currentAction = GLIDING;
		    animation.setFrames(sprites.get(GLIDING));
		    animation.setDelay(100);
		    width = 30;
		}
	    } else if (currentAction != FALLING) {
		currentAction = FALLING;
		animation.setFrames(sprites.get(FALLING));
		animation.setDelay(100);
		width = 30;
	    }
	} else if (dy < 0) {
	    if (currentAction != JUMPING) {
		currentAction = JUMPING;
		animation.setFrames(sprites.get(JUMPING));
		animation.setDelay(-1);
		width = 30;
	    }
	} else if (left || right) {
	    if (currentAction != WALKING) {
		currentAction = WALKING;
		animation.setFrames(sprites.get(WALKING));
		animation.setDelay(40);
		width = 30;
	    }
	} else {
	    if (currentAction != IDLE) {
		currentAction = IDLE;
		animation.setFrames(sprites.get(IDLE));
		animation.setDelay(400);
		width = 30;
	    }
	}
	animation.update();

    }

    public void draw(Graphics2D g2d) {
	setMapPosition();

	for (int i = 0; i < fireBalls.size(); i++) {
	    fireBalls.get(i).draw(g2d);
	}

	// draw player
	if (flinching) {
	    long elapsed = (System.nanoTime() - flinchTimer) / 1000000;
	    if (elapsed / 100 % 2 == 0) {
		return;
	    }

	}
	Sprite sprite = new Sprite("Resources/Sprites/Player/player.png");
	//g2d.drawImage(sprite.getImage(), (int) (x + xmap - width / 2), (int) (y + ymap - height / 2), null);
	super.draw(g2d);
    }
}

