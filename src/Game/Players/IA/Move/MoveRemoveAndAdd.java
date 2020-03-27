package Game.Players.IA.Move;

import Game.Players.IA.IA;
import Game.Players.Player;
import Game.Table.Table;

public class MoveRemoveAndAdd extends RummikubMove {

    public MoveRemoveAndAdd(Table t, IA p, Player adv) {
        super(t, p, adv);
    }

    @Override
    public int scoreMove() {
        return 0;
    }

}
