// Author: William Curtis
public class Knight extends GamePiece {
    private static final String TYPE = "Knight";

    public Knight(String colour) {
        super(colour);
    }

    public String getType() {
        return this.TYPE;
    }
}
