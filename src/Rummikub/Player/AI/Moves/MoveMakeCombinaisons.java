package Rummikub.Player.AI.Moves;

import Rummikub.Player.Player;
import Rummikub.Rummikub;
import Rummikub.Tablle.Combinaison;
import Rummikub.Tablle.Table;

import java.util.ArrayList;
import java.util.Objects;

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

    public void makeRummikubMove(Rummikub game) throws Exception {
        for(RummikubMove m : combi) {
            m.makeRummikubMove(game);
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MoveMakeCombinaisons)) return false;
        if (!super.equals(o)) return false;
        MoveMakeCombinaisons that = (MoveMakeCombinaisons) o;
        return Objects.equals(combi, that.combi) &&
                Objects.equals(combinaisons, that.combinaisons);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), combi, combinaisons);
    }

    @Override
    public String toString() {
        return "MoveMakeCombinaison{" +
                "combi=" + combi +
                '}';
    }


}
