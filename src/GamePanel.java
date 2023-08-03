import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GamePanel extends JPanel implements ActionListener {

    private final int UNIT_SIZE = 10;
    private final int SCREEN_SIDE_UNITS = 90;
    private final int SCREEN_SIZE = UNIT_SIZE*SCREEN_SIDE_UNITS;
    private final int DELAY = 50;
    private boolean[][] cells = new boolean[SCREEN_SIDE_UNITS][SCREEN_SIDE_UNITS];
    Timer timer;
    GamePanel(){
        this.setPreferredSize(new Dimension(SCREEN_SIZE, SCREEN_SIZE));
        this.setLayout(null);
        this.setBackground(Color.BLACK);

        cells[41][46] = true;
        cells[42][46] = true;
        cells[42][44] = true;
        cells[44][45] = true;
        cells[45][46] = true;
        cells[46][46] = true;
        cells[47][46] = true;

        timer = new Timer(DELAY, this);
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        boolean[][] frozenBoard = deepCopy(cells);

        //paint board
        for (int i = 0; i < SCREEN_SIDE_UNITS; i++) {
            for (int j = 0; j < SCREEN_SIDE_UNITS; j++) {
                if(cells[i][j]){
                    g.setColor(Color.LIGHT_GRAY);
                    g.fillRect(UNIT_SIZE*i, UNIT_SIZE*j, UNIT_SIZE, UNIT_SIZE);
                } else{
                    g.setColor(Color.BLACK);
                    g.fillRect(UNIT_SIZE*i, UNIT_SIZE*j, UNIT_SIZE, UNIT_SIZE);
                }
            }
        }

        for (int i = 0; i < SCREEN_SIDE_UNITS; i++) {
            for (int j = 0; j < SCREEN_SIDE_UNITS; j++) {
                if(calcNeighbours(i, j, frozenBoard) < 2) {cells[i][j] = false;} //underpopulation
                else if(calcNeighbours(i, j, frozenBoard) > 3) {cells[i][j] = false;} //overpopulation
                else if(calcNeighbours(i, j, frozenBoard) == 3) {cells[i][j] = true;} //born
            }
        }
    }

    private int calcNeighbours(int x, int y, boolean[][] board){
        int neighbours = 0;
        for (int i = -1; i <= 1; i++) {
            if(x+i < SCREEN_SIDE_UNITS && x+i >= 0){
                for (int j = -1; j <= 1; j++) {
                    if(y+j < SCREEN_SIDE_UNITS && y+j >= 0 && board[x+i][y+j] == true && !(i==0 && j==0)){
                        neighbours++;
                    }
                }
            }
        }
        return neighbours;
    }

    private boolean[][] deepCopy(boolean[][] original) {
        int rows = original.length;
        int cols = original[0].length;
        boolean[][] copy = new boolean[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                copy[i][j] = original[i][j];
            }
        }
        return copy;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }
}
