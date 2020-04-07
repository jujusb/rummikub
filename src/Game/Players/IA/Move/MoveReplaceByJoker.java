package Game.Players.IA.Move;

import Game.Pion.Joker;
import Game.Pion.Pion;
import Game.Players.IA.IA;
import Game.Players.Player;
import Game.Table.Table;

public class MoveReplaceByJoker extends RummikubMove {
    Joker joker;
    Pion p ;
    public MoveReplaceByJoker(Table t, Player currentPlayer, Joker joker) {
        super(t, currentPlayer);
        this.joker = joker;

    }

    public void makeRummikubMove(){
        //TODO
    }

    @Override
    public int scoreMove() {
        return 0;
    }
}
