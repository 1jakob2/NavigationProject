import MapData.DistanceBetween;
import breadthFirst.BreadthFirst;
import depthFirst.DepthFirst;

import java.util.ArrayList;

public class DepthFirstMain {
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
        long totalDistance = 0;

        long startTime = System.nanoTime();
        for (int i = 0; i < startLocations.length; i++) {
            String start = startLocations[i % startLocations.length];
            System.out.println("Start: " + start + ", End: " + endLocation);

            DepthFirst depthFirst = new DepthFirst(start, endLocation);
            path = depthFirst.getPath();
            loopCounter = depthFirst.getLoopCounter() + loopCounter;

            DistanceBetween distanceBetween = new DistanceBetween();
            long distance = 0;
            int currentNode = 0;
            for(int nextNode = 0; nextNode < path.size(); nextNode++){
                distance = distanceBetween.calculateDistance(path.get(currentNode), path.get(nextNode)) + distance;
                //System.out.println(path.get(currentNode) + " - " + path.get(i) + " distance: " + distance);
                if(nextNode == 0){
                    currentNode = nextNode -1; // current node has to be -1 to calculate the distance
                }
                currentNode++;
            }
            totalDistance = distance + totalDistance;

            path.stream().forEach(System.out::println);
            System.out.println("Edges: " + path.size());
            System.out.println("Distance: " + distance + " meters");
            System.out.println("Number of loops: " + depthFirst.getLoopCounter());
            System.out.println("----------------");
        }
        long endTime = System.nanoTime();
        double durationInSeconds = (endTime - startTime) / 1_000_000_000.0;

        System.out.println("Total distance: " + totalDistance + " meters");
        System.out.println("Duration: " + durationInSeconds + " seconds");
        System.out.println("Total number of loops: " + loopCounter);
        System.out.println("------------------------------------------");

    }
}
