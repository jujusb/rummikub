package jeux;

import java.util.ArrayList;
import java.util.List;

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
}
