package Game.Table;

import Game.Pion.Couleur;
import Game.Pion.Joker;
import Game.Pion.Pion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TreeSet;

public class Combinaison extends ArrayList<Pion> {
    private boolean suite;
    private boolean serie;
    private ArrayList<Couleur> containsList;

    public Combinaison() {
        super();
        containsList= new ArrayList<>();
    }

    public boolean estValide() {
        if(size()< 3 || size() > 13)
            return false;
        sort();

        boolean memeNum= true;
        boolean suiteNum = true;
        boolean memeCouleur = true;
        boolean serieCouleur = true;

        TreeSet<Couleur> temp = new TreeSet<>();
        for(int i=0; i< size()-1; i++) {
            //Numéro en i différent de celui en i+1
            if(get(i).getNum() != get(i+1).getNum())
                memeNum=false;
            if(get(i).getNum()+1 != get(i+1).getNum())
                suiteNum=false;
            if(!get(i).getCouleur().equals(get(i+1).getCouleur()))
                memeCouleur=false;
            temp.add(get(i).getCouleur());
        }
        temp.add(get(size()-1).getCouleur());
        if(temp.size() < size())
            serieCouleur= false;
        suite = memeCouleur && suiteNum;
        serie = serieCouleur && memeNum;
        return memeCouleur && suiteNum || serieCouleur && memeNum;
    }

    public boolean isSuite() {
        return suite;
    }

    public boolean isSerie() {
        return serie;
    }

    public boolean contientJoker(){
        boolean res = false;
        for (Pion p : this){
            if (p instanceof Game.Pion.Joker){
                res = true;
            }
        }
        return res;
    }

    public ArrayList<Couleur> setContainsList(){
        List<Couleur> couleursCombinaison = new ArrayList<Couleur>();
        for(Pion pi : this){
            if(!(pi instanceof Joker)){
                couleursCombinaison.add(pi.getCouleur());
            }
        }
        for(Couleur col : Couleur.values()){
            if(!(couleursCombinaison.contains(col))){
                containsList.add(col);
            }
        }
        return containsList;
    }

    public void sort() {
        Collections.sort(this);
    }

    public String toString() {
        String str= new String();
        int i = 0;
        for(Pion p : this)
            str += i++ + ":" + p.toString() + " ";
        return str;
    }
}
