package Game.Players.IA.Move;

import Game.Players.IA.IA;
import Game.Players.IA.MCTS.Move;
import Game.Players.Player;
import Game.Table.Combinaison;
import Game.Table.Table;

import java.util.List;
import java.util.Objects;

public abstract class RummikubMove implements Move {
    Table table;
    Player currentPlayer;
    int nbPionsAvantMove;

    public RummikubMove(Table t, Player currentPlayer) {
        table=t;
        this.currentPlayer = currentPlayer;
        nbPionsAvantMove = currentPlayer.getChevalet().getNbPions();
    }

    public List<Combinaison> combinaisonsDébut() {
        return null;
    }

    public List<Combinaison> combinaisonsApresDebut() {
        return null;
    }

    public abstract int scoreMove(); //score correspond aux nombres de pions sur le chevalet après le move

    public abstract void makeRummikubMove() throws Exception;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RummikubMove)) return false;
        RummikubMove that = (RummikubMove) o;
        return nbPionsAvantMove == that.nbPionsAvantMove &&
                table.equals(that.table) &&
                currentPlayer.equals(that.currentPlayer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(table, currentPlayer, nbPionsAvantMove);
    }

    @Override
    public int compareTo(Move o) {
        if(this.scoreMove() < ((RummikubMove) o).scoreMove()){
            return -1;
        }else{
            if(this.scoreMove() > ((RummikubMove) o).scoreMove()){
                return 1;
            }else{
                return 0;
            }
        }
    }
}
