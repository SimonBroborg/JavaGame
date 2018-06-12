package GameState;

import TileMap.Background;

import java.awt.*;
import java.awt.event.KeyEvent;

/**
 *
 */
public class MenuState extends GameState
{
    private Background bg;

    private int currentChoice = 0;
    private String[] options = { "Start", "Help", "Quit" };

    private Color titleColor;
    private Font titleFont;
    private Font font;

    public MenuState(GameStateManager gsm) {

	this.gsm = gsm;

	try {
	    bg = new Background("Resources/Backgrounds/menubg.png", 1);
	    bg.setVector(-0.1, 0);
	    titleColor = new Color(128, 0, 0);
	    titleFont = new Font("Century Gothic", Font.PLAIN, 28);

	    font = new Font("Arial", Font.PLAIN, 12);

	} catch (Exception e) {
	    e.printStackTrace();
	}

    }

    @Override public void init() {

    }

    @Override public void update() {
	bg.update();
    }

    @Override public void draw(final Graphics2D g2d) {
	// draw background
	bg.draw(g2d);

	// draw title
	g2d.setColor(titleColor);
	g2d.setFont(titleFont);
	g2d.drawString("WOWOWOW", 80, 70);

	// draw menu options
	g2d.setFont(font);
	for (int i = 0; i < options.length; i++) {
	    if (i == currentChoice) {
		g2d.setColor(Color.BLACK);
	    } else {
		g2d.setColor(Color.RED);
	    }
	    g2d.drawString(options[i], 145, 140 + i * 15);
	}
    }

    private void select() {
	if (currentChoice == 0) {
	    gsm.setState(GameStateManager.LEVEL1STATE);
	}
	if (currentChoice == 1) {
	    // help
	}
	if (currentChoice == 2) {
	    System.exit(0);
	}
    }

    @Override public void keyPressed(final int k) {
	if (k == KeyEvent.VK_ENTER || k == KeyEvent.VK_E) {
	    select();
	}
	if (k == KeyEvent.VK_UP || k == KeyEvent.VK_W) {
	    currentChoice--;
	    if (currentChoice == -1) {
		currentChoice = options.length - 1;
	    }
	}
	if (k == KeyEvent.VK_DOWN || k == KeyEvent.VK_S) {
	    currentChoice++;
	    if (currentChoice == options.length) {
		currentChoice = 0;
	    }
	}
    }

    @Override public void keyReleased(final int k) {

    }
}
