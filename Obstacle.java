package com;

import java.awt.image.BufferedImage;

public class Obstacle implements Runnable {
	
	
	





	

	private int x;
	private int y;
	
	private int type;
	private BufferedImage show=null;
	
	private BackGround bg=null;
	private Thread thread = new Thread(this);

	public Obstacle(int x, int y, int type, BackGround bg) {
		this.x=x;
		this.type=y;
		this.y=y;
		this.bg=bg;
		show=StaticValue.obstacle.get(type);
		
		
		if (type==8) {
			thread.start();
		}
		
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (true) {
			
			if (this.bg.isReach()) {
				if (this.y<374) {
					this.y+=5;
				}else {
					this.bg.setBase(true);
				}
			}
			
			try {
				thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	// getters
	public int getX() {
		return x;
	}



	public int getY() {
		return y;
	}



	public int getType() {
		return type;
	}
	

	
	public BufferedImage getShow() {
		return show;
	}

	public BackGround getBg() {
		return bg;
	}
	
	
}
