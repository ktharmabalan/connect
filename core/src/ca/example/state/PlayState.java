package ca.example.state;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Random;

import ca.example.game.Grid;

public class PlayState extends State {

    private final int DRAG_DIST = 50;

    private Grid grid;

    private float startx;
    private float starty;

    public PlayState(StateManager stateManager) {
        super(stateManager);

        int rows = 5;
        int cols = 5;

        int[][] g = new int[rows][cols];

        Random ran = new Random();

        for(int r = 0; r < rows; r++) {
            for(int c = 0; c < cols; c++) {
                int i = ran.nextInt(3);
//                System.out.println(i);
                g[r][c] = i;
            }
        }
        grid = new Grid(g);
    }

    public void update(float dt) {
        if(Gdx.input.justTouched()) {
            mouse.x = Gdx.input.getX();
            mouse.y = Gdx.input.getY();
            cam.unproject(mouse);
            grid.click(mouse.x, mouse.y);
            startx = mouse.x;
            starty = mouse.y;
        } else if(Gdx.input.isTouched()) {
            mouse.x = Gdx.input.getX();
            mouse.y = Gdx.input.getY();
            cam.unproject(mouse);
            if(mouse.x > startx + DRAG_DIST) {
                grid.move(1, 0);
            } else if(mouse.x < startx - DRAG_DIST) {
                grid.move(-1, 0);
            } else if(mouse.y > starty + DRAG_DIST) {
                grid.move(0, 1);
            } else if(mouse.y < starty - DRAG_DIST) {
                grid.move(0, -1);
            }
        }

        grid.update(dt);
    }

    public void render(SpriteBatch sb) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        grid.render(sb);
        sb.end();
    }
}
