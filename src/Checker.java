public class Checker {

    private Color color;

    public Checker(Color c) {
        color = c;
    }


    // Prints the color of the checker instead of memory address
    @Override
    public String toString() {
        return color.toString();
    }

    public Color getColor() {
        return color;
    }
}
