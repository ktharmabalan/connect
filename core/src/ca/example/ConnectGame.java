package ca.example;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import ca.example.state.StateManager;
import ca.example.state.PlayState;

public class ConnectGame extends ApplicationAdapter {
	public static final String TITLE = "Connect";
	public static int WIDTH = 480;
	public static int HEIGHT = 800;

	private SpriteBatch sb;
	private StateManager stateManager;

	@Override
	public void create () {
        Gdx.gl.glClearColor(0.9f, 0.9f, 0.9f, 1);

		sb = new SpriteBatch();

		stateManager = new StateManager();
		stateManager.push(new PlayState(stateManager));
	}

	@Override
	public void render () {
		stateManager.update(Gdx.graphics.getDeltaTime());
		stateManager.render(sb);
	}
}
