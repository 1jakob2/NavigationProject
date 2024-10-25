import MapData.DistanceBetween;
import MapData.MemoryUtilisation;
import aStar.AStar;
import bestFirst.BestFirst;

import java.util.List;

public class AStarMain {
    public static void main(String[] args) {
        String[] startLocations = {
                "Buempliz/Gotenstrasse/1", "Buempliz/Schwabstrasse/1", "Buempliz/Keltenstrasse/2", "Buempliz/Buchdruckerweg/1", "Buempliz/Lorbeerstrasse/1",
                "Suhr16", "Suhr17", "Buempliz/Buemplitzstrasse/1", "Suhr19", "Suhr20",
                "Buempliz/Stapfenackerstrasse/1", "Suhr22", "Suhr23", "Suhr24", "Suhr25",
                "Lagerweg/1", "Nordring/2", "Wyttenbach/4", "Birkenweg/1", "Rönerweg/1"
        };
        String[] endLocation = {"Buempliz/Nord/1", "Nordring/5", "Suhr1"};

        List<String> path = null;
        int loopCounter = 0;
        long totalDistance = 0;

        long memoryBefore = MemoryUtilisation.getMemoryUsage();
        long startTime = System.nanoTime();
        for (int i = 0; i < startLocations.length; i++) {
            String start = startLocations[i % startLocations.length];
            for (int n = 0; n < endLocation.length; n++) {
                String end = endLocation[i % endLocation.length];
                System.out.println("Start: " + start +", End: " + end);

                AStar aStar = new AStar(start, end);
                path = aStar.getPath();
                loopCounter = aStar.getLoopCounter() + loopCounter;

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
                System.out.println("Number of loops: " + aStar.getLoopCounter());
                System.out.println("------------------");
            }

        }
        double memoryUsedPercentage = MemoryUtilisation.getMemoryUsagePercentage();
        long endTime = System.nanoTime();
        double durationInSeconds = (endTime - startTime) / 1_000_000_000.0;

        long memoryAfter = MemoryUtilisation.getMemoryUsage();
        long memoryUsed = memoryAfter - memoryBefore;

        System.out.println("Total distance: " + totalDistance + " meters");
        System.out.println("Duration: " + durationInSeconds + " secondes");
        System.out.println("Total number of loops: " + loopCounter);
        System.out.println("Memory Used: " + memoryUsed + " Bytes");
        System.out.println("Memory Used Percentage: " + memoryUsedPercentage + "%");
        System.out.println("------------------------------------------");
    }
}
