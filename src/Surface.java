import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class Surface extends JPanel implements ActionListener {
    private Timer timer;
    final int defaultGridWidth = 25;
    final int defaultGridHeight = 25;
    final int defaultgameLoopcount = 30;

    private int gridWidth;
    private int gridHeight;
    private int gameLoopCount;
    private int gameLoopCounter;
    private int pixelWidth;
    private int pixelHeight;

    ArrayList<Cell> cells;
    Grid grid;

    private boolean pause;
    private boolean drawGrid;
    private int mouseHoverX;
    private int mouseHoverY;




    public Surface(){
        super();
        mouseHoverX = 0;
        mouseHoverY = 0;
        gameLoopCounter = 0;
        gridHeight = defaultGridHeight;
        gridWidth = defaultGridWidth;
        gameLoopCount = defaultgameLoopcount;

        initTimer();
        initUI();
        cells = new ArrayList<>();
        for (int i = 0; i < gridWidth; i++) {
            for (int j = 0; j < gridHeight ; j++) {
                Cell cell = new Cell(i,j);
                this.cells.add(cell);

            }
        }
        this.grid = new Grid(cells,gridWidth,gridHeight);
        drawGrid = true;
        this.pixelHeight = getHeight();
        this.pixelWidth = getWidth();




    }

    public void gameloop(){

        for (int i = 0; i < gridHeight; i++) {
            for (int j = 0; j < gridWidth ; j++) {
                Cell cell = grid.getGrid()[i][j];
                cell.setNeighbours(grid.neighbours(cell));
            }
        }
        for (int i = 0; i < gridHeight; i++) {
            for (int j = 0; j < gridWidth ; j++) {
                Cell cell = grid.getGrid()[i][j];
                grid.conditions(cell,cell.getNeighbours());
            }
        }

    };

    public void pause(){pause = !pause;}

    private void initTimer() {
        timer = new Timer(17, this);
        timer.start();

    }

    private void initUI(){
        pause = true;
    }

    private void doDrawing(Graphics2D g2d){
        pixelWidth = getWidth();
        pixelHeight = getHeight();
        if(drawGrid) Grid.drawGrid(g2d,gridWidth,gridHeight,pixelWidth,pixelHeight);

        for (int i = 0; i < gridWidth ; i++) {
            for (int j = 0; j < gridHeight; j++) {

                Cell cell = grid.getCell(i,j);

                if((drawMouseHover() == cell)){
                    Color current = g2d.getColor();
                    g2d.setPaint(new Color(191, 0, 139));
                    Grid.drawCell(g2d,cell.getX(),cell.getY(),gridWidth,gridHeight,pixelWidth,pixelHeight);
                    g2d.setPaint(current);
                }
                else if(cell.isAlive()){
                    Color current = g2d.getColor();
                    int neighbours = grid.neighbours(cell);
                    g2d.setPaint(new Color((19*(neighbours) % 155) + 100, 0, 0));
                    Grid.drawCell(g2d,cell.getX(),cell.getY(),gridWidth,gridHeight,pixelWidth,pixelHeight);
                    g2d.setPaint(current);

                }

            }

        }




    };

    @Override
    public void paintComponent(Graphics g){

        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        doDrawing(g2d);
        g2d.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

       // System.out.println(pixelWidth + " " + pixelHeight);
        repaint();
        if(!pause){
            gameLoopCounter++;
            System.out.println(gameLoopCounter);
            if(gameLoopCount == gameLoopCounter){
                gameloop();
                gameLoopCounter = 0;
            }
        }
    }

    public int[] region(int x, int y){
        int x2 = (x * gridWidth)/pixelWidth;
        int y2 = (y * gridHeight)/pixelHeight;
        if(y2 != 0) y2 -= 1;
      //  System.out.println(x2 + " " + y2);
        int[] temp = {x2,y2};
        return temp;
    }

    public void makeAlive(int x , int y){

        int[] coords = region(x,y);
        int xgrid = coords[0];
        int ygrid = coords[1];
       // System.out.println(xgrid + " " + ygrid);
        Cell cell = grid.getCell(xgrid,ygrid);
        cell.setAlive(true);
        repaint();
    }

    public void gridCoordSetAlive(int x, int y, boolean dead){

        Cell cell = grid.getCell(x,y);
        cell.setAlive(dead);
        repaint();



    }

    public void gridCoordSetAlive(int x, int y){

        Cell cell = grid.getCell(x,y);
        cell.setAlive(!cell.isAlive());
        repaint();



    }

    public void drawGrid(){drawGrid = !drawGrid;}

    public boolean newRegion(int prevx, int prevy, int x , int y){
        int[] coords = region(x,y);
        if(prevx != coords[0] || prevy != coords[1]) return true;
        return false;
    }

    public boolean getPause(){return pause;}

    public Cell drawMouseHover(){
        int[] coords = region(mouseHoverX,mouseHoverY);
        return grid.getCell(coords[0],coords[1]);

    }

    public void setMouseHover(int x, int y ){
        mouseHoverX = x;
        mouseHoverY = y;
    }

    public void counterPlus(){
        if (gameLoopCount < 31) gameLoopCount++;
        gameLoopCounter = 0;
        System.out.println(gameLoopCount);

    }
    public void counterSub(){
        if (gameLoopCount > 5) gameLoopCount--;
        gameLoopCounter = 0;
        System.out.println(gameLoopCount);

    }

    public void increasesize(){
        gridWidth++;
        gridHeight++;
        grid = new Grid(grid,cells,gridHeight,gridWidth);
        cells = grid.getCells();
    }

    public void decreasesize(){
        gridWidth--;
        gridHeight--;
        //grid = new Grid(grid,cells,gridHeight,gridWidth);
        //cells = grid.getCells();
//q
    }

    public void reset(){
        gridWidth = defaultGridWidth;
        gridHeight = defaultGridHeight;
        gameLoopCount = defaultgameLoopcount;
        for (Cell cell:cells) {
            cell.setAlive(false);

        }
        pause = true;

    }









}
