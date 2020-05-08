package Rummikub.Pion;

public enum Couleur {
    BLEU,
    ROUGE,
    JAUNE,
    NOIR;

    @Override
    public String toString(){
        switch(this){
            case NOIR	: return "Blk";
            case BLEU	: return "Blu";
            case ROUGE	: return "Red";
            default		: return "Yel";
        }
    }


    public static Couleur getValueOf(String name) {
        switch(name){
            case "noir"	: return NOIR;
            case "bleu"	: return BLEU;
            case "rouge": return ROUGE;
            case "jaune": return JAUNE;
            default		: return null;
        }
    }
}
