public class Checker {

    private Color color;

    private Pip position;

    public Checker(Color c, Pip pos) {
        color = c;
        position = pos;
    }

    // Prints the color of the checker instead of memory address
    @Override
    public String toString() {
        return color.toString();
    }

    public Color getColor() {
        return color;
    }

    public Pip getPosition() {
        return position;
    }

    public int getDirection() {
        if (color == Color.WHITE) {
            return 1;
        }
        return -1; // Direction for red
    }
}