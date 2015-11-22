package com.balloonadventure.androidimpl.canvasbitmapgraphics;

import gameengine.graphicengine.BackgroundGraphicData;
import gameengine.graphicengine.CircularBackgroundGraphicData;
import gameengine.graphicengine.CustomShapeBackgroundGraphicData;
import gameengine.graphicengine.GraphicsDrawer;
import gameengine.graphicengine.RectangularBackgroundGraphicData;
import gameengine.graphicengine.SpriteObjectGraphicData;
import gameengine.menus.controls.GameText;

import java.util.HashMap;
import java.util.Map;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

public class CanvasBitmapGraphicsDrawer extends GraphicsDrawer {
	private Canvas canvas;
	private Paint worldObjectPaint = new Paint();
	private boolean unScaled = true;
	private Map<GameText, Paint> textPaint = new HashMap<GameText, Paint>();
	
	public CanvasBitmapGraphicsDrawer(float screenWidth, float screenHeight) {
		super(screenWidth, screenHeight);
		//Log.d("Multiplier", String.valueOf(this.getGraphicResizeMultiplier()));
		//textPaint.setColor(Color.RED);
		//textPaint.setTypeface(Typeface.createFromFile("assets/fonts/yardsale.ttf"));
	}
	
	public void setCanvas(Canvas canvas) {
		this.canvas = canvas;
		//this.canvas.scale(getGraphicResizeMultiplier(), getGraphicResizeMultiplier());
		unScaled = false;
	}

	
	@Override
	public void drawSpriteObject(SpriteObjectGraphicData wogd) {
	//	GameEngine.sendDebugMessage("Sprawdzam nulle", "wogd: " + Boolean.toString(wogd == null) + " "
	//													+ "canvas: " + Boolean.toString(canvas == null) + " "
	//													+ "paint: " + Boolean.toString(worldObjectPaint == null));
		((CanvasBitmapSpriteObjectGraphicData) wogd).draw(canvas, worldObjectPaint);
		//Log.d("Rysowanie", "Rysuje obiekt swiata. Koordynaty: [" + String.valueOf(wogd.getX()) + ", "
		//					+ String.valueOf(wogd.getY()) + "].");
		
	}	

	@Override
	public void drawBackground(BackgroundGraphicData bgd) {
		((CanvasBitmapBackgroundGraphicData) bgd).draw(canvas);
	}
		

	@Override
	public void drawText(GameText text) {
		if (!textPaint.containsKey(text)) {
			Paint p = new Paint(CanvasBitmapGraphicDataFactory.getTextPaint());
			p.setTextSize(text.getHeightAvailable());
			p.setTextAlign(Paint.Align.CENTER);
			p.setColor(Color.parseColor("#" + text.getColourHexString()));
			while(p.measureText(text.getText()) < text.getWidthAvailable()
				  && p.measureText(text.getText()) < text.getHeightAvailable()) {
				p.setTextSize(p.getTextSize()+1);
				Log.d("mierzymy tekst", String.valueOf(p.getTextSize()));
			}
			while(p.measureText(text.getText()) > text.getWidthAvailable()
				  || p.measureText(text.getText()) > text.getHeightAvailable()) {
				p.setTextSize(p.getTextSize()-1);
			}
			textPaint.put(text, p);
		}
		canvas.drawText(text.getText(), text.getX()*GraphicsDrawer.getGraphicResizeMultiplierX(),
						text.getY()*GraphicsDrawer.getGraphicResizeMultiplierY(), textPaint.get(text));
	}

	@Override
	public void drawCircularBackground(CircularBackgroundGraphicData cbgd) {
		((CanvasBitmapCircularBackgroundGraphicData) cbgd).draw(canvas);
		
	}

	@Override
	public void drawRectangularBackground(RectangularBackgroundGraphicData rbgd) {
		((CanvasBitmapRectangularBackgroundGraphicData) rbgd).draw(canvas);
		
	}

	@Override
	public void drawCustomShapeBackground(CustomShapeBackgroundGraphicData csbgd) {
		//((CanvasBitmapCustomShapeBackgroundGraphicData) csbgd).draw(canvas); TODO
		Log.d("DEBUG", "Nie zrobione!");
	}


}
