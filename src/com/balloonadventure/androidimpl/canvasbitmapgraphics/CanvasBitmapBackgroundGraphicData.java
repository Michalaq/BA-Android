package com.balloonadventure.androidimpl.canvasbitmapgraphics;

import gameengine.graphicengine.BackgroundGraphicData;
import gameengine.objects.TextureObject;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader.TileMode;

public class CanvasBitmapBackgroundGraphicData extends BackgroundGraphicData {
	private Bitmap texture;
	private BitmapShader textureShader;
	private Paint fillPaint;
	private Matrix matrix = new Matrix();
	
	// dla wypelniajacego
	public CanvasBitmapBackgroundGraphicData(Bitmap bitmap) {
		texture = bitmap;
		textureShader = new BitmapShader(texture, TileMode.REPEAT, TileMode.REPEAT);
		fillPaint = new Paint();
		fillPaint.setColor(0xFFFFFFFF);  
        fillPaint.setStyle(Paint.Style.FILL); 
        fillPaint.setShader(textureShader);
	}

	
	public void draw(Canvas canvas) {
		canvas.drawPaint(fillPaint);
		
	}
	
	@Override
	public void update(TextureObject to) {
		textureShader.getLocalMatrix(matrix);
		matrix.reset();
		matrix.postTranslate(to.getOffsetX(), to.getOffsetY());
		textureShader.setLocalMatrix(matrix);
	}

}
