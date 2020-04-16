package Game.Players.IA;

import Game.Players.IA.MCTS.Board;
import Game.Players.IA.MCTS.support.PlayoutSelection;
import Game.Players.Player;
import Game.Rummikub;
import Game.Table.Table;

public class Playout implements PlayoutSelection {
    public Playout() {

    }

    @Override
    public void Process(Board board) {
        Player p = ((Rummikub)board).playerGetCurrentPlayer() ;
        Table t = ((Rummikub)board).getTable();
        p.getChevalet().ajouter(t.piocherPion()); //Piocher pion random
    }
}
