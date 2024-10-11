import bestFirst.BestFirst;
import breadthFirst.BreadthFirst;

public class Main {
    public static void main(String[] args) {
        String start = "Rönerweg/1";
        String end = "Schänzlihalde/1";
        //BreadthFirst breadthFirst = new BreadthFirst(start, end);
        BestFirst bestFirst = new BestFirst(start, end);
    }
}