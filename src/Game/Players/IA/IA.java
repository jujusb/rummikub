package Game.Players.IA;

import Game.Players.IA.MCTS.FinalSelectionPolicy;
import Game.Players.IA.MCTS.MCTS;
import Game.Players.IA.Move.MoveMakeCombinaison;
import Game.Players.IA.Move.MoveMakeCombinaisons;
import Game.Players.IA.Move.MovePiocher;
import Game.Players.IA.Move.RummikubMove;
import Game.Players.Player;
import Game.Table.Chevalet;
import Game.Table.Combinaison;
import Game.Table.Table;
import Game.Rummikub;

import java.util.ArrayList;
import java.util.List;

public class IA extends Player {
    MCTS mcts;
    public Rummikub board;

    public IA(Table table, Rummikub board) {
        super("IA", table);
        this.board=board;
        mcts = new MCTS();
        //TODO check alls the values
        mcts.setExplorationConstant(0.2);
        mcts.setTimeDisplay(true);
        mcts.setOptimisticBias(0.0d);
        mcts.setPessimisticBias(0.0d);
        mcts.setMoveSelectionPolicy(FinalSelectionPolicy.robustChild);
        mcts.setPlayoutSelection(new Playout());
        mcts.setHeuristicFunction(new Heuristic());
    }

    public IA() {

    }

    @Override
    public boolean jouer(){
        RummikubMove move = (RummikubMove) mcts.runMCTS_UCT(board,10,true);
        if(move instanceof MovePiocher) {
            return false;
        }
        return true;
        //return super.jouer();
    }

    @Override
    public List<Combinaison> jouerdebut() {
        RummikubMove move = (RummikubMove) mcts.runMCTS_UCT(board,2,true);
        List<Combinaison> t;
        if(move instanceof MovePiocher) {
            t=null;
        } else if(move instanceof MoveMakeCombinaisons) {
            t = ((MoveMakeCombinaisons)move).getCombinaisons();
        } else { // if(move instanceof MoveMakeCombinaison)
            t = new ArrayList<>();
            t.add(((MoveMakeCombinaison) move).getCombi());
        }
        return t;
        //return super.jouerdebut();
    }

    @Override
    public Object clone() {
        IA ia = new IA();
        ia.setDebut(isDebut());
        ia.setChevalet((Chevalet) getChevalet().clone());
        ia.setEndOfCombinaison(isEndOfCombinaison());
        ia.setEndOfTurn(isEndOfTurn());
        ia.setName(getName());
        ia.mcts = mcts;
        ia.board = (Rummikub) board.clone();
        return ia;
    }

    public void setboard(Rummikub rummikub) {
        this.board = board;
    }
}