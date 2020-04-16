package Game.Players.IA;

import Game.Players.IA.MCTS.Board;
import Game.Players.IA.MCTS.support.HeuristicFunction;
import Game.Rummikub;

public class Heuristic implements HeuristicFunction {
    public Heuristic() {

    }

    @Override
    public double h(Board board) {
        Rummikub game = (Rummikub)board;
        double[] t = game.getScore();
        return t[game.getCurrentPlayer()];
    }
}