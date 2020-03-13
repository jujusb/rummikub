package Game.Players.IA;

import Game.Players.Player;
import Game.Table.Chevalet;
import Game.Table.Combinaison;
import Game.Table.Table;

import java.util.List;

public class IA extends Player {
    public IA(Table table) {
        super("IA", table);
    }

    public IA() {
    }

    @Override
    public boolean jouer(){
        //TODO
        return super.jouer();
    }

    @Override
    public boolean passerTour(){
        //TODO
        return super.passerTour();
    }

    @Override
    public List<Combinaison> jouerdebut() {
        //TODO
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