import java.util.List;
import java.util.ArrayList;

public class Chevalet {
	private List<Pion> tab;
	private int nbPions;
	
	public Chevalet() {
		tab = new ArrayList<Pion>();
		nbPions=0;
	}
	
	public boolean contient(Pion p) {
		return tab.contains(p);
	}
	
	public void ajouter(Pion p) {
		tab.add(p);
		nbPions++;
	}
	
	public Pion retirer(Pion p) throws Exception {
		if(!this.contient(p)) {
			throw new Exception("Votre chevalet ne contient pas ce pion.");
		} else {
			tab.remove(p);
			return p;
		}
	}
	
	public String toString() {
		String s = "Votre chevalet contient " + tab.size() + " pions : ";
		for(Pion p : tab) {
			s+=p.toString()+" ";
		}
		return s;
	}
	
	public static void main (String[] args) throws Exception {
		Pioche p1 = new Pioche();
		Chevalet c1 = new Chevalet();
		System.out.println(c1);
		c1.ajouter(new Pion(1,Couleur.BLEU));
		c1.ajouter(new Pion(12,Couleur.ROUGE));
		System.out.println(c1);
		c1.ajouter(new Joker(Couleur.ROUGE));
		System.out.println(c1);
		Pion pion1 = c1.tab.get(0);
		c1.retirer(pion1);
		System.out.println(c1);
	}
	
}
