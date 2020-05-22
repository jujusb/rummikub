package Rummikub.Player.AI;

import Rummikub.Player.AI.MCTS.Board;
import Rummikub.Player.AI.MCTS.support.HeuristicFunction;
import Rummikub.Rummikub;

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