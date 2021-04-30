import java.util.Stack;

public class Pip {

    private Stack<Checker> checkerStack;

    public Pip() {
        checkerStack = new Stack<Checker>();
    }

    public void addChecker(Checker checker) {
        checkerStack.push(checker);
    }

    public Checker removeChecker() {
        return checkerStack.pop();
    }

    public boolean isOccupied() {
        return !checkerStack.isEmpty();
    }

    public boolean isStacked() {
        return checkerStack.size() > 1;
    }

    public Color getColor() {
        if (checkerStack.isEmpty()) {
            return null;
        }
        return checkerStack.peek().getColor();
    }

    @Override
    public String toString() {
        return checkerStack.toString();
    }

    public static void main(String[] args) {
        Pip pip = new Pip();
        System.out.println(pip.isOccupied());
        System.out.println(pip.getColor());
        System.out.println(pip.isStacked());
        pip.addChecker(new Checker(Color.RED));
        System.out.println(pip.toString());
        System.out.println(pip.isOccupied());
        System.out.println(pip.getColor());
        System.out.println(pip.isStacked());
        pip.addChecker(new Checker(Color.RED));
        pip.addChecker(new Checker(Color.RED));
        System.out.println(pip.toString());
        System.out.println(pip.isStacked());

    }
}