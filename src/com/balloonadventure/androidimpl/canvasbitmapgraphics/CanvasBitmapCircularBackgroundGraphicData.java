package com.balloonadventure.androidimpl.canvasbitmapgraphics;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader.TileMode;
import gameengine.GameEngine;
import gameengine.graphicengine.CircularBackgroundGraphicData;
import gameengine.objects.TextureObject;

public class CanvasBitmapCircularBackgroundGraphicData extends
		CircularBackgroundGraphicData {
	private Bitmap texture;
	private BitmapShader textureShader;
	private Paint fillPaint;
	
	public CanvasBitmapCircularBackgroundGraphicData(Bitmap bitmap, float x, float y,
			float radius) {
		super(x, y, radius);
		texture = bitmap;
		textureShader = new BitmapShader(texture, TileMode.REPEAT, TileMode.REPEAT);
		fillPaint = new Paint();
		fillPaint.setColor(0xFFFFFFFF);  
        fillPaint.setStyle(Paint.Style.FILL); 
        fillPaint.setShader(textureShader);
	}

	@Override
	public void update(TextureObject to) {
		// TODO Auto-generated method stub

	}
	
	public void draw(Canvas canvas) {
		GameEngine.sendDebugMessage("BITMAP", String.format("x: %f, y: %f, rad: %f", x, y, radius));
		canvas.drawCircle(x, y, radius, fillPaint);
	}

}
