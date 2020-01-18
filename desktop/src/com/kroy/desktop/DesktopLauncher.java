package com.kroy.desktop;

// Constants import
import static com.config.Constants.GAME_NAME;
import static com.config.Constants.SCREEN_HEIGHT;
import static com.config.Constants.SCREEN_WIDTH;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.kroy.Kroy;

/**
 * DesktopLauncher starts the Kroy application on desktop, as required by
 * libGDX. This is the entry point to the application.
 * @author Archie
 * @since 04/11/2019
 */
public class DesktopLauncher {
	/**
	 * The entry point to the Kroy application, taking configuration information
	 * and calling LwjglApplication - the main libGDX instance.
	 * 
	 * @param arg 	Arguments needed to run the game
	 */
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = GAME_NAME;
		config.width = SCREEN_WIDTH;
		config.height = SCREEN_HEIGHT;
		new LwjglApplication(new Kroy(), config);
	}
}
