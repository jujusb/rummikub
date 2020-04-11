package Game.Players.IA.Move;

import Game.Pion.Pion;
import Game.Players.IA.IA;
import Game.Players.Player;
import Game.Rummikub;
import Game.Table.Combinaison;
import Game.Table.Table;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MovePiocher extends RummikubMove {
    private int indexpionAPiocher;
    private Pion pionAPiocher;
    private Rummikub rummikub;
    public MovePiocher(Rummikub rummikub, Table t, Player currentPlayer, int indexpionAPiocher) {
        super(t, currentPlayer);
        //possiblePioche = new HashMap<>();
        this.indexpionAPiocher=indexpionAPiocher;
        this.rummikub = rummikub;
        pionAPiocher = table.getPioche().get(indexpionAPiocher);
        currentPlayer.setEndOfTurn(true);
    }

    //est-ce que toutes les possibilités de pioche doivent être dans cette classe ?

    /*
    public void setPossiblePioche(){
        for(Pion p : table.getPioche()){
            if(possiblePioche.containsKey(p)){
                possiblePioche.replace(p, possiblePioche.get(p)+1);
            }else{
                possiblePioche.put(p,1);
            }
        }
    }
    */

    //piocher se fait automatiquement
    public void makeRummikubMove(){
        currentPlayer.getChevalet().ajouter(pionAPiocher);
        table.getPioche().remove(pionAPiocher);
        rummikub.changeCurrentPlayer();
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
