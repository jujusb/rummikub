package jeux;
import java.util.List;
import java.util.ArrayList;

public class Chevalet {
	private List<Pion> tab;
	private int nbPions;
	
	public Chevalet(Pioche pioche) {
		tab = new ArrayList<Pion>();
		for(int i=0; i<15; i++) {
			tab.add(pioche.retirer());
		}
		nbPions = 14;
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
	
	
	
}
