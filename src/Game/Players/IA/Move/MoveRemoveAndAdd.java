package Game.Players.IA.Move;

import Game.Pion.Pion;
import Game.Players.IA.IA;
import Game.Players.Player;
import Game.Table.Combinaison;
import Game.Table.Table;

public class MoveRemoveAndAdd extends RummikubMove {
    int numberCombiRemove; //numéro de la combinaison auquelle on enlève un pion
    Pion pion; //pion à déplacer d'une combinaison à une autre
    int numberCombiAdd; //numéro de la combinaison auquelle on ajoute un pion

    public MoveRemoveAndAdd(Table t, Player currentPlayer, int nR, int nA, Pion pi) {
        super(t, currentPlayer);
        numberCombiAdd = nA;
        numberCombiRemove = nR;
        pion = pi;
    }

    public void makeRummikubMove() { //TODO il faudrait tester si la combinaison contient plus de 3 pions au départ
        table.get(numberCombiRemove).remove(pion);
        table.ajoutALaCombinaison(table.get(numberCombiAdd), pion);
    }

    @Override
    public int scoreMove() {
        return nbPionsAvantMove;
    }

}
