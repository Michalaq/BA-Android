package com.balloonadventure;


import gameengine.GameEngine;
import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

import com.balloonadventure.androidimpl.canvasbitmapgraphics.CanvasBitmapGraphicsDrawer;

public class LevelView extends SurfaceView implements Callback {
	private GameEngine gameEngine = null;
	private CanvasBitmapGraphicsDrawer cbGraphicsDrawer = null;
	public LevelView(Context context) {
		super(context);
		getHolder().addCallback(this);
	}
	
	public void setDrawingUtilities(GameEngine ge, CanvasBitmapGraphicsDrawer cbGraphicsDrawer) {
		gameEngine = ge;
		this.cbGraphicsDrawer = cbGraphicsDrawer;
	}
	
	@Override
	public void onDraw(Canvas canvas) {
		if (gameEngine != null) {
			cbGraphicsDrawer.setCanvas(canvas);
			gameEngine.getGameScene().draw(cbGraphicsDrawer);
		}
	}
	
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		setWillNotDraw(false); //Allows us to use invalidate() to call onDraw()
		Log.d("Canvas", "powinno byc!");
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		
	}

}
