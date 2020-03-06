package Game.Table;

import Game.Pion.Couleur;
import Game.Pion.Pion;
import Game.Table.Combinaison;
import Game.Table.Pioche;

import java.util.ArrayList;
import java.util.List;

public class Table extends ArrayList<Combinaison> {
    Pioche pioche;
    public Table() {
        super();
        pioche = new Pioche();
    }

    public Combinaison nouvelleCombinaison(Pion p) {
        Combinaison c = new Combinaison();
        c.add(p);
        add(c);
        return c;
    }

    public void ajoutALaCombinaison(Combinaison c, Pion p) {
        get(indexOf(c)).add(p);
    }

    public boolean retirerDeCombinaison(Combinaison c, Pion p) {
        boolean succes = get(indexOf(c)).remove(p);
        if(succes && c.size() == 0)
            remove(c);
        return succes;
    }

    public boolean estValide() {
        for(Combinaison c : this)
            if(!c.estValide())
                return false;
        return true;
    }

    public Pion piocherPion() {
        return pioche.retirer();
    }

    public int pionsSurTable() {
        int compte = 0;
        for(Combinaison c : this)
            compte+= c.size();
        return compte;
    }

    public int comptePointsCombinaisons(List<Combinaison> combinaisons) {
        int compte = 0;
        for(Combinaison c : combinaisons) {
            for (Pion p : c) {
                compte += p.getNum();
            }
        }
        return compte;
    }

    public int pionsDansPioche() {
        return pioche.size();
    }

    public String toString() {
        String str = new String("Pions dans la pioche : "+pionsDansPioche()+ "\n");
        str+="Pions sur la table : "+pionsSurTable()+ "\n";
        int i =0;
        for(Combinaison c : this)
            str+= "Combinaison " + i++ + " : " + c.toString() + "\n";
        return str;
    }

    public static void main(String[] args) {
        Table t = new Table();
        t.pionsDansPioche();
        System.out.println(t);
        Pion p = new Pion(1, Couleur.BLEU);
        Pion p2 = new Pion(2, Couleur.BLEU);
        Pion p3 = new Pion(3, Couleur.BLEU);
        Pion p4 = new Pion(4, Couleur.ROUGE);
        Pion p5 = new Pion(4, Couleur.NOIR);
        Pion p6 = new Pion(4, Couleur.JAUNE);
        Combinaison c = t.nouvelleCombinaison(p);
        t.ajoutALaCombinaison(c,p2);
        System.out.println(t.estValide());
        t.ajoutALaCombinaison(c,p3);
        System.out.println(t.estValide());
        System.out.println(t);
        Combinaison c2 = t.nouvelleCombinaison(p4);
        t.ajoutALaCombinaison(c2,p5);
        System.out.println(t.estValide());
        t.ajoutALaCombinaison(c2,p6);
        System.out.println(t.estValide());
        System.out.println(t);
    }
}