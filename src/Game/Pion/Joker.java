package Game.Pion;

import Game.Table.Combinaison;

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

    public boolean getUseSerie(){
        return useSerie;
    }

    public boolean getUseSuite(){
        return useSuite;
    }

    public void reset(){
        num = 0;
        containsList.clear();
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


    public void setContainsList(Combinaison c){
        List<Couleur> couleursCombinaison = new ArrayList<Couleur>();
        for(Pion pi : c){
            if(!(pi instanceof Joker)){
                couleursCombinaison.add(pi.getCouleur());
            }
        }
        for(Couleur col : Couleur.values()){
            if(!(couleursCombinaison.contains(col))){
                containsList.add(col);
            }
        }
    }

    public void changeValue(int n, Couleur c) {
        num=n;
        couleur=c;
        useSuite=true;
    }

    public boolean canReplace(Pion p) {
        return useSuite && containsList.contains(p.couleur) && p.num == num
                || useSerie && containsList.contains(p.couleur) && p.num == num;
    }


    @Override
    public String toString() {
        if(useSuite) {
            return "Joker{à remplacer par ce " +
                    super.toString()+
                    '}';
        } else if(useSerie) {
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

    public static void main(String[] args) {
        Joker j = new Joker(Couleur.NOIR);
        System.out.println(j.toString());
    }
}
