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
        playerHumain = new Player("HUMAIN");
        ia = new IA();
        table = new Table();
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
        Scanner sc = new Scanner(System.in);
        while(!(playerHumain.gagne() || ia.gagne())) {
            System.out.println(table);
            System.out.println(currentPlayer);
            System.out.println("Passe ton tour et pioche (Y/n) ?");
            String b = sc.nextLine();
            if(b.toUpperCase().equals("Y")) {
                currentPlayer.getChevalet().ajouter(table.piocherPion());
            } else {
                if(currentPlayer.isDebut()) {
                    List<Combinaison> list = new ArrayList<>();
                    while(!currentPlayer.isEndOfTurn() && table.estValide()) {
                        Combinaison c = jouerUneCombinaison();
                        list.add(c);
                    }
                    if(table.estValide()) {
                        int compte = table.comptePointsCombinaisons(list);
                        if(compte>=30) {
                            currentPlayer.setDebutFait();
                        } else {
                            this.backup();
                        }
                    }
                } else {
                    while(!currentPlayer.isEndOfTurn() && table.estValide()) {
                        jouerUneCombinaison();
                    }
                }
            }
            changeCurrentPlayer();
        }
    }

    private void backup() {
    }

    private Combinaison jouerUneCombinaison() {
        Scanner sc = new Scanner(System.in);
        Combinaison c = table.nouvelleCombinaison(currentPlayer.selectPion());
        table.ajoutALaCombinaison(c,currentPlayer.selectPion());
        while(!currentPlayer.isEndOfCombinaison()) {
            table.ajoutALaCombinaison(c,currentPlayer.selectPion());
            System.out.println("is end of combinaison (Y/n) ?");
            String b = sc.nextLine();
            if(b.toUpperCase().equals("Y")) {
                currentPlayer.setEndOfCombinaison(true);
            }
        }
        currentPlayer.setEndOfCombinaison(false);
        System.out.println("is end of turn (Y/n) ?");
        String b = sc.nextLine();
        if(b.toUpperCase().equals("Y")) {
            currentPlayer.setEndOfTurn(true);
        }
        return c;
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