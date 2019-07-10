import java.util.ArrayList;

public class Grid extends AbstractGrid{

    private Cell[][] grid;
    private int gridWidth;
    private int gridHeight;
    private ArrayList<Cell> cells;



    public Grid(ArrayList<Cell> cell, int gridWidth, int gridHeight){
        grid = new Cell[gridWidth][gridHeight];
        int counter = 0;
        for (int i = 0; i < gridWidth; i++) {
            for (int j = 0; j < gridHeight ; j++) {

                grid[i][j] = cell.get(counter);
                counter++;

            }
        }
        this.gridWidth = gridWidth;
        this.gridHeight = gridHeight;
        this.cells = cell;

    }

    public Grid(Grid grid){
        this.grid = grid.getGrid();
        this.gridWidth = grid.getGridWidth();
        this.gridHeight = grid.getGridHeight();
        this.cells = grid.getCells();
    }

    public Grid(Grid grid,ArrayList<Cell> cell, int gridHeight, int gridWidth){
        this.grid = new Cell[gridWidth][gridHeight];
        ArrayList<Cell> cells = new ArrayList<>();

//        //smaller
//        if(grid.getGridWidth() > gridWidth ){
//            for (int i = gridWidth; i <grid.getGridWidth() ; i++) {
//                for (int j = gridHeight; j <grid.getGridHeight() ; j++) {
//                    cells.remove(grid.getCell(i,j));
//                }
//            }
//            for (int i = 0; i <gridWidth ; i++) {
//                for (int j = 0; j <gridHeight ; j++) {
//                    this.grid[i][j] = grid.getCell(i,j);
//                }
//            }
//        }
//        //bigger
//        else{
//            for (int i = 0; i < gridWidth; i++) {
//                for (int j = 0; j < gridHeight; j++) {
//                    if(grid.getCell(i,j) != null) this.grid[i][j] = grid.getCell(i,j);
//                    else {
//                        this.grid[i][j] = new Cell(i,j);
//                        cells.add(this.grid[i][j]);
//                    }
//                }
//            }
//        }

        for (int i = 0; i < gridWidth; i++) {
            for (int j = 0; j < gridHeight; j++) {
                if(grid.getCell(i,j) != null) this.grid[i][j] = grid.getCell(i,j);
                else this.grid[i][j] = new Cell(i,j);
                cells.add(this.grid[i][j]);
            }
        }





        this.gridWidth = gridWidth;
        this.gridHeight = gridHeight;
        this.cells = cells;
    }

    public ArrayList<Cell> getCells(){ return cells;}


    public Cell[][] getGrid() { return grid; }

    public int getGridWidth() { return gridWidth;}

    public int getGridHeight() { return gridHeight;}

    public void setGridWidth(int w) { gridWidth = w;}

    public void setGridHeight(int h) { gridHeight = h;}


    @Override
    public Cell getCell(int x, int y) {
        if(x >= gridWidth || y >= gridHeight) return null;
        return grid[x][y];
    }

    @Override
    public boolean checkUp(Cell cell) {
        int x = cell.getX();
        int y = cell.getY();
        if (y == 0) return false;
        try{return grid[x][y-1].isAlive();}
        catch (Exception e){
            throw new IllegalStateException(x + " " + y);
        }


    }

    @Override
    public boolean checkDown(Cell cell) {
        int x = cell.getX();
        int y = cell.getY();
        if (y == gridHeight - 1) return false;
        return grid[x][y+1].isAlive();
    }

    @Override
    public boolean checkRight(Cell cell) {
        int x = cell.getX();
        int y = cell.getY();
        if (x == gridWidth - 1) return false;
        return grid[x+1][y].isAlive();

    }

    @Override
    public boolean checkLeft(Cell cell){
        int x = cell.getX();
        int y = cell.getY();
        if (x == 0) return false;
        return grid[x-1][y].isAlive();
    }

    @Override
    public boolean checkUpRight(Cell cell) {
        if(cell.getY() == 0) return false;
        Cell cell2 = grid[cell.getX()][cell.getY()-1];
        return checkRight(cell2);
    }

    @Override
    public boolean checkUpLeft(Cell cell) {
        if(cell.getY() == 0) return false;
        Cell cell2 = grid[cell.getX()][cell.getY()-1];
        return checkLeft(cell2);

    }

    @Override
    public boolean checkDownRight(Cell cell) {
        if(cell.getY() == gridHeight - 1) return false;
        Cell cell2 = grid[cell.getX()][cell.getY()+1];
        return checkRight(cell2);

    }

    @Override
    public boolean checkDownLeft(Cell cell) {
        if(cell.getY() == gridHeight - 1) return false;
        Cell cell2 = grid[cell.getX()][cell.getY()+1];
        return checkLeft(cell2);
    }



    @Override
    public int neighbours(Cell cell) {
        int counter = 0;
        if(checkUp(cell)) counter++;
        if(checkDown(cell)) counter++;
        if(checkLeft(cell)) counter++;
        if(checkRight(cell)) counter++;
        if(checkUpRight(cell)) counter++;
        if(checkUpLeft(cell)) counter++;
        if(checkDownRight(cell)) counter++;
        if(checkDownLeft(cell)) counter++;
        return counter;
    }

    @Override
    public void conditions(Cell cell, int neighbours) {
        if(neighbours == 3 && !cell.isAlive()){ cell.setAlive(true); return;}
        if(neighbours < 2){ cell.setAlive(false); return;}
        if(neighbours == 2 || neighbours == 3){ return;}
        else{ cell.setAlive(false); return;}
    }

}
