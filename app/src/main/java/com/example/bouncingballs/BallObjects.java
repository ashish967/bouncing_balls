package com.example.bouncingballs;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.text.method.MovementMethod;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

class Ball {

	public int mX, mY;

	public float mSlope;

	public BitmapDrawable bd;

	public boolean mDirection;
	
	public boolean isRunning;

	public static final boolean POSTIVE = true, NEGATIVE = false;

	private int left, right, top, bottom;

	public static final int LEFT = 1, RIGHT = 2, TOP = 3, BOTTOM = 4;

	public int mConstant;
	
	public int mRadius=50;
	
	public int mVelocity;

	public void moveTo(int x, int y) {

		Log.d("Ashish","moving to "+x+" "+y);
		mX = mapToScreenX(x);
		mY = mapToScreenY(y);
	}
	
	public void stop(){
		
		isRunning=false;
	}
	
	public void start(){
		
		isRunning=true;
	}

	public boolean isRunning(){
		
		return isRunning;
	}
	public int getX() {

		return mapToAxisX(mX);

	}

	public int getY() {

		return mapToAxisY(mY);
	}

	public void setDirectionSlope(float m) {
		mSlope = m;
	}

	public void setBitmap(BitmapDrawable bd) {

		this.bd = bd;
	}

	public BitmapDrawable getBitmap() {

		return bd;
	}

	public void setBounds(int left, int top, int right, int bottom) {
		// TODO Auto-generated method stub

		this.left = left;
		this.top = top;
		this.right = right;
		this.bottom = bottom;

	}

	public void setSlope(float slope) {

		mSlope = slope;
	}

	public boolean isTouchingBounds() {

		if (mX - 50 <= left || mX + 50 >= right)
			return true;

		if (mY - 50 <= top || mY + 50 >= bottom)
			return true;

		return false;

	}

	public int getTouchingSide() {

		if (mX - 50 <= left)
			return LEFT;

		else if (mX + 50 >= right)
			return RIGHT;

		else if (mY - 50 <= top)
			return TOP;

		else if (mY + 50 >= bottom)
			return BOTTOM;
		return 0;

	}

	
	public boolean isTouching(Ball b){
		
		int x1=getX();
		int y1=getY();
		int x2=b.getX();
		int y2=b.getY();
		
		
		if((x2-x1)*(x2-x1)+(y2-y1)*(y2-y1)<=4*b.mRadius*b.mRadius){
			
			return true;
			
		}
		
		return false;
	}
	private int mapToScreenX(int p) {

		p = left + (right - left) / 2 + p;

		return p;
		// TODO Auto-generated method stub

	}

	private int mapToScreenY(int p) {

		p = top + (bottom - top) / 2 - p;

		return p;
		// TODO Auto-generated method stub

	}

	private int mapToAxisX(int p) {

		p = p - left - (right - left) / 2;

		return p;
		// TODO Auto-generated method stub

	}

	private int mapToAxisY(int p) {

		p = top + (bottom - top) / 2 - p;

		return p;
		// TODO Auto-generated method stub

	}

}

public class BallObjects extends View {

	Ball balls[];
	long lastdrawTime;
	int offset = 2;
	Paint mPaint;
	private boolean isRunning;
	private int mRadius;
	private int MAX_SLOPE=1000;
	public BallObjects(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		balls = new Ball[4];
		
		for(int i=0;i<balls.length;i++){
			
			balls[i]= new Ball();
			balls[i].mRadius=getResources().getDimensionPixelSize(R.dimen.ball_radius);
		}
		mPaint = new Paint();
	}

	public BallObjects(Context context) {

		super(context, null);
	}

	public void stop() {

		isRunning = false;
	}

	public void start() {

		isRunning = true;
		for(int i=0;i<balls.length;i++){
			
			balls[i].start();
		}
	}

	public boolean isRunning() {

		return isRunning;
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {

		Log.d("Ashish", "onLayout");
		// TODO Auto-generated method stub
		super.onLayout(changed, left, top, right, bottom);
		
		
		mRadius=50;
		balls[0].mSlope =(float) 1.5;
		balls[0].mDirection = Ball.POSTIVE;
		balls[0].setBounds(left, top, right, bottom);
		balls[0].moveTo(0, 0);
		balls[0].start();
		balls[0].mVelocity=8;
		balls[0].mConstant=(int) (balls[0].getY()-balls[0].getX()*balls[0].mSlope);
		
		balls[1].mSlope = (float) 8.5;
		balls[1].mDirection = Ball.NEGATIVE;
		balls[1].setBounds(left, top, right, bottom);
		balls[1].moveTo(0,balls[1].mRadius*5);
		balls[1].mConstant=(int) (balls[1].getY()-balls[1].getX()*balls[1].mSlope);
		balls[1].start();
		balls[1].mVelocity=8;
		isRunning = true;
		balls[2].mSlope = (float) 8.5;
		balls[2].mDirection = Ball.NEGATIVE;
		balls[2].setBounds(left, top, right, bottom);
		balls[2].moveTo(0, -balls[1].mRadius*5);
		balls[2].mConstant=(int) (balls[1].getY()-balls[1].getX()*balls[1].mSlope);
		balls[2].start();
		balls[2].mVelocity=8;
		isRunning = true;
		balls[3].mSlope = (float) 8.5;
		balls[3].mDirection = Ball.POSTIVE;
		balls[3].setBounds(left, top, right, bottom);
		balls[3].moveTo(balls[1].mRadius*1, 0);
		balls[3].mConstant=(int) (balls[1].getY()-balls[1].getX()*balls[1].mSlope);
		balls[3].start();
		balls[3].mVelocity=8;
		isRunning = true;
	}

	
	private int[] calculateNext(Ball b,int offset){
		
		int p[]= new int[2];
		int x=b.getX(),y=b.getY();
		
		if (Math.abs(b.mSlope) < Math.PI / 4.0) {
			if (b.mDirection == Ball.POSTIVE) {

				x = x + offset;

			} else {
				x = x -offset;
			}

			y = (int) (b.mSlope * x + b.mConstant);

		

		}
		else{
			
			if (b.mDirection == Ball.POSTIVE) {
				
				if(b.mSlope >0){
					y = y +offset;

				} else {
					y = y -offset;
				}


			}
			else{
				
				if(b.mSlope >0){
					y = y - offset;

				} else {
					y = y + offset;
				}
			}
			
			x = (int) ( (y-b.mConstant)/b.mSlope);

			
		}

		p[0]=x;
		p[1]=y;
		return p;
	}
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub

		Log.d("Ashish", "onTouchEvent called");

		return super.onTouchEvent(event);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub

		super.onDraw(canvas);

		Log.d("Ashish", "onDraw Called");
		
		mPaint.setTextSize(20);
		for(int i=0;i<balls.length;i++){
			mPaint.setColor(Color.WHITE);
			canvas.drawCircle(balls[i].mX, balls[i].mY, balls[i].mRadius, mPaint);
			mPaint.setColor(Color.BLACK);
			canvas.drawText(""+(i+1),balls[i].mX, balls[i].mY, mPaint);
		}
		
		if ((System.currentTimeMillis() - lastdrawTime) > 1 && isRunning) {

			// ball.getBitmap().draw(canvas);
			for(int i=0;i<balls.length;i++){
			
				boolean touching=false;
				int ballNo;
				for(int j=0;j<balls.length&&balls[i].isRunning;j++){
					
					if(i!=j && balls[i].isTouching(balls[j])){
						touching=true;
						ballNo=j;
						
						float surfaceSlope;
						if((balls[j].getY()-balls[i].getY())==0){
							
							 surfaceSlope= (float) (Math.PI/2*(balls[i].getX()-balls[j].getX()>0?1:-1));
						}
						
						else{
						 surfaceSlope= (float) (Math.atan((balls[i].getX()-balls[j].getX())/(balls[j].getY()-balls[i].getY())));
						}
						
						
						Log.d("Ashish1","ball "+i+"colided with ball"+j+"coliding  surface slope is "+Math.tanh(surfaceSlope) );
						
						balls[i].mSlope= (float) Math.tan(2*surfaceSlope-Math.atan(balls[i].mSlope));
						balls[j].mSlope= (float) Math.tan(2*surfaceSlope-Math.atan(balls[j].mSlope));
						
						balls[i].mDirection=!balls[i].mDirection;
						balls[j].mDirection=!balls[j].mDirection;
						
						
						
						
						balls[i].mConstant=(int) (balls[i].getY()-balls[i].mSlope*balls[i].getX());
						balls[j].mConstant=(int) (balls[j].getY()-balls[j].mSlope*balls[j].getX());
							
						
						Log.d("Ashish","ball "+i+"next m="+balls[i].mSlope+" c: "+balls[i].mConstant+"  ball"+j+" next m is "+balls[j].mSlope+" c: "+balls[j].mConstant );
						
						
						int x=balls[i].getX();
						int y=balls[i].getY();
						
						if (Math.abs(balls[i].mSlope) < Math.PI / 4.0) {
							if (balls[i].mDirection == Ball.POSTIVE) {

								x = x + balls[i].mRadius;

							} else {
								x = x - balls[i].mRadius;
							}

							y = (int) (balls[i].mSlope * x + balls[i].mConstant);

							balls[i].moveTo(x, y);

						}
						else{
							
							if (balls[i].mDirection == Ball.POSTIVE) {
								
								if(balls[i].mSlope >0){
									y = y + balls[i].mRadius;

								} else {
									y = y - balls[i].mRadius;
								}


							}
							else{
								
								if(balls[i].mSlope >0){
									y = y - balls[i].mRadius;

								} else {
									y = y + balls[i].mRadius;
								}
							}
							
							x = (int) ( (y-balls[i].mConstant)/balls[i].mSlope);

							balls[i].moveTo(x, y);
						}
						
						
						/*if (balls[j].mDirection == Ball.POSTIVE) {

							x1 = x1 + balls[j].mRadius;

						} else {
							x1 = x1 - balls[j].mRadius/2;
						}

						y1 = (int) (balls[i].mSlope * x1 + balls[j].mConstant);

						balls[j].moveTo(x1, y1);*/
						
//						balls[i].stop();
	//					balls[j].stop();
						
					}
					
				}
				
				if (!balls[i].isTouchingBounds() && balls[i].isRunning() ) {

					
					int p[]=calculateNext(balls[i],balls[i].mVelocity);
					int x=p[0];
					int y=p[1];
					
					balls[i].moveTo(x, y);
					/*int x = balls[i].getX();
					int y = balls[i].getY();
*/
					
					
					
					/*if (Math.abs(balls[i].mSlope) < Math.PI / 4.0) {
						if (balls[i].mDirection == Ball.POSTIVE) {

							x = x + balls[i].mVelocity;

						} else {
							x = x - balls[i].mVelocity;
						}

						y = (int) (balls[i].mSlope * x + balls[i].mConstant);

						balls[i].moveTo(x, y);

					}
					else{
						
						if (balls[i].mDirection == Ball.POSTIVE) {
							
							if(balls[i].mSlope >0){
								y = y + balls[i].mVelocity;

							} else {
								y = y - balls[i].mVelocity;
							}


						}
						else{
							
							if(balls[i].mSlope >0){
								y = y - balls[i].mVelocity;

							} else {
								y = y + balls[i].mVelocity;
							}
						}
						
						x = (int) ( (y-balls[i].mConstant)/balls[i].mSlope);

						balls[i].moveTo(x, y);
					}

*/				} else { 
					
					

					int touchingSide = balls[i].getTouchingSide();
					int x = balls[i].getX();
					int y = balls[i].getY();

					if (balls[i].mDirection == Ball.POSTIVE) {

						if (touchingSide == Ball.RIGHT) {

							balls[i].mDirection = Ball.NEGATIVE;
							balls[i].mSlope = -balls[i].mSlope;
							balls[i].mConstant = (int) (y - balls[i].mSlope * x);
							
							/*x = x - mRadius;
							y = (int) (balls[i].mSlope * x + balls[i].mConstant);
							*/
							int p[]=calculateNext(balls[i], balls[i].mRadius);
							
							x=p[0];
							y=p[1];
							balls[i].moveTo(x, y);
						} else if (touchingSide == Ball.TOP) {

							balls[i].mSlope = -balls[i].mSlope;
							balls[i].mConstant = (int) (y - balls[i].mSlope * x);
							/*y = y - mRadius;
							x = (int) ((int) (y - balls[i].mConstant) / balls[i].mSlope);
							*/
							int p[]=calculateNext(balls[i], balls[i].mRadius);
							
							x=p[0];
							y=p[1];
							balls[i].moveTo(x, y);

						} else if (touchingSide == Ball.BOTTOM) {

							balls[i].mSlope = -balls[i].mSlope;
							balls[i].mConstant = (int) (y - balls[i].mSlope * x);
							/*y = y + mRadius;
							x = (int) ((int) (y - balls[i].mConstant) / balls[i].mSlope);
							*/
							int p[]=calculateNext(balls[i], balls[i].mRadius);
							x=p[0];
							y=p[1];
							
							balls[i].moveTo(x, y);

						}

					} else {

						if (touchingSide == Ball.LEFT) {

							balls[i].mDirection = Ball.POSTIVE;
							balls[i].mSlope = -balls[i].mSlope;
							balls[i].mConstant = (int) (y - balls[i].mSlope * x);
							/*x = x + mRadius;
							y = (int) (balls[i].mSlope * x + balls[i].mConstant);*/

							int p[]=calculateNext(balls[i], balls[i].mRadius);
							
							x=p[0];
							y=p[1];
							
							balls[i].moveTo(x, y);
						} else if (touchingSide == Ball.TOP) {

							balls[i].mSlope = -balls[i].mSlope;
							balls[i].mConstant = (int) (y - balls[i].mSlope * x);
							/*y = y - mRadius;
							x = (int) ((int) (y - balls[i].mConstant) / balls[i].mSlope);
							*/
							int p[]=calculateNext(balls[i], balls[i].mRadius);
							x=p[0];
							y=p[1];
							
							balls[i].moveTo(x, y);

						} else if (touchingSide == Ball.BOTTOM) {

							balls[i].mSlope = -balls[i].mSlope;
							balls[i].mConstant = (int) (y - balls[i].mSlope * x);
							/*y = y + mRadius;
							x = (int) ((int) (y - balls[i].mConstant) / balls[i].mSlope);
							*/
							int p[]=calculateNext(balls[i], balls[i].mRadius);
							x=p[0];
							y=p[1];
							
							balls[i].moveTo(x, y);

						}

					}

				}

			}
			
			lastdrawTime = System.currentTimeMillis();
		}

		invalidate();
	}
}
