import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Board {

    private ArrayList<Pip> pips; // 0--23

    private Map<Color, Integer> bar;

    public Board() {
        pips = new ArrayList<>(24);
        bar = new HashMap<>();

        for (int i = 0; i < 24; i++) {
            pips.set(i, new Pip());
        }
        bar.put(Color.WHITE, 0);
        bar.put(Color.RED, 0);
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

    public void increaseBar(Color color) {
        bar.put(color, bar.get(color) + 1);
    }
}