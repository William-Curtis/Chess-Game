// Author: William Curtis
public class King extends GamePiece {
    private static final String TYPE = "King";
    private boolean firstMove;

    public King(String colour) {
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