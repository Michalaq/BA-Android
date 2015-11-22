package com.balloonadventure.androidimpl.canvasbitmapgraphics;

import gameengine.graphicengine.GraphicalResourceIdManager;

import java.io.IOException;
import java.io.InputStream;

import android.content.Context;

import com.google.gson.Gson;

public class AndroidGraphicalResourceIdManager extends GraphicalResourceIdManager{
	private static final String graphicalIdConfigFile = "graphical_config";
	private Context context;
	
	public AndroidGraphicalResourceIdManager(Context context) {
		this.context = context;
	}
	
	class ResourcesIds {
		private String[] spriteIds;
		private String[] textureIds;
	}
	
	@Override
	public void loadIds() {
		try {
			Gson g = new Gson();
			InputStream is = context.getResources().openRawResource(context.getResources().
								getIdentifier(graphicalIdConfigFile,//+bitmapExtension,
							    "raw", context.getPackageName()));
			byte[] data;
			data = new byte[is.available()];
			is.read(data); is.close();
			String jsonString = new String(data);
			ResourcesIds rld = g.fromJson(jsonString, ResourcesIds.class);
			AndroidGraphicalResourceIdManager.spriteIds = rld.spriteIds;
			AndroidGraphicalResourceIdManager.textureIds = rld.textureIds;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
