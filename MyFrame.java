package com;

import java.util.List;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class MyFrame extends JFrame implements KeyListener, Runnable {
	

	private List<BackGround> allBg = new ArrayList<>();
	
	private BackGround nowBg = new BackGround();
	
	private Image offScreenImage = null;
	
	private Mario mario=new Mario();
	
	private Thread thread = new Thread(this);
	
	public MyFrame() {
		this.setSize(800, 600);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		
		this.addKeyListener(this);
		this.setTitle("Super Mario");
		
		StaticValue.init();
		mario=new Mario(10,355);
		for (int i=1; i<=3;i++) {
			allBg.add(new BackGround(i, i==3?true:false));
		}
		nowBg=allBg.get(0);
		mario.setBackGround(nowBg);
		repaint();
		thread.start();
		
		
	}
	
	@Override
	public void paint(Graphics g) {
		if (offScreenImage == null) {
			offScreenImage=createImage(800,600);
		} 
		
		Graphics graphics = offScreenImage.getGraphics();
		graphics.fillRect(0, 0, 800, 600);
		
		graphics.drawImage(nowBg.getBgImage(), 0, 0, this);
		
		
		for (Enemy e:nowBg.getEnemyList()) {
			graphics.drawImage(e.getShow(),e.getX(),e.getY(),this);
		}
		
		
		for (Obstacle ob : nowBg.getObstacleList()) {
			graphics.drawImage(ob.getShow(),ob.getX(), ob.getY(), this);
		}
		
		graphics.drawImage(nowBg.getTower(), 620,270, this);
		
		graphics.drawImage(nowBg.getFlg(), 500,220, this);
		
		
		graphics.drawImage(mario.getShow(), mario.getX(),mario.getY(), this);
		
		g.drawImage(offScreenImage, 0,0,this);
		
		
		
	}
	
	public static void main(String[] args) {
		MyFrame myFrame = new MyFrame();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if (e.getKeyCode()==39) {
			mario.rightMove();
		}
		if (e.getKeyCode()==37) {
			mario.leftMove();
		}
		if (e.getKeyCode()==38) {
			mario.jump();
		}

	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		if (e.getKeyCode()==37) {
			mario.leftStop();
		}
		if (e.getKeyCode()==39) {
			mario.rightStop();
		}
	}
	
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (true) {
			repaint();
			try {
				Thread.sleep(50);
				
				if(mario.getX()>=775) {
					nowBg=allBg.get(nowBg.getSort());
					mario.setBackGround(nowBg);
					mario.setX(10);
					mario.setY(355);
				}
				if (mario.isOK()) {
					JOptionPane.showMessageDialog(this, "Passed!");
					System.exit(0);
				}
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	
}
