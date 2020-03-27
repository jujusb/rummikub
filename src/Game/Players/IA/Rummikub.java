package Game.Players.IA;


import Game.Players.IA.MCTS.Board;
import Game.Players.IA.MCTS.CallLocation;
import Game.Players.IA.MCTS.Move;
import Game.Players.Player;
import Game.Table.Combinaison;
import Game.Table.Table;

import java.util.ArrayList;
import java.util.List;

public class Rummikub implements Board {
    private Player playerHumain;
    private IA ia;
    private Table table;
    private Player currentPlayer;
    private Table backUpTable;
    private Player backUpPlayerHumain;
    private IA backUpIA;

    public Rummikub() {
        table = new Table();
        playerHumain = new Player("HUMAIN", table);
        ia = new IA(table);
        for (int i = 0; i < 14; i++) {
            playerHumain.getChevalet().ajouter(table.piocherPion());
            ia.getChevalet().ajouter(table.piocherPion());
        }
        table.setChevaletJoueurHumain(playerHumain.getChevalet());
        table.setChevaletIA(ia.getChevalet());
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
        while (!(playerHumain.gagne()) && !(ia.gagne())) {
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
        if(ia.gagne()) {
            System.out.println("L'IA a gagné !");
        }
        if(playerHumain.gagne()) {
            System.out.println("Le joueur humain a gagné !");
        }
    }

    private void backUp() {
        backUpTable = (Table) table.clone();
        backUpPlayerHumain = (Player) playerHumain.clone();
        backUpIA = (IA) ia.clone();
        backUpPlayerHumain.setTable(backUpTable);
        backUpIA.setTable(backUpTable);
        backUpTable.setChevaletJoueurHumain(backUpPlayerHumain.getChevalet());
        backUpTable.setChevaletIA(backUpIA.getChevalet());
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
        Rummikub rummikub = new Rummikub();
        System.out.println(rummikub);
        rummikub.startGame();
    }

    @Override
    public Board duplicate() {
        Rummikub rummikub = new Rummikub();
        rummikub.table = backUpTable;
        if (currentPlayer == playerHumain) {
            rummikub.currentPlayer = backUpPlayerHumain;
        } else {
            rummikub.currentPlayer = backUpIA;
        }
        rummikub.playerHumain = backUpPlayerHumain;
        rummikub.ia = backUpIA;
        return rummikub;
    }

    @Override
    public ArrayList<Move> getMoves(CallLocation location) {
        //TODO
        return null;
    }

    @Override
    public void makeMove(Move m) {
        //TODO
    }

    @Override
    public boolean gameOver() {
        return playerHumain.gagne() || ia.gagne() ;
    }

    @Override
    public int getCurrentPlayer() {
        return currentPlayer.equals(playerHumain)?0:1;
    }

    @Override
    public int getQuantityOfPlayers() {
        return 2;
    }

    @Override
    public double[] getScore() {
        double[] score = new double[2];
        int playerhumainnbPions= playerHumain.getChevalet().getNbPions();
        int ianbPions= ia.getChevalet().getNbPions();
        //score calculé en fonction du nombre de pions de chaque joueur
        int tot = playerhumainnbPions + ianbPions;
        score[0] = 1 - playerhumainnbPions/tot;
        score[1] = 1 - ianbPions/tot;
        return score;
    }

    @Override
    public double[] getMoveWeights() {
        //TODO
        return new double[0];
    }

    @Override
    public void bPrint() {
        System.out.println(this.toString());
    }
}