package Rummikub.Player.AI.Moves;

import Rummikub.Pion.Pion;
import Rummikub.Player.Player;
import Rummikub.Tablle.Table;

import java.util.Objects;

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

    @Override
    public String toString() {
        return "MoveAddPionToCombinaison{" +
                "pionToAdd=" + pionToAdd +
                ", numberCombi=" + numberCombi +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MoveAddPionToCombinaison)) return false;
        if (!super.equals(o)) return false;
        MoveAddPionToCombinaison that = (MoveAddPionToCombinaison) o;
        return numberCombi == that.numberCombi &&
                Objects.equals(pionToAdd, that.pionToAdd);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), pionToAdd, numberCombi);
    }
}
