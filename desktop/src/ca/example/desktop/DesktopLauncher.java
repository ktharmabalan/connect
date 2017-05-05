package ca.example.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import ca.example.ConnectGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = ConnectGame.TITLE;
		config.width = ConnectGame.WIDTH / 1;
		config.height = ConnectGame.HEIGHT / 1;
		new LwjglApplication(new ConnectGame(), config);
	}
}
