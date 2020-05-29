package Rummikub.Pion;

import java.util.ArrayList;
import java.util.List;

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

    public boolean getUseSerie(){
        return useSerie;
    }

    public boolean getUseSuite(){
        return useSuite;
    }

    public void reset(){
        num = 0;
        couleur=null;
        containsList.clear();
        useSerie=false;
        useSuite=false;
    }

    /**
     *
     * @param c
     * @param n
     */
    public void setValueJokerInSuite(Couleur c, int n) {
        this.couleur = c;
        this.num = n;
        this.useSuite = true;
    }

    public void setValueJokerInSerie(List<Couleur> list, int n) {
        this.containsList = list;
        if(list.size()>0) {
            this.couleur = list.get(0);
        } else {
            this.couleur = Couleur.BLEU;
        }
        this.num = n;
        this.useSerie = true;
    }

    public void setValueJoker(int use, int c, int n) {
        if(c == 1){
            this.couleur = Couleur.JAUNE;
        }else{
            if(c == 2){
                this.couleur = Couleur.BLEU;
            }else{
                if(c == 3){
                    this.couleur = Couleur.NOIR;
                }else{
                        this.couleur = Couleur.ROUGE;
                }
            }
        }
        this.num = n;
        if(use==1){
            useSuite = true;
        } else {
            useSerie = true;
        }
    }

    public void changeValue(int n, Couleur c) {
        num=n;
        couleur=c;
        useSuite=true;
    }

    public boolean canReplace(Pion p) {
        return useSuite && couleur.equals(p.couleur) && p.num == num
                || useSerie && containsList.contains(p.couleur) && p.num == num;
    }


    @Override
    public String toString() {
        if(useSuite) {
            return "Joker{à remplacer par ce " +
                    super.toString()+
                    ", JColor=" + couleurJoker +
                    '}';
        } else if(useSerie) {
            return "Joker{à remplacer par un pion de num " +
                    num+" de couleur"+
                    containsList+
                    ", JColor=" + couleurJoker +
                    '}';
        } else {
            return "Joker{" +
                    "JColor=" + couleurJoker + '}';
        }
    }

    @Override
    public Object clone() {
        Joker c = new Joker();
        c.couleur=couleur;
        c.num=num;
        c.useSuite=useSuite;
        c.containsList=containsList;
        c.useSerie=useSerie;
        c.couleurJoker= couleurJoker;
        return c;
    }

    public boolean equals(Object o) {
        if (o instanceof Joker){
            Joker j = (Joker)o;
            return this.couleurJoker.equals(j.couleurJoker);
        } else {
            return false;
        }
    }

    public static void main(String[] args) {
        Joker j = new Joker(Couleur.NOIR);
        System.out.println(j.toString());
    }
}
