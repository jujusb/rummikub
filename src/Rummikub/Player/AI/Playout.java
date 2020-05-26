package Rummikub.Player.AI;

import Rummikub.Player.AI.MCTS.Board;
import Rummikub.Player.AI.MCTS.Move;
import Rummikub.Player.AI.MCTS.support.PlayoutSelection;
import Rummikub.Player.AI.Moves.MovePiocher;
import Rummikub.Player.AI.Moves.MoveRemoveAndAdd;
import Rummikub.Player.AI.Moves.RummikubMove;
import Rummikub.Rummikub;

import java.util.ArrayList;
import java.util.Random;

public class Playout implements PlayoutSelection {
    public Playout() {

    }

    @Override
    public void Process(Board board) {
        Rummikub game = (Rummikub) board;
        ArrayList<Move> listMoves = game.getAllsMoves();
        RummikubMove randomMove;
        int random;
        ArrayList<Move> movesSansPioche = new ArrayList<>();
        movesSansPioche.add(null);
        ArrayList<Move> movesPioche = new ArrayList<>();
        int etape = 0;
        boolean piochepossible = true;
        while ((piochepossible || !movesSansPioche.isEmpty()) && !board.gameOver()) { //partie aléatoire jusqu'à la fin
            movesSansPioche = new ArrayList<>();
            movesPioche = new ArrayList<>();
            for (Move move : listMoves) {
                if (move instanceof MovePiocher) {
                    movesPioche.add(move);
                } else if (move instanceof MoveRemoveAndAdd) {
                    movesPioche.add(move);
                } else {
                    movesSansPioche.add(move);
                    System.out.println(move);
                }
            }
            piochepossible=this.piochepossible(movesPioche);
            if (!(movesSansPioche.isEmpty() && movesPioche.isEmpty())) {
                try {
                    if (movesSansPioche.isEmpty()) {
                        random = new Random().nextInt(movesPioche.size());
                        randomMove = (RummikubMove) movesPioche.get(random);
                    } else {
                        random = new Random().nextInt(movesSansPioche.size());
                        randomMove = (RummikubMove) movesSansPioche.get(random);
                        if (game.playerGetCurrentPlayer().isDebut()) {
                            game.playerGetCurrentPlayer().setDebutFait();
                        }
                    }
                    System.out.println(randomMove);
                    randomMove.makeRummikubMove(game);
                    System.out.println(game.getTable().toString());
                    System.out.println(game.playerGetCurrentPlayer().toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                game.changeCurrentPlayer();
                listMoves = game.getAllsMoves();
                etape++;
            }
        }
        double[] scores = game.getScore();
        if (!game.gameOver()) {
            System.out.println("Match null en "+etape+" etapes");
            if (scores[0] > scores[1]) {
                System.out.println("Joueur humain gagne de peu");
            }
            else System.out.println("IA gagne de peu");
        } else {
            if (game.playerGetCurrentPlayer().gagne()) {
                System.out.println(game.playerGetCurrentPlayer().getName() + " is Winner en"+etape+" etapes");
            } else {
                game.changeCurrentPlayer();
                System.out.println(game.playerGetCurrentPlayer().getName() + " is Winner "+etape+" etapes");
            }
        }
        System.out.println(scores[0]);
        System.out.println(scores[1]);
    }

    public boolean piochepossible(ArrayList<Move> movesPioche) {
        for(Move m : movesPioche) {
            if(m instanceof MovePiocher) {
                return true;
            }
        }
        return false;
    }
    public static void main(String[] args) {
        Rummikub rummikub = new Rummikub();
        Playout playout = new Playout();
        rummikub.changeCurrentPlayer();
        rummikub.changeCurrentPlayer();
        System.out.println("Initial 0");
        System.out.println(rummikub);
        Rummikub r= rummikub.duplicate();
        playout.Process(r);
        System.out.println("Initial 1");
        System.out.println(rummikub);
        System.out.println("Finale");
        System.out.println(r);
    }
}
/*TODO il reste ce problème à corriger
   Initial 0
Joueur humain : HUMAIN
Votre chevalet contient 14 pions : 0:Pion{1 Blu} 1:Pion{5 Blu} 2:Pion{10 Blu} 3:Pion{11 Blu}
                                   4:Pion{1 Red} 5:Pion{2 Red} 6:Pion{10 Red}
                                   7:Pion{12 Yel} 8:Pion{13 Yel}
                                   9:Pion{4 Blk} 10:Pion{4 Blk} 11:Pion{7 Blk} 12:Pion{8 Blk} 13:Joker{JColor=Blk}
ia : IA
Votre chevalet contient 14 pions : 0:Pion{12 Blu}
                                   1:Pion{2 Red} 2:Pion{6 Red} 3:Pion{9 Red} 4:Pion{12 Red}
                                   5:Pion{5 Yel} 6:Pion{8 Yel} 7:Pion{10 Yel} 8:Pion{12 Yel}
                                   9:Pion{5 Blk} 10:Pion{9 Blk} 11:Pion{11 Blk} 12:Pion{13 Blk} 13:Joker{JColor=Red}
table :
Pions dans la pioche : 78
Pions sur la table : 0
*
* MoveMakeCombinaison{combi=0:Pion{11 Blk} 1:Joker{à remplacer par ce Pion{12 Blk}} 2:Pion{13 Blk} }
Pions dans la pioche : 78
Pions sur la table : 3
Chevalet Joueur Humain : Votre chevalet contient 14 pions : 0:Pion{1 Blu} 1:Pion{5 Blu} 2:Pion{10 Blu} 3:Pion{11 Blu}
                                   4:Pion{1 Red} 5:Pion{2 Red} 6:Pion{10 Red}
                                   7:Pion{12 Yel} 8:Pion{13 Yel}
                                   9:Pion{4 Blk} 10:Pion{4 Blk} 11:Pion{7 Blk} 12:Pion{8 Blk} 13:Joker{JColor=Blk}
Chevalet IA : Votre chevalet contient 14 pions : 0:Pion{12 Blu}
                                   1:Pion{2 Red} 2:Pion{6 Red} 3:Pion{9 Red} 4:Pion{12 Red}
                                   5:Pion{5 Yel} 6:Pion{8 Yel} 7:Pion{10 Yel} 8:Pion{12 Yel}
                                   9:Pion{5 Blk} 10:Pion{9 Blk} 11:Pion{11 Blk} 12:Pion{13 Blk} 13:Joker{JColor=Red}
Combinaison 0 : 0:Pion{11 Blk} 1:Joker{à remplacer par ce Pion{12 Blk}} 2:Pion{13 Blk}

*
*
Initial 1
Joueur humain : HUMAIN
Votre chevalet contient 14 pions : 0:Pion{1 Blu} 1:Pion{5 Blu} 2:Pion{10 Blu} 3:Pion{11 Blu}
                                   4:Pion{1 Red} 5:Pion{2 Red} 6:Pion{10 Red}
                                   7:Pion{12 Yel} 8:Pion{13 Yel}
                                   9:Pion{4 Blk} 10:Pion{4 Blk} 11:Pion{7 Blk} 12:Pion{8 Blk} 13:Joker{JColor=Blk}
ia : IA
Votre chevalet contient 11 pions : 0:Pion{12 Blu}
                                   1:Pion{2 Red} 2:Pion{6 Red} 3:Pion{9 Red} 4:Pion{12 Red}
                                   5:Pion{5 Yel} 6:Pion{8 Yel} 7:Pion{10 Yel} 8:Pion{12 Yel}
                                   9:Pion{5 Blk} 10:Pion{9 Blk}
table :
Pions dans la pioche : 78
Pions sur la table : 0
Chevalet Joueur Humain : Votre chevalet contient 14 pions : 0:Pion{1 Blu} 1:Pion{5 Blu} 2:Pion{10 Blu} 3:Pion{11 Blu}
                                   4:Pion{1 Red} 5:Pion{2 Red} 6:Pion{10 Red}
                                   7:Pion{12 Yel} 8:Pion{13 Yel}
                                   9:Pion{4 Blk} 10:Pion{4 Blk} 11:Pion{7 Blk} 12:Pion{8 Blk} 13:Joker{JColor=Blk}
Chevalet IA : Votre chevalet contient 11 pions : 0:Pion{12 Blu}
                                   1:Pion{2 Red} 2:Pion{6 Red} 3:Pion{9 Red} 4:Pion{12 Red}
                                   5:Pion{5 Yel} 6:Pion{8 Yel} 7:Pion{10 Yel} 8:Pion{12 Yel}
                                   9:Pion{5 Blk} 10:Pion{9 Blk}

Finale
Joueur humain : HUMAIN
Votre chevalet contient 1 pions : 0:Pion{6 Blk}
ia : IA
Votre chevalet contient 0 pions :
table :
Pions dans la pioche : 0
Pions sur la table : 108
Chevalet Joueur Humain : Votre chevalet contient 1 pions : 0:Pion{6 Blk}
Chevalet IA : Votre chevalet contient 0 pions :
Combinaison 0 : 0:Pion{11 Blk} 1:Pion{12 Blk} 2:Pion{13 Blk}
Combinaison 1 : 0:Pion{12 Blu} 1:Pion{12 Red} 2:Pion{12 Yel}
Combinaison 2 : 0:Pion{8 Blk} 1:Pion{9 Blk} 2:Pion{10 Blk} 3:Pion{11 Blk}
Combinaison 3 : 0:Pion{6 Blu} 1:Pion{7 Blu} 2:Pion{8 Blu}
Combinaison 4 : 0:Pion{1 Blk} 1:Pion{2 Blk} 2:Pion{3 Blk} 3:Pion{4 Blk}
Combinaison 5 : 0:Pion{2 Blu} 1:Pion{2 Yel} 2:Pion{2 Blk}
Combinaison 6 : 0:Pion{4 Blu} 1:Pion{4 Red} 2:Pion{4 Yel} 3:Pion{4 Blk}
Combinaison 7 : 0:Pion{3 Blu} 1:Pion{3 Yel} 2:Pion{3 Blk}
Combinaison 8 : 0:Pion{5 Blk} 1:Pion{6 Blk} 2:Pion{7 Blk}
Combinaison 9 : 0:Pion{8 Red} 1:Pion{8 Yel} 2:Pion{8 Blk}
Combinaison 10 : 0:Pion{1 Blu} 1:Pion{1 Red} 2:Pion{1 Yel} 3:Pion{1 Blk}
Combinaison 11 : 0:Pion{5 Blu} 1:Pion{5 Red} 2:Pion{5 Yel} 3:Pion{5 Blk}
Combinaison 12 : 0:Pion{9 Blu} 1:Pion{9 Red} 2:Pion{9 Yel} 3:Pion{9 Blk}
Combinaison 13 : 0:Pion{10 Blu} 1:Pion{10 Red} 2:Pion{10 Yel} 3:Pion{10 Blk}
Combinaison 14 : 0:Pion{1 Red} 1:Pion{2 Red} 2:Pion{3 Red}
Combinaison 15 : 0:Pion{2 Red} 1:Pion{3 Red} 2:Pion{4 Red} 3:Pion{5 Red} 4:Pion{6 Red}
Combinaison 16 : 0:Pion{11 Blk} 1:Pion{12 Blk} 2:Pion{13 Blk}
Combinaison 17 : 0:Pion{6 Red} 1:Pion{7 Red} 2:Pion{8 Red} 3:Pion{9 Red} 4:Pion{10 Red} 5:Pion{11 Red}
Combinaison 18 : 0:Pion{13 Blu} 1:Pion{13 Red} 2:Pion{13 Yel} 3:Pion{13 Blk}
Combinaison 19 : 0:Pion{7 Red} 1:Pion{7 Yel} 2:Pion{7 Blk}
Combinaison 20 : 0:Pion{1 Yel} 1:Pion{2 Yel} 2:Pion{3 Yel} 3:Pion{4 Yel} 4:Pion{5 Yel} 5:Pion{6 Yel}
Combinaison 21 : 0:Pion{6 Yel} 1:Pion{7 Yel} 2:Pion{8 Yel}
Combinaison 22 : 0:Joker{à remplacer par ce Pion{8 Yel}} 1:Pion{9 Yel} 2:Pion{10 Yel} 3:Pion{11 Yel}
Combinaison 23 : 0:Pion{11 Blu} 1:Pion{11 Red} 2:Pion{11 Yel}
Combinaison 24 : 0:Pion{1 Blu} 1:Pion{2 Blu} 2:Pion{3 Blu} 3:Pion{4 Blu}
Combinaison 25 : 0:Pion{12 Blu} 1:Pion{12 Red} 2:Pion{12 Yel} 3:Pion{12 Blk}
Combinaison 26 : 0:Pion{13 Blu} 1:Pion{13 Red} 2:Pion{13 Yel} 3:Joker{à remplacer par un pion de num 13 de couleur[Blk]}
Combinaison 27 : 0:Pion{5 Blu} 1:Pion{6 Blu} 2:Pion{7 Blu} 3:Pion{8 Blu} 4:Pion{9 Blu} 5:Pion{10 Blu} 6:Pion{11 Blu}
 * */