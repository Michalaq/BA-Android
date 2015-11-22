package com.balloonadventure;

import gameengine.Debugger;
import gameengine.GameEngine;
import gameengine.levels.LevelCreator;
import gameengine.menus.MenuManager;
import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.widget.Toast;
import balloonadventure.level.BalloonAdventureLevelCreator;
import balloonadventure.level.BalloonAdventureMenuManager;

import com.balloonadventure.androidimpl.AndroidGameThread;
import com.balloonadventure.androidimpl.AndroidInputManager;
import com.balloonadventure.androidimpl.AndroidLevelCreator;
import com.balloonadventure.androidimpl.canvasbitmapgraphics.AndroidGraphicalResourceIdManager;
import com.balloonadventure.androidimpl.canvasbitmapgraphics.CanvasBitmapGraphicDataFactory;
import com.balloonadventure.androidimpl.canvasbitmapgraphics.CanvasBitmapGraphicsDrawer;
import com.balloonadventure.androidimpl.sound.AndroidMusicManager;

public class MainActivity extends Activity implements Debugger {
	private static final int MAX_STREAM_COUNT = 20;
	private GameEngine gameEngine;
	private LevelView levelView;
	private AndroidGameThread androidGameThread;
	private GestureDetectorCompat gestureDetector;
	private CanvasBitmapGraphicsDrawer cbGraphicsDrawer;
	private AndroidMusicManager androidMusicManager;
	private AndroidInputManager androidClickSource;
	private MenuManager androidMenuManager;
	private LevelCreator androidLevelCreator;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		levelView = new LevelView(this);
		setContentView(levelView);
		Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		int width = size.x;
		int height = size.y;
		Log.d("Szerokosc view:", String.valueOf(width));
		Log.d("Wysokosc view:", String.valueOf(height));
		GameEngine.setDebugger(this);
		cbGraphicsDrawer = new CanvasBitmapGraphicsDrawer((float) width, (float) height);
		androidGameThread = new AndroidGameThread(levelView.getHolder(), levelView);
		androidMusicManager = new AndroidMusicManager(this, MAX_STREAM_COUNT);
		androidClickSource = new AndroidInputManager();
		levelView.setOnTouchListener(androidClickSource);
		androidLevelCreator = new AndroidLevelCreator(this, androidClickSource);
		androidMenuManager = new BalloonAdventureMenuManager(androidClickSource, (BalloonAdventureLevelCreator) androidLevelCreator);
		gameEngine = new GameEngine(
					cbGraphicsDrawer,
					androidGameThread,
					new AndroidGraphicalResourceIdManager(this),
					//androidInputManager,
					new CanvasBitmapGraphicDataFactory(this),
					androidMusicManager,
					androidClickSource,
					androidMenuManager,
					androidLevelCreator
					);
		gameEngine.start();
		AndroidGameThread.init(gameEngine);
		levelView.setDrawingUtilities(gameEngine, cbGraphicsDrawer);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
	    return gestureDetector.onTouchEvent(event);
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		if (gameEngine != null)
			gameEngine.pause();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		if (gameEngine != null)
			gameEngine.resume();
	}
	
	@Override
	protected void onDestroy() {
		gameEngine.end();
		//if (androidGameThread.getCanvas() != null) {
		//	levelView.getHolder().unlockCanvasAndPost(androidGameThread.getCanvas());
		//}
		super.onDestroy();
		Log.d("Koniec", "konczymy");
	}

	@Override
	public void print(String type, String msg) {
		Log.d(type, msg);
		if(type.equals("TOAST_DEBUG"))
			Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
	}
}
