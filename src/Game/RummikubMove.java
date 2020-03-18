package Game;

import Game.Players.IA.MCTS.Move;
import Game.Players.Player;
import Game.Table.Combinaison;
import Game.Table.Table;

import java.util.List;

public class RummikubMove implements Move {
    Table table;
    // Player p;

    public RummikubMove(Table t) {
        table=t;
    }

    public List<Combinaison> combinaisonsDébut() {
        return null;
    }

    public List<Combinaison> combinaisonsApresDebut() {
        return null;
    }


    //TODO

    @Override
    public int compareTo(Move o) {
        //TODO
        return 0;
    }
}
