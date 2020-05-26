package Rummikub.Player.AI.Moves;

import Rummikub.Player.Player;
import Rummikub.Rummikub;
import Rummikub.Table.Combinaison;
import Rummikub.Table.Table;

import java.util.ArrayList;
import java.util.Objects;

public class MoveSetMoves extends RummikubMove {
    ArrayList<RummikubMove> moves;//ensemble combinaison qui fera partie des combinaisons de départs
    ArrayList<Combinaison> combinaisons;

    public MoveSetMoves(Table t, Player currentPlayer, ArrayList<RummikubMove> mov) {
        super(t, currentPlayer);
        moves = mov;
        combinaisons = new ArrayList<>();
    }

    public ArrayList<RummikubMove> getMoves() {
        ArrayList<RummikubMove> move = new ArrayList<>();
        for(RummikubMove m : moves) {
            if(m instanceof MoveSetMoves)
                move.addAll(((MoveSetMoves)m).getMoves());
            else {
                move.add(m);
            }
        }
        return move;
    }
    public ArrayList<Combinaison> getCombinaisons() {
        ArrayList<Combinaison> combinaisons = new ArrayList<>();
        for(RummikubMove m : getMoves()) {
            if(m instanceof MoveMakeCombinaison) {
                combinaisons.add(((MoveMakeCombinaison)m).combi);
            } else if(m instanceof MoveRemoveAndAdd) {
                combinaisons.get(combinaisons.size()-1).add(((MoveRemoveAndAdd)m).getPion());
            }
        }
        return combinaisons;
    }
    public void makeRummikubMove(Rummikub game) throws Exception {
        for(RummikubMove m : moves) {
            m.makeRummikubMove(game);
        }
    }

    @Override
    public int scoreMove() {
        int score = 0;
        for(RummikubMove m : moves) {
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
        return Objects.equals(moves, that.moves) ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), moves);
    }

    @Override
    public String toString() {
        return "SetMoves{" +
                "moves=" + moves +
                '}';
    }


}
