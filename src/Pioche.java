package jeux;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Pioche {
    private List<Pion> pions;

    public Pioche() {
        pions=new ArrayList<>();
        for(Couleur c : Couleur.values()) {
            for(int i=1; i<14; i++){
                pions.add(new Pion(i,c));
                pions.add(new Pion(i,c)); //chaque pièce est en double dans le jeu
            }
        }
    }

    @Override
    public String toString() {
        return "Pioche{" +
                "pions=" + pions +
                '}';
    }

    public List<Pion> getPions() {
        return pions;
    }
    
    public Pion retirer() {
    	int random = new Random().nextInt(pions.size()); 
    	return pions.remove(random);
    }

    
    public static void main (String[] args) {
        Pioche p = new Pioche();
        System.out.println(p);
    }
}
