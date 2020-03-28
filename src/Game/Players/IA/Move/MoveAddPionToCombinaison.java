package Game.Players.IA.Move;

import Game.Pion.Pion;
import Game.Players.IA.IA;
import Game.Players.Player;
import Game.Table.Combinaison;
import Game.Table.Table;

public class MoveAddPionToCombinaison extends RummikubMove {
    Pion pionToAdd;
    int numberCombi; //numéro de la combinaison qui va recevoir le pion

    public MoveAddPionToCombinaison(Table t, IA p, Player adv, Pion pi, int n) {
        super(t, p, adv);
        pionToAdd = pi;
        numberCombi = n;
    }

    public void makeRummikubMove(){
        table.ajoutALaCombinaison(table.get(numberCombi),pionToAdd);
    }

    @Override
    public int scoreMove() {
        return nbPionsAvantMove - 1;
    }

}
