import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Pioche {
    private List<Pion> pions;

    public Pioche() {
        pions=new ArrayList<>();
        for(Couleur c : Couleur.values()) {
            for(int i=0; i<26; i++){
                pions.add(new Pion(i%13+1,c)); //chaque pièce est en double dans le jeu
            }
        }
        pions.add(new Joker(Couleur.NOIR));
        pions.add(new Joker(Couleur.ROUGE));
        Collections.shuffle(pions);
    }

    @Override
    public String toString() {
        return "Pions dans la pioche : "+pions.size()+"\n Pioche : {" +
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
