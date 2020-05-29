package Rummikub;

import Rummikub.Pion.Couleur;
import Rummikub.Pion.Joker;
import Rummikub.Pion.PartitionSetCreator;
import Rummikub.Pion.Pion;
import Rummikub.Player.AI.IA;
import Rummikub.Player.AI.MCTS.Board;
import Rummikub.Player.AI.MCTS.CallLocation;
import Rummikub.Player.AI.MCTS.Move;
import Rummikub.Player.AI.Moves.MoveAddPionToCombinaison;
import Rummikub.Player.AI.Moves.MoveMakeCombinaison;
import Rummikub.Player.AI.Moves.MovePiocher;
import Rummikub.Player.AI.Moves.MoveRemoveAndAdd;
import Rummikub.Player.AI.Moves.MoveReplaceByJoker;
import Rummikub.Player.AI.Moves.MoveSetMoves;
import Rummikub.Player.AI.Moves.RummikubMove;
import Rummikub.Player.Player;
import Rummikub.Table.Chevalet;
import Rummikub.Table.Combinaison;
import Rummikub.Table.Table;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;
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
        ia = new IA(table);
        for (int i = 0; i < 14; i++) {
            playerHumain.getChevalet().ajouter(table.piocherPion());
            ia.getChevalet().ajouter(table.piocherPion());
        }
        table.setChevaletJoueurHumain(playerHumain.getChevalet());
        table.setChevaletIA(ia.getChevalet());
        currentPlayer = playerHumain;
    }

    public Rummikub(Player player, Player opponent, Table t) {
        this.table = (Table) t.clone();
        if (player instanceof IA) {
            ia = (IA) player.clone();
            playerHumain = (Player) opponent.clone();
            currentPlayer = playerHumain;
        } else {
            playerHumain = (Player) player.clone();
            ia = (IA) opponent.clone();
            currentPlayer = ia;
        }
        ia.setTable(table);
        playerHumain.setTable(table);
        table.setChevaletIA(ia.getChevalet());
        table.setChevaletJoueurHumain(playerHumain.getChevalet());
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
                    list = currentPlayer.jouerdebut(currentPlayer, getOpponent(), table);
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
                boolean continueTour = true;
                try {
                    continueTour = currentPlayer.jouer(currentPlayer, getOpponent(), table);
                } catch (InputMismatchException e) {
                    e.printStackTrace();
                }
                for (Move m : moves) {
                    System.out.println(m);
                }
                while (continueTour) {
                    try {
                        continueTour = currentPlayer.jouer(currentPlayer, getOpponent(), table);
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

    private Player getOpponent() {
        Player opponent;
        if (currentPlayer == playerHumain) {
            opponent = ia;
        } else {
            opponent = playerHumain;
        }
        return opponent;
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
        return new Rummikub(currentPlayer, getOpponent(), table);
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
        Chevalet chev;
        for (ArrayList<Pion> tab : tabSuite) {
            chev = (Chevalet) chevalet.clone();
            if (tab.size() > 0) {
                Pion p = tab.get(0);
                Combinaison c = new Combinaison();
                c.add(p);
                Combinaison cc = null;
                ArrayList<RummikubMove> removeAndAddPion = new ArrayList<>();
                for (int i = 1; i < tab.size(); i++) {
                    Pion pp = tab.get(i);
                    if (pp.equals(p)) {
                        i++;
                        if (i < tab.size()) {
                            pp = tab.get(i);
                        }
                    }
                    if (pp.compareTo(p) == 1) { //Ecart de 1 et même couleur
                        c.add(pp);
                        p = pp;
                    } else {
                        if (nbJoker != 0) {
                            nbJoker--;
                            int posjokRouge = chev.getIndexJokerRouge();
                            int posjokNoir = chev.getIndexJokerNoir();
                            Joker joker1;
                            if (posjokNoir != -1) {
                                joker1 = (Joker) chev.get(posjokNoir).clone();
                            } else if (posjokRouge != -1) {
                                joker1 = (Joker) chev.get(posjokRouge).clone();
                            } else {
                                joker1 = null;
                            }
                            if (joker1 != null) {
                                try {
                                    chev.retirer(joker1);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                if (c.size() == 2 && c.get(0).getNum() == 12) {
                                    joker1.setValueJokerInSuite(p.getCouleur(), 10);
                                } else {
                                    joker1.setValueJokerInSuite(p.getCouleur(), p.getNum() + 1);
                                }
                                c.add(joker1);
                                p = joker1;
                                if (pp.compareTo(joker1) == 1) {
                                    c.add(pp);
                                    p = pp;
                                } else {
                                    c = new Combinaison();
                                    c.add(pp);
                                    chev = (Chevalet) chevalet.clone();
                                    nbJoker = chev.contientJoker();
                                }
                            }
                            //} else {
                            //add pion déjà sur table dans la combinaison si pas début
                            //if (!currentPlayer.isDebut()) {
                            //    Couleur couleur = c.get(0).getCouleur();
                            //    int numMin = c.get(0).getNum();
                            //    int numMax = c.get(c.size() - 1).getNum();
                            //    removeAndAddPion = new ArrayList<>();
                            //    if (numMin != 1) {
                            //        Pion pion = new Pion(numMin - 1, couleur);
                            //        Table t = (Table) table.clone();
                            //        removeAndAddPion.addAll(removeAndAddPion(moves, table.size(), t, pion, true, false, 0, c));
                            //    }
                            //    if (numMax != 13) {
                            //        Pion pion = new Pion(numMax + 1, couleur);
                            //        Table t = (Table) table.clone();
                            //        removeAndAddPion.addAll(removeAndAddPion(moves, table.size(), t, pion, true, false, 0, c));
                            //    }
                            //}
                        } else {
                            c = new Combinaison();
                            c.add(pp);
                            chev = (Chevalet) chevalet.clone();
                            nbJoker = chev.contientJoker();
                        }
                        if (c.size() >= 3 && c.estValide() && !(c.equals(cc))) {
                            cc = (Combinaison) c.clone();
                            MoveMakeCombinaison mc = new MoveMakeCombinaison(table, currentPlayer, c);
                            if (!moves.contains(mc)) {
                                moves.add(mc);
                            }
                            if (!currentPlayer.isDebut()) {
                                movesCreerCombinaisonPlusAjoutPiondeTable(moves, c, removeAndAddPion);
                            }
                        }
                    }
                }
            }
            nbJoker = chevalet.contientJoker();
        }

        int num = 1;
        //System.out.println("Joker ? :" + joker);
        for (ArrayList<Pion> l : tabSerie) {
            TreeSet<Pion> pionTreeSet = new TreeSet<>(l);
            ArrayList<Couleur> list = new ArrayList<>();
            TreeSet<Couleur> couleurs = new TreeSet<>();
            for (Pion p : l) {
                couleurs.add(p.getCouleur());
                pionTreeSet.add(p);
            }
            for (Couleur coul : Couleur.values()) {
                if (!couleurs.contains(coul)) {
                    list.add(coul);
                }
            }
            //System.out.println(list);
            PartitionSetCreator<Pion> partition = new PartitionSetCreator<>(pionTreeSet);
            Set<Set<Set<Pion>>> partitionsSet = partition.findAllPartitions();
            for (Set<Set<Pion>> setSize : partitionsSet) {
                for (Set<Pion> setPion : setSize) {
                    Combinaison c = new Combinaison();
                    c.addAll(setPion);
                    if ((setPion.size() == 1 || setPion.size() == 2) && nbJoker == 2) {
                        Joker j = (Joker) chevalet.get(chevalet.getIndexJokerRouge()).clone();
                        Joker j2 = (Joker) chevalet.get(chevalet.getIndexJokerNoir()).clone();
                        c.add(j);
                        c.add(j2);
                        j.setValueJokerInSerie(list, num);
                        j2.setValueJokerInSerie(list, num);
                    } else if ((setPion.size() == 2 || setPion.size() == 3) && nbJoker==1) {
                        Joker j = (Joker) chevalet.get(chevalet.getIndexJoker()).clone();
                        c.add(j);
                        j.setValueJokerInSerie(list, num);
                    } // if (setPion.size() == 3) and  if (setPion.size() == 4) les pions sont déjà dans la combinaison
                    if(c.estValide()) {
                        MoveMakeCombinaison mc = new MoveMakeCombinaison(table, currentPlayer, (Combinaison) c.clone());
                        if (!moves.contains(mc)) {
                            moves.add(mc);
                        }
                    }
                    //add pion at combinaison pas forcément complète from table if début passé
                    //if (!currentPlayer.isDebut()) {
                    //    if (c.size() > 0) {
                    //        int numS = c.get(0).getNum();
                    //        ArrayList<Couleur> containList = c.setContainsList();
                    //        ArrayList<RummikubMove> removeAndAddPion = new ArrayList<>();
                    //        for (Couleur couleur : containList) {
                    //            Pion pion = new Pion(numS, couleur);
                    //            Table t = (Table) table.clone();
                    //            removeAndAddPion.addAll(removeAndAddPion(moves, table.size(), t, pion, true, true, 0, c));
                    //        }
                    //        movesCreerCombinaisonPlusAjoutPiondeTable(moves, c, removeAndAddPion);
                    //    }
                    //}
                }
            }
            //System.out.println(partitionsSet);
            num++;
        }

        return moves;
    }

    private void movesCreerCombinaisonPlusAjoutPiondeTable(ArrayList<Move> moves, Combinaison c, ArrayList<RummikubMove> removeAndAddPion) {
        for (RummikubMove removeMove : removeAndAddPion) {
            ArrayList<RummikubMove> setMoves = new ArrayList<>();
            MoveMakeCombinaison mc = new MoveMakeCombinaison(table, currentPlayer, c);
            setMoves.add(mc);
            setMoves.add(removeMove);
            MoveSetMoves moveSetMoves = new MoveSetMoves(table, currentPlayer, setMoves);
            if (!moves.contains(moveSetMoves)) {
                moves.add(moveSetMoves);
            }
        }
    }

    private void setValueJokerInSerie(Chevalet chevalet, ArrayList<Move> moves, int num, Combinaison c, Joker
            j, List<Couleur> list) {
        j.setValueJokerInSerie(list, num);
        if (c.estValide()) {
            MoveMakeCombinaison mc = new MoveMakeCombinaison(table, currentPlayer, (Combinaison) c.clone());
            if (!moves.contains(mc)) {
                moves.add(mc);
            }
        }
    }

    public ArrayList<ArrayList<TreeMap<Pion,ArrayList<ArrayList<Integer>>>>> tabSuiteSerie () {
        //-1 s'il est dans le chevalet et >0 si le pion est dans la table selon la version
        ArrayList<HashMap<Pion,ArrayList<ArrayList<Integer>>>> pionsUtilisablesSerie = new ArrayList<>();
        ArrayList<HashMap<Pion,ArrayList<ArrayList<Integer>>>> pionsUtilisablesSuite = new ArrayList<>();
        Chevalet chevalet = (Chevalet) currentPlayer.getChevalet().clone();
        chevalet.sort();
        for (int i = 0; i < 13; i++) {
            pionsUtilisablesSerie.add(i, new HashMap<>());
        }
        for (int i = 0; i < 4; i++) {
            pionsUtilisablesSuite.add(i, new HashMap<>());
        }
        //Pion pp=new Pion();
        for (Pion p : chevalet){
            //if(!pp.equals(p)) {
                addPionUtilisableToTabs(pionsUtilisablesSerie, pionsUtilisablesSuite, p, -1, 0);
            //}
            //pp=p;
        }
        int numCombinaison = 0;
        for (Combinaison c : table) {
            if(c.size() > 3){
                if(c.isSerie()){
                    int compteurVersion = 1;
                    for(Pion p : c){
                        addPionUtilisableToTabs(pionsUtilisablesSerie, pionsUtilisablesSuite, p, numCombinaison, compteurVersion);
                        compteurVersion++;
                    }
                }else{
                    int compteurVersion = 1;
                    int nbPionsUtilisables = c.size()-3;
                    for(int i=1; i<=nbPionsUtilisables; i++){
                        int compteurDebut = compteurVersion;
                        int compteurFin = compteurVersion+1;
                        for(int j=0; j<i; j++){
                            Pion pDebut = c.get(j);
                            Pion pFin = c.get(c.size()-j-1);
                            addPionUtilisableToTabs(pionsUtilisablesSerie, pionsUtilisablesSuite, pDebut, numCombinaison, compteurDebut);
                            addPionUtilisableToTabs(pionsUtilisablesSerie, pionsUtilisablesSuite, pFin, numCombinaison, compteurFin);
                        }
                        compteurVersion++;
                    }
                }
            }
            numCombinaison++;
        }
        ArrayList<ArrayList<TreeMap<Pion,ArrayList<ArrayList<Integer>>>>> res = new ArrayList<>();
        res.add(new ArrayList<>());
        //Dans une TreeMap pour trier les pions et en garder qu'un exemplaire de chaque si jamais il y en a plusieurs
        pionsUtilisablesSerie.stream().map(TreeMap::new).forEach(t -> res.get(0).add(t));
        res.add(new ArrayList<>());
        pionsUtilisablesSuite.stream().map(TreeMap::new).forEach(t -> res.get(1).add(t));
        return res;
    }

    private void addPionUtilisableToTabs(ArrayList<HashMap<Pion,ArrayList<ArrayList<Integer>>>> pionsUtilisablesSerie, ArrayList<HashMap<Pion,ArrayList<ArrayList<Integer>>>> pionsUtilisablesSuite, Pion p, int numCombinaison, int compteurVersion) {
        if (!(p instanceof Joker)) {
            try {
                pionsUtilisablesSerie.get(p.getNum() - 1).get(p).add(new ArrayList<>());
            } catch (Exception e) {
                pionsUtilisablesSerie.get(p.getNum() - 1).put(p, new ArrayList<>());
                pionsUtilisablesSerie.get(p.getNum() - 1).get(p).add(new ArrayList<>());
            }
            pionsUtilisablesSerie.get(p.getNum() - 1).get(p).get(pionsUtilisablesSerie.get(p.getNum() - 1).get(p).size() - 1).add(numCombinaison);
            if (numCombinaison != -1) {
                pionsUtilisablesSerie.get(p.getNum() - 1).get(p).get(pionsUtilisablesSerie.get(p.getNum() - 1).get(p).size() - 1).add(compteurVersion);
            }
            try {
                pionsUtilisablesSuite.get(p.getCouleur().ordinal()).get(p).add(new ArrayList<>());
            } catch (Exception e) {
                pionsUtilisablesSuite.get(p.getCouleur().ordinal()).put(p, new ArrayList<>());
                pionsUtilisablesSuite.get(p.getCouleur().ordinal()).get(p).add(new ArrayList<>());
            }
            pionsUtilisablesSuite.get(p.getCouleur().ordinal()).get(p).get(pionsUtilisablesSuite.get(p.getCouleur().ordinal()).get(p).size() - 1).add(numCombinaison);
            if (numCombinaison != -1) {
                pionsUtilisablesSuite.get(p.getCouleur().ordinal()).get(p).get(pionsUtilisablesSuite.get(p.getCouleur().ordinal()).get(p).size() - 1).add(compteurVersion);
            }
        }
    }

    public ArrayList<Move> getAllsMoves() {
        for (Combinaison c : table) {
            c.sort();
        }
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
        ArrayList<ArrayList<TreeMap<Pion, ArrayList<ArrayList<Integer>>>>> tabSuiteSerie = tabSuiteSerie();
        ArrayList<TreeMap<Pion, ArrayList<ArrayList<Integer>>>> tabSuit = tabSuiteSerie.get(0);
        ArrayList<TreeMap<Pion, ArrayList<ArrayList<Integer>>>> tabSeri = tabSuiteSerie.get(1);
        //System.out.println(tabSuite);
        //System.out.println(tabSeries);
        moves = new ArrayList<>();
        //MOVES PIOCHER
        for (int i = 0; i < table.getPioche().size(); i++) {
            MovePiocher mp = new MovePiocher(table, currentPlayer, i);
            moves.add(mp);
        }
        //MAKES COMBINAISONS
        ArrayList<Move> movesCombinaisons = getMoveCombinaisons(tabSeries, tabSuite, chevalet);
        for (Move move : movesCombinaisons) {
            if (!moves.contains(move)) {
                if (currentPlayer.isDebut()) {
                    if (((MoveMakeCombinaison) move).getCombi().score() >= 30) {
                        moves.add(move);
                    }
                } else {
                    moves.add(move);
                }
            }
            ArrayList<RummikubMove> makeCombinaisons = new ArrayList<>();
            if (move instanceof MoveMakeCombinaison) {
                makeCombinaisons.add((MoveMakeCombinaison) move);
                boolean ok = true;
                chevalet = (Chevalet) currentPlayer.getChevalet().clone();
                for (Move move1 : movesCombinaisons) {
                    if (move1 instanceof MoveMakeCombinaison) {
                        for (Pion p : ((MoveMakeCombinaison) move1).getCombi()) {
                            for (Move m : makeCombinaisons) {
                                if (m instanceof MoveMakeCombinaison) {
                                    for (Pion pp : ((MoveMakeCombinaison) m).getCombi()) {
                                        if (p.equals(pp)) {
                                            ok = false;
                                        }
                                    }
                                }
                            }
                        }
                    }
                    if (ok) {
                        if (move1 instanceof MoveMakeCombinaison) {
                            makeCombinaisons.add((MoveMakeCombinaison) move1);
                            int score = 0;
                            for (RummikubMove make : makeCombinaisons) {
                                score += ((MoveMakeCombinaison) make).getCombi().score();
                            }
                            if (currentPlayer.isDebut()) {
                                if (score > 30) {
                                    Move m = new MoveSetMoves(table, currentPlayer, makeCombinaisons);
                                    if (!moves.contains(m)) {
                                        moves.add(m);
                                    }
                                }
                            } else {
                                Move m = new MoveSetMoves(table, currentPlayer, makeCombinaisons);
                                if (!moves.contains(m)) {
                                    moves.add(m);
                                }
                            }
                        }
                    }
                }
            }
        }
        //MOVES ADD PION TO COMBINAISON AND REMOVE AND ADD
        //System.out.println(tabSeries);
        if (!currentPlayer.isDebut()) {
            int index = 0;
            for (Combinaison combinaison : table) {
                combinaison.estValide();
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
                    if(!(combinaison.contientJoker()>0 && combinaison.size()==4)) {
                        int numS = combinaison.get(0).getNum();
                        ArrayList<Couleur> containList = combinaison.setContainsList();
                        for (Couleur couleur : containList) {
                            Pion p = new Pion(numS, couleur);
                            moveAddPion(moves, chevalet, index, p);
                        }
                    }
                }
                index++;
            }
            int ind = 0;
            for (Combinaison combinaison : table) {
                if (combinaison.contientJoker() > 0) {
                    Joker joker = (Joker) combinaison.get(combinaison.getIndexJoker());
                    for (Pion p : chevalet) {
                        if (joker.canReplace(p)) {
                            MoveReplaceByJoker move = new MoveReplaceByJoker(table, currentPlayer, ind, joker, true, p, -1);
                            if (!moves.contains(move)) {
                                moves.add(move);
                            }
                        }
                    }
                    int indcombinaison = 0;
                    for (Combinaison comb : table) {
                        Combinaison combclone = (Combinaison) comb.clone();
                        if (!comb.equals(combinaison)) {
                            for (Pion p : comb) {
                                if (joker.canReplace(p)) {
                                    combclone.remove(p);
                                    if (combclone.estValide()) {
                                        MoveReplaceByJoker move = new MoveReplaceByJoker(table, currentPlayer, ind, joker, false, p, indcombinaison);
                                        if (!moves.contains(move)) {
                                            moves.add(move);
                                        }
                                    }
                                }
                            }
                        }
                        indcombinaison++;
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
            if (!moves.contains(moveadd)) {
                moves.add(moveadd);
            }
        } else if (chevalet.contientJoker() > 0) {
            Joker j = (Joker) chevalet.get(chevalet.getIndexJoker()).clone();
            Combinaison c = table.get(index);
            c.estValide();
            if (c.isSerie()) {
                j.setValueJokerInSerie(c.setContainsList(), p.getNum());
            } else {
                j.setValueJokerInSuite(p.getCouleur(), p.getNum());
            }
            MoveAddPionToCombinaison moveadd = new MoveAddPionToCombinaison(table, currentPlayer, j, index);
            if (!moves.contains(moveadd)) {
                moves.add(moveadd);
            }
        }
        Table t = (Table) table.clone();
        removeAndAddPion(moves, index, t, p, false, false, 0, new Combinaison());
    }

    private ArrayList<RummikubMove> removeAndAddPion(ArrayList<Move> moves, int index, Table table1, Pion p, boolean createCombi, boolean serie, int nbFois, Combinaison c) {
        ArrayList<RummikubMove> movesRemoveAndAdd = new ArrayList<>();
        if (nbFois != 2) {
            int ind = 0;
            for (Combinaison cc : table1) {
                if (cc.size() > 3) {
                    boolean first = p.equals(cc.get(0));
                    boolean last = p.equals(cc.get(cc.size() - 1));
                    if (first || last) {
                        Combinaison ccclone = (Combinaison) cc.clone();
                        ccclone.remove(p);
                        if (ccclone.estValide()) {
                            if (index != ind) {
                                MoveRemoveAndAdd moveRemoveAndAdd = new MoveRemoveAndAdd(table, currentPlayer, ind, index, p, createCombi);
                                if (createCombi) {
                                    Combinaison cclone = (Combinaison) c.clone();
                                    cclone.add(p);
                                    if (cclone.estValide()) {
                                        movesRemoveAndAdd.add(moveRemoveAndAdd);
                                    }
                                    ArrayList<RummikubMove> removeAndAdd2 = new ArrayList<>();
                                    if (serie) {
                                        int numS = c.get(0).getNum();
                                        ArrayList<Couleur> containList = c.setContainsList();
                                        for (Couleur couleur : containList) {
                                            Pion pion = new Pion(numS, couleur);
                                            Table t = (Table) table1.clone();
                                            removeAndAdd2.addAll(removeAndAddPion(moves, table.size(), t, pion, true, true, nbFois + 1, c));
                                        }
                                    } else {
                                        Couleur couleur = c.get(0).getCouleur();
                                        int numMin = c.get(0).getNum();
                                        int numMax = c.get(c.size() - 1).getNum();
                                        if (numMin != 1) {
                                            Pion pion = new Pion(numMin - 1, couleur);
                                            Table t = (Table) table1.clone();
                                            removeAndAdd2.addAll(removeAndAddPion(moves, table.size(), t, pion, true, false, nbFois + 1, c));
                                        }
                                        if (numMax != 13) {
                                            Pion pion = new Pion(numMax + 1, couleur);
                                            Table t = (Table) table1.clone();
                                            removeAndAdd2.addAll(removeAndAddPion(moves, table.size(), t, pion, true, false, nbFois + 1, c));
                                        }
                                    }
                                    //ajout d'un pion de plus déjà sur la table dans la combinaison si pas début
                                    for (RummikubMove moveRemoveAndAdd2 : removeAndAdd2) {
                                        ArrayList<RummikubMove> move = new ArrayList<RummikubMove>();
                                        move.add(moveRemoveAndAdd);
                                        move.add(moveRemoveAndAdd2);
                                        MoveSetMoves moveSetMoves = new MoveSetMoves(table, currentPlayer, move);
                                        if (moveRemoveAndAdd2 instanceof MoveRemoveAndAdd) {
                                            cclone.add(((MoveRemoveAndAdd) moveRemoveAndAdd2).getPion());
                                            if (cclone.estValide()) {
                                                movesRemoveAndAdd.add(moveSetMoves);
                                            }
                                        }
                                    }
                                } else {
                                    if (!moves.contains(moveRemoveAndAdd)) {
                                        moves.add(moveRemoveAndAdd);
                                    }
                                }
                            }
                        }
                    }
                    ind++;
                }
            }
        }
        return movesRemoveAndAdd;
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