package jeux;

public class Pion {
    private int num ;
    private Couleur couleur;

    public Pion(int n, String c) throws Exception {
        if(num>0&&num<14) {
            num = n;
        } else {
            throw new Exception("Le chiffre doit être entre 1 et 13");
        }
        couleur=Couleur.valueOf(c);
    }

    public Pion(int n, Couleur c) {
        num = n;
        couleur=c;
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
    
    
}
