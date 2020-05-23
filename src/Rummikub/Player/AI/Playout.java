package Rummikub.Player.AI;

import Rummikub.Player.AI.MCTS.Board;
import Rummikub.Player.AI.MCTS.Move;
import Rummikub.Player.AI.MCTS.support.PlayoutSelection;
import Rummikub.Player.AI.Moves.MovePiocher;
import Rummikub.Player.AI.Moves.RummikubMove;
import Rummikub.Rummikub;

import java.util.ArrayList;
import java.util.Random;

public class Playout implements PlayoutSelection {
    public Playout() {

    }

    @Override
    public void Process(Board board) {
        Rummikub game = (Rummikub)board;
        ArrayList<Move> listMoves= new ArrayList<>();
        RummikubMove randomMove;
        int random;
        ArrayList<Move> movesSansPioche = new ArrayList<>();
        ArrayList<Move> movesPioche = new ArrayList<>();
        int etape = 0;
        while(!board.gameOver()||(!(movesPioche.isEmpty() && movesSansPioche.isEmpty()))) { //TODO partie aléatoire jusqu'à la fin
            try {
                listMoves = game.getAllsMoves();
                movesSansPioche = new ArrayList<>();
                movesPioche = new ArrayList<>();
                for(Move move:listMoves) {
                    if (!(move instanceof MovePiocher)) {
                        movesSansPioche.add(move);
                    } else {
                        movesPioche.add(move);
                    }
                }
                if(movesSansPioche.isEmpty()) {
                    random = new Random().nextInt(movesPioche.size());
                    randomMove = (RummikubMove) movesPioche.get(random);
                    if(game.playerGetCurrentPlayer().isDebut()) {
                        game.playerGetCurrentPlayer().setDebutFait();
                    }
                } else {
                    random = new Random().nextInt(movesSansPioche.size());
                    randomMove = (RummikubMove) movesSansPioche.get(random);
                }
                System.out.println(randomMove);
                randomMove.makeRummikubMove(game);
                System.out.println(game.getTable().toString());
                System.out.println(game.playerGetCurrentPlayer().toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
            game.changeCurrentPlayer();
            etape++;
        }
        if(listMoves.size()==0) {
            System.out.println("Match null");
        }
        System.out.println(etape);
    }

    public static void main(String[] args) {
        Rummikub rummikub = new Rummikub();
        Playout playout = new Playout();
        playout.Process(rummikub);
    }
}
