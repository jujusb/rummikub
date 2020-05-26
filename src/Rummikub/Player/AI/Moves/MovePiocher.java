package Rummikub.Player.AI.Moves;

import Rummikub.Pion.Pion;
import Rummikub.Player.Player;
import Rummikub.Rummikub;
import Rummikub.Table.Table;

public class MovePiocher extends RummikubMove {
    private int indexpionAPiocher;
    private Pion pionAPiocher;
    private Rummikub rummikub;
    public MovePiocher(Table t, Player currentPlayer, int indexpionAPiocher) {
        super(t, currentPlayer);
        //possiblePioche = new HashMap<>();
        this.indexpionAPiocher=indexpionAPiocher;
        pionAPiocher = table.getPioche().get(indexpionAPiocher);
    }

    //piocher se fait automatiquement
    public void makeRummikubMove(Rummikub game){
        game.playerGetCurrentPlayer().getChevalet().ajouter(pionAPiocher);
        game.getTable().getPioche().remove(pionAPiocher);
        game.playerGetCurrentPlayer().setEndOfTurn(true);
    }

    @Override
    public int scoreMove() {
        return nbPionsAvantMove + 1;
    }

    @Override
    public String toString() {
        return "MovePiocher{" +
                "indexpionAPiocher=" + indexpionAPiocher +
                '}';
    }
}
