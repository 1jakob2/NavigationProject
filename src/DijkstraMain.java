import aStar.AStar;
import dijkstra.Dijkstra;

import java.util.List;

public class DijkstraMain {

    public static void main(String[] args) {
        String[] startLocations = {
                "Suhr1", "Suhr2", "Suhr3", "Suhr4", "Suhr5",
                "Suhr6", "Suhr7", "Suhr8", "Suhr9", "Suhr10",
                "Suhr11", "Suhr12", "Suhr13", "Suhr14", "Suhr15",
                "Suhr16", "Suhr17", "Suhr18", "Suhr19", "Suhr20"
        };
        String endLocation = "Schänzlihalde/1";
        List<String> path = null;
        int loopCounter = 0;

        long startTime = System.nanoTime();
        for (int i = 0; i < startLocations.length; i++) {
            String start = startLocations[i % startLocations.length];
            System.out.println("Start: " + start + ", End: " + endLocation);

            Dijkstra dijkstra = new Dijkstra(start, endLocation);
            path = dijkstra.getPath();
            loopCounter = dijkstra.getLoopCounter() + loopCounter;

            path.stream().forEach(System.out::println);
            System.out.println("Edges: " + path.size());
            System.out.println("Number of loops: " + dijkstra.getLoopCounter());
            System.out.println("------------------");
        }
        long endTime = System.nanoTime();
        double durationInSeconds = (endTime - startTime) / 1_000_000_000.0;
        System.out.println("Duration: " + durationInSeconds + " secondes");
        System.out.println("Total number of loops: " + loopCounter);
        System.out.println("------------------------------------------");
    }
}
