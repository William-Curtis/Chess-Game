// Author: William Curtis
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GridPanel extends JPanel {
    private int row;
    private int col;
    private GamePiece Piece = null;
    private JLabel label;
    private Color backColor;

    public GridPanel(Color backColor, int row, int col, GamePiece Piece) {
        this.backColor = backColor;
        this.setBackground(backColor);
        this.setPreferredSize(new Dimension(100, 100));
        this.setBorder(BorderFactory.createLineBorder(Color.black));
        label = new JLabel();
        this.add(label);

        this.row = row;
        this.col = col;
        if (Piece != null) {
            this.Piece = Piece;
        }

        addMouseListener(new MoveListener());
    }

    public GamePiece getPiece() {
        return Piece;
    }

    public void setPiece(GamePiece Piece) {
        this.Piece = Piece;
    }

    public void selectTile() {
        this.setBackground(Color.YELLOW);
    }

    public void unselectTile() {
        this.setBackground(this.backColor);
    }

    public void updateIcon() {
        /*
         * Updates the displayed Icon of this tile
         */
        ImageIcon icon;
        if (this.Piece == null) {
            icon = new ImageIcon();
        }
        else if (this.Piece.getType().equals("Pawn")) {
            if (this.Piece.getColour().equals("White")) {
                icon = new ImageIcon("Icons\\White Pawn.png");
            }
            else {
                icon = new ImageIcon("Icons\\Black Pawn.png");
            }
        }
        else if (this.Piece.getType().equals("Rook")) {
            if (this.Piece.getColour().equals("White")) {
                icon = new ImageIcon("Icons\\White Rook.png");
            }
            else {
                icon = new ImageIcon("Icons\\Black Rook.png");
            }
        }
        else if (this.Piece.getType().equals("Knight")) {
            if (this.Piece.getColour().equals("White")) {
                icon = new ImageIcon("Icons\\White Knight.png");
            }
            else {
                icon = new ImageIcon("Icons\\Black Knight.png");
            }
        }
        else if (this.Piece.getType().equals("Bishop")) {
            if (this.Piece.getColour().equals("White")) {
                icon = new ImageIcon("Icons\\White Bishop.png");
            }
            else {
                icon = new ImageIcon("Icons\\Black Bishop.png");
            }
        }
        else if (this.Piece.getType().equals("Queen")) {
            if (this.Piece.getColour().equals("White")) {
                icon = new ImageIcon("Icons\\White Queen.png");
            }
            else {
                icon = new ImageIcon("Icons\\Black Queen.png");
            }
        }
        else if (this.Piece.getType().equals("King")) {
            if (this.Piece.getColour().equals("White")) {
                icon = new ImageIcon("Icons\\White King.png");
            }
            else {
                icon = new ImageIcon("Icons\\Black King.png");
            }
        }
        else {
            icon = new ImageIcon();
        }
        this.label.setIcon(icon);
    }

    private class MoveListener extends MouseAdapter {
        public MoveListener() {
        }

        public void mousePressed(MouseEvent e) {
            Globals.location[0] = row;
            Globals.location[1] = col;
            Globals.locationEntered = true;
            //System.out.println("Mouse clicked in square: " + row + " " + col);
        }
    }
}