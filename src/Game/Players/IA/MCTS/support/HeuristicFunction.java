package Game.Players.IA.MCTS.support;

import Game.Players.IA.MCTS.Board;

/**
 * Create a class implementing this interface and instantiate
 * it. Pass the instance to the MCTS instance using the
 * {@link #setHeuristicFunction(HeuristicFunction h) setHeuristicFunction} method.

 * @author KGS
 *
 */
public interface HeuristicFunction {
	public double h(Board board);
}
