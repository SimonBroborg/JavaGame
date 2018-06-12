package GameState;

import Entity.*;
import Entity.Entity.Enemies.Slugger;
import Main.GamePanel;
import TileMap.*;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 *
 */
public class Level1State extends GameState
{
    private TileMap tileMap;
    private Background bg;

    private Player player;

    private ArrayList<Enemy> enemies;

    public Level1State(GameStateManager gsm) {
	this.gsm = gsm;
	init();
    }

    @Override public void init() {
	tileMap = new TileMap(30);
	tileMap.loadTiles(
		"Resources/TileSets/tileset.png");
	tileMap.loadMap(
		"Resources/Maps/level1-1.txt");
	tileMap.setPosition(0, 0);
	tileMap.setTween(1);
	bg = new Background("Resources/Backgrounds/space.png", 0.1);

	player = new Player(tileMap);
	player.setPosition(100, 100);

	enemies = new ArrayList<>();
	Slugger s;
	s = new Slugger(tileMap);
	s.setPosition(100, 100);
	enemies.add(s);
    }

    @Override public void update() {
	player.update();
	tileMap.setPosition(GamePanel.WIDTH / 2 - player.getX(), GamePanel.HEIGHT / 2 - player.getY());

	// set background
	bg.setPosition(tileMap.getX(), tileMap.getY());

	// update all enemies
	for(int i = 0; i < enemies.size(); i++){
	    enemies.get(i).update();
	}
    }

    @Override public void draw(final Graphics2D g2d) {
	// clear screen
	g2d.setColor(Color.WHITE);
	g2d.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);

	// draw bg
	bg.draw(g2d);

	// draw tilemap
	tileMap.draw(g2d);

	// draw player
	player.draw(g2d);

	for(int i = 0; i < enemies.size(); i++){
	    enemies.get(i).draw(g2d);
	}
    }

    @Override public void keyPressed(final int k) {
	if (k == KeyEvent.VK_A) player.setLeft(true);
	if (k == KeyEvent.VK_D) player.setRight(true);
	if (k == KeyEvent.VK_W) player.setUp(true);
	if (k == KeyEvent.VK_S) player.setDown(true);
	if (k == KeyEvent.VK_SPACE) player.setJumping(true);
	if (k == KeyEvent.VK_O) player.setGliding(true);
	if (k == KeyEvent.VK_P) player.setScratching();
	if (k == KeyEvent.VK_I) player.setFiring();

	if(k == KeyEvent.VK_ESCAPE){
	    gsm.setState(GameStateManager.MENUSTATE);
	}
    }

    @Override public void keyReleased(final int k) {
	if (k == KeyEvent.VK_A) player.setLeft(false);
	if (k == KeyEvent.VK_D) player.setRight(false);
	if (k == KeyEvent.VK_W) player.setUp(false);
	if (k == KeyEvent.VK_S) player.setDown(false);
	if (k == KeyEvent.VK_SPACE) player.setJumping(false);
	if (k == KeyEvent.VK_O) player.setGliding(false);
    }
}
