package Rummikub;

import Rummikub.Pion.Couleur;
import Rummikub.Pion.Joker;
import Rummikub.Pion.Pion;
import Rummikub.Player.AI.IA;
import Rummikub.Player.AI.MCTS.Board;
import Rummikub.Player.AI.MCTS.CallLocation;
import Rummikub.Player.AI.MCTS.Move;
import Rummikub.Player.AI.Moves.MoveAddPionToCombinaison;
import Rummikub.Player.AI.Moves.MoveMakeCombinaison;
import Rummikub.Player.AI.Moves.MoveMakeCombinaisons;
import Rummikub.Player.AI.Moves.MovePiocher;
import Rummikub.Player.AI.Moves.MoveRemoveAndAdd;
import Rummikub.Player.AI.Moves.MoveReplaceByJoker;
import Rummikub.Player.AI.Moves.RummikubMove;
import Rummikub.Player.Player;
import Rummikub.Tablle.Chevalet;
import Rummikub.Tablle.Combinaison;
import Rummikub.Tablle.Table;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.TreeSet;

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
        playerHumain.setTable(table);
        ia = new IA(table,this);
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

    private void startGame() {
        //ia.setboard(this);
        //ia.setTable(table);
        //playerHumain.setTable(table);
        while (!(playerHumain.gagne()) && !(ia.gagne())) {
            backUp();
            ArrayList<Move> moves = getAllsMoves();
            for (Move m : moves) {
                System.out.println(m);
            }
            if (currentPlayer.isDebut()) {
                System.out.println(currentPlayer);
                List<Combinaison> list = null;
                try {
                    list = currentPlayer.jouerdebut();
                } catch (InputMismatchException e) {
                    e.printStackTrace();
                }
                if (list != null) {
                    boolean valide = table.estValide();
                    int compte = table.comptePointsCombinaisons(list);
                    if (compte >= 30 && valide) {
                        currentPlayer.setDebutFait();
                        System.out.println(Arrays.toString(getScore()));
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
                boolean continueTour = true ;
                try {
                    continueTour=currentPlayer.jouer();
                } catch (InputMismatchException e) {
                    e.printStackTrace();
                }
                for (Move m : moves) {
                    System.out.println(m);
                }
                while (continueTour) {
                    try {
                        continueTour=currentPlayer.jouer();
                    } catch (InputMismatchException e) {
                        e.printStackTrace();
                    }
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
                    System.out.println(Arrays.toString(getScore()));
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
    public Rummikub duplicate() {
        IA ai = (IA) ia.clone();
        Rummikub rummikub = ai.board;
        rummikub.table = (Table) table.clone();
        rummikub.playerHumain = (Player) playerHumain.clone();
        rummikub.playerHumain.setTable(rummikub.table);
        rummikub.ia = ai;
        rummikub.ia.setTable(rummikub.table);
        if (currentPlayer == playerHumain) {
            rummikub.currentPlayer = rummikub.playerHumain;
        } else {
            rummikub.currentPlayer = rummikub.ia;
        }
        return rummikub;
    }

    public Board clone() {
        return new Rummikub();
    }

    @Override
    public ArrayList<Move> getMoves(CallLocation location) {
        return getAllsMoves();
    }

    public ArrayList<Move> getMoveCombinaisons(ArrayList<ArrayList<Pion>> tabSerie, ArrayList<ArrayList<Pion>> tabSuite, Chevalet chevalet) {
        ArrayList<Move> moves = new ArrayList<>();
        //MOVES MAKE COMBINAISON
        int nbJoker = chevalet.contientJoker();
        boolean joker = nbJoker > 0;
        for (ArrayList<Pion> tab : tabSuite) {
            for (int i = 0; i < tab.size(); i++) {
                Pion p = tab.get(i);
                Combinaison c = new Combinaison();
                c.add(p);
                boolean suite = true;
                Combinaison cc = null;
                for (int j = i + 1; j < tab.size(); j++) {
                    if (suite) {
                        Pion pp = tab.get(j);
                        if (pp.compareTo(p) == 1) { //Ecart de 1 et même couleur
                            c.add(pp);
                            p = pp;
                        } else {
                            if (joker) {
                                nbJoker--;
                                if (nbJoker == 0) joker = false;
                                int posjok = chevalet.getIndexJoker();
                                Joker joker1 = (Joker) chevalet.get(posjok);
                                joker1.setValueJoker(1, p.getCouleur(), p.getNum() + 1);
                                c.add(joker1);
                                c.add(pp);
                                p = pp;
                            } else {
                                suite = false;
                                nbJoker = chevalet.contientJoker();
                            }
                        }
                        if (c.size() >= 3 && c.estValide() && !(c.equals(cc))) {
                            cc = (Combinaison) c.clone();
                            MoveMakeCombinaison mc = new MoveMakeCombinaison(table, currentPlayer, c);
                            if(!moves.contains(mc)) {
                                moves.add(mc);
                            }
                        }
                    }
                    nbJoker = chevalet.contientJoker();
                    joker= nbJoker>0;
                }
                nbJoker = chevalet.contientJoker();
                joker= nbJoker>0;
            }
            nbJoker = chevalet.contientJoker();
            joker= nbJoker>0;
        }

        //TODO A corriger avec joker
        int num = 1;
        for (ArrayList<Pion> l : tabSerie) {
            if (l.size() == 3) {
                Combinaison c = new Combinaison();
                for (Pion p : l) {
                    c.add(p);
                }
                if (c.estValide()) {
                    MoveMakeCombinaison mc = new MoveMakeCombinaison(table, currentPlayer, c);
                    if (!moves.contains(mc)) {
                        moves.add(mc);
                    }
                }
            }
            if (l.size() > 3) {
                Combinaison c = new Combinaison();
                for (Pion p : l) {
                    c.add(p);
                    if (c.size() >= 3 && c.estValide()) {
                        MoveMakeCombinaison mc = new MoveMakeCombinaison(table, currentPlayer, c);
                        if(!moves.contains(mc)) {
                            moves.add(mc);
                        }
                    }
                }
            }
            if (joker) {
                TreeSet<Couleur> couleurs = new TreeSet<>();
                for (Pion p : l) {
                    couleurs.add(p.getCouleur());
                }
                ArrayList<Couleur> list = new ArrayList<>();
                for (Couleur coul : Couleur.values()) {
                    if (!couleurs.contains(coul)) {
                        list.add(coul);
                    }
                }
                Combinaison c = new Combinaison();
                for (Pion p : l) {
                    c.add(p);
                    if (c.size() >= 2) {
                        if (nbJoker == 1) {
                            Joker j = (Joker) chevalet.get(chevalet.getIndexJoker());
                            c.add(j);
                            for (Couleur couleur : list) {
                                setValueJokerInSerie(chevalet, moves, num, c, j, couleur);
                            }
                        } else if (nbJoker == 2) {
                            Joker j = (Joker) chevalet.get(chevalet.getIndexJoker());
                            try {
                                chevalet.retirer(j);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            Joker jj = (Joker) chevalet.get(chevalet.getIndexJoker());
                            try {
                                chevalet.retirer(jj);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            c.add(j);
                            chevalet.ajouter(j);
                            chevalet.ajouter(jj);
                            for (Couleur couleur : list) {
                                setValueJokerInSerie(chevalet, moves, num, c, j, couleur);
                                for (Couleur couleur1 : list) {
                                    if (couleur != couleur1) {
                                        setValueJokerInSerie(chevalet, moves, num, c, jj, couleur1);
                                    }
                                }
                            }
                        }
                    }
                }
            }
            num++;
        }

        return moves;
    }

    private void setValueJokerInSerie(Chevalet chevalet, ArrayList<Move> moves, int num, Combinaison c, Joker
            j, Couleur couleur) {
        j.setValueJoker(0, couleur, num);
        if (c.estValide()) {
            MoveMakeCombinaison mc = new MoveMakeCombinaison(table, currentPlayer, c);
            if(!moves.contains(mc)) {
                moves.add(mc);
            }
        }
    }

    public ArrayList<Move> getAllsMoves() {
        ArrayList<Move> moves;
        Chevalet chevalet = (Chevalet) currentPlayer.getChevalet().clone();
        chevalet.sort();
        //tab for series
        ArrayList<ArrayList<Pion>> tabSeries = new ArrayList<>();
        for (int i = 0; i < 13; i++) {
            tabSeries.add(i, new ArrayList<>());
        }
        for (Pion p : chevalet) {
            if (!(p instanceof Joker)) {
                tabSeries.get(p.getNum() - 1).add(p);
            }
        }
        //tab for suites
        ArrayList<ArrayList<Pion>> tabSuite = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            tabSuite.add(i, new ArrayList<>());
        }
        for (Pion p : chevalet) {
            if (!(p instanceof Joker)) {
                tabSuite.get(p.getCouleur().ordinal()).add(p);
            }
        }
        System.out.println(tabSuite);
        System.out.println(tabSeries);
        moves = new ArrayList<>();
        //MOVES PIOCHER
        for (int i = 0; i < table.getPioche().size(); i++) {
            MovePiocher mp = new MovePiocher(table, currentPlayer, i);
            moves.add(mp);
        }
        //MAKES COMBINAISONS
        ArrayList<Move> movesCombinaisons = getMoveCombinaisons(tabSeries, tabSuite, chevalet);
        for (Move move : movesCombinaisons) {
            if (currentPlayer.isDebut()) {
                if (((MoveMakeCombinaison) move).getCombi().score() >= 30) {
                    moves.add(move);
                }
            } else {
                for (Move m : movesCombinaisons) {
                    moves.add(m);
                }
            }
            ArrayList<MoveMakeCombinaison> makeCombinaisons = new ArrayList<>();
            makeCombinaisons.add((MoveMakeCombinaison) move);
            boolean ok = true;
            chevalet = (Chevalet) currentPlayer.getChevalet().clone();
            for (Move move1 : movesCombinaisons) {
                for (Pion p : ((MoveMakeCombinaison) move1).getCombi()) {
                    for (Move m : makeCombinaisons) {
                        for (Pion pp : ((MoveMakeCombinaison) m).getCombi()) {
                            if (p.equals(pp)) {
                                ok = false;
                            }
                        }
                    }
                }
                if (ok) {
                    makeCombinaisons.add((MoveMakeCombinaison) move1);
                    int score = 0;
                    for (MoveMakeCombinaison make : makeCombinaisons) {
                        score += make.getCombi().score();
                    }
                    if (currentPlayer.isDebut()) {
                        if (score > 30) {
                            Move m = new MoveMakeCombinaisons(table, currentPlayer, makeCombinaisons);
                            if (!moves.contains(m)) {
                                moves.add(m);
                            }
                        }
                    } else {
                        Move m = new MoveMakeCombinaisons(table, currentPlayer, makeCombinaisons);
                        if (!moves.contains(m)) {
                            moves.add(m);
                        }
                    }
                }
            }
        }
        //MOVES ADD PION TO COMBINAISON AND REMOVE AND ADD
        System.out.println(tabSeries);
        if (!currentPlayer.isDebut()) {
            int index = 0;
            for (Combinaison combinaison : table) {
                if (combinaison.isSuite()) {
                    Couleur couleur = combinaison.get(0).getCouleur();
                    int numMin = combinaison.get(0).getNum();
                    int numMax = combinaison.get(combinaison.size() - 1).getNum();
                    if (numMin != 1) {
                        Pion p = new Pion(numMin - 1, couleur);
                        moveAddPion(moves, chevalet, index, p);
                    }
                    if (numMax != 13) {
                        Pion p = new Pion(numMax + 1, couleur);
                        moveAddPion(moves, chevalet, index, p);
                    }
                } else if (combinaison.isSerie()) {
                    int numS = combinaison.get(0).getNum();
                    ArrayList<Couleur> containList = combinaison.setContainsList();
                    for (Couleur couleur : containList) {
                        Pion p = new Pion(numS, couleur);
                        moveAddPion(moves, chevalet, index, p);
                    }
                }
                index++;
            }

            int ind = 0;
            for (Combinaison combinaison : table) {
                if (combinaison.contientJoker() > 0) {
                    Joker joker = (Joker)combinaison.get(combinaison.getIndexJoker());
                    for(Pion p : chevalet) {
                        if(joker.canReplace(p)) {
                            MoveReplaceByJoker move = new MoveReplaceByJoker(table,currentPlayer, ind, joker,true, p, -1);
                            if(!moves.contains(move)) {
                                moves.add(move);
                            }
                        }
                    }
                    for(Combinaison comb : table) {
                        Combinaison combclone = (Combinaison) comb.clone();
                        if(!comb.equals(combinaison)) {
                            int indcombinaison = 0;
                            for (Pion p : comb) {
                                if (joker.canReplace(p)) {
                                    combclone.remove(p);
                                    if(combclone.estValide()) {
                                        MoveReplaceByJoker move = new MoveReplaceByJoker(table, currentPlayer, indcombinaison, joker, false, p, indcombinaison);
                                        if(!moves.contains(move)) {
                                            moves.add(move);
                                        }
                                    }
                                }
                                indcombinaison++;
                            }
                        }
                    }
                }
                ind++;
            }
        }
        return moves;
    }

    private void moveAddPion(ArrayList<Move> moves, Chevalet chevalet, int index, Pion p) {
        if (chevalet.contient(p)) {
            MoveAddPionToCombinaison moveadd = new MoveAddPionToCombinaison(table, currentPlayer, p, index);
            if(!moves.contains(moveadd)) {
                moves.add(moveadd);
            }
        }
        int ind = 0;
        for (Combinaison cc : table) {
            if (cc.contains(p)) {
                Combinaison ccclone = (Combinaison) cc.clone();
                ccclone.remove(p);
                if (ccclone.estValide()) {
                    if(index != ind) {
                        MoveRemoveAndAdd moveRemoveAndAdd = new MoveRemoveAndAdd(table, currentPlayer, index, ind, p);
                        if(!moves.contains(moveRemoveAndAdd)) {
                            moves.add(moveRemoveAndAdd);
                        }
                    }
                }
            }
            ind++;
        }
    }

    @Override
    public void makeMove(Move m) {
        try {
            ((RummikubMove) m).makeRummikubMove(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean gameOver() {
        return playerHumain.gagne() || ia.gagne();
    }

    public Player playerGetCurrentPlayer() {
        return currentPlayer;
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

    public Table getTable() {
        return table;
    }
}