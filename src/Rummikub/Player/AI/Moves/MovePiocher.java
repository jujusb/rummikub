package Rummikub.Player.AI.Moves;

import Rummikub.Pion.Pion;
import Rummikub.Player.Player;
import Rummikub.Rummikub;
import Rummikub.Tablle.Table;

public class MovePiocher extends RummikubMove {
    private int indexpionAPiocher;
    private Pion pionAPiocher;
    private Rummikub rummikub;
    public MovePiocher(Table t, Player currentPlayer, int indexpionAPiocher) {
        super(t, currentPlayer);
        //possiblePioche = new HashMap<>();
        this.indexpionAPiocher=indexpionAPiocher;
        pionAPiocher = table.getPioche().get(indexpionAPiocher);
        //
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
        currentPlayer.setEndOfTurn(true);
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
