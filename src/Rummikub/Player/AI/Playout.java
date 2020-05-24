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
        movesSansPioche.add(new MovePiocher(game.getTable(), game.playerGetCurrentPlayer(),1));
        ArrayList<Move> movesPioche = new ArrayList<>();
        int etape = 0;
        boolean piochepossible = true;
        while ((piochepossible || !movesSansPioche.isEmpty()) && !board.gameOver()) { //TODO partie aléatoire jusqu'à la fin
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
        if (listMoves.size() == 0) {
            System.out.println("Match null");
        }
        System.out.println(etape);
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
        playout.Process(rummikub);
    }
}
