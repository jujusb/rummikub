package Game.Pion;

import com.sun.xml.internal.bind.v2.runtime.output.StAXExStreamWriterOutput;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Joker extends Pion {
    private boolean useSuite ;
    private boolean useSerie ;
    private List<Couleur> containsList;
    private Couleur couleurJoker ;

    public Joker(Couleur c) {
        super(0, c);
        useSerie = false ;
        useSuite = false ;
        couleurJoker = c;
        containsList=new ArrayList<>();
    }

    public Joker() {
    }

    public boolean getUseSerie(){
        return useSerie;
    }

    public boolean getUseSuite(){
        return useSuite;
    }

    public void reset(){
        num = 0;
        containsList.clear();
    }

    public void setValueJoker() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Souhaitez vous utiliser le joker dans une suite (1) ? dans une série (2) ?");
        int use = sc.nextInt();
        System.out.println("Quelle couleur souhaitez vous utiliser pour le joker ? Jaune (1) Bleu (2) Noir (3) Rouge (4)");
        int c = sc.nextInt();
        if(c == 1){
            this.couleur = Couleur.JAUNE;
        }else{
            if(c == 2){
                this.couleur = Couleur.BLEU;
            }else{
                if(c == 3){
                    this.couleur = Couleur.NOIR;
                }else{
                        this.couleur = Couleur.ROUGE;
                }
            }
        }
        System.out.println("Quelle numéro souhaitez vous utiliser pour le joker ?");
        int n = sc.nextInt();
        if(n>13 || n<1){
            System.out.println("Le numéro doit être compris entre 1 et 13.");
            n = sc.nextInt();
        }
        this.num = n;
        if(use==1){
            useSuite = true;
        } else {
            useSerie = true;
        }
    }


    public void setContainsList(Game.Table.Combinaison c){
        List<Couleur> couleursCombinaison = new ArrayList<Couleur>();
        for(Pion pi : c){
            if(!(pi instanceof Joker)){
                couleursCombinaison.add(pi.getCouleur());
            }
        }
        for(Couleur col : Couleur.values()){
            if(!(couleursCombinaison.contains(col))){
                containsList.add(col);
            }
        }
    }

    public void changeValue(int n, Couleur c) {
        num=n;
        couleur=c;
        useSuite=true;
    }

    public boolean canReplace(Pion p) {
        return useSuite && containsList.contains(p.couleur) && p.num == num
                || useSerie && containsList.contains(p.couleur) && p.num == num;
    }


    @Override
    public String toString() {
        if(useSuite) {
            return "Joker{à remplacer par ce " +
                    super.toString()+
                    '}';
        } else if(useSerie) {
            return "Joker{à remplacer par un pion de num " +
                    num+" de couleur"+
                    containsList+
                    '}';
        } else {
            return "Joker{" +
                    "JColor=" + couleurJoker +
                    '}';
        }
    }

    @Override
    public Object clone() {
        Joker c = new Joker();
        c.useSuite=useSuite;
        c.containsList=containsList;
        c.useSerie=useSerie;
        c.couleurJoker= couleurJoker;
        return c;
    }

    public static void main(String[] args) {
        Joker j = new Joker(Couleur.NOIR);
        System.out.println(j.toString());
    }
}
