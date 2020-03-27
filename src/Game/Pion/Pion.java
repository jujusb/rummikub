package Game.Pion;

import Game.Table.Pioche;

public class Pion implements  Comparable<Pion> {
    int num ;
    Couleur couleur;
    private Boolean select = false;

    public Pion(int n, String c) throws Exception {
        if(num>0&&num<14) {
            num = n;
        } else {
            throw new Exception("Le chiffre doit être entre 1 et 13");
        }
        couleur= Couleur.valueOf(c);
    }

    public Pion(int n, Couleur c) {
        num = n;
        couleur=c;
    }

    public Pion() {
    }

    public Couleur getCouleur() {
        return couleur;
    }

    public int getNum() {
        return num;
    }

    @Override
    public String toString() {
        return "Pion{" +
                num + " " + couleur +
                '}';
    }
    @Override
    public int compareTo(Pion pion) {
        if(pion instanceof Joker){
            if(!(((Joker) pion).getUseSerie()) && !(((Joker) pion).getUseSuite())){
                return -1;
            } else {
                if(couleur.equals(pion.couleur))
                    return num - pion.num;
                else
                    return couleur.compareTo(pion.couleur);
            }
        }
        if(this instanceof Joker){
            if(!(((Joker) this).getUseSerie()) && !(((Joker) this).getUseSuite())){
                return 1;
            } else {
                if(couleur.equals(pion.couleur))
                    return num - pion.num;
                else
                    return couleur.compareTo(pion.couleur);
            }
        }
        if(couleur.equals(pion.couleur))
            return num - pion.num;
        else
            return couleur.compareTo(pion.couleur);
    }

    public void select(){
        this.select = true;
    }

    public void deSelet(){
        this.select = false;
    }

    public Boolean isSelected(){
        return select;
    }

    @Override
    public Object clone() {
        Pion p = new Pion();
        p.num= num;
        p.couleur=couleur;
        p.select=select;
        return p;
    }

    public boolean equals(Object o){
        if (o instanceof Pion){
            Pion t = (Pion)o;
            return t.getCouleur() == this.couleur && t.getNum() == this.getNum();
        }else{
            return false;
        }
    }
}