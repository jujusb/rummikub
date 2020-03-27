package Game.Players.IA;

import Game.Players.IA.MCTS.FinalSelectionPolicy;
import Game.Players.IA.MCTS.MCTS;
import Game.Players.Player;
import Game.Table.Chevalet;
import Game.Table.Combinaison;
import Game.Table.Table;

import java.util.List;

public class IA extends Player {
    MCTS mcts;
    Rummikub board;

    public IA(Table table) {
        super("IA", table);
        //this.board=board;
        mcts = new MCTS();
        //TODO check alls the values
        mcts.setExplorationConstant(0.2);
        mcts.setTimeDisplay(true);
        mcts.setOptimisticBias(0.0d);
        mcts.setPessimisticBias(0.0d);
        mcts.setMoveSelectionPolicy(FinalSelectionPolicy.robustChild);
    }

    public IA() {

    }

    @Override
    public boolean jouer(){
        //Move move = mcts.runMCTS_UCT(board,10,true);
        //move.combinaisonsApresDebut();
        //TODO
        // RummikubMove move =  new RummikubMove(getTable());
        // List<Combinaison> combs = move.combinaisonsApresDebut();
        // /* for(Combinaison c : combs) {
        //     Combinaison comb = super.getTable().nouvelleCombinaison(c.get(0));
        //     for(int i = 1 ; i<c.size(); i++) {
        //         super.getTable().ajoutALaCombinaison(comb, c.get(i));
        //     }
        // } */
        // return false;
        return super.jouer();
    }

    @Override
    public boolean passerTour(){
        //TODO return false; //ne passe pas son tour ici mais après si il a essayé de jouer mais qu'il ne peut pas
        return super.passerTour();
    }

    @Override
    public List<Combinaison> jouerdebut() {
        //Move move = mcts.runMCTS_UCT(board,10,true);
        //move.combinaisonsDébut();
        //TODO
        // RummikubMove move =  new RummikubMove(getTable());
        // List<Combinaison> combs =
        // return combs;
        return super.jouerdebut();
    }

    @Override
    public Object clone() {
        IA ia = new IA();
        ia.setDebut(isDebut());
        ia.setChevalet((Chevalet) getChevalet().clone());
        ia.setEndOfCombinaison(isEndOfCombinaison());
        ia.setEndOfTurn(isEndOfTurn());
        ia.setName(getName());
        return ia;
    }

}