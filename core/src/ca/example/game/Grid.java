package ca.example.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

import ca.example.ConnectGame;

public class Grid {

//    public static int PADDING = 20;
    public static int PADDING = 0;
    public static int SIZE = ConnectGame.WIDTH - PADDING * 2;
    private final int SCORE_MULTIPLIER = 10;

//    private Color bgColor  = new Color(0, 0, 0, 1);
    private Color bgColor  = Color.LIGHT_GRAY;
    private Texture texture;

    private Cell[][] grid;
    private static int numRows;
    private static int numCols;

    private float x;
    private float y;
    private float xOffset = 0;
    private float yOffset = 0;

    private int clickedRow;
    private int clickedCol;
    private Cell clickedCell;
    private ArrayList<Cell> selectedGroup;
    private Random ran = new Random();

    private BitmapFont font;
    private boolean displayText = false;
    private String text = "";
    private Vector2 textLocation = new Vector2(0f, 0f);
    private float displayTime = Gdx.graphics.getDeltaTime();
    private int score = 0;

    private int removeCount = 0;

    private float lastAction = 0;
    private float time = 0;
    private float hint_display = 0;

    private boolean search_pattern = false;

    private ArrayList<Cell> searchedGroup;
    private Cell.CellType searchedType;
    private ArrayList<Cell> nonMatchable;

    DecimalFormat df = new DecimalFormat();

    private Texture cell_background = new Texture("white.png");

    public Grid(int[][] types) {
        nonMatchable = new ArrayList<Cell>();
        df.setMaximumFractionDigits(1);

        font = new BitmapFont();
        font.setColor(Color.WHITE);
        font.setColor(Color.PURPLE);
        font.getData().setScale(2);

        numRows = types.length;
        numCols = types[0].length;
        grid = new Cell[numRows][numCols];

        selectedGroup = new ArrayList<Cell>();
        searchedGroup = new ArrayList<Cell>();

        SIZE = (ConnectGame.WIDTH > ConnectGame.HEIGHT ? ConnectGame.HEIGHT : ConnectGame.WIDTH) - PADDING * 2;

//        /*if(ConnectGame.WIDTH > ConnectGame.HEIGHT && numRows > numCols) {
//            Cell.SIZE = ConnectGame.HEIGHT / numRows;
//        } else if(ConnectGame.WIDTH < ConnectGame.HEIGHT && numRows < numCols) {
//            Cell.SIZE = ConnectGame.WIDTH / numCols;
//        } else if(ConnectGame.WIDTH < ConnectGame.HEIGHT && numRows > numCols) {
//            Cell.SIZE = ConnectGame.HEIGHT / numRows;
//        } else if(ConnectGame.WIDTH > ConnectGame.HEIGHT && numRows < numCols) {
//            Cell.SIZE = ConnectGame.WIDTH / numCols;
//        } else if(ConnectGame.WIDTH == ConnectGame.HEIGHT && numRows > numCols) {
//            Cell.SIZE = ConnectGame.WIDTH / numRows;
//        } else if(ConnectGame.WIDTH == ConnectGame.HEIGHT && numRows < numCols) {
//            Cell.SIZE = ConnectGame.WIDTH / numCols;
//        } else if(ConnectGame.WIDTH > ConnectGame.HEIGHT && numRows == numCols) {
//            Cell.SIZE = ConnectGame.HEIGHT / numRows;
//        } else if(ConnectGame.WIDTH == ConnectGame.HEIGHT && numRows < numCols) {
//            Cell.SIZE = ConnectGame.WIDTH / numCols;
//        } else if(ConnectGame.WIDTH == ConnectGame.HEIGHT && numRows == numCols) {
//            Cell.SIZE = ConnectGame.WIDTH / numRows;
//        }

//        Cell.SIZE = (SIZE / numRows);

//        x = ConnectGame.WIDTH / 2;
//        y = ConnectGame.HEIGHT / 2;

//        Cell.SIZE = (SIZE / numRows);
        Cell.SIZE = (SIZE / (numRows > numCols ? numRows : numCols));

        x = PADDING;
        y = (ConnectGame.HEIGHT - SIZE) / 2;

        if(numRows > numCols) {
            xOffset = (numRows - numCols) * Cell.SIZE / 2;
        } else if(numCols > numRows) {
            yOffset = (numCols - numRows) * Cell.SIZE / 2;
        }

        x += xOffset;
        y += yOffset;

        System.out.println(SIZE);
        System.out.println(Cell.SIZE);
        System.out.println("X: " + x + ", Y: " + y);

        for(int row = 0; row < numRows; row++) {
            for(int col = 0; col < numCols; col++) {
                Cell cell = new Cell(types[row][col], x + col * Cell.SIZE, y + row * Cell.SIZE, row, col);
                grid[row][col] = cell;
            }
        }

        texture = new Texture("white.png");
    }

    public static int getRows() {
        return numRows;
    }

    public static int getCols() {
        return numCols;
    }

    public void click(float mx, float my) {
        boolean cellClicked = false;
        displayText = false;
//
        // Sorting
//        Collections.sort(selectedGroup, new Comparator<Cell>() {
//            public int compare(Cell cell1, Cell cell2)
//            {
//                return  cell1.getObject().compareTo(cell2.getObject());
//            }
//        });

        clearSearch();

        for(int row = 0; row < numRows; row++) {
            for(int col = 0; col < numCols; col++) {
                if(grid[row][col].contains(mx, my)) {
                    clickedRow = row;
                    clickedCol = col;

//                    if(selectedGroup.contains(grid[row][col])) {
//                        // Double clicked
//                        for(Cell cell: selectedGroup) {
////                            Cell cell = grid[row][col];
//                            cell.setDestroy(true);
//                            if (cell.getRow() - 1 > 0) {
//                                Cell above = grid[cell.getRow() - 1][cell.getCol()];
////                                if (!(above.getCellType()).equals(above.getCellType())) {
//                                    above.setDestination(
//                                            x + cell.getCol() * Cell.SIZE,
//                                            y + (numRows - cell.getRow() - 1) * Cell.SIZE
//                                    );
//                                    above.setRow(cell.getRow());
////                                }
////                                cell.setDestination(0, 0);
////                                System.out.println(above.getObject());
//                                cell.setRow(cell.getRow() - 1);
//                            }
//
//                        }
//                        for(Cell cell: selectedGroup) {
//                            cell.setSelected(false);
//                            cell.setSearched(false);
//                        }
//
//                        selectedGroup.clear();
//                    } else {
                        for(Cell cell: selectedGroup) {
                            cell.setSelected(false);
                            cell.setSearched(false);
                        }
                        selectedGroup.clear();

                        clickedCell = grid[row][col];
                        addGroup(clickedCell);
                        if(selectedGroup.size() > 1) {
                            displayTime = 0;
//                            font.setColor(Color.WHITE);
                            displayText = true;
                            if(numCols - 1 == col) {
                                textLocation.set(mx - Cell.SIZE * 2, my);
                            } else {
                                textLocation.set(mx, my);
                            }
                            score += selectedGroup.size() * SCORE_MULTIPLIER;
                            text = "" + selectedGroup.size() * SCORE_MULTIPLIER;

                            for(Cell cell: selectedGroup) {
                                cell.setDestroy(true);
//                                search_pattern = true;
                            }
                        }
//                    }
                    cellClicked = true;
                    lastAction = 0;
                    break;
                }
            }
        }

        if(!cellClicked) {
            clickedCell = null;
        }
    }

    public void move(int dx, int dy) {
        if(clickedCell == null) {
            return;
        }

        if(dx > 0) {
            // left to right
            if(clickedCol < numCols - 1) {
                Cell temp = grid[clickedRow][clickedCol];
                if(!temp.getRemoved()&& !grid[clickedRow][clickedCol + 1].getRemoved()) {
                    grid[clickedRow][clickedCol] = grid[clickedRow][clickedCol + 1];
                    grid[clickedRow][clickedCol].setCol(clickedCol);
                    grid[clickedRow][clickedCol + 1] = temp;
                    grid[clickedRow][clickedCol + 1].setCol(clickedCol + 1);

                    grid[clickedRow][clickedCol].setDestination(
                            x + clickedCol * Cell.SIZE,
                            y + (clickedRow) * Cell.SIZE);
                    grid[clickedRow][clickedCol + 1].setDestination(
                            x + (clickedCol + 1) * Cell.SIZE,
                            y + (clickedRow) * Cell.SIZE);
                }
                clickedCell = null;
            }
        } else if(dx < 0) {
            // right to left
            if(clickedCol > 0) {
                Cell temp = grid[clickedRow][clickedCol];
                if(!temp.getRemoved()&& !grid[clickedRow][clickedCol - 1].getRemoved()) {
                    grid[clickedRow][clickedCol] = grid[clickedRow][clickedCol - 1];
                    grid[clickedRow][clickedCol].setCol(clickedCol);
                    grid[clickedRow][clickedCol - 1] = temp;
                    grid[clickedRow][clickedCol - 1].setCol(clickedCol - 1);

                    grid[clickedRow][clickedCol].setDestination(
                            x + clickedCol * Cell.SIZE,
                            y + (clickedRow) * Cell.SIZE);
                    grid[clickedRow][clickedCol - 1].setDestination(
                            x + (clickedCol - 1) * Cell.SIZE,
                            y + (clickedRow) * Cell.SIZE);
                }
                clickedCell = null;
            }
        } else if(dy > 0) {
            // down to up
            if(clickedRow < numRows - 1) {
                Cell temp = grid[clickedRow][clickedCol];
                if(!temp.getRemoved()&& !grid[clickedRow + 1][clickedCol].getRemoved()) {
                    grid[clickedRow][clickedCol] = grid[clickedRow + 1][clickedCol];
                    grid[clickedRow][clickedCol].setRow(clickedRow);
                    grid[clickedRow + 1][clickedCol] = temp;
                    grid[clickedRow + 1][clickedCol].setRow(clickedRow + 1);

                    grid[clickedRow][clickedCol].setDestination(
                            x + clickedCol * Cell.SIZE,
                            y + (clickedRow) * Cell.SIZE);
                    grid[clickedRow + 1][clickedCol].setDestination(
                            x + clickedCol * Cell.SIZE,
                            y + (clickedRow + 1) * Cell.SIZE);
                }
                clickedCell = null;
            }
        } else if(dy < 0) {
            // up to down
            if(clickedRow > 0) {
                Cell temp = grid[clickedRow][clickedCol];
                if(!temp.getRemoved()&& !grid[clickedRow - 1][clickedCol].getRemoved()) {
                    grid[clickedRow][clickedCol] = grid[clickedRow - 1][clickedCol];
                    grid[clickedRow][clickedCol].setRow(clickedRow);
                    grid[clickedRow - 1][clickedCol] = temp;
                    grid[clickedRow - 1][clickedCol].setRow(clickedRow - 1);

                    grid[clickedRow][clickedCol].setDestination(
                            x + clickedCol * Cell.SIZE,
                            y + (clickedRow) * Cell.SIZE);
                    grid[clickedRow - 1][clickedCol].setDestination(
                            x + clickedCol * Cell.SIZE,
                            y + (clickedRow - 1) * Cell.SIZE);
                }
                clickedCell = null;
            }
        }
    }

    private void removeCell(Cell cell) {
        cell.setRemoved(true);
        removeCount++;
        if(removeCount == numRows * numCols) {
            System.out.println("GAME OVER");
        } else {
            System.out.println((numRows * numCols) - removeCount + " left!");
        }


    }

    private void getAbove(Cell cell) {
        if(cell.getRow() + 1 < numRows) {
            Cell above = grid[cell.getRow() + 1][cell.getCol()];
            if(!above.getRemoved()) {
                if (!above.toDestroy()) {
                    cell.setCellType(above.getColorType());
                    cell.setSelected(false);
                    cell.setSearched(false);
                    cell.setDestroy(false);

                    cell.setPosition(
                            x + cell.getCol() * Cell.SIZE,
                            y + (cell.getRow() + 1) * Cell.SIZE);
                    cell.setDestination(
                            x + cell.getCol() * Cell.SIZE,
                            y + (cell.getRow()) * Cell.SIZE);
                }
                above.setDestroy(true);
                getAbove(above);
            } else {
                // Remove cell if above is removed
                if(!cell.getRemoved()) {
                    removeCell(cell);
                }
            }
        } else {
            // Remove top row if empty
            if(!cell.getRemoved()) {
                removeCell(cell);
            }

            // Populate top row
            /*if(cell.toDestroy()) {
                cell.setCellType(ran.nextInt(3));
                cell.setPosition(
                        x + cell.getCol() * Cell.SIZE,
                        y + (cell.getRow() + 1) * Cell.SIZE);
                cell.setDestination(
                        x + cell.getCol() * Cell.SIZE,
                        y + (cell.getRow()) * Cell.SIZE);
                cell.setSelected(false);
                cell.setSearched(false);
                cell.setDestroy(false);
            }*/
        }
    }

    private void addGroup(Cell cell) {
        if(!cell.getRemoved()) {
            cell.setSelected(true);
            cell.setSearched(true);
            searchGrid(cell);
            selectedGroup.add(cell);
        }
    }

    private void addSearch(Cell cell) {
        if(!cell.getRemoved()) {
             if (searchedType == null){
                searchedType = cell.getCellType();
            }
            if(searchedType != null) {
                if(cell.getCellType() == searchedType) {
                    cell.setSelected(true);
                    cell.setSearched(true);
                    searchedGroup.add(cell);
                    searchGrid(cell);
                }
            }
        }
    }

//    private Cell[] getGroup() {
//        Cell[] group = new Cell[selectedGroup.size()];
////        Cell[] rows = new Cell[numRows];
//
//        HashMap<Integer, ArrayList<Cell>> rowList = new HashMap<Integer, ArrayList<Cell>>();
//
////        for(Cell cell: selectedGroup) {
////            if(rowList.containsKey(cell.getRow())) {
////
////            }
////            rowList.put(cell.getRow(), )
////        }
//        return group;
//    }

    private void clearGroup() {
        selectedGroup.clear();
    }

    private void clearSearch() {
        for(Cell cell: searchedGroup) {
            if(!nonMatchable.contains(cell)) {
                cell.setSearched(false);
                cell.setSelected(false);
            }
        }

        searchedGroup.clear();
        search_pattern = false;
        searchedType = null;
    }


    private void searchGrid(Cell cell) {
        // Check vertical: down
        if(cell.getRow() + 1 <= numRows - 1) {
            Cell temp = grid[cell.getRow() + 1][cell.getCol()];
            if ((temp.getCellType()).equals(cell.getCellType())) {
                if (!temp.isSearched()) {
                    if(search_pattern) {
                        addSearch(temp);
                    } else {
                        addGroup(temp);
                    }
                }
            }
        }
        // Check horizontal: left
        if(cell.getCol() - 1 >= 0) {
            Cell temp = grid[cell.getRow()][cell.getCol() - 1];
            if ((temp.getCellType()).equals(cell.getCellType())) {
                if (!temp.isSearched()) {
                    if(search_pattern) {
                        addSearch(temp);
                    } else {
                        addGroup(temp);
                    }
                }
            }
        }
        // Check horizontal: right
        if(cell.getCol() + 1 <= numCols - 1) {
            Cell temp = grid[cell.getRow()][cell.getCol() + 1];
            if ((temp.getCellType()).equals(cell.getCellType())) {
                if (!temp.isSearched()) {
                    if(search_pattern) {
                        addSearch(temp);
                    } else {
                        addGroup(temp);
                    }
                }
            }
        }
        // Check vertical: up
        if(cell.getRow() - 1 >= 0) {
            Cell temp = grid[cell.getRow() - 1][cell.getCol()];
            if ((temp.getCellType()).equals(cell.getCellType())) {
                if (!temp.isSearched()) {
                    if(search_pattern) {
                        addSearch(temp);
                    } else {
                        addGroup(temp);
                    }
                }
            }
        }
    }

    private void moveHorizontal(Cell cell) {
        if(cell.getCol() > 0 && cell.getCol() + 1 < numCols && grid[cell.getRow()][cell.getCol() + 1].getRemoved()) {
            System.out.println("Move right");
            Cell right = grid[cell.getRow()][cell.getCol() + 1];
            right.setCellType(cell.getColorType());
            right.setRemoved(false);
            right.setPosition(
                    x + (cell.getCol() + 1) * Cell.SIZE,
                    y + cell.getRow() * Cell.SIZE);
            cell.setRemoved(true);
        } else if(cell.getCol() < numCols && cell.getCol() - 1 > 0 && grid[cell.getRow()][cell.getCol() - 1].getRemoved()) {
            System.out.println("Move left");
        }
    }

    public void update(float dt) {
        hint_display += Gdx.graphics.getDeltaTime();
        lastAction += Gdx.graphics.getDeltaTime();
        time += Gdx.graphics.getDeltaTime();

        for(int row = 0; row < numRows; row++) {
            for(int col = 0; col < numCols; col++) {
                Cell cell = grid[row][col];

                if(search_pattern) {
                    // Should search for patterns
                    if(searchedType == null) {
                        // searchGrid(cell);
                        Cell search_cell = grid[ran.nextInt(numRows)][ran.nextInt(numCols)];
                        if(!nonMatchable.contains(search_cell)) {
                            searchGrid(search_cell);

                            if(searchedGroup.size() < 2) {
                                for(Cell nonMatch: searchedGroup) {
                                    nonMatchable.add(nonMatch);
                                }

                                clearSearch();
                                search_pattern = true;
                            } else {
                                hint_display = 0;
                            }
                        }
                    } else {
                        // matching searches
                        if(hint_display > 1) {
                            // hide hint
                            clearSearch();
                            search_pattern = false;
                            lastAction = 0;
                        }
                    }
                } else {
                    if(lastAction > 3) {
                        // show hint
                        clearSearch();
                        search_pattern = true;
                    }
                }
                cell.update(dt);
            }
        }

        for(int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                Cell cell = grid[row][col];
                if (cell.toDestroy()) {
                    getAbove(cell);
                }
//                if(!cell.getRemoved()) {
//                    moveHorizontal(cell);
//                }
                cell.update(dt);
            }
        }
    }

    public void render(SpriteBatch sb) {
        sb.setColor(bgColor);
        sb.draw(texture, x - Cell.PADDING - xOffset, y - Cell.PADDING - yOffset, SIZE + Cell.PADDING * 2, SIZE + Cell.PADDING * 2);

        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
//                // Checkered Background
//                if(col % 2 == 0) {
//                    sb.setColor(Color.GRAY);
//                    if(row % 2 == 0) {
//                        sb.setColor(Color.WHITE);
//                    }
//                } else {
//                    sb.setColor(Color.WHITE);
//                    if(row % 2 == 0) {
//                        sb.setColor(Color.GRAY);
//                    }
//                }
//                // sb.draw(cell_background, x, y, SIZE, SIZE);
//                sb.draw(cell_background, x + col * Cell.SIZE, y + row * Cell.SIZE, Cell.SIZE, Cell.SIZE);

                // Draw cells
                grid[row][col].render(sb);
            }
        }

        font.setColor(Color.PURPLE);
        font.draw(sb, "SCORE: " + score, x, ConnectGame.HEIGHT - 20);

        font.setColor(Color.PURPLE);
        font.draw(sb, "TIME: " + df.format(time) + "s", x, ConnectGame.HEIGHT - 50);

        if (displayText) {
            displayTime += Gdx.graphics.getDeltaTime();
            if(displayTime < 0.5f) {
//                font.getData().setScale(1);
                font.setColor(Color.WHITE.r, Color.WHITE.g, Color.WHITE.b, 1.0f - displayTime);
                font.draw(sb, "+" + text, textLocation.x, textLocation.y + displayTime * 100);
            } else {
                displayText = false;
            }
        }
    }
}
