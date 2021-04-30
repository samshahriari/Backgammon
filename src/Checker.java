public class Checker {

    private Color color;


    private int position;

    public Checker(Color c, int pos) {
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

    public int getPosition() {
        return position;
    }
}

