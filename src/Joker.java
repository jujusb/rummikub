public class Joker extends Pion {
    private boolean use = false ;
    public Joker(Couleur c) {
        super(0, c);
    }

    public void changeValue(int n, Couleur c) {
        super.setNum(n);
        super.setCouleur(c);
        use=true;
    }

    @Override
    public String toString() {
        if(use) {
            return "Joker{" +
                    "Color=" + super.toString() +
                    '}';
        } else {
            return "Joker{" +
                    "Color=" + super.getCouleur() +
                    '}';
        }
    }
}
