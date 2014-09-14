package work_1;

import java.applet.Applet;
import java.awt.Button;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class EXP extends Applet implements Runnable {

	Thread runner;
	int count = 0;
	int cloud_1 = 500;
	int cloud_2 = 200;
	int cloud_3 = 700;
	int cloud_4 = 900;
	int i = 0;
	int bin_movement = 5;
	int j = 0;
	int init = 130;
	int passCount = 0;
	int SpaceCount = 0;
	int score = 0;
	int x = 0;
	int sleepnano = 999999;
	int sleepmili = 45;
	int round = 0;
	int rannum = 3;
	int pass_changer = 0;
	boolean Space = false;
	boolean hit = false;
	BufferedImage img1;
	BufferedImage img2;
	BufferedImage img3;
	BufferedImage img4;
	BufferedImage img5;
	BufferedImage img6;
	BufferedImage img7;

	public void init() {
		try {
			img1 = ImageIO.read(ClassLoader.getSystemResource("bottle.png"));
			img2 = ImageIO.read(ClassLoader.getSystemResource("bottle2.png"));
			img3 = ImageIO.read(ClassLoader.getSystemResource("carboard.png"));
			img4 = ImageIO.read(ClassLoader.getSystemResource("crushedP.png"));
			img5 = ImageIO.read(ClassLoader.getSystemResource("glass.png"));
			img6 = ImageIO.read(ClassLoader.getSystemResource("newspaper.png"));
			img7 = ImageIO.read(ClassLoader.getSystemResource("recycle.png"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent ke) {
			}

			public void keyReleased(KeyEvent ke) {
				if (ke.getKeyCode() == KeyEvent.VK_SPACE) {
					Space = true;
					SpaceCount = 1;
				}
			}

			public void keyTyped(KeyEvent e) {
			}
		});
		setSize(500, 500);
	}

	public void start() {
		if (runner == null) {
			runner = new Thread(this);
			runner.start();
		}
	}

	public void stop() {
		if (runner != null) {
			runner = null;
		}
	}

	public void destroy() {
		System.out.println("destroy()");
	}

	public void run() {

		while (true) {
			repaint();
			try {

				Thread.sleep(sleepmili, sleepnano);
				if (SpaceCount == 0) {
					showStatus("Press Space to Start");
					score = 0;
				} else if (SpaceCount == -1) {
					showStatus("Game Over! You Lose. Press space to play again.");
					score = 0;
					sleepnano = 999999;
					sleepmili = 45;

				} else
					showStatus("Score: " + score);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void paint(Graphics g) {

		count += 15;
		if (passCount < 0) {
			pass_changer = 0;
		} else {
			pass_changer = 1;
		}
		Color Sky = new Color((pass_changer * passCount * 25),
				210 - (pass_changer * passCount * 25), 220 - (pass_changer
						* passCount * 25));
		Color Ground = new Color(90 + (pass_changer * passCount * 20),
				205 - (pass_changer * passCount * 25), 0 + (pass_changer
						* passCount * 25));

		Color Clouds = new Color(255 - (pass_changer * passCount * 25),
				255 - (pass_changer * passCount * 25), 255 - (pass_changer
						* passCount * 25));
		Color Pipe = new Color(0, 0, 255 - (pass_changer * passCount * 20));

		g.setColor(Sky);
		g.fillRect(0, 0, 500, 420);
		g.setColor(Ground);
		g.fillRect(0, 420, 500, 250);
		g.setColor(Color.BLACK);

		g.setColor(Clouds);
		g.fillOval(cloud_1 -= 3, 85, 70, 20);
		g.fillOval(cloud_2 -= 3, 65, 70, 20);
		g.fillOval(cloud_3 -= 3, 75, 70, 20);
		g.fillOval(cloud_4 -= 3, 95, 70, 20);

		int[] gravity = { 0, 3, 12, 26, 37, 73, 106, 73, 27, 26, 12, 3 };

		if (j > 11) {
			j = 0;
		}

		if (SpaceCount == 1) {
			i += 7;

			// g.setColor(Color.BLACK);
			// g.fillRect(i, init + gravity[j], 10, 10);
			if (rannum == 1)
				g.drawImage(img1, i, init + gravity[j] - 50, this);
			if (rannum == 2)
				g.drawImage(img2, i, init + gravity[j] - 50, this);
			if (rannum == 3)
				g.drawImage(img3, i, init + gravity[j] - 50, this);
			if (rannum == 4)
				g.drawImage(img4, i, init + gravity[j] - 50, this);
			if (rannum == 5)
				g.drawImage(img5, i, init + gravity[j] - 50, this);
			if (rannum == 6)
				g.drawImage(img6, i, init + gravity[j] - 50, this);
			if (!Space) {
				g.setColor(Pipe);
				g.fillRect(350, 0, 75, 115);
				g.fillRect(350, 295, 75, 125);

				g.fillRect(340, 115, 95, 15);
				g.fillRect(340, 280, 95, 15);

				g.drawImage(img7, 360, 20, this);
				g.drawImage(img7, 360, 300, this);
			} else if (Space) {
				if (bin_movement > 75 && round == 0) {
					round = 1;
				}
				g.setColor(Pipe);
				g.fillRect(350, 0, 75, 115 + bin_movement);
				g.fillRect(350, 295 - bin_movement, 75, 125 + bin_movement);

				g.fillRect(340, 115 + bin_movement, 95, 15);
				g.fillRect(340, 280 - bin_movement, 95, 15);

				g.drawImage(img7, 360, 20 + bin_movement, this);
				g.drawImage(img7, 360, 300 - bin_movement, this);
				if (i > 360 && i < 440) {
					i = -150;
					rannum = 1 + ((int) (Math.random() * 6));
					if (x < 40) {
						x += 10;
					}
					hit = true;
					passCount--;
				}
				bin_movement += 10;

			}
			if (bin_movement > 80) {
				if (hit == false && round == 1) {
					score--;
					round = 0;
				}
				bin_movement = 7;
				Space = false;
			}
			if (i > 440) {
				rannum = 1 + ((int) (Math.random() * 6));
				i = -150;
				passCount++;
				score--;
				if (passCount > 6)
					SpaceCount = -1;
			}
			if (hit && round == 1) {

				if (sleepnano < 500000) {
					sleepnano = 999999;
					if (sleepmili > 9) {
						sleepmili--;
					} else {
						System.out.println("max");
					}
				}
				round = 0;
				sleepnano -= 500000;
				score++;
				hit = false;

			}
		}
		if (score <= -10)
			SpaceCount = -1;
		if (cloud_1 <= -450) {
			cloud_1 = 500;
		}
		if (cloud_2 <= -450) {
			cloud_2 = 500;
		}
		if (cloud_3 <= -450) {
			cloud_3 = 500;
		}
		if (cloud_4 <= -450) {
			cloud_4 = 500;
		}
		j++;
	}
}
