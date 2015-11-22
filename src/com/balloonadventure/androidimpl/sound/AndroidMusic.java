package com.balloonadventure.androidimpl.sound;

import gameengine.soundengine.BackgroundMusicManager;
import gameengine.soundengine.Music;

public class AndroidMusic extends Music {
	
	public AndroidMusic(int soundId, BackgroundMusicManager bmm) {
		super(soundId, bmm);
	}
	
	@Override
	public int getDuration() {
		return 0;
	}

}
