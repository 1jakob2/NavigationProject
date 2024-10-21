package bestFirst;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import MapData.MapData;
import MapData.DistanceBetween;

public class BestFirst {
    private record Path(ArrayList<String> nodes, long distanceToGoal) {};

    private Map<String, ArrayList<MapData.Destination>> adjList;
    private Map<String, MapData.GPS> nodeList;
    private Path path = null;
    private DistanceBetween distanceBetween = new DistanceBetween();
    private int loopCounter = 0;// find how many times the algorithm goes through loops to find a result
    public BestFirst(String start, String end){
        MapData data = null;
        try {
            data = new MapData();
        } catch (Exception e) {
            System.out.println("Error reading map data");
        }
        adjList = data.getAdjacencyList();
        nodeList = data.getNodes();
        path = bestFirst(start, end);

    }

    public ArrayList<String> getNode(){
        return path.nodes;
    }

    private Path bestFirst(String start, String end) {
        // Create path list and add a starting path to the list
        ArrayList<Path> paths = new ArrayList<>();
        ArrayList<String> startingNodeList = new ArrayList<>(Arrays.asList(start));
        paths.add(new Path(startingNodeList, distanceBetween.calculateDistance(start, end))); // Add starting path to list
        // System.out.println(paths.stream().findAny());

        boolean solutionFound = false;
        while (!solutionFound && paths.size() > 0) {
            // Find the path whose end-node is closest to our goal
            // Remove this path from the list, and use it for the next step
            int bestIndex = bestPath(paths, end);
            // System.out.println(paths.get(bestIndex));
            Path oldPath = paths.remove(bestIndex);

            // Extend it in all possible ways, adding each new path to the end of the list
            // Omit any paths that would re-visit an old node
            ArrayList<String> oldNodes = oldPath.nodes;
            String lastNode = oldNodes.get(oldNodes.size() - 1);
            ArrayList<MapData.Destination> connectedNodes = adjList.get(lastNode);
            for (MapData.Destination destination : connectedNodes) {
                loopCounter++;
                if (!oldNodes.contains(destination.node())) {
                    ArrayList<String> newNodes = (ArrayList<String>) oldNodes.clone();
                    newNodes.add(destination.node());
                    paths.add(new Path(newNodes, distanceBetween.calculateDistance(destination.node(), end)));
                    if (destination.node().equals(end)) {
                        solutionFound = true;
                        break; // Otherwise continue searching
                    }
                }
            }
        }
        return paths.size() == 0 ? null : paths.get(paths.size() - 1);
    }

    private int bestPath(ArrayList<Path> paths, String goal) {
        int bestPath = 0;
        long smallestDistance = paths.get(0).distanceToGoal;
        for (int i = 1; i < paths.size(); i++) {
            loopCounter++;
            if (paths.get(i).distanceToGoal < smallestDistance) {
                bestPath = i;
                smallestDistance = paths.get(i).distanceToGoal;
            }
        }
        return bestPath;
    }

    public void printPath() {
        System.out.print("Final solution: ");
        for (String node : path.nodes()) System.out.printf("%s ", node);
        System.out.println();
    }

    public int getLoopCounter() {
        return loopCounter;
    }
}
