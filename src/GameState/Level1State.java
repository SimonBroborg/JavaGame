package GameState;

import Entity.*;
import Main.GamePanel;
import TileMap.*;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.security.Key;

/**
 *
 */
public class Level1State extends GameState
{
    private TileMap tileMap;
    private Background bg;

    private Player player;

    public Level1State(GameStateManager gsm) {
	this.gsm = gsm;
	init();
    }

    @Override public void init() {
	tileMap = new TileMap(30);
	tileMap.loadTiles(
		"F:\\Users\\Simon\\Documents\\Krunch_In_The_Catacombs\\Krunch_In_The_Catacombs\\Resources\\TileSets\\tileset.png");
	tileMap.loadMap(
		"F:\\Users\\Simon\\Documents\\Krunch_In_The_Catacombs\\Krunch_In_The_Catacombs\\Resources\\Maps\\level1-1.txt");
	tileMap.setPosition(0, 0);
	tileMap.setTween(1);
	bg = new Background(
		"F:\\Users\\Simon\\Documents\\Krunch_In_The_Catacombs\\Krunch_In_The_Catacombs\\Resources\\Backgrounds\\level1bg.png",
		0.1);

	player = new Player(tileMap);
	player.setPosition(100, 100);
    }

    @Override public void update() {
	player.update();
	tileMap.setPosition(GamePanel.WIDTH / 2 - player.getX(), GamePanel.HEIGHT / 2 - player.getY());
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
