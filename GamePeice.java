// Author: William Curtis
abstract class GamePiece {
    private String colour;

    public GamePiece(String colour) {
        this.colour = colour;
    }

    abstract public String getType();

    public String getColour() {
        return colour;
    }
}