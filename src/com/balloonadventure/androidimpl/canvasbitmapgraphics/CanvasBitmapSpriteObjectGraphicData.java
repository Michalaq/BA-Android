package com.balloonadventure.androidimpl.canvasbitmapgraphics;

import gameengine.graphicengine.SpriteObjectGraphicData;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;

public class CanvasBitmapSpriteObjectGraphicData extends SpriteObjectGraphicData {
	private final Bitmap bitmap;
	private Matrix matrix = new Matrix();
	
	public CanvasBitmapSpriteObjectGraphicData(Bitmap bitmap) {
		this.bitmap = bitmap;
	}
	
	public Bitmap getBitmap() {
		return bitmap;
	}
	
	public void draw(Canvas canvas, Paint paint) {
		canvas.scale(multiplierX, multiplierY);
		paint.setFilterBitmap(true);
		if (width != currentWidth || height != currentHeight) {
			matrix.postScale(currentWidth/width, currentHeight/height);
		}
		matrix.postTranslate(x, y);
		canvas.drawBitmap(bitmap, matrix, paint);
		matrix.reset();
		paint.setFilterBitmap(false);
		canvas.scale(1/multiplierX, 1/multiplierY);
	}
}
