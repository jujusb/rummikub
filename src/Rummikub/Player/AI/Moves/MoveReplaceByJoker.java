package Rummikub.Player.AI.Moves;

import Rummikub.Pion.Joker;
import Rummikub.Pion.Pion;
import Rummikub.Player.Player;
import Rummikub.Rummikub;
import Rummikub.Table.Combinaison;
import Rummikub.Table.Table;

import java.util.Objects;

public class MoveReplaceByJoker extends RummikubMove {
    Joker joker;
    Pion p ;
    boolean chevalet;
    int combinaisonJ ;
    int combinaisonP ;

    public MoveReplaceByJoker(Table table, Player player, int combinaisonJ, Joker joker, boolean chevalet, Pion p, int combinaisonP) {
        super(table, player);
        this.joker = joker;
        this.p = p;
        this.chevalet = chevalet;
        this.combinaisonJ = combinaisonJ;
        this.combinaisonP = combinaisonP;
    }

    @Override
    public String toString() {
        if(chevalet) {
            return "MoveReplaceByJoker{" +
                    "joker=" + joker +
                    ", p=" + p +
                    ", combinaisonJ=" + combinaisonJ +
                    '}';
        } else {
            return "MoveReplaceByJoker{Joker à remplace" +
                    "joker=" + joker +
                    ", combinaisonJ=" + combinaisonJ +
                    ", p=" + p +
                    ", combinaisonP=" + combinaisonP +
                    '}';
        }
    }

    public void makeRummikubMove(Rummikub game) throws Exception {
        if(!chevalet) {
            game.getTable().get(combinaisonP).remove(p);
        } else {
            game.playerGetCurrentPlayer().getChevalet().retirer(p);
        }
        Combinaison c = game.getTable().get(combinaisonJ);
        game.getTable().retirerDeCombinaison(c, joker);
        game.playerGetCurrentPlayer().getChevalet().ajouter(joker);
        joker.reset();
        game.getTable().ajoutALaCombinaison(c, p);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MoveReplaceByJoker)) return false;
        if (!super.equals(o)) return false;
        MoveReplaceByJoker that = (MoveReplaceByJoker) o;
        return chevalet == that.chevalet &&
                combinaisonJ == that.combinaisonJ &&
                combinaisonP == that.combinaisonP &&
                joker.equals(that.joker) &&
                p.equals(that.p);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), joker, p, chevalet, combinaisonJ, combinaisonP);
    }

    @Override
    public int scoreMove() {
        return 0;
    }
}
