package com.balloonadventure.androidimpl.canvasbitmapgraphics;

import gameengine.graphicengine.RectangularBackgroundGraphicData;
import gameengine.objects.TextureObject;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader.TileMode;

public class CanvasBitmapRectangularBackgroundGraphicData extends
		RectangularBackgroundGraphicData {
	private Bitmap texture;
	private BitmapShader textureShader;
	private Paint fillPaint;
	
	public CanvasBitmapRectangularBackgroundGraphicData(Bitmap bitmap, float x, float y,
			float xEnd, float yEnd) {
		super(x, y, xEnd, yEnd);
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
		canvas.drawRect(x, y, xEnd, yEnd, fillPaint);
	}

}
