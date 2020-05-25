package Rummikub.Player.AI.Moves;

import Rummikub.Player.Player;
import Rummikub.Rummikub;
import Rummikub.Table.Table;

import java.util.ArrayList;
import java.util.Objects;

public class MoveSetMoves extends RummikubMove {
    ArrayList<RummikubMove> combi;//ensemble combinaison qui fera partie des combinaisons de départs

    public MoveSetMoves(Table t, Player currentPlayer, ArrayList<RummikubMove> c) {
        super(t, currentPlayer);
        combi = c;
    }

    public void makeRummikubMove(Rummikub game) throws Exception {
        for(RummikubMove m : combi) {
            m.makeRummikubMove(game);
        }
    }

    @Override
    public int scoreMove() {
        int score = 0;
        for(RummikubMove m : combi) {
            score+=(nbPionsAvantMove-m.scoreMove());
        }
        return nbPionsAvantMove - score ;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MoveSetMoves)) return false;
        if (!super.equals(o)) return false;
        MoveSetMoves that = (MoveSetMoves) o;
        return Objects.equals(combi, that.combi) ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), combi);
    }

    @Override
    public String toString() {
        return "SetMoves{" +
                "moves=" + combi +
                '}';
    }


}
