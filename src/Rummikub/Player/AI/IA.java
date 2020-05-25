package Rummikub.Player.AI;

import Rummikub.Player.AI.MCTS.FinalSelectionPolicy;
import Rummikub.Player.AI.MCTS.MCTS;
import Rummikub.Player.AI.Moves.MoveMakeCombinaison;
import Rummikub.Player.AI.Moves.MovePiocher;
import Rummikub.Player.AI.Moves.MoveSetMoves;
import Rummikub.Player.AI.Moves.RummikubMove;
import Rummikub.Player.Player;
import Rummikub.Rummikub;
import Rummikub.Table.Chevalet;
import Rummikub.Table.Combinaison;
import Rummikub.Table.Table;

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
        mcts.enableRootParallelisation(20);//TODO
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
        } else if(move instanceof MoveSetMoves) {
            t = ((MoveSetMoves)move).getCombinaisons();
        } else if(move instanceof MoveMakeCombinaison) { // move instanceof MoveMakeCombinaison
            t = new ArrayList<>();
            t.add(((MoveMakeCombinaison) move).getCombi());
        } else {
            t=null;
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