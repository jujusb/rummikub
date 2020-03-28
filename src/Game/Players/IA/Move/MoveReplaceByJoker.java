package Game.Players.IA.Move;

import Game.Players.IA.IA;
import Game.Players.Player;
import Game.Table.Table;

public class MoveReplaceByJoker extends RummikubMove {

    public MoveReplaceByJoker(Table t, IA p, Player adv) {
        super(t, p, adv);
    }

    public void makeRummikubMove(){
        //TODO
    }

    @Override
    public int scoreMove() {
        return 0;
    }
}
