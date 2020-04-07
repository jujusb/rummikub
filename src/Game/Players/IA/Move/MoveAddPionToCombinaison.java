package Game.Players.IA.Move;

import Game.Pion.Joker;
import Game.Pion.Pion;
import Game.Players.IA.IA;
import Game.Players.Player;
import Game.Table.Combinaison;
import Game.Table.Table;

public class MoveAddPionToCombinaison extends RummikubMove {
    Pion pionToAdd;
    int numberCombi; //numéro de la combinaison qui va recevoir le pion

    public MoveAddPionToCombinaison(Table t, Player currentPlayer, Pion pi, int n) {
        super(t, currentPlayer);
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
