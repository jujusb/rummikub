package Game.Table;

import Game.Pion.Couleur;
import Game.Pion.Joker;
import Game.Pion.Pion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Pioche extends ArrayList<Pion> {
    public Pioche() {
        super();
        for(Couleur c : Couleur.values()) {
            for(int i=0; i<26; i++){
                add(new Pion(i%13+1,c));
            }
        }
        //add(new Joker(Couleur.NOIR));
        //add(new Joker(Couleur.ROUGE));
        Collections.shuffle(this);
    }

    @Override
    public String toString() {
        String str = new String("Pions dans la pioche : "+size()+"\n Game.Table.Table.Pioche : {");
        for(Pion p : this) {
            str+=p.toString()+" ";
        }
        str +="}";
        return str;
    }

    public List<Pion> getPioche() {
        return this;
    }

    public Pion retirer() {
    	int random = new Random().nextInt(size());
    	return remove(random);
    }

    @Override
    public Object clone() {
        Pioche pioche = new Pioche();
        pioche.clear();
        for(Pion p : this)
            pioche.add((Pion)p.clone());
        return pioche;
    }

    public static void main (String[] args) {
        Pioche p = new Pioche();
        System.out.println(p.toString());
    }
}
