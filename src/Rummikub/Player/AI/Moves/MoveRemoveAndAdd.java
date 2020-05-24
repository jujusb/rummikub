package Rummikub.Player.AI.Moves;

import Rummikub.Pion.Pion;
import Rummikub.Player.Player;
import Rummikub.Rummikub;
import Rummikub.Tablle.Table;

import java.util.Objects;

public class MoveRemoveAndAdd extends RummikubMove {
    int numberCombiRemove; //numéro de la combinaison auquelle on enlève un pion
    Pion pion; //pion à déplacer d'une combinaison à une autre
    int numberCombiAdd; //numéro de la combinaison auquelle on ajoute un pion
    boolean createCombi;

    public MoveRemoveAndAdd(Table t, Player currentPlayer, int nR, int nA, Pion pi, boolean createCombi) {
        super(t, currentPlayer);
        numberCombiAdd = nA;
        numberCombiRemove = nR;
        pion = pi;
        this.createCombi=createCombi;
    }

    @Override
    public String toString() {
        return "MoveRemoveAndAdd{" +
                "numberCombiRemove=" + numberCombiRemove +
                ", pion=" + pion +
                ", numberCombiAdd=" + numberCombiAdd +
                '}';
    }

    public void makeRummikubMove(Rummikub game) { // test fait si la combinaison contient plus de 3 pions au départ
        game.getTable().get(numberCombiRemove).remove(pion);
        game.getTable().ajoutALaCombinaison(game.getTable().get(createCombi?game.getTable().size()-1:numberCombiAdd), pion);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MoveRemoveAndAdd)) return false;
        if (!super.equals(o)) return false;
        MoveRemoveAndAdd that = (MoveRemoveAndAdd) o;
        return numberCombiRemove == that.numberCombiRemove &&
                numberCombiAdd == that.numberCombiAdd &&
                Objects.equals(pion, that.pion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), numberCombiRemove, pion, numberCombiAdd);
    }

    @Override
    public int scoreMove() {
        return nbPionsAvantMove;
    }

}
