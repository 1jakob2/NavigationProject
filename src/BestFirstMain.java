import bestFirst.BestFirst;

import java.util.ArrayList;
import java.util.List;

public class BestFirstMain {
    public static void main(String[] args) {

        String[] startLocations = {
                "Suhr1", "Suhr2", "Suhr3", "Suhr4", "Suhr5",
                "Suhr6", "Suhr7", "Suhr8", "Suhr9", "Suhr10",
                "Suhr11", "Suhr12", "Suhr13", "Suhr14", "Suhr15",
                "Suhr16", "Suhr17", "Suhr18", "Suhr19", "Suhr20"
        };
        String endLocation = "Schänzlihalde/1";
        ArrayList<String> path = null;
        int loopCounter = 0;

        long startTime = System.nanoTime();
        for (int i = 0; i < startLocations.length; i++) {
            String start = startLocations[i % startLocations.length];
            System.out.println("Start: " + start +", End: " + endLocation);

            BestFirst bestFirst = new BestFirst(start, endLocation);
            path = bestFirst.getNode();
            loopCounter = bestFirst.getLoopCounter() + loopCounter;

            path.stream().forEach(System.out::println);
            System.out.println("Edges: " + path.size());
            System.out.println("Number of loops: " + bestFirst.getLoopCounter());
            System.out.println("------------------");
            }
        long endTime = System.nanoTime();
        double durationInSeconds = (endTime - startTime) / 1_000_000_000.0;
        System.out.println("Duration: " + durationInSeconds + " seconds");
        System.out.println("Total number of loops: " + loopCounter);
        System.out.println("------------------------------------------");

    }
}
