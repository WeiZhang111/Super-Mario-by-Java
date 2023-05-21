package com;

import java.awt.image.BufferedImage;

public class Enemy implements Runnable {
	
	

	//
	private int x, y;
	private int type;
	private boolean face_to =true;
	private BufferedImage show;
	private BackGround bg;
	private int max_up = 0;
	private int max_down = 0;
	private Thread thread = new Thread(this);
	private int image_type = 0;
	
	
	public Enemy(int x, int y, boolean face_to, int type, BackGround bg) {
		this.max_down=x;
		this.type=y;
		this.face_to=face_to;
		this.type=type;
		this.bg=bg;
		show = StaticValue.mushroom.get(0);
		thread.start();
	}
	
	public Enemy(int x, int y, boolean face_to, int type, int max_up, int max_down, BackGround bg) {
		this.max_down=x;
		this.type=y;
		this.face_to=face_to;
		this.type=type;
		this.max_up=max_up;
		this.max_down=max_down;
		this.bg=bg;
		show = StaticValue.mushroom.get(0);
		thread.start();
	}
	public void death() {
		show = StaticValue.mushroom.get(2);
		this.bg.getEnemyList().remove(this);
	}
	
	
	
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public BufferedImage getShow() {
		return show;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (true) {
			if (type==1) {
				// mushroom
				if (face_to) {
					this.x-=2;
				}else {
					this.x+=2;
				}
				image_type = image_type ==1? 0:1;
				show=StaticValue.mushroom.get(image_type);
			}
			
			boolean canLeft=true;
			boolean canRight=true;
			
			for (int i=0;i<bg.getObstacleList().size();i++) {
				Obstacle obl=bg.getObstacleList().get(i);
				if (obl.getX()==this.x+36 && (obl.getY()+65>this.y &&obl.getY()-35<this.y)) {
					canRight = false;
				}
				
				if (obl.getX()==this.x-36 && (obl.getY()+65>this.y &&obl.getY()-35<this.y)) {
					canLeft=false;
				}
				
			}
			
			if (face_to && (!canLeft) || this.x==0) {
				face_to=false;
			}
			else if ((!face_to) && (!canRight) && this.max_down==764) {
				face_to=true;
			}
			
			if (type ==2) {
				if (face_to) {
					this.y -=2;
				}else {
					this.y+=2;
				}
				image_type =image_type==1?0:1;
				if (face_to && (this.image_type==max_up)) {
					face_to=false;
				}
				if ((!face_to) && (this.image_type==max_down)) {
					face_to=true;
				}
				show = StaticValue.flower.get(image_type);
			}
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
		}	
			
	}

}
