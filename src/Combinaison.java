import java.util.ArrayList;
import java.util.Collections;
import java.util.TreeSet;

public class Combinaison extends ArrayList<Pion> {
    public Combinaison() {
        super();
    }

    public boolean estValide() {
        if(size()< 3 || size() > 13)
            return false;
        sort();

        boolean memeNum= true;
        boolean suiteNum = true;
        boolean memeCouleur = true;
        boolean serieCouleur = true;

        TreeSet<Couleur> temp = new TreeSet<>();
        for(int i=0; i< size()-1; i++) {
            //Numéro en i différent de celui en i+1
            if(get(i).getNum() != get(i+1).getNum())
                memeNum=false;
            if(get(i).getNum()+1 != get(i+1).getNum())
                suiteNum=false;
            if(!get(i).getCouleur().equals(get(i+1).getCouleur()))
                memeCouleur=false;
            temp.add(get(i).getCouleur());
        }
        temp.add(get(size()-1).getCouleur());
        if(temp.size() < size())
            serieCouleur= false;
        return memeCouleur && suiteNum || serieCouleur && memeNum;
    }

    public void sort() {
        Collections.sort(this);
    }

    public String toString() {
        String str= new String();
        for(Pion p : this)
            str +=p.toString() + " ";
        return str;
    }
}
