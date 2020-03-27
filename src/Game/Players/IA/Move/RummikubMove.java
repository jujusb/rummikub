package Game.Players.IA.Move;

import Game.Players.IA.IA;
import Game.Players.IA.MCTS.Move;
import Game.Players.IA.Rummikub;
import Game.Players.Player;
import Game.Table.Combinaison;
import Game.Table.Table;

import java.util.List;

public abstract class RummikubMove implements Move {
    Table table;
    IA player;
    Player adversaire;

    public RummikubMove(Table t, IA p, Player adv) {
        table=t;
        player = p;
        adversaire = adv;
    }


    public abstract int scoreMove();

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
