package bestFirst;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import MapData.MapData;

public class BestFirst {
    private record Path(ArrayList<String> nodes, long distanceToGoal) {};

    private static Map<String, ArrayList<MapData.Destination>> adjList;
    private static Map<String, MapData.GPS> nodeList;

    Path path = null;
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

    private static Path bestFirst(String start, String end) {
        // Create path list and add a starting path to the list
        ArrayList<Path> paths = new ArrayList<>();
        ArrayList<String> startingNodeList = new ArrayList<>(Arrays.asList(start));
        paths.add(new Path(startingNodeList, distanceBetween(start, end))); // Add starting path to list
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
                if (!oldNodes.contains(destination.node())) {
                    ArrayList<String> newNodes = (ArrayList<String>) oldNodes.clone();
                    newNodes.add(destination.node());
                    paths.add(new Path(newNodes, distanceBetween(destination.node(), end)));
                    if (destination.node().equals(end)) {
                        solutionFound = true;
                        break; // Otherwise continue searching
                    }
                }
            }
        }
        return paths.size() == 0 ? null : paths.get(paths.size() - 1);
    }

    private static int bestPath(ArrayList<Path> paths, String goal) {
        int bestPath = 0;
        long smallestDistance = paths.get(0).distanceToGoal;
        for (int i = 1; i < paths.size(); i++) {
            if (paths.get(i).distanceToGoal < smallestDistance) {
                bestPath = i;
                smallestDistance = paths.get(i).distanceToGoal;
            }
        }
        return bestPath;
    }

    private static long distanceBetween(String node, String goal) {
        MapData.GPS lastPos = nodeList.get(node);
        MapData.GPS goalPos = nodeList.get(goal);
        long xDiff = lastPos.east() - goalPos.east();
        long yDiff = lastPos.north() - goalPos.north();
        return xDiff * xDiff + yDiff * yDiff;
    }

    public void printPath(ArrayList<String> path) {
        System.out.print("Final solution: ");
        for (String node : path) System.out.printf("%s ", node);
        System.out.println();
    }
}
