package Rummikub.Player.AI;

import Rummikub.Player.AI.MCTS.Board;
import Rummikub.Player.AI.MCTS.support.PlayoutSelection;
import Rummikub.Player.Player;
import Rummikub.Rummikub;
import Rummikub.Tablle.Table;

public class Playout implements PlayoutSelection {
    public Playout() {

    }

    @Override
    public void Process(Board board) {
        Player p = ((Rummikub)board).playerGetCurrentPlayer() ;
        Table t = ((Rummikub)board).getTable();
        //if(mv instanceof MovePiocher) {
        //    ((Rummikub)board).changeCurrentPlayer();
        //}
        p.getChevalet().ajouter(t.piocherPion()); //Piocher pion random
    }
}
