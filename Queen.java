// Author: William Curtis
public class Queen extends GamePiece {
    private static final String TYPE = "Queen";

    public Queen(String colour) {
        super(colour);
    }

    public String getType() {
        return this.TYPE;
    }
}
