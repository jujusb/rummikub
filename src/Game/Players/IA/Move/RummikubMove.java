package Game.Players.IA.Move;

import Game.Players.IA.IA;
import Game.Players.IA.MCTS.Move;
import Game.Players.Player;
import Game.Table.Combinaison;
import Game.Table.Table;

import java.util.List;

public abstract class RummikubMove implements Move {
    Table table;
    Player currentPlayer;
    int nbPionsAvantMove;

    public RummikubMove(Table t, Player currentPlayer) {
        table=t;
        currentPlayer = currentPlayer;
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
