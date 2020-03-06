package Game.Pion;

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
}
