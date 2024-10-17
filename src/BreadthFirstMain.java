import bestFirst.BestFirst;
import breadthFirst.BreadthFirst;

import java.util.ArrayList;

public class BreadthFirstMain {
    public static void main(String[] args) {
        String[] startLocations = {
                "Suhr1", "Suhr2", "Suhr3", "Suhr4", "Suhr5",
                "Suhr6", "Suhr7", "Suhr8", "Suhr9", "Suhr10",
                "Suhr11", "Suhr12", "Suhr13", "Suhr14", "Suhr15",
                "Suhr16", "Suhr17", "Suhr18", "Suhr19", "Suhr20"
        };
        String endLocation = "Schänzlihalde/1";

        ArrayList<String> path = null;
        long startTime = System.nanoTime();
        for (int i = 0; i < 3; i++) {
            String start = startLocations[i % startLocations.length];
            BreadthFirst bestFirst = new BreadthFirst(start, endLocation);
            path = bestFirst.getPath();
            // bestFirst.printPath(path);
            System.out.println("Ecken: " + path.size());
            System.out.println("-------------");
        }
        long endTime = System.nanoTime();
        double durationInSeconds = (endTime - startTime) / 1_000_000_000.0;
        System.out.println("Dauer: " + durationInSeconds + " Sekunden");
        System.out.println("------------------------------------------");
    }
}
