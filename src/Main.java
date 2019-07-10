import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Main extends JFrame implements KeyListener, MouseListener, MouseMotionListener {
    Surface surface;
    int mouseX;
    int mouseY;
    int prevX;
    int prevY;
    boolean mouseIn;
    ArrayList<ArrayList<Integer>> coords;



    //update

    //paint

    //dodrawing

    public Main() {
        initUI();
    }

    private void initUI() {
        surface = new Surface();
        prevX = 0;
        prevY = 0;
        mouseIn = false;
        coords = new ArrayList<>();

        add(surface);
        addKeyListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);

        setTitle("Game of life");
        setSize(Toolkit.getDefaultToolkit().getScreenSize().width/2, Toolkit.getDefaultToolkit().getScreenSize().height/2);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {

        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {

                Main ex = new Main();
                ex.setVisible(true);

            }
        });
    }

    public void cursorHover(int x, int y){
        mouseX = x;
        mouseY = y;
        surface.setMouseHover(x,y);


    }

    public void cursorRegionAdd(int x ,int y){

        if(surface.getPause()){

            int[] coords = surface.region(x,y);
            ArrayList<Integer> coords2 = new ArrayList<>();
            coords2.add(coords[0]);
            coords2.add(coords[1]);
            if(!this.coords.contains(coords2)){
                this.coords.add(coords2);
                surface.gridCoordSetAlive(coords[0],coords[1]);

            }
        }

    }



    public void cursorAdd(int x ,int y){

        if(surface.getPause()){
            int[] coords = surface.region(x,y);
            prevX = coords[0];
            prevY = coords[1];
            surface.gridCoordSetAlive(prevX,prevY);
        }

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {



    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_E ) surface.counterPlus();
        else if(e.getKeyCode() == KeyEvent.VK_Q) surface.counterSub();
        else if(e.getKeyCode() == KeyEvent.VK_D) surface.increasesize();
        else if(e.getKeyCode() == KeyEvent.VK_A) surface.decreasesize();
        else if(e.getKeyCode() == KeyEvent.VK_R) surface.reset();

        else surface.pause();

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        mouseIn = true;


    }

    @Override
    public void mousePressed(MouseEvent e) { }

    @Override
    public void mouseReleased(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
        if(coords.isEmpty()) cursorAdd(mouseX,mouseY);
        coords.clear();

    }

    @Override
    public void mouseEntered(MouseEvent e) { }

    @Override
    public void mouseExited(MouseEvent e) { }

    @Override
    public void mouseDragged(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
        //cursorRegionAdd(mouseX,mouseY);
        cursorRegionAdd(mouseX,mouseY);
        cursorHover(mouseX,mouseY);


    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
        cursorHover(mouseX,mouseY);


    }
}
