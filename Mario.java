package com;

import java.awt.image.BufferedImage;

public class Mario implements Runnable{
	
	
	private int x;

	private int y;
	private String status;
	private BufferedImage show=null;
	private BackGround backGround = new BackGround();
	

	private Thread thread = null;
	
	private int xSpeed;
	private int ySpeed;
	
	private int index;
	
	
	private int upTime=0;
	private boolean isOK;
	
	
	
	
	public Mario(){
		
	}
	public Mario(int x, int y) {
		this.x=x;
		this.y=y;
		show=StaticValue.stand_R;
		this.status="stand--right";
		thread=new Thread(this);
		thread.start();
	}
	
	public void leftMove() {
		xSpeed=-5;
		
		if (backGround.isReach()) {
			xSpeed=0;
		}
		
		
		
		
		if (status.indexOf("jump")!=-1) {
			status="jump--left";
		} else {
			status = "move--left";
		}
		
	}
	
	public void rightMove() {
		xSpeed=5;
		
		if (backGround.isReach()) {
			xSpeed=0;
		}
		
		
		
		if (status.indexOf("jump")!=-1) {
			status="jump--right";
		} else {
			status = "move--right";
		}
	}
	
	public void leftStop() {
		xSpeed=0;
		if (status.indexOf("jump")!=-1) {
			status="jump--left";
		} else {
			status = "stop--left";
		}
	}
	public void rightStop() {
		xSpeed=0;
		if (status.indexOf("jump")!=-1) {
			status="jump--right";
		} else {
			status = "stop--right";
		}
	}
	
	
	public void jump() {
		if (status.indexOf("jump")==-1) {
			if (status.indexOf("left")!=-1) {
				status="jump--left";
			}else {
				status="jump--right";
			}
			ySpeed=-10;
			upTime=7;
		}
		if (backGround.isReach()) {
			ySpeed=0;
		}
	}
	public void fall() {
		if (status.indexOf("left")!=-1) {
			status="jump--left";

		}else {
			status="jump--right";
		}
		ySpeed=10;
		
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (true) {
			
			boolean onObstacle=false;
			boolean canRight = true;
			boolean canLeft = true;
			if (backGround.isFlag() && this.x >=3500) {
				this.backGround.setReach(true);
				
				if (this.backGround.isBase()) {
					status="move--right";
					if (x<690) {
						x+=5;
					}else {
						isOK=true;
					}
				}else {
					if (y<395) {
						xSpeed=0;
						this.y+=5;
						status="jump--right";
					}
					if (y>395) {
						this.y=395;
						status="stop--right";
					}
				}
				
			} else {
				
			}
			
			for(int i=0;i<backGround.getObstacleList().size();i++) {
				Obstacle ob=backGround.getObstacleList().get(i);
				if (ob.getY()==this.y+25 && (ob.getX()>this.x-30 && ob.getX()<this.x+25)) {
					onObstacle=true;
				}
				if ((ob.getY()>=this.y-30 && ob.getY()<=this.y-20) && (ob.getX()>this.x-30 && ob.getX()<this.x+25)) {
//					if (ob.getType() == 5) {
//						
//						backGround.getObstacleList().remove(ob);
//						
//					}
					backGround.getObstacleList().remove(ob);
					upTime=0;
				}
				if (ob.getX()==this.x+25 && (ob.getY()>this.y-30 && ob.getY()<this.y+25 )) {
					canRight=false;
				}
				if (ob.getX()==this.x-30 && (ob.getY()>this.y-30 && ob.getY()<this.y+25 )) {
					canLeft=false;
				}
				
				
			}
			
			
			
			
			if (onObstacle && upTime==0) {
				if (status.indexOf("left")!=-1) {
					if (xSpeed !=0) {
						status = "move--left";
					} else {
						status = "step--left";
					}
				}else {
					if (xSpeed !=0) {
						status = "move--right";
					} else {
						status = "step--right";
					}
				}
			} else {
				if (upTime !=0) {
					upTime--;
				}else {
					fall();
				}
				y+=ySpeed;
			}
				
			
			
			
			
			if((canLeft && xSpeed<0)||(canRight && xSpeed>0)) {
				x+=xSpeed;
				if(x<0) {
					x=0;
				}
			}
			if (status.contains("move")) {
				index=index==0?1:0;
			}
			if ("move--left".equals(status)) {
				show=StaticValue.run_L.get(index);
				
			}
			if ("move--right".equals(status)) {
				show=StaticValue.run_R.get(index);
				
			}
			if ("stop--left".equals(status)) {
				show=StaticValue.stand_L;
				
			}
			if ("stop--right".equals(status)) {
				show=StaticValue.stand_R;
				
			}
			
			if ("jump--left".equals(status)) {
				show=StaticValue.jump_L;
				
			}
			if ("jump--right".equals(status)) {
				show=StaticValue.jump_R;
				
			}
			
			
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
		}
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public BufferedImage getShow() {
		return show;
	}
	public void setShow(BufferedImage show) {
		this.show = show;
	}
	
	
	public void setBackGround(BackGround backGround) {
		this.backGround = backGround;
	}
	
	public boolean isOK() {
		return isOK;
	}
	
}
