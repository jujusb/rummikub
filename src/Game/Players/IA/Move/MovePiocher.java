package Game.Players.IA.Move;

import Game.Pion.Pion;
import Game.Players.IA.IA;
import Game.Players.Player;
import Game.Table.Combinaison;
import Game.Table.Table;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MovePiocher extends RummikubMove {
    //private HashMap<Pion,Integer> possiblePioche;

    public MovePiocher(Table t, IA p, Player adv) {
        super(t, p, adv);
        //possiblePioche = new HashMap<>();
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
        player.getChevalet().ajouter(table.piocherPion());
    }

    @Override
    public int scoreMove() {
        return nbPionsAvantMove + 1;
    }



}
