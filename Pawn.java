// Author: William Curtis
public class Pawn extends GamePiece {
    private static final String TYPE = "Pawn";
    private boolean firstMove;
    private boolean enPassantable;

    public Pawn(String colour) {
        super(colour);
        this.firstMove = true;
        this.enPassantable = false;
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

    public void setEnPassantable(boolean bool) {
        this.enPassantable = bool;
    }
    
    public boolean isEnPassantable() {
        return this.enPassantable;
    }
}