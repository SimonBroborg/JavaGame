package Main;

import GameState.GameStateManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;

/**
 *
 */
public class GamePanel extends JPanel implements Runnable, KeyListener
{
    // dimensions
    public static final int WIDTH = 320;
    public static final int HEIGHT = 240;
    public static final int SCALE = 2;

    // game thread

    private Thread thread;
    private volatile boolean running;
    private int FPS = 60;
    private long targetTime = 1000 / FPS;

    // image
    private BufferedImage image;
    private Graphics2D g;

    // game state manager
    private GameStateManager gsm;

    public GamePanel() {
	super();
	setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
	setFocusable(true);
	requestFocus();
    }

    public void addNotify() {
	super.addNotify();
	if (thread == null) {
	    thread = new Thread(this);
	    addKeyListener(this);
	    thread.start();

	}
    }

    private void init() {
	image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	running = true;

	g = (Graphics2D) image.getGraphics();

	gsm = new GameStateManager();
    }

    @Override public void run() {
	init();

	long start;
	long elapsed;
	long wait;

	// game loop
	while (running) {

	    start = System.nanoTime();

	    update();
	    draw();
	    drawToScreen();

	    elapsed = System.nanoTime() - start;

	    wait = targetTime - elapsed / 1000000;

	    if (wait < 0) wait = 5;

	    try {
		Thread.sleep(wait);
	    } catch (Exception e) {
		e.printStackTrace();
	    }
	}
    }

    private void update() {
	gsm.update();
    }

    private void draw() {
	gsm.draw(g);
    }

    public void drawToScreen() {
	Graphics2D g2d = (Graphics2D) getGraphics();
	g2d.drawImage(image, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);
	g2d.dispose();
    }

    @Override public void keyTyped(final KeyEvent e) {

    }

    @Override public void keyPressed(final KeyEvent e) {
	gsm.keyPressed(e.getKeyCode());
    }

    @Override public void keyReleased(final KeyEvent e) {
	gsm.keyReleased(e.getKeyCode());
    }


}
