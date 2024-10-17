import bestFirst.BestFirst;

public class BestFirstMain {
    public static void main(String[] args) {
        String start = "Suhr30";
        String end = "Schänzlihalde/1";

        long startTime = System.nanoTime();

        BestFirst bestFirst = new BestFirst(start, end);

        long endTime = System.nanoTime();
        double durationInSeconds = (endTime - startTime) / 1_000_000_000.0;
        System.out.print("Dauer: " + durationInSeconds);

    }
}
