// Author: William Curtis
import javax.swing.JLabel;

public class Globals {
    // Graphic Constants
    public static final int ROWS = 8;
    public static final int COLS = 8;
    public static final int CHOICE_ROWS = 1;
    public static final int CHOICE_COLS = 4;
    
    // Game Variables
    public static boolean gameOver = false;
    public static GridPanel[][] grid = new GridPanel[ROWS][COLS];
    public static GridPanel[][] choiceGrid = new GridPanel[CHOICE_ROWS][CHOICE_COLS];
    public static JLabel status = new JLabel("Game Status: White's Turn");
    public static int[] location = new int[2];
    public static boolean locationEntered = false;
}