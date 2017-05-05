package ca.example.game;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

import ca.example.ConnectGame;

public class Grid {

    public static int PADDING = 20;
    public static int SIZE = ConnectGame.WIDTH - PADDING * 2;

    private Color bgColor  = new Color(0, 0, 0, 1);
    private Texture texture;

    private Cell[][] grid;
    private int numRows;
    private int numCols;

    private float x;
    private float y;

    private int clickedRow;
    private int clickedCol;
    private Cell clickedCell;
    private ArrayList<Cell> selectedGroup;

    public Grid(int[][] types) {
        numRows = types.length;
        numCols = types[0].length;
        grid = new Cell[numRows][numCols];

        selectedGroup = new ArrayList<Cell>();

        Cell.SIZE = (SIZE / numRows);

        x = PADDING;
        y = (ConnectGame.HEIGHT - SIZE) / 2;

        for(int row = 0; row < numRows; row++) {
            for(int col = 0; col < numCols; col++) {
                Cell cell = new Cell(types[row][col], x + col * Cell.SIZE, y + row * Cell.SIZE, row, col);
//                Cell cell = new Cell(types[row][col], x + col * Cell.SIZE, y + (numRows - row - 1) * Cell.SIZE, row, col);
                grid[row][col] = cell;
//                System.out.println("ROW: " + row + ", COL: " + col);
            }
        }

        texture = new Texture("white.png");
    }

    public void click(float mx, float my) {
        boolean cellClicked = false;
//
        // Sorting
//        Collections.sort(selectedGroup, new Comparator<Cell>() {
//            public int compare(Cell cell1, Cell cell2)
//            {
//                return  cell1.getObject().compareTo(cell2.getObject());
//            }
//        });

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
                            for(Cell cell: selectedGroup) {
                                cell.setDestroy(true);
                            }
                        }
//                    }
                    cellClicked = true;
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
            if(clickedCol < numCols - 1) {
                Cell temp = grid[clickedRow][clickedCol];
                grid[clickedRow][clickedCol] = grid[clickedRow][clickedCol + 1];
                grid[clickedRow][clickedCol].setCol(clickedCol);
                grid[clickedRow][clickedCol + 1] = temp;
                grid[clickedRow][clickedCol + 1].setCol(clickedCol + 1);

                grid[clickedRow][clickedCol].setDestination(
                        x + clickedCol * Cell.SIZE,
                        y + (numRows - clickedRow - 1) * Cell.SIZE);
                grid[clickedRow][clickedCol + 1].setDestination(
                        x + (clickedCol + 1) * Cell.SIZE,
                        y + (numRows - clickedRow - 1) * Cell.SIZE);

                clickedCell = null;
            }
        } else if(dx < 0) {
            if(clickedCol > 0) {
                Cell temp = grid[clickedRow][clickedCol];
                grid[clickedRow][clickedCol] = grid[clickedRow][clickedCol - 1];
                grid[clickedRow][clickedCol].setCol(clickedCol);
                grid[clickedRow][clickedCol - 1] = temp;
                grid[clickedRow][clickedCol - 1].setCol(clickedCol - 1);

                grid[clickedRow][clickedCol].setDestination(
                        x + clickedCol * Cell.SIZE,
                        y + (numRows - clickedRow - 1) * Cell.SIZE);
                grid[clickedRow][clickedCol - 1].setDestination(
                        x + (clickedCol - 1) * Cell.SIZE,
                        y + (numRows - clickedRow - 1) * Cell.SIZE);
                clickedCell = null;
            }
        } else if(dy > 0) {
            if(clickedRow > 0) {
                Cell temp = grid[clickedRow][clickedCol];
                grid[clickedRow][clickedCol] = grid[clickedRow - 1][clickedCol];
                grid[clickedRow][clickedCol].setRow(clickedRow);
                grid[clickedRow - 1][clickedCol] = temp;
                grid[clickedRow - 1][clickedCol].setRow(clickedRow - 1);

                grid[clickedRow][clickedCol].setDestination(
                        x + clickedCol * Cell.SIZE,
                        y + (numRows - clickedRow - 1) * Cell.SIZE);
                grid[clickedRow - 1][clickedCol].setDestination(
                        x + clickedCol * Cell.SIZE,
                        y + (numRows - clickedRow) * Cell.SIZE);
                clickedCell = null;
            }
        } else if(dy < 0) {
            if(clickedRow < numRows - 1) {
                Cell temp = grid[clickedRow][clickedCol];
                grid[clickedRow][clickedCol] = grid[clickedRow + 1][clickedCol];
                grid[clickedRow][clickedCol].setRow(clickedRow);
                grid[clickedRow + 1][clickedCol] = temp;
                grid[clickedRow + 1][clickedCol].setRow(clickedRow + 1);

                grid[clickedRow][clickedCol].setDestination(
                        x + clickedCol * Cell.SIZE,
                        y + (numRows - clickedRow - 1) * Cell.SIZE);
                grid[clickedRow + 1][clickedCol].setDestination(
                        x + clickedCol * Cell.SIZE,
                        y + (numRows - clickedRow - 2) * Cell.SIZE);
                clickedCell = null;
            }
        }
    }

    private void getAbove(Cell cell) {
        if(cell.getRow() + 1 < numRows) {
            Cell above = grid[cell.getRow() + 1][cell.getCol()];
            grid[cell.getRow() + 1][cell.getCol()] = cell;
            grid[cell.getRow() + 1][cell.getCol()].setRow(cell.getRow());

            cell = above;
            cell.setRow(cell.getRow());
            cell.setDestroy(false);

            if(grid[cell.getRow()][cell.getCol()].toDestroy()) {
                if(cell.getRow() + 1 < numRows) {
                    getAbove(grid[cell.getRow() + 1][cell.getCol()]);
                }
            }
//                grid[cell.getRow()][cell.getCol()].setDestination(
//                        x + clickedCol * Cell.SIZE,
//                        y + (numRows - clickedRow - 1) * Cell.SIZE);
//                grid[cell.getRow()][cell.getCol() + 1].setDestination(
//                        x + (clickedCol + 1) * Cell.SIZE,
//                        y + (numRows - clickedRow - 1) * Cell.SIZE);
        }
    }

    public void update(float dt) {
        for(int row = 0; row < numRows; row++) {
            for(int col = 0; col < numCols; col++) {
                Cell cell = grid[row][col];
                cell.update(dt);
            }
        }

        for(int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                Cell cell = grid[row][col];
                if (cell.toDestroy()) {
//                    if (cell.getRow() + 1 < numRows) {
                        getAbove(cell);
//                        System.out.println(cell.getObject());
//                    }
                }
                cell.update(dt);
            }
        }
    }




    public void render(SpriteBatch sb) {
        sb.setColor(bgColor);
        sb.draw(texture, x - Cell.PADDING, y - Cell.PADDING, SIZE + Cell.PADDING * 2, SIZE + Cell.PADDING * 2);

        for(int row = 0; row < numRows; row++) {
            for(int col = 0; col < numCols; col++) {
                grid[row][col].render(sb);
            }
        }
    }

    private void addGroup(Cell cell) {
//        System.out.println(cell.getRow() + "," +
        cell.setSelected(true);
        cell.setSearched(true);
        searchGrid(cell);
//        System.out.println("Searching: " + cell.getObject());
        selectedGroup.add(cell);
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

    private void searchGrid(Cell cell) {
        // Check vertical: down
        if(cell.getRow() + 1 <= numRows - 1) {
            Cell temp = grid[cell.getRow() + 1][cell.getCol()];
            if ((temp.getCellType()).equals(cell.getCellType())) {
                if (!temp.isSearched()) {
                    addGroup(temp);
                }
            }
        }
        // Check horizontal: left
        if(cell.getCol() - 1 >= 0) {
            Cell temp = grid[cell.getRow()][cell.getCol() - 1];
            if ((temp.getCellType()).equals(cell.getCellType())) {
                if (!temp.isSearched()) {
                    addGroup(temp);
                }
            }
        }
        // Check horizontal: right
        if(cell.getCol() + 1 <= numCols - 1) {
            Cell temp = grid[cell.getRow()][cell.getCol() + 1];
            if ((temp.getCellType()).equals(cell.getCellType())) {
                if (!temp.isSearched()) {
                    addGroup(temp);
                }
            }
        }
        // Check vertical: up
        if(cell.getRow() - 1 >= 0) {
            Cell temp = grid[cell.getRow() - 1][cell.getCol()];
            if ((temp.getCellType()).equals(cell.getCellType())) {
                if (!temp.isSearched()) {
                    addGroup(temp);
                }
            }
        }
    }
}