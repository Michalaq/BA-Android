package com.balloonadventure.androidimpl.canvasbitmapgraphics;

import gameengine.Coordinates;
import gameengine.graphicengine.BackgroundGraphicData;
import gameengine.graphicengine.CircularBackgroundGraphicData;
import gameengine.graphicengine.CustomShapeBackgroundGraphicData;
import gameengine.graphicengine.GraphicDataFactory;
import gameengine.graphicengine.GraphicsDrawer;
import gameengine.graphicengine.RectangularBackgroundGraphicData;
import gameengine.graphicengine.SpriteObjectGraphicData;

import java.util.HashMap;
import java.util.List;
//import java.util.HashSet;
import java.util.Map;
//import java.util.Set;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.Log;

public class CanvasBitmapGraphicDataFactory extends GraphicDataFactory {
	private static final String bitmapExtension = ".png";
	private static final boolean scaleSprites = false;
	private static final boolean scaleTextures = true;
	private static Map<String, Bitmap> sprites;
	private static Map<String, Bitmap> textures;
	private Bitmap temp;
	private static Context context;
	private static Paint textPaint = new Paint();
	//private static Set<Bitmap> allBitmaps;
	
	public CanvasBitmapGraphicDataFactory(Context context) {
		CanvasBitmapGraphicDataFactory.context = context;
		sprites = new HashMap<String, Bitmap>();
		textures = new HashMap<String, Bitmap>();
		textPaint.setColor(Color.RED);
		textPaint.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/giantsteeth.otf"));
		//allBitmaps = new HashSet<Bitmap>();
	}
	
	public static Paint getTextPaint() {
		return textPaint;
	}
	
	private final String getResourceType() {
		return "drawable"; // w przyszlosci na podstawie rozmiaru okna/czegostam zrobic inne
	}
	
	@Override
	protected Bitmap getResizedSpriteResource(String sprite, float xResized,
			float yResized) {
		temp = getSpriteResource(sprite);
		if (xResized == 1.0f && yResized == 1.0f) {
			return temp;
		}
		return Bitmap.createScaledBitmap(temp, (int) (temp.getWidth()*xResized),
				(int) (temp.getHeight()*yResized), false);
	}

	@Override
	protected Bitmap getResizedSpriteResource(String sprite, int toWidth,
			int toHeight) {
		temp = getSpriteResource(sprite);
		if (temp.getWidth() == toWidth && temp.getHeight() == toHeight) {
			return temp;
		}
		return Bitmap.createScaledBitmap(temp, toWidth,
				toHeight, false);
	}
	
	@Override
	protected Bitmap getSpriteResource(String sprite) {
		return sprites.get(sprite);
	}

	@Override
	protected Bitmap getTextureResource(String texture) {
		return textures.get(texture);
	}
	
	@Override
	protected void loadSprite(String sprite) {
		try {//
			temp = BitmapFactory.decodeResource(context.getResources(),
					context.getResources().
					getIdentifier(sprite.toLowerCase(),//+bitmapExtension,
								  getResourceType(), context.getPackageName()));
			Log.d("BITMAP LOAD", "Sprite load check: " + Boolean.toString(temp == null));
			if (scaleSprites) {
				Bitmap b = Bitmap.createScaledBitmap(temp, (int) (temp.getWidth()*GraphicsDrawer.getGraphicResizeMultiplierX()),
						(int) (temp.getHeight()*GraphicsDrawer.getGraphicResizeMultiplierY()), false);
				temp.recycle();
				temp = b;
			}
			sprites.put(sprite, temp);
		} catch (Exception e) {
			e.printStackTrace();
			Log.d("BITMAP LOAD", "Sprite load fail: " + sprite);
		}
					
	}	
	
	@Override
	protected void loadTexture(String texture) {
		try {
		temp = BitmapFactory.decodeResource(context.getResources(),
								context.getResources().
								getIdentifier(texture.toLowerCase(),//+bitmapExtension,
										      getResourceType(), context.getPackageName()));
		Log.d("BITMAP LOAD", "Texture load check: " + Boolean.toString(temp == null));
		if (scaleTextures) {
			Bitmap b = Bitmap.createScaledBitmap(temp, (int) (temp.getWidth()*GraphicsDrawer.getGraphicResizeMultiplierX()),
					(int) (temp.getHeight()*GraphicsDrawer.getGraphicResizeMultiplierY()), false);
			temp.recycle();
			temp = b;
		}
		textures.put(texture, temp);
		} catch (Exception e) {
			e.printStackTrace();
			Log.d("BITMAP LOAD", "Texture load fail: " + texture);
		}
	}

	@Override
	public BackgroundGraphicData createSceneryGraphicData(String texture) {
		return new CanvasBitmapBackgroundGraphicData(getTextureResource(texture));
	}


	@Override
	public CircularBackgroundGraphicData createCircularFieldGraphicData(String texture, float x,
			float y, float radius) {
		return new CanvasBitmapCircularBackgroundGraphicData(getTextureResource(texture), x, y, radius);
	}


	@Override
	public RectangularBackgroundGraphicData createRectangularFieldGraphicData(String texture,
			float x, float y, float xEnd, float yEnd) {
		// TODO Auto-generated method stub
		return new CanvasBitmapRectangularBackgroundGraphicData(getTextureResource(texture), x, y, xEnd, yEnd);
	}


	@Override
	public CustomShapeBackgroundGraphicData createCustomShapeFieldGraphicData(String texture,
			List<Coordinates> edgeCoordinates) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SpriteObjectGraphicData createSpriteObjectGraphicData(String sprite) {
		return new CanvasBitmapSpriteObjectGraphicData(getSpriteResource(sprite));
	}

	@Override
	public SpriteObjectGraphicData createResizedSpriteObjectGraphicData(
			String sprite, int toWidth, int toHeight) {
		return new CanvasBitmapSpriteObjectGraphicData(getResizedSpriteResource(sprite, toWidth, toHeight));
	}

	@Override
	public SpriteObjectGraphicData createResizedSpriteObjectGraphicData(
			String sprite, float xResized, float yResized) {
		return new CanvasBitmapSpriteObjectGraphicData(getResizedSpriteResource(sprite, xResized, yResized));
	}


}
