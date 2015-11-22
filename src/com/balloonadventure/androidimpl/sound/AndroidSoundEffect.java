package com.balloonadventure.androidimpl.sound;

import gameengine.soundengine.SoundEffect;
import android.media.SoundPool;
import android.util.Log;

public class AndroidSoundEffect extends SoundEffect {
	private static SoundPool soundPool;
	private final int id, priority, duration;
	
	public AndroidSoundEffect(int soundId, int priority, int duration) {
		id = soundId;
		this.priority = priority;
		this.duration = duration;
	}
	
	public static void setSoundPool(SoundPool soundPool) {
		AndroidSoundEffect.soundPool = soundPool;
	}
	
	@Override
	public void playLoop(int times) {
		soundPool.play(id, 1.0f, 1.0f, priority, times-1, 1.0f);

	}

	@Override
	public void play() {
		long t = System.currentTimeMillis();
		soundPool.play(id, 1.0f, 1.0f, priority, 0, 1.0f);
		t = System.currentTimeMillis() - t;
		Log.d(this.getClass().toString(), "Czas trwania playa: " + String.valueOf(t));
	}

	@Override
	public int getDuration() {
		return duration;
	}

}
