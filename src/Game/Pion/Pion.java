package Game.Pion;

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

    public boolean equals(Object o){
        if (o instanceof Pion){
            Pion t = (Pion)o;
            return t.getCouleur() == this.couleur && t.getNum() == this.getNum();
        }else{
            return false;
        }
    }
}