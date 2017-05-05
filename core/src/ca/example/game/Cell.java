package ca.example.game;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Cell {
    public enum CellType {
        RED(Color.RED),
        GREEN(Color.GREEN),
        BLUE(Color.BLUE);
        Color color;
        Color selectedColor;
        Color destroyColor;

        private CellType(Color color) {
            this.color = color;
            selectedColor = new Color(color.r, color.g, color.b, .5f);
            destroyColor = new Color(0, 0, 0, 0);
        }

        public Color getColor() {
            return color;
        }

        public Color getSelectedColor() {
            return selectedColor;
        }

        public Color getDestroyColor() {
            return destroyColor;
        }
    }

    public static CellType[] cellTypeValues = CellType.values();

    public static int SIZE;
    public static int PADDING = 10;

    private CellType cellType;
    private Texture texture;

    private float x;
    private float y;
    private float xdest;
    private float ydest;
    private float dx;
    private float dy;

    private int row;
    private int col;

    private boolean searched;
    private boolean selected;
    private boolean destroy;

    private static float speed = 2000;

    public Cell(int type, float x, float y) {
        cellType = cellTypeValues[type];
        texture = new Texture("white.png");
        this.x = x;
        this.y = y;
        selected = false;
        searched = false;
    }

    public Cell(int type, float x, float y, int row, int col) {
        cellType = cellTypeValues[type];
        texture = new Texture("white.png");
        this.x = x;
        this.y = y;
        selected = false;
        this.row = row;
        this.col = col;
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void setDestination(float xdest, float ydest) {
        this.xdest = xdest;
        this.ydest = ydest;
        dx = xdest - x;
        dy = ydest - y;
        float dist = (float) Math.sqrt(dx * dx + dy * dy);
        dx /= dist;
        dy /= dist;
    }

    public boolean contains(float mx, float my) {
        return mx > x && mx < x + SIZE && my > y && my < y + SIZE;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isSelected() {
        return selected;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public void setSearched(boolean searched) {
        this.searched = searched;
    }

    public boolean isSearched() {
        return searched;
    }

    public CellType getCellType() {
        return cellType;
    }

    public void render(SpriteBatch sb) {
        if(toDestroy()) {
            sb.setColor(cellType.getDestroyColor());
            sb.draw(texture, x, y, SIZE, SIZE);
        } else {
            sb.setColor(cellType.getColor());
            sb.draw(texture, x + PADDING, y + PADDING, SIZE - PADDING * 2, SIZE - PADDING * 2);
            if (isSelected()) {
                sb.setColor(cellType.getSelectedColor());
                sb.draw(texture, x, y, SIZE, SIZE);
            }
        }
    }

    public String getObject() {
        return row + "," + col;
    }

    public void setDestroy(boolean destroy) {
        this.destroy = destroy;
    }

    public boolean toDestroy() {
        return destroy;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public void update(float dt) {
        x += dx * speed * dt;
        y += dy * speed * dt;

        if((dx < 0 && x <= xdest) || (dx > 0 && x >= xdest)) {
            dx = 0;
            x = xdest;
        }

        if((dy < 0 && y <= ydest) || (dy > 0 && y >= ydest)) {
            dy = 0;
            y = ydest;
        }
    }
}
