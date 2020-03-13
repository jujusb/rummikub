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

    public void reset(){ //TODO il faut reset à chaque fois que l'on récupère un joker du plateau
        num = 0;
        containsList.clear();
    }

    public void setValueJoker() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Souhaitez vous utiliser le joker dans une suite (1) ? dans une série (2) ?");
        int use = sc.nextInt();
        System.out.println("Quelle couleur souhaitez vous utiliser pour le joker ?");
        String c = sc.nextLine();
        this.couleur = Couleur.getValueOf(c);
        System.out.println("Quelle numéro souhaitez vous utiliser pour le joker ?");
        int n = sc.nextInt();
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

    //TODO coder l'utilisation des jokers et leur moyens de se faire remplacer

    @Override
    public String toString() {
        if(useSerie) {
            return "Joker{à remplacer par ce " +
                    super.toString()+
                    '}';
        } else if(useSuite) {
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
