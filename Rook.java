// Author: William Curtis
public class Rook extends GamePiece {
    private static final String TYPE = "Rook";
    private boolean firstMove;

    public Rook(String colour) {
        super(colour);
        this.firstMove = true;
    }

    public String getType() {
        return this.TYPE;
    }

    public boolean isFirstMove() {
        return this.firstMove;
    }

    public void hasMoved() {
        this.firstMove = false;
    }
}
