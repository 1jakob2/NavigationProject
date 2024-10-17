import depthFirst.DepthFirst;

public class DepthFirstMain {
    public static void main(String[] args) {
        String start = "Suhr30";
        String end = "Schänzlihalde/1";

        long startTime = System.nanoTime();

        DepthFirst depth = new DepthFirst(start, end);

        long endTime = System.nanoTime();
        double durationInSeconds = (endTime - startTime) / 1_000_000_000.0;
        System.out.print("Dauer: " + durationInSeconds);
    }
}
