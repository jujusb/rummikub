package Game.Pion;

public class Joker extends Pion {
    private boolean use = false ;
    public Joker(Couleur c) {
        super(0, c);
    }

    public void changeValue(int n, Couleur c) {
        num=n;
        couleur=c;
        use=true;
    }
    //TODO coder l'utilisation des jokers et leur moyens de se faire remplacer
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

    @Override
    public Object clone() {
        Joker c = new Joker(couleur);
        c.use=use;
        return c;
    }
}
