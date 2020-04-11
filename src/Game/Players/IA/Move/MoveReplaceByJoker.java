package Game.Players.IA.Move;

import Game.Pion.Joker;
import Game.Pion.Pion;
import Game.Players.IA.IA;
import Game.Players.Player;
import Game.Table.Combinaison;
import Game.Table.Table;

import java.util.Objects;

public class MoveReplaceByJoker extends RummikubMove {
    Joker joker;
    Pion p ;
    boolean chevalet;
    int combinaisonJ ;
    int combinaisonP ;

    public MoveReplaceByJoker(Table t, Player currentPlayer, int combinaisonJ, Joker joker, boolean chevalet, Pion p, int combinaisonP) {
        super(t, currentPlayer);
        this.joker = joker;
        this.p = p;
        this.chevalet = chevalet;
        this.combinaisonJ = combinaisonJ;
        this.combinaisonP = combinaisonP;
    }

    public void makeRummikubMove(){
        if(!chevalet) {
            table.get(combinaisonP).remove(p);
        }
        Combinaison c = table.get(combinaisonJ);
        table.retirerDeCombinaison(c, joker);
        currentPlayer.getChevalet().ajouter(joker);
        joker.reset();
        table.ajoutALaCombinaison(c, p);
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
                Objects.equals(joker, that.joker) &&
                Objects.equals(p, that.p);
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
