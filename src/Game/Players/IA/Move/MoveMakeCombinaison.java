package Game.Players.IA.Move;

import Game.Pion.Pion;
import Game.Players.IA.IA;
import Game.Players.Player;
import Game.Table.Combinaison;
import Game.Table.Table;

public class MoveMakeCombinaison extends RummikubMove {
    Combinaison combi;//combinaison qui fera partie de toutes les possibilités de combinaisons

    public MoveMakeCombinaison(Table t, IA p, Player adv, Combinaison c) {
        super(t, p, adv);
        combi = c;
    }

    public void makeRummikubMove() throws Exception {
        for(Pion p : combi){
            player.getChevalet().retirer(p);
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
    public int scoreMove() {
        return nbPionsAvantMove - combi.size();
    }

}
