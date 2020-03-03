import java.util.Observable;
import java.util.Observer;

public class Player {

    private String name;
    private Chevalet chevalet;

    public Player(String name){
        this.name = name;
        this.chevalet=new Chevalet();
    }

    public String getName(){
        return name;
    }

    public Chevalet getChevalet() {
        return chevalet;
    }

    @Override
    public String toString(){
        return name +"\n" +chevalet;
    }
}
