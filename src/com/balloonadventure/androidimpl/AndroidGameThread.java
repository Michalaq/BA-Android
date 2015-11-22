package com.balloonadventure.androidimpl;

import gameengine.GameThread;
import android.graphics.Canvas;
import android.view.SurfaceHolder;

import com.balloonadventure.LevelView;
import com.balloonadventure.androidimpl.canvasbitmapgraphics.CanvasBitmapGraphicsDrawer;

public class AndroidGameThread extends GameThread {
	private SurfaceHolder surfaceHolder;
	private LevelView levelView;
	private Canvas c = null;
	private long previousSystemTime = System.currentTimeMillis(), currentSystemTime;
	private int interval;
	/////////DEBUG
	private long loopStart, loopEnd;
	private float loopDiff = 0, tries = 1;
	/////////DEBUG
	
	public AndroidGameThread(SurfaceHolder surfaceHolder, LevelView levelView) {
		this.surfaceHolder = surfaceHolder;
		this.levelView = levelView;
		interval = 1000/CanvasBitmapGraphicsDrawer.getFPS();
	}
	
	@Override
	public void run() {
			while (gameEngine.isRunning()) {
				try {
            		//if (!surfaceHolder.isCreating()) {
            		//	c = surfaceHolder.lockCanvas();
            		//}               
	                synchronized (surfaceHolder) {
	                	//if (c != null) {
		                	// tu wszystko
		                	//gameEngine.updateInput();
		                	// tu aktualizacje
		                	currentSystemTime = System.currentTimeMillis();
		                	if (currentSystemTime - previousSystemTime >= interval) {
		                		previousSystemTime = currentSystemTime;
		                		gameEngine.getGameScene().update();
		                		gameEngine.updateGameState();
				                levelView.postInvalidate();	
		                	}

	                	//}
		              }
	            } finally {
	                if (c != null) {
	                    surfaceHolder.unlockCanvasAndPost(c);
	                }
	            }
			}
	}
}
