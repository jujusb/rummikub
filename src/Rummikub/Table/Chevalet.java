package Rummikub.Table;

import Rummikub.Pion.Couleur;
import Rummikub.Pion.Joker;
import Rummikub.Pion.Pion;

import java.util.Collections;
import java.util.ArrayList;

public class Chevalet extends ArrayList<Pion> {
	private int nbPions;

	public Chevalet() {
		super();
		nbPions=0;
	}

	public int getNbPions() {
		return nbPions;
	}

	public boolean contient(Pion p) {
		return this.contains(p);
	}
	
	public void ajouter(Pion p) {
		this.add(p);
		nbPions++;
		sort();
	}

	public void sort() {
		Collections.sort(this);
	}

	public Pion retirer(Pion p) throws Exception {
		if(!this.contient(p)) {
			throw new Exception("Votre chevalet ne contient pas ce pion.");
		} else {
			this.remove(p);
			nbPions--;
			return p;
		}
	}

	public int contientJoker(){
		int res = 0;
		for (Pion p : this){
			if (p instanceof Joker){
				res ++;
			}
		}
		return res;
	}
	public String toString() { //chevalet affiché par couleur pour que ce soit plus pratique
		String s = "Votre chevalet contient " + size() + " pions : ";
		int i = 0;
		Couleur prec = null;
		boolean first = true;
		for(Pion p : this) {
			if(!(p instanceof Joker)){
				if(!p.getCouleur().equals(prec) && !first){ //TODO à débuguer car des fois il y a un java.lang.nullException
					s += "\n                                   ";
				}
				s+= i++ +":"+ p.toString()+" ";
				prec = p.getCouleur();
				first = false;
			} else {
				s += i++ +":"+ p.toString()+" ";
			}
		}
		return s;
	}
	
	public static void main (String[] args) throws Exception {
		//Pioche p1 = new Pioche();
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

    @Override
    public Object clone() {
        Chevalet chevalet = new Chevalet();
        for(Pion p : this) {
            chevalet.ajouter((Pion)p.clone());
        }
        return chevalet;
    }

	public int getIndexJokerRouge() {
		Joker j = new Joker(Couleur.ROUGE);
		for (int i = 0; i< size(); i++){
			if (get(i).equals(j)) {
				return i;
			}
		}
		return -1;
	}

	public int getIndexJokerNoir() {
		Joker j = new Joker(Couleur.NOIR);
		for (int i = 0; i< size(); i++){
			if (get(i).equals(j)) {
				if(!j.getUseSerie()||j.getUseSuite())
				return i;
			}
		}
		return -1;
	}

	public int getIndexJoker() {
		Joker j = new Joker(Couleur.NOIR);
		Joker j2 = new Joker(Couleur.ROUGE);
		for (int i = 0; i< size(); i++){
			if (get(i).equals(j)||get(i).equals(j2)) {
				return i;
			}
		}
		return -1;
	}
}