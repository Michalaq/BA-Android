package com.balloonadventure.androidimpl;

import gameengine.GameEngine;
import gameengine.graphicengine.GraphicsDrawer;
import gameengine.input.ClickSource;
import gameengine.input.Clickable;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class AndroidInputManager implements ClickSource, OnTouchListener {
	private static final int max_clickables = 100;
	private PriorityQueue<Clickable> listeners = new PriorityQueue<Clickable>(100,
				new Comparator<Clickable>() {

					@Override
					public int compare(Clickable lhs, Clickable rhs) {
						return rhs.getPriority()-lhs.getPriority();
					}
					
				}
			);
	private PriorityQueue<Clickable> tmp;
	private Clickable c;
	private Collection<Clickable> listenersToRemove = new HashSet<Clickable>();
	private MotionEvent lastEvent;
	
	@Override
	public synchronized void addListener(Clickable c) {
		listeners.add(c);
		GameEngine.sendDebugMessage("Input_3", "Dodaje: " + c.toString());
	}

	@Override
	public synchronized void removeListener(Clickable c) {
		c.setEnabled(false);
		listenersToRemove.add(c);

	}

	@Override
	public synchronized void update(Clickable c) {
		
	}

	@Override
	public synchronized void notifyListeners() {
		float fixedX = lastEvent.getX()/GraphicsDrawer.getGraphicResizeMultiplierX(),
			  fixedY = lastEvent.getY()/GraphicsDrawer.getGraphicResizeMultiplierY();
		listeners.removeAll(listenersToRemove);
		listenersToRemove.clear();
		tmp = new PriorityQueue<Clickable>(listeners);
		while(!tmp.isEmpty()) {
			c = tmp.remove();
			if (c.isEnabled()) {
				GameEngine.sendDebugMessage("Input", "Clickable jakis: " + c.getClass().toString());
				if (lastEvent.getAction() == MotionEvent.ACTION_DOWN
					&& c.isInitialPressWithin(fixedX, fixedY)) {
					c.onInitialPress(fixedX,
									fixedY);
					break; // konczy dzialanie po pierwszym napotkanym
				} else switch(lastEvent.getAction()) {
					case MotionEvent.ACTION_MOVE : c.onHold(fixedX,
															fixedY); break;
					case MotionEvent.ACTION_UP : c.onRelease(fixedX,
															 fixedY);
				}
			}
		}
	}

	@Override
	public boolean onTouch(View v, MotionEvent e) {
		lastEvent = e;
		Log.d("INPUT_2", e.toString());
		notifyListeners();
		return true;
	}

}
