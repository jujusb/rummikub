package Game.Players;

import Game.Pion.Couleur;
import Game.Pion.Joker;
import Game.Pion.Pion;
import Game.Table.Chevalet;
import Game.Table.Combinaison;
import Game.Table.Table;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Player {

    String name;


    Chevalet chevalet;
    boolean endOfTurn;
    boolean endOfCombinaison;
    boolean debut;
    Table table;

    public Player(String name, Table table) {
        this.name = name;
        this.chevalet = new Chevalet();
        this.debut = true; //à mettre à true pour démarrer le jeu (utilisé à false pour faire des tests :) )
        this.endOfTurn = false;
        this.endOfCombinaison = false;
        this.table = table;
    }

    public Table getTable() {
        return table;
    }

    public Player() {
    }

    public boolean jouer() {
        Scanner sc = new Scanner(System.in);
        int compteur = 0;
        while (!isEndOfTurn()) {
            System.out.println(table);
            System.out.println(chevalet);
            System.out.println("Que souhaites-tu jouer ?");
            System.out.println("Passer tour (0) ?" +
                    "Une nouvelle combinaison (1) ? " +
                    "Ajouter un pion à une combinaison (2) ?" +
                    "Retirer un pion d'une combinaison pour l'ajouter à une autre (3) ?" +
                    "Remplacer un joker ? (4)");
            int jeu = sc.nextInt();
            if (jeu == 0) {
                setEndOfTurn(true);
                if (compteur == 0) {
                    chevalet.ajouter(table.piocherPion());
                }
                return false;
            }
            if (jeu == 1) {
                Combinaison c = jouerUneCombinaison();
                if (c == null) {
                    return true;
                }
            }
            if (jeu == 2) {
                Pion p = selectPion();
                if (!AjoutACombinaison(p)) return true;
            }
            if (jeu == 3) {
                System.out.println(this);
                System.out.println(table + "Selectionez la combinaison où doit être retirée le pion :");
                int c = sc.nextInt();
                if (c == -1) {
                    return true;
                }
                if (c < 0 || c > table.size()) {
                    System.out.println("Ce numéro n'est pas valable. Il doit être compris entre 0 et " + table.size() + " pour être contenu sur la table. \n Selectionez la combinaison où doit être retiré le pion :");
                    c = sc.nextInt();
                }
                Combinaison cc = table.get(c);
                System.out.println(cc + "Selectionez la position du pion à retirer de cette combinaison :");
                int p = sc.nextInt();
                if (p == -1) {
                    return true;
                }
                if (p < 0 || p > cc.size()) {
                    System.out.println("Ce numéro n'est pas valable. Il doit être compris entre 0 et " + cc.size() + " pour être contenu dans cette combinaison. \n Selectionez la position du pion à retirer de cette combinaison :");
                    p = sc.nextInt();
                }
                Pion pp = cc.get(p);
                table.retirerDeCombinaison(cc, pp);
                if (!AjoutACombinaison(pp)) return true;
            }
            if (jeu == 4) {
                System.out.println(this);
                System.out.println(table + "Selectionez la combinaison où doit être retirée le joker :");
                int c = sc.nextInt();
                if (c == -1) {
                    return true;
                }
                if (c < 0 || c > table.size()) {
                    System.out.println("Ce numéro n'est pas valable. Il doit être compris entre 0 et " + table.size() + " pour être contenu sur la table. \n Selectionez la combinaison où doit être retiré le pion :");
                    c = sc.nextInt();
                    if (c == -1) {
                        return true;
                    }
                }
                if (table.get(c).contientJoker()==0) {
                    System.out.println("Cette combinaison ne contient pas de joker. \n Selectionez la combinaison où doit être retiré le joker :\"");
                    c = sc.nextInt();
                    if (c == -1) {
                        return true;
                    }
                }
                Combinaison cc = table.get(c);
                //System.out.println(cc + "Selectionez la position du joker à retirer de cette combinaison :");
                //int p = sc.nextInt();
                //if (p == -1) {
                //    return true;
                //}
                //if (p < 0 || p > cc.size()) {
                //    System.out.println("Ce numéro n'est pas valable. Il doit être compris entre 0 et " + cc.size() + " pour être contenu dans cette combinaison. \n Selectionez la position du pion à retirer de cette combinaison :");
                //    p = sc.nextInt();
                //}
                //if (!(cc.get(p) instanceof Joker)) {
                //    System.out.println("Ce pion n'est pas un joker. \n Selectionez un joker :\"");
                //    p = sc.nextInt();
                //}
                Pion pp = cc.get(cc.getIndexJoker());
                Pion select = selectPion();
                if(select == null) {
                    return true;
                }
                while(!((Joker) pp).canReplace(select)) {
                    chevalet.ajouter(select);
                    System.out.println("Selectionner un pion qui correspond à la valeur du joker.");
                    select = selectPion();
                    if(select == null) {
                        return true;
                    }
                }
                table.retirerDeCombinaison(cc, pp);
                chevalet.ajouter(pp);
                ((Joker) pp).reset();
                table.ajoutALaCombinaison(cc, select);
                //System.out.println(table + "Selectionez la combinaison où doit être ajouté le joker :");
                //int ccc = sc.nextInt();
                //if (ccc < 0 || ccc > table.size()) {
                //    System.out.println("Ce numéro n'est pas valable. Il doit être compris entre 0 et " + table.size() + " pour être contenu sur la table. \n Selectionez la combinaison où doit être ajouté le joker :");
                //    ccc = sc.nextInt();
                //}
                //table.ajoutALaCombinaison(table.get(ccc), pp);
                //useJoker(pp);
                //setContainListForJoker(table.get(ccc));
            }
            compteur++;
        }
        return false;
    }

    private boolean AjoutACombinaison(Pion pp) {
        Scanner sc = new Scanner(System.in);
        System.out.println(table + "Selectionez la combinaison où doit être ajouté le pion :");
        int c2 = sc.nextInt();
        if (c2 == -1) {
            return false;
        }
        if (c2 < 0 || c2 > table.size()) {
            System.out.println("Ce numéro n'est pas valable. Il doit être compris entre 0 et " + table.size() + " pour être contenu sur la table. \n Selectionez la combinaison où doit être ajouté le pion :");
            c2 = sc.nextInt();
        }
        Combinaison c = table.get(c2);
        table.ajoutALaCombinaison(c, pp);
        setContainListForJoker(c);
        return true;
    }


    public boolean isEndOfTurn() {
        return endOfTurn;
    }

    public void setEndOfTurn(boolean turn) {
        this.endOfTurn = turn;
    }

    public String getName() {
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
    public String toString() {
        return name + "\n" + chevalet;
    }

    public Pion selectPion() {
        Scanner sc = new Scanner(System.in);
        System.out.println(chevalet.toString());
        System.out.println("Selectionez le pion du chevalet à jouer :");
        int n = sc.nextInt();
        if (n == -1) {
            return null;
        }
        while (n < 0 || n > chevalet.size()) {
            System.out.println("Ce numéro n'est pas valable. Il doit être compris entre 0 et " + chevalet.size() + " pour être contenu dans ton chevalet. \n Selectionez le  pion du chevalet à jouer :");
            n = sc.nextInt();
        }
        Pion p = chevalet.get(n);
        if (p instanceof Joker) {
            useJoker(p);
            try {
                chevalet.retirer(p);
            } catch (Exception e) {
                System.out.println(e);
            }
            return p;
        }
        if (chevalet.contient(p)) {
            try {
                chevalet.retirer(p);
            } catch (Exception e) {
                System.out.println(e);
            }
            return p;
        }
        return null;
    }

    private void useJoker(Pion p) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Souhaitez vous utiliser le joker dans une suite (1) ? dans une série (2) ?");
        int use = sc.nextInt();
        int n3;
        System.out.println("Quelle numéro souhaitez vous utiliser pour le joker ?");
        n3 = sc.nextInt();
        if (n3 > 13 || n3 < 1) {
            System.out.println("Le numéro doit être compris entre 1 et 13.");
            n3 = sc.nextInt();
        }
        System.out.println("Quelle couleur souhaitez vous utiliser pour le joker ? Jaune (1) Bleu (2) Noir (3) Rouge (4)");
        int c = sc.nextInt();
        if(c<0 || c>4) {
            System.out.println("Cette couleur n'est pas valable. Quelle couleur souhaitez vous utiliser pour le joker ? Jaune (1) Bleu (2) Noir (3) Rouge (4)");
            c = sc.nextInt();
        }
        ((Joker) p).setValueJoker(use, c, n3);
    }

    public boolean isEndOfCombinaison() {
        return endOfCombinaison;
    }

    public void setEndOfCombinaison(Boolean b) {
        endOfCombinaison = b;
    }

    public List<Combinaison> jouerdebut() {
        List<Combinaison> list = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        System.out.println("Passer tour (0) ?" +
                "Jouer une nouvelle combinaison à 30 points minimum (1 ou plus) ?");
        int jeu = sc.nextInt();
        if (jeu == 0) {
            Pion p = table.piocherPion();
            chevalet.ajouter(p);
            System.out.println(p);
            return null;
        } else {
            while (!isEndOfTurn() && table.estValide()) {
                Combinaison c = jouerUneCombinaison();
                if (c == null) {
                    return null;
                }
                list.add(c);
            }
        }
        return list;
    }

    private Combinaison jouerUneCombinaison() {
        Scanner sc = new Scanner(System.in);
        Pion p = selectPion();
        if (p == null) {
            return null;
        }
        Combinaison c = table.nouvelleCombinaison(p);
        System.out.println(c);
        if (debut) {
            Pion p2 = selectPion();
            if (p2 == null) {
                return null;
            }
            table.ajoutALaCombinaison(c, p2);
            System.out.println(c);
        } else {
            System.out.println("is end of combinaison (Y/n) ?");
            String b = sc.nextLine();
            if (b.toUpperCase().equals("Y")) {
                setEndOfCombinaison(true);
            }
        }
        Pion pp;
        while (!isEndOfCombinaison()) {
            pp = selectPion();
            if (pp == null) {
                return null;
            }
            table.ajoutALaCombinaison(c, pp);
            System.out.println(c);
            System.out.println("is end of combinaison (Y/n) ?");
            String b = sc.nextLine();
            if (b.toUpperCase().equals("Y")) {
                setEndOfCombinaison(true);
            }
        }
        setEndOfCombinaison(false);
        setContainListForJoker(c);
        System.out.println(c);
        System.out.println("is end of turn (Y/n) ?");
        String b = sc.nextLine();
        if (b.toUpperCase().equals("Y")) {
            setEndOfTurn(true);
        }
        return c;
    }

    public void setContainListForJoker(Combinaison c) {
        if (c.contientJoker()>0) { //si la combinaison contient un joker on regarde si celle-ci est une série afin de set la containsList
            Pion joker = null;
            for (Pion pi : c) {
                if (pi instanceof Joker) {
                    joker = pi;
                }
            }
            if (((Joker) joker).getUseSerie()) {
                c.setContainsList();
            }
        }
    }

    @Override
    public Object clone() {
        Player player = new Player();
        player.debut = debut;
        player.chevalet = (Chevalet) chevalet.clone();
        player.endOfCombinaison = endOfCombinaison;
        player.endOfTurn = endOfTurn;
        player.name = name;
        return player;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setChevalet(Chevalet chevalet) {
        this.chevalet = chevalet;
    }

    public void setEndOfCombinaison(boolean endOfCombinaison) {
        this.endOfCombinaison = endOfCombinaison;
    }

    public void setDebut(boolean debut) {
        this.debut = debut;
    }

}
