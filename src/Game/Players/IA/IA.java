package Game.Players.IA;

import Game.Players.IA.MCTS.Board;
import Game.Players.IA.MCTS.FinalSelectionPolicy;
import Game.Players.IA.MCTS.MCTS;
import Game.Players.IA.MCTS.Move;
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
import java.util.concurrent.ConcurrentLinkedDeque;

public class IA extends Player {
    MCTS mcts;
    Rummikub board;

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
        //move.combinaisonsApresDebut();
        //return super.jouer();
        return true;
    }

    /*
    @Override
    public boolean passerTour(){
        //TODO return false; //ne passe pas son tour ici mais après si il a essayé de jouer mais qu'il ne peut pas
        return super.passerTour();
    }
*/
    @Override
    public List<Combinaison> jouerdebut() {
        RummikubMove move = (RummikubMove) mcts.runMCTS_UCT(board,10,true);
        if(move instanceof MovePiocher) {
            return null;
        } else if(move instanceof MoveMakeCombinaisons) {
            List<Combinaison> t = ((MoveMakeCombinaisons)move).getCombinaisons();
            return t;
        }
        //move.combinaisonsApresDebut();
        return null;
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
        return ia;
    }

    public void setboard(Rummikub rummikub) {
        this.board = board;
    }
}