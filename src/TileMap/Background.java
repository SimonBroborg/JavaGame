package TileMap;


import Entity.Sprite;
import Main.GamePanel;
import java.awt.*;


/**
 *
 */
public class Background
{
    private Sprite sprite;

    private double x;
    private double y;
    private double dx;
    private double dy;

    private double moveScale;

    public Background(String s, double ms) {
	sprite = new Sprite(s);
	moveScale = ms;

    }

    public void setPosition(double x, double y) {
	this.x = (x * moveScale) % GamePanel.WIDTH;
	this.y = (y * moveScale) % GamePanel.HEIGHT;
    }

    public void setVector(double dx, double dy) {
	this.dx = dx;
	this.dy = dy;
    }

    public void update() {
	x += dx;
	y += dy;
    }

    public void draw(Graphics2D g2d) {
	g2d.drawImage(sprite.getImage(), (int) x, (int) y, null);
	if (x < 0) {
	    g2d.drawImage(sprite.getImage(), (int) x + GamePanel.WIDTH, (int) y, null);
	}
	if (x > 0) {
	    g2d.drawImage(sprite.getImage(), (int) x - GamePanel.WIDTH, (int) y, null);
	}
    }
}
