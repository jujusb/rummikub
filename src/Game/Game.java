package Game;


import Game.Pion.Pion;
import Game.Players.IA;
import Game.Players.Player;
import Game.Table.Combinaison;
import Game.Table.Table;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private Player playerHumain;
    private IA ia ;
    private Table table;
    private Player currentPlayer;
    private Game backup;

    public Game() {
        playerHumain = new Player("HUMAIN");
        ia = new IA();
        table = new Table();
        for(int i=0; i<14; i++) {
            playerHumain.getChevalet().ajouter(table.piocherPion());
            ia.getChevalet().ajouter(table.piocherPion());
        }
        currentPlayer=playerHumain;
    }

    public void changeCurrentPlayer() {
        if(currentPlayer.equals(playerHumain)) {
            currentPlayer = ia;
        } else {
            currentPlayer = playerHumain;
        }
    }

    public void startGame() {
        while(!(playerHumain.gagne() || ia.gagne())) {
            if(currentPlayer.isDebut()) {
                List<Combinaison> list = new ArrayList<>();
                while(!currentPlayer.isEndOfTurn() && table.estValide()) {
                    Combinaison c = table.nouvelleCombinaison(currentPlayer.selectPion());
                    while(!currentPlayer.isEndOfCombinaison()) {
                        table.ajoutALaCombinaison(c,currentPlayer.selectPion());
                    }
                    currentPlayer.setEndOfCombinaison(false);
                    table.add(c);
                    list.add(c);
                }
                if(table.estValide()) {
                    int compte = table.comptePointsCombinaisons(list);
                    if(compte>=30) {
                        currentPlayer.setDebutFait();
                    }
                }
            } else {
                while(!currentPlayer.isEndOfTurn() && table.estValide()) {
                    Combinaison c = table.nouvelleCombinaison(currentPlayer.selectPion());
                    while(!currentPlayer.isEndOfCombinaison()) {
                        table.ajoutALaCombinaison(c,currentPlayer.selectPion());
                    }
                    currentPlayer.setEndOfCombinaison(false);
                    table.add(c);
                }
            }
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