package Game.Players.IA.Move;

import Game.Pion.Pion;
import Game.Players.Player;
import Game.Table.Combinaison;
import Game.Table.Table;

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

    public void makeRummikubMove() throws Exception {
        for(Pion p : combi) {
            currentPlayer.getChevalet().retirer(p);
        }
        int i = 0;
        Combinaison c = null;
        for(Pion p : combi){
            if(i == 0){
                c = table.nouvelleCombinaison(p);
            } else {
                table.ajoutALaCombinaison(c,p);
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
        return Objects.equals(combi, that.combi);
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
