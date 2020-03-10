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
    private IA ia;
    private Table table;
    private Player currentPlayer;
    private Table backUpTable;
    private Player backUpPlayerHumain;
    private IA backUpIA;

    public Game() {
        table = new Table();
        playerHumain = new Player("HUMAIN", table);
        ia = new IA(table);
        for (int i = 0; i < 14; i++) {
            playerHumain.getChevalet().ajouter(table.piocherPion());
            ia.getChevalet().ajouter(table.piocherPion());
        }
        currentPlayer = playerHumain;
    }

    public void changeCurrentPlayer() {
        if (currentPlayer.equals(playerHumain)) {
            currentPlayer = ia;
        } else {
            currentPlayer = playerHumain;
        }
    }

    public void startGame() {
        while (!(playerHumain.gagne() || ia.gagne())) {
            backUp();
            if (currentPlayer.isDebut()) {
                if (!currentPlayer.passerTour()) {
                    List<Combinaison> list = currentPlayer.jouerdebut();
                    if (list != null) {
                        boolean valide = table.estValide();
                        int compte = table.comptePointsCombinaisons(list);
                        if (compte >= 30 && valide) {
                            currentPlayer.setDebutFait();
                            changeCurrentPlayer();
                        } else {
                            System.out.println(table);
                            if (valide && compte < 30) {
                                System.out.println("Ces coups de jeu ne sont pas valide pour le jeu car il faut au moins 30 " +
                                        "points pour démarrer la partie : veuillez rejouer !");
                                System.out.println(table);
                            } else {
                                System.out.println("Ces coups de jeu ne sont pas valide pour le jeu car il faut des combinaisons" +
                                        "(séries de couleurs d'un même chiffre ou suites de chiffres d'une même couleur) " +
                                        "d'au moins 3 pions pour jouer : veuillez rejouer !");
                            }
                            restoreBackUp();
                            System.out.println(table);
                        }
                    } else {
                        restoreBackUp();
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
                    System.out.println(table);
                    System.out.println("Ces coups de jeu ne sont pas valide pour le jeu.");
                    System.out.println("Il faut des combinaisons (séries de couleurs d'un même chiffre ou " +
                            "suites de chiffres d'une même couleur) d'au moins 3 pions " +
                            "et au maximum 13 pions. Veuillez rejouer !");
                    restoreBackUp();
                    System.out.println(table);
                } else {
                    currentPlayer.setEndOfTurn(false);
                    changeCurrentPlayer();
                }
            }
        }
    }

    private void backUp() {
        backUpTable = (Table) table.clone();
        backUpPlayerHumain = (Player) playerHumain.clone();
        backUpIA = (IA) ia.clone();
        backUpPlayerHumain.setTable(backUpTable);
        backUpIA.setTable(backUpTable);
    }

    private void restoreBackUp() {
        table = backUpTable;
        if (currentPlayer == playerHumain) {
            currentPlayer = backUpPlayerHumain;
        } else {
            currentPlayer = backUpIA;
        }
        playerHumain = backUpPlayerHumain;
        ia = backUpIA;
    }

    public String toString() {
        String str = new String("Joueur humain : " + playerHumain.toString() + "\n");
        str += "ia : " + ia.toString() + "\n";
        str += "table :\n" + table.toString();
        return str;
    }

    public static void main(String[] args) {
        Game game = new Game();
        System.out.println(game);
        game.startGame();
    }
}