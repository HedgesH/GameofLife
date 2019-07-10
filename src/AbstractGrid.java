import java.awt.*;

//creates a matrix with the positions of every cell
abstract public class AbstractGrid {

    abstract public Cell getCell(int x, int y);

    public static void drawGrid(Graphics2D g, int x, int y, int width, int height){
        int dx = width/x;
        int dy = height/y;
        for (int i = 0; i <width ; i+=dx) {
            g.drawLine(i, 0, i, height);
        }
        for (int i = 0; i <height ; i+= dy) {
            g.drawLine(0, i, width, i);
        }
    };

    public static void drawCell(Graphics2D g, int x, int y, int gridWidth, int gridHeight, int width, int height){

        int dx = (width/gridWidth) * x;
        int dy = (height/gridHeight) * y;


        Rectangle rect = new Rectangle(dx, dy, (width/gridWidth), (height/gridHeight));
        g.fill(rect);
    };

    //check if cell in given direction
    abstract public boolean checkRight(Cell cell);
    abstract public boolean checkLeft(Cell cell);
    abstract public boolean checkUp(Cell cell);
    abstract public boolean checkDown(Cell cell);
    abstract public boolean checkUpRight(Cell cell);
    abstract public boolean checkUpLeft(Cell cell);
    abstract public boolean checkDownRight(Cell cell);
    abstract public boolean checkDownLeft(Cell cell);

    abstract public int neighbours(Cell cell);
    abstract public void conditions(Cell cell, int neighbours);



}




