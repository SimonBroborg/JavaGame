package GameState;

import Main.GamePanel;
import TileMap.TileMap;

import java.awt.*;

/**
 *
 */
public class Level1State extends GameState
{
    private TileMap tileMap;

    public Level1State(GameStateManager gsm){
        this.gsm = gsm;
        init();
    }

    @Override public void init() {
	tileMap = new TileMap(30);
	tileMap.loadTiles("/Tilesets/grasstileset");
	tileMap.loadMap("/Maps/level1-1.map");
	tileMap.setPosition(0,0);

    }

    @Override public void update() {

    }

    @Override public void draw(final Graphics2D g2d) {
        // clear screen
	g2d.setColor(Color.WHITE);
	g2d.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);

	// draw tilemap
	tileMap.draw(g2d);
    }

    @Override public void keyPressed(final int k) {

    }

    @Override public void keyReleased(final int k) {

    }
}
