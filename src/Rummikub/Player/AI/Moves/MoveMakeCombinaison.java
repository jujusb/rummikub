package Rummikub.Player.AI.Moves;

import Rummikub.Pion.Pion;
import Rummikub.Player.Player;
import Rummikub.Rummikub;
import Rummikub.Table.Combinaison;
import Rummikub.Table.Table;

import java.util.Objects;

public class MoveMakeCombinaison extends RummikubMove {
    Combinaison combi;//combinaison qui fera partie de toutes les possibilités de combinaisons

    public MoveMakeCombinaison(Table t, Player currentPlayer, Combinaison c) {
        super(t, currentPlayer);
        combi = c;
    }

    public Combinaison getCombi() {
        return combi;
    }

    public void makeRummikubMove(Rummikub game) throws Exception {
        for(Pion p : combi) {
            game.playerGetCurrentPlayer().getChevalet().retirer(p);
        }
        int i = 0;
        Combinaison c = null;
        for(Pion p : combi){
            if(i == 0){
                c = game.getTable().nouvelleCombinaison(p);
            } else {
                game.getTable().ajoutALaCombinaison(c,p);
            }
            i++;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MoveMakeCombinaison)) return false;
        if (!super.equals(o)) return false;
        MoveMakeCombinaison that = (MoveMakeCombinaison) o;
        return combi.equals(that.combi);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), combi);
    }

    @Override
    public int scoreMove() {
        return nbPionsAvantMove - combi.size();
    }

    @Override
    public String toString() {
        return "MoveMakeCombinaison{" +
                "combi=" + combi +
                '}';
    }
}
