package Game.Pion;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Joker extends Pion {
    private boolean useSuite ;
    private boolean useSerie ;
    private List<Couleur> containsList;
    private Couleur couleurJoker ;
    public Joker(Couleur c) {
        super(0, c);
        useSerie = false ;
        useSuite = false ;
        couleurJoker = c;
        containsList=new ArrayList<>();
    }

    public Joker() {
    }

    public void setValueJoker() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Souhaitez vous utiliser le joker dans une suite (1) ? dans une série (2) ?");
        if(!useSuite) {
            System.out.println("Quelle couleur souhaitez vous utiliser le joker ?");
            String c = sc.nextLine();
        }
    }

    public void changeValueSerie(int n) { //TODO Garder liste des possibilités pour ce joker
        num=n;
        useSuite=true;
        //TODO containsList.add(Couleur.JAUNE);
    }
    public void changeValue(int n, Couleur c) {
        num=n;
        couleur=c;
        useSuite=true;
    }

    public boolean canReplace(Pion p) {
        return useSuite && p.couleur.equals(couleur) && p.num == num
                || useSerie && containsList.contains(p.couleur) && p.num == num;
    }
    //TODO coder l'utilisation des jokers et leur moyens de se faire remplacer
    @Override
    public String toString() {
        if(useSerie) {
            return "Joker{à remplacer par ce " +
                    super.toString()+
                    '}';
        } else if(useSuite) {
            return "Joker{à remplacer par un pion de num " +
                    num+" de couleur"+
                    containsList+
                    '}';
        } else {
            return "Joker{" +
                    "JColor=" + couleurJoker +
                    '}';
        }
    }

    @Override
    public Object clone() {
        Joker c = new Joker();
        c.useSuite=useSuite;
        c.containsList=containsList;
        c.useSerie=useSerie;
        c.couleurJoker= couleurJoker;
        return c;
    }
}
