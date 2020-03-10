package Game.Players;

import Game.Table.Chevalet;
import Game.Table.Table;

public class IA extends Player {
    public IA(Table table) {
        super("N", table);
    }

    public IA() {
    }

    @Override
    public Object clone() {
        IA ia = new IA();
        ia.debut=debut;
        ia.chevalet=(Chevalet) chevalet.clone();
        ia.endOfCombinaison=endOfCombinaison;
        ia.endOfturn=endOfturn;
        ia.name = name;
        return ia;
    }

}