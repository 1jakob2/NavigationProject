import breadthFirst.BreadthFirst;

public class Main {
    public static void main(String[] args) {
        String start = "Neuengasse/1";
        String end = "Schänzlihalde/1";
        BreadthFirst breadthFirst = new BreadthFirst(start, end);
    }
}