import breadthFirst.BreadthFirst;

public class BreadthFirstMain {
    public static void main(String[] args) {
        String start = "Suhr30";
        String end = "Schänzlihalde/1";

        long startTime = System.nanoTime();

        BreadthFirst breadthFirst = new BreadthFirst(start, end);

        long endTime = System.nanoTime();
        double durationInSeconds = (endTime - startTime) / 1_000_000_000.0;
        System.out.print("Dauer: " + durationInSeconds);
    }
}
