package Game.Players;

import Game.Pion.Couleur;
import Game.Pion.Pion;
import Game.Table.Chevalet;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.zip.CheckedOutputStream;

public class Player {

    private String name;
    private Chevalet chevalet;
    private boolean endOfturn;
    private boolean endOfCombinaison;
    private boolean debut;

    public Player(String name){
        this.name = name;
        this.chevalet=new Chevalet();
        this.debut=true;
        this.endOfturn=false;
        this.endOfCombinaison = false;
    }

    public boolean isEndOfTurn() {
        return endOfturn;
    }

    public void setEndOfTurn(boolean turn) {
        this.endOfturn = turn;
    }

    public String getName(){
        return name;
    }

    public Chevalet getChevalet() {
        return chevalet;
    }

    public boolean gagne() {
        return chevalet.getNbPions() == 0;
    }

    public boolean isDebut() {
        return debut;
    }

    public void setDebutFait() {
        this.debut = false;
    }

    @Override
    public String toString(){
        return name +"\n" +chevalet;
    }

    public Pion selectPion() {
        Scanner sc = new Scanner(System.in);
        System.out.println(chevalet.toString());
        System.out.println("Selectionez le numéro du pion à jouer :");
        String num = sc.nextLine();
        int n = Integer.parseInt(num);
        while(n<0 || n > 14) {
            System.out.println("Numéro pas bon. Il doit être compris entre 1 et 13. \n Selectionez le numéro du pion à jouer :");
            num = sc.nextLine();
            n = Integer.parseInt(num);
        }
        System.out.println("Selectionez la couleur du pion à jouer :");
        String couleur = sc.nextLine();
        Couleur c = Couleur.getValueOf(couleur);
        while(c == null) {
            System.out.println("Couleur doit être comprise dans l'ensemble {rouge, jaune, bleu, noir}. \n Selectionez la couleur du pion à jouer :");
            couleur = sc.nextLine();
            c = Couleur.getValueOf(couleur);
        }
        Pion p = new Pion(n,c);
        if(chevalet.contient(p)) {
            chevalet.retirer(p);
            return p;
        }
    }

    public boolean isEndOfCombinaison() {
        return endOfCombinaison;
    }

    public void setEndOfCombinaison(Boolean b) {
        endOfCombinaison = b;
    }
}
