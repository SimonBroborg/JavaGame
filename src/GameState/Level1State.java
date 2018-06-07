package GameState;

import Main.GamePanel;
import TileMap.Background;
import TileMap.TileMap;

import java.awt.*;

/**
 *
 */
public class Level1State extends GameState
{
    private TileMap tileMap;
    private Background bg;

    public Level1State(GameStateManager gsm){
        this.gsm = gsm;
        init();
    }

    @Override public void init() {
	tileMap = new TileMap(30);
	tileMap.loadTiles("F:\\Users\\Simon\\Documents\\Krunch_In_The_Catacombs\\Krunch_In_The_Catacombs\\Resources\\TileSets\\tileset.png");
	tileMap.loadMap("F:\\Users\\Simon\\Documents\\Krunch_In_The_Catacombs\\Krunch_In_The_Catacombs\\Resources\\Maps\\level1-1.txt");
	tileMap.setPosition(0,0);

	bg = new Background("F:\\Users\\Simon\\Documents\\Krunch_In_The_Catacombs\\Krunch_In_The_Catacombs\\Resources\\Backgrounds\\level1bg.png", 0.1);

    }

    @Override public void update() {

    }

    @Override public void draw(final Graphics2D g2d) {
        // clear screen
	g2d.setColor(Color.WHITE);
	g2d.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);

	// draw bg
	bg.draw(g2d);

	// draw tilemap
	tileMap.draw(g2d);
    }

    @Override public void keyPressed(final int k) {

    }

    @Override public void keyReleased(final int k) {

    }
}
