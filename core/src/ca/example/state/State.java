package ca.example.state;


import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

import ca.example.ConnectGame;

public abstract class State {
    protected StateManager stateManager;
    protected OrthographicCamera cam;
    protected Vector3 mouse;

    protected State(StateManager stateManager) {
        this.stateManager = stateManager;
        cam = new OrthographicCamera();
        cam.setToOrtho(false, ConnectGame.WIDTH, ConnectGame.HEIGHT);
        mouse = new Vector3();
    }

    public abstract void update(float dt);
    public abstract void render(SpriteBatch sb);
}
