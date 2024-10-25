import MapData.DistanceBetween;
import MapData.MemoryUtilisation;
import bestFirst.BestFirst;
import breadthFirst.BreadthFirst;

import java.util.ArrayList;

public class BreadthFirstMain {
    public static void main(String[] args) {
        String[] startLocations = {
                "Buempliz/Gotenstrasse/1", "Buempliz/Schwabstrasse/1", "Buempliz/Keltenstrasse/2", "Buempliz/Buchdruckerweg/1", "Buempliz/Lorbeerstrasse/1",
                "Suhr16", "Suhr17", "Buempliz/Buemplitzstrasse/1", "Suhr19", "Suhr20",
                "Buempliz/Stapfenackerstrasse/1", "Suhr22", "Suhr23", "Suhr24", "Suhr25",
                "Lagerweg/1", "Nordring/2", "Wyttenbach/4", "Birkenweg/1", "Rönerweg/1"
        };
        String[] endLocation = {"Buempliz/Nord/1", "Suhr1"};
        ArrayList<String> path = null;
        int loopCounter = 0;
        long totalDistance = 0;

        long memoryBefore = MemoryUtilisation.getMemoryUsage();
        long startTime = System.nanoTime();
        for (int i = 0; i < startLocations.length; i++) {
            String start = startLocations[i % startLocations.length];
            for (int n = 0; n < endLocation.length; n++) {
                String end = endLocation[n % endLocation.length];
                System.out.println("Start: " + start + ", End: " + end);

                BreadthFirst breadthFirst = new BreadthFirst(start, end);
                path = breadthFirst.getPath();
                loopCounter = breadthFirst.getLoopCounter() + loopCounter;

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
                System.out.println("Number of loops: " + breadthFirst.getLoopCounter());
                System.out.println("-------------");
            }

        }
        double memoryUsedPercentage = MemoryUtilisation.getMemoryUsagePercentage();
        long endTime = System.nanoTime();
        double durationInSeconds = (endTime - startTime) / 1_000_000_000.0;

        long memoryAfter = MemoryUtilisation.getMemoryUsage();
        long memoryUsed = memoryAfter - memoryBefore;

        System.out.println("Total distance: " + totalDistance + " meters");
        System.out.println("Duration: " + durationInSeconds + " seconds");
        System.out.println("Total number of loops: " + loopCounter);
        System.out.println("Memory Used: " + memoryUsed + " Bytes");
        System.out.println("Memory Used Percentage: " + memoryUsedPercentage + "%");
        System.out.println("------------------------------------------");
    }
}
