package Game.Table;

import Game.Pion.Couleur;
import Game.Pion.Joker;
import Game.Pion.Pion;

import java.util.List;
import java.util.ArrayList;

public class Chevalet extends ArrayList<Pion> {
	public int getNbPions() {
		return nbPions;
	}

	private int nbPions;
	
	public Chevalet() {
		super();
		nbPions=0;
	}
	
	public boolean contient(Pion p) {
		return this.contains(p);
	}
	
	public void ajouter(Pion p) {
		this.add(p);
		nbPions++;
	}
	
	public Pion retirer(Pion p) throws Exception {
		if(!this.contient(p)) {
			throw new Exception("Votre chevalet ne contient pas ce pion.");
		} else {
			this.remove(p);
			return p;
		}
	}
	
	public String toString() {
		String s = "Votre chevalet contient " + size() + " pions : ";
		for(Pion p : this) {
			s+=p.toString()+" ";
		}
		return s;
	}
	
	public static void main (String[] args) throws Exception {
		Pioche p1 = new Pioche();
		Chevalet c1 = new Chevalet();
		System.out.println(c1);
		c1.ajouter(new Pion(1, Couleur.BLEU));
		c1.ajouter(new Pion(12, Couleur.ROUGE));
		System.out.println(c1);
		c1.ajouter(new Joker(Couleur.ROUGE));
		System.out.println(c1);
		Pion pion1 = c1.get(0);
		c1.retirer(pion1);
		System.out.println(c1);
	}
	
}
