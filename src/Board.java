import java.util.ArrayList;

public class Board {


    private ArrayList<Pip> pips = new ArrayList<>(24); // 0--23


    private int redBar;
    private int whiteBar;

    public Board() {
        for (int i = 0; i < 24; i++) {
            pips.set(i, new Pip());
        }
    }


    public int getPipIndex(Pip pip) {
        return pips.indexOf(pip);
    }

    public ArrayList<Pip> getPips() {
        return pips;
    }

    public Pip getPip(int index) {
        return pips.get(index);
    }
}


