package Game;


import Game.Pion.Pion;
import Game.Players.IA;
import Game.Players.Player;
import Game.Table.Combinaison;
import Game.Table.Table;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Game {
    private Player playerHumain;
    private IA ia ;
    private Table table;
    private Player currentPlayer;
    private Game backup;

    public Game() {
        table = new Table();
        playerHumain = new Player("HUMAIN",table);
        ia = new IA(table);
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
            if (currentPlayer.isDebut()) {
                if (!currentPlayer.passerTour()) {
                    List<Combinaison> list = currentPlayer.jouerdebut();
                    if (table.estValide()) {
                        int compte = table.comptePointsCombinaisons(list);
                        if (compte >= 30) {
                            currentPlayer.setDebutFait();
                            changeCurrentPlayer();
                        } else {
                            backup();//TODO backup all game
                        }
                    }
                } else {
                    currentPlayer.getChevalet().ajouter(table.piocherPion());
                    changeCurrentPlayer();
                }
            } else {
                boolean continueTour = currentPlayer.jouer();
                while (continueTour) {
                    continueTour = currentPlayer.jouer();
                }
                if (!table.estValide()) {
                    backup();
                } else {
                    currentPlayer.setEndOfTurn(false);
                    changeCurrentPlayer();
                }
            }
        }
    }

    private void backup() {
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
        game.startGame();
    }
}