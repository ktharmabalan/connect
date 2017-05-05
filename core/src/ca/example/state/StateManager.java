package ca.example.state;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.LinkedList;

public class StateManager {

    private LinkedList<State> states;

    public StateManager() {
        states = new LinkedList<State>();
    }

    public void update(float dt) {
        states.peek().update(dt);
    }

    public void render(SpriteBatch sb) {
        states.peek().render(sb);
    }

    public void pop() {
        states.pop();
    }

    public void push(State s) {
        states.push(s);
    }

    public void set(State s) {
        states.pop();
        states.push(s);
    }

}
