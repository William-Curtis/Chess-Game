// Author: William Curtis
public class Bishop extends GamePiece {
    private static final String TYPE = "Bishop";

    public Bishop(String colour) {
        super(colour);
    }

    public String getType() {
        return this.TYPE;
    }
}
