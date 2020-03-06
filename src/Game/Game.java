package Game;


import Game.Players.IA;
import Game.Players.Player;
import Game.Table.Table;

public class Game {
    private Player playerHumain;
    private IA ia ;
    private Table table;

    public Game() {
        playerHumain = new Player("HUMAIN");
        ia = new IA();
        table = new Table();
        for(int i=0; i<14; i++) {
            playerHumain.getChevalet().ajouter(table.piocherPion());
            ia.getChevalet().ajouter(table.piocherPion());
        }
    }

    public String toString() {
        String str = new String("Joueur humain : " + playerHumain.toString()+"\n");
        str+="ia : " + ia.toString()+"\n";
        str+="table :\n"+ table.toString();
        return str;
    }

    public static void main(String[] args) {
        Game game = new Game();
        System.out.println(game);
    }
}