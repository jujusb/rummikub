package jeux;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Pioche {
    private List<Pion> pions;

    public Pioche() {
        pions=new ArrayList<>();
        for(Couleur c : Couleur.values()) {
            for(int i =0;i<13;i++){
                pions.add(new Pion(i,c));
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
    
    public static void main() {
        Pioche p = new Pioche();
        System.out.println(p);
    }
}
