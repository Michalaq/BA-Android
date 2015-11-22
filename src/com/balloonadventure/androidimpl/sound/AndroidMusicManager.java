package com.balloonadventure.androidimpl.sound;

import gameengine.levels.LevelCreator;
import gameengine.soundengine.BackgroundMusicManager;
import gameengine.soundengine.Music;
import gameengine.soundengine.MusicManager;
import gameengine.soundengine.SoundEffect;

import java.io.IOException;
import java.util.HashMap;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.net.Uri;
import android.widget.Toast;
import balloonadventure.world.Balloon;
import balloonadventure.world.Spiky;

import com.balloonadventure.R;

public class AndroidMusicManager extends MusicManager implements BackgroundMusicManager, MediaPlayer.OnErrorListener {
	private static SoundPool soundPool;
	private static MediaPlayer mediaPlayer;
	private Context context;
	private AndroidMusic currentlyPlayedMusic;
	private int lastStopped = -1;
	
	public AndroidMusicManager(Context context, int maxStreamCount) {
		soundPool = new SoundPool(maxStreamCount, AudioManager.STREAM_MUSIC, 0);
		AndroidSoundEffect.setSoundPool(soundPool);
		this.context = context;
		soundEffects = new HashMap<String, SoundEffect>();
		mediaPlayer = new MediaPlayer();
		mediaPlayer.setOnErrorListener(this);
		mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
		mediaPlayer.setLooping(true);

	}
	
	@Override
	public void loadSoundData() {
		levelMusic = new Music[LevelCreator.getLevelCount()+2];
		currentlyPlayedMusic = new AndroidMusic(R.raw.music_level_one, this);
		for(int i=1; i<levelMusic.length; i++)
			levelMusic[i] = currentlyPlayedMusic;
		soundEffects.put("BALLOON_POP",
						new AndroidSoundEffect(soundPool.load(context, R.raw.balloon_pop, 0),
						0, 1000)
						);
		soundEffects.put("BALLOON_FLYHIGH",
						new AndroidSoundEffect(soundPool.load(context, R.raw.balloon_flyhigh, 0),
						0, 2000)
						);
		soundEffects.put("SPIKY_LAUGH",
						new AndroidSoundEffect(soundPool.load(context, R.raw.spiky_laugh, 0),
						0, 1000)
						);
	}
	
	public void endWork() {
		if(mediaPlayer != null)
		{
			try{
				mediaPlayer.stop();
				mediaPlayer.release();
			}finally {
				mediaPlayer = null;
			}
		}
	}

	@Override
	public void setBackgroundMusic(Music music) {
		mediaPlayer.reset();
		try {
			mediaPlayer.setDataSource(context, Uri.parse("android.resource://"+ context.getPackageName() +"/" + music.getId()));
			currentlyPlayedMusic = (AndroidMusic) music;
			mediaPlayer.setLooping(true);
			mediaPlayer.prepare();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void changeBackgroundMusic(Music toMusic) {
		try {
			mediaPlayer.reset();
			mediaPlayer.setDataSource(context, Uri.parse("android.resource://"+ context.getPackageName() +"/" + toMusic.getId()));
			currentlyPlayedMusic = (AndroidMusic) toMusic;
			mediaPlayer.setLooping(true);
			mediaPlayer.prepare();
			mediaPlayer.start();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void playBackgroundMusic() {
		try {
			mediaPlayer.start();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void stopBackgroundMusic() {
		try {
			mediaPlayer.stop();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void pauseBackgroundMusic() {
		if (mediaPlayer.isPlaying()) {
			try {
				mediaPlayer.pause();
				currentlyPlayedMusic.pause();
				lastStopped = mediaPlayer.getCurrentPosition();
			} catch (IllegalStateException e) {
				e.printStackTrace();
			}
		}
		
	}

	@Override
	public void resumeBackgroundMusic() {
		if (!mediaPlayer.isPlaying()) {
			try {
				if (lastStopped > 0) {
					mediaPlayer.seekTo(lastStopped);
				}
				mediaPlayer.start();
			} catch (IllegalStateException e) {
				e.printStackTrace();
			}
		}
		
	}

	@Override
	public boolean onError(MediaPlayer mp, int what, int extra) {
		Toast.makeText(context, "music player failed", Toast.LENGTH_SHORT).show();
		if(mediaPlayer != null)
		{
			try{
				mediaPlayer.stop();
				mediaPlayer.release();
			}finally {
				mediaPlayer = null;
			}
		}
		return false;
	}

	@Override
	protected void initWorldObjectSounds() {
		Balloon.setSoundEffects(soundEffects.get("BALLOON_POP"), soundEffects.get("BALLOON_FLYHIGH"));
		Spiky.setSoundEffects(soundEffects.get("SPIKY_LAUGH"));
		
	}

}
