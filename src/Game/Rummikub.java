package Game;

import Game.Pion.Couleur;
import Game.Pion.Joker;
import Game.Pion.Pion;
import Game.Players.IA.IA;
import Game.Players.IA.MCTS.Board;
import Game.Players.IA.MCTS.CallLocation;
import Game.Players.IA.MCTS.Move;
import Game.Players.IA.Move.MoveAddPionToCombinaison;
import Game.Players.IA.Move.MoveMakeCombinaison;
import Game.Players.IA.Move.MovePiocher;
import Game.Players.IA.Move.RummikubMove;
import Game.Players.Player;
import Game.Table.Chevalet;
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

    private void changeCurrentPlayer() {
        if (currentPlayer.equals(playerHumain)) {
            currentPlayer = ia;
        } else {
            currentPlayer = playerHumain;
        }
    }

    private void startGame() {
        while (!(playerHumain.gagne()) && !(ia.gagne())) {
            backUp();
            ArrayList<Move> moves = getMoves();
            for (Move m : moves) {
                System.out.println(m);
            }
            if (currentPlayer.isDebut()) {
                System.out.println(currentPlayer);
                List<Combinaison> list = currentPlayer.jouerdebut();
                if (list != null) {
                    boolean valide = table.estValide();
                    int compte = table.comptePointsCombinaisons(list);
                    if (compte >= 30 && valide) {
                        currentPlayer.setDebutFait();
                        startGame();
                    } else {
                        System.out.println(table);
                        if (compte < 30) {
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
        if (ia.gagne()) {
            System.out.println("L'IA a gagné !");
        }
        if (playerHumain.gagne()) {
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
        String str = "Joueur humain : " + playerHumain.toString() + "\n";
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
        return getMoves();
    }

    public ArrayList<Move> getMoves() {
        ArrayList<Move> moves = new ArrayList<>();
        for (int i = 0; i < table.getPioche().size(); i++) {
            MovePiocher mp = new MovePiocher(table, currentPlayer, i);
            moves.add(mp);
        }
        currentPlayer.getChevalet().sort();
        Chevalet chevalet = (Chevalet) currentPlayer.getChevalet().clone();
        for (int i = 0; i < chevalet.size(); i++) {
            Pion p = chevalet.get(i);
            Combinaison c = new Combinaison();
            c.add(p);
            boolean suite = true;
            Combinaison cc = null;
            for (int j = i + 1; j < chevalet.size(); j++) {
                if (suite) {
                    Pion pp = chevalet.get(j);
                    if (!(p instanceof Joker)) {
                        if (pp.getCouleur().equals(p.getCouleur())) {
                            if (pp.compareTo(p) == 1) { //Ecart de 1 et même couleur
                                c.add(pp);
                                p = pp;
                            } else {
                                suite = false;
                            }
                        } else {
                            suite = false;
                        }
                        if (c.size() >= 3 && c.estValide() && !(c.equals(cc))) {
                            cc = (Combinaison) c.clone();
                            System.out.println(c);
                            MoveMakeCombinaison mc = new MoveMakeCombinaison(table, currentPlayer, c);
                            moves.add(mc);
                        }
                    }
                }
            }
        }
        ArrayList<ArrayList<Pion>> tab = new ArrayList<>();
        for (int i = 0; i < 13; i++) {
            tab.add(i, new ArrayList<>());
        }
        for (Pion p : chevalet) {
            ArrayList<Pion> list = tab.get(p.getNum() - 1);
            list.add(p);
        }
        for (ArrayList<Pion> l : tab) {
            if (l.size() == 3) {
                Combinaison c = new Combinaison();
                for (Pion p : l) {
                    c.add(p);
                    if (c.size() >= 3 && c.estValide()) {
                        MoveMakeCombinaison mc = new MoveMakeCombinaison(table, currentPlayer, c);
                        moves.add(mc);
                    }
                }
            }
        }
        System.out.println(tab);
        if (!currentPlayer.isDebut()) {
            int index = 0;
            for (Combinaison c : table) {
                if (c.isSuite()) {
                    Couleur couleur = c.get(0).getCouleur();
                    int numMin = c.get(0).getNum();
                    int numMax = c.get(c.size() - 1).getNum();
                    if (numMin != 1) {
                        Pion p = new Pion(numMin - 1, couleur);
                        if (chevalet.contient(p)) {
                            MoveAddPionToCombinaison moveadd = new MoveAddPionToCombinaison(table, currentPlayer, p, index);
                            moves.add(moveadd);
                        }
                    }
                    if (numMax != 13) {
                        Pion p = new Pion(numMax + 1, couleur);
                        if (chevalet.contient(p)) {
                            MoveAddPionToCombinaison moveadd = new MoveAddPionToCombinaison(table, currentPlayer, p, index);
                            moves.add(moveadd);
                        }
                    }
                } else if (c.isSerie()) {
                    int num = c.get(0).getNum();
                    ArrayList<Couleur> containList = c.setContainsList();
                    for(Couleur couleur : containList) {
                        Pion p = new Pion(num, couleur);
                        if(chevalet.contient(p)) {
                            MoveAddPionToCombinaison moveadd = new MoveAddPionToCombinaison(table, currentPlayer, p, index);
                            moves.add(moveadd);
                        }
                    }
                }
                index++;
            }
        }
        //TODO
        return moves;
    }

    @Override
    public void makeMove(Move m) {
        try {
            ((RummikubMove) m).makeRummikubMove();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean gameOver() {
        return playerHumain.gagne() || ia.gagne();
    }

    @Override
    public int getCurrentPlayer() {
        return currentPlayer.equals(playerHumain) ? 0 : 1;
    }

    @Override
    public int getQuantityOfPlayers() {
        return 2;
    }

    @Override
    public double[] getScore() {
        double[] score = new double[2];
        int playerhumainnbPions = playerHumain.getChevalet().getNbPions();
        int ianbPions = ia.getChevalet().getNbPions();
        //score calculé en fonction du nombre de pions de chaque joueur
        int tot = playerhumainnbPions + ianbPions;
        score[0] = 1.0 - 1.0 * playerhumainnbPions / tot;
        score[1] = 1.0 - 1.0 * ianbPions / tot;
        return score;
    }

    @Override
    public double[] getMoveWeights() {
        int size = table.getPioche().size();
        double[] piocherMoveWeights = new double[size];
        double proba = 1.0 / size;
        for (int i = 0; i < size; i++) {
            piocherMoveWeights[i] = proba;
        }
        return piocherMoveWeights;
    }

    @Override
    public void bPrint() {
        System.out.println(this.toString());
    }
}