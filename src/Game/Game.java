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
                            backup();//TODO backup all game
                        }
                    }
                } else {
                    while(!currentPlayer.isEndOfTurn()) {
                        System.out.println("Que souhaite-tu jouer ?");
                        System.out.println("Une nouvelle combinaison (1) ? " +
                                "Ajouter un pion à une combinaison (2) ?"+
                                "Retirer un pion d'une combinaison pour l'ajouter à une autre (3) ?");
                        int jeu = sc.nextInt();
                        if(jeu==1) {
                            jouerUneCombinaison();
                        }
                        if(jeu==2) {
                            System.out.println(currentPlayer);
                            System.out.println("Selectionez le pion du chevalet à jouer :");
                            int n = sc.nextInt();
                            if(n<0 || n > currentPlayer.getChevalet().size()) {
                                System.out.println("Ce numéro n'est pas valable. Il doit être compris entre 0 et "+ currentPlayer.getChevalet().size() + " pour être contenu dans ton chevalet. \n Selectionez le  pion du chevalet à jouer :");
                                n = sc.nextInt();
                            }
                            Pion p = currentPlayer.getChevalet().get(n);
                            try {
                                currentPlayer.getChevalet().retirer(p);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            System.out.println(table + "Selectionez la combinaison où doit être ajouté le pion :");
                            int c = sc.nextInt();
                            if(c<0 || c > table.size()) {
                                System.out.println("Ce numéro n'est pas valable. Il doit être compris entre 0 et "+ table.size() + " pour être contenu sur la table. \n Selectionez la combinaison où doit être ajouté le pion :");
                                c = sc.nextInt();
                            }
                            table.ajoutALaCombinaison(table.get(c),p);
                        }
                        if(jeu==3) {
                            System.out.println(currentPlayer);
                            System.out.println(table + "Selectionez la combinaison où doit être retirée le pion :");
                            int c = sc.nextInt();
                            if(c<0 || c > table.size()) {
                                System.out.println("Ce numéro n'est pas valable. Il doit être compris entre 0 et "+ table.size() + " pour être contenu sur la table. \n Selectionez la combinaison où doit être retiré le pion :");
                                c = sc.nextInt();
                            }
                            Combinaison cc = table.get(c);
                            System.out.println(cc + "Selectionez la position du pion à retirer de cette combinaison :");
                            int p = sc.nextInt();
                            if(p<0 || p > cc.size()) {
                                System.out.println("Ce numéro n'est pas valable. Il doit être compris entre 0 et "+ cc.size() + " pour être contenu dans cette combinaison. \n Selectionez la position du pion à retirer de cette combinaison :");
                                p = sc.nextInt();
                            }
                            Pion pp = cc.get(p);
                            table.retirerDeCombinaison(cc,pp);
                            System.out.println(table + "Selectionez la combinaison où doit être ajouté le pion :");
                            int c2 = sc.nextInt();
                            if(c2<0 || c2 > table.size()) {
                                System.out.println("Ce numéro n'est pas valable. Il doit être compris entre 0 et "+ table.size() + " pour être contenu sur la table. \n Selectionez la combinaison où doit être ajouté le pion :");
                                c2 = sc.nextInt();
                            }
                            table.ajoutALaCombinaison(table.get(c2),pp);
                        }
                        System.out.println("is end of turn (Y/n) ?");
                        String bb = sc.nextLine();
                        if(bb.toUpperCase().equals("Y")) {
                            currentPlayer.setEndOfTurn(true);
                        }
                    }
                    if(!table.estValide()) {
                        backup();
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