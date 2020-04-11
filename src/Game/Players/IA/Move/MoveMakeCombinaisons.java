package Game.Players.IA.Move;

import Game.Pion.Pion;
import Game.Players.IA.IA;
import Game.Players.Player;
import Game.Rummikub;
import Game.Table.Combinaison;
import Game.Table.Table;

import java.util.ArrayList;
import java.util.TreeSet;
public class MoveMakeCombinaisons extends RummikubMove {
    ArrayList<MoveMakeCombinaison> combi;//ensemble combinaison qui fera partie des combinaisons de départs
    ArrayList<Combinaison> combinaisons;

    public MoveMakeCombinaisons(Table t, Player currentPlayer, ArrayList<MoveMakeCombinaison> c) {
        super(t, currentPlayer);
        combi = c;
        combinaisons = new ArrayList<>();
        for(MoveMakeCombinaison m : c) {
            combinaisons.add(m.combi);
        }
    }

    public ArrayList<Combinaison> getCombinaisons() {
        return combinaisons;
    }

    public void makeRummikubMove() throws Exception {
        for(RummikubMove m : combi) {
            m.makeRummikubMove();
        }
    }

    @Override
    public int scoreMove() {
        int score = 0;
        for(MoveMakeCombinaison m : combi) {
            score+=m.combi.size();
        }
        return nbPionsAvantMove - score ;
    }

    @Override
    public String toString() {
        return "MoveMakeCombinaison{" +
                "combi=" + combi +
                '}';
    }


}
