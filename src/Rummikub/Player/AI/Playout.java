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
        RummikubMove randomMove = null;
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
                    //System.out.println(move);
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
                    //System.out.println(randomMove);
                    randomMove.makeRummikubMove(game);
                    System.out.println(game.getTable().toString());
                    //System.out.println(game.playerGetCurrentPlayer().toString());
                } catch (Exception e) {
                    System.out.println(randomMove);
                    System.out.println(game.playerGetCurrentPlayer());
                    e.printStackTrace();
                }
                game.changeCurrentPlayer();
                listMoves = game.getAllsMoves();
                etape++;
            }
        }
        double[] scores = game.getScore();
        //if (!game.gameOver()) {
        //    System.out.println("Match null en "+etape+" etapes");
        //    if (scores[0] > scores[1]) {
        //        System.out.println("Joueur humain gagne de peu");
        //    }
        //    else System.out.println("IA gagne de peu");
        //} else {
        //    if (game.playerGetCurrentPlayer().gagne()) {
        //        //System.out.println(game.playerGetCurrentPlayer().getName() + " is Winner en"+etape+" etapes");
        //    } else {
        //        game.changeCurrentPlayer();
        //        //System.out.println(game.playerGetCurrentPlayer().getName() + " is Winner "+etape+" etapes");
        //    }
        //}
        //System.out.println(scores[0]);
        //System.out.println(scores[1]);
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
        System.out.println("Initial 0");
        System.out.println(rummikub);
        long start = System.nanoTime();
        Rummikub r = null;
        for(int i =0 ; i<10 ; i++) {
            r= rummikub.duplicate();
            playout.Process(r);
        }
        System.out.println((System.nanoTime()-start)/1000000.0);
        //System.out.println("Initial 1");
        //System.out.println(rummikub);
        System.out.println("Finale");
        System.out.println(r);
    }
}