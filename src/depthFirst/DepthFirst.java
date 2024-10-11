package depthFirst;

import MapData.MapData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DepthFirst {

    private static Map<String, ArrayList<MapData.Destination>> adjList = new HashMap<>();

    public DepthFirst(String current, String end){
        MapData data = null;
        try {
            data = new MapData();
        } catch (Exception e) {
            System.out.println("Error reading map data");
        }
        adjList = data.getAdjacencyList();
        System.out.println("Fastest Route Found");

        ArrayList<String> path = depthFirst(current, end);
        printPath(path);
    }

    private void printPath(ArrayList<String> path) {
        System.out.print("Final solution: ");
        for (String node : path) System.out.printf("%s ", node);
        System.out.println();
    }

    private static ArrayList<String> depthFirst(String start, String end) {
        ArrayList<String> path = new ArrayList<>();
        path.add(start); // Add starting node to path

        // Search using a recursive method
        return depthFirstRecursive(path, start, end);
    }

    private static ArrayList<String> depthFirstRecursive(ArrayList<String> path, String current, String end) {
        if (current.equals(end)) { // Base case - we are finished!
            // Nothing to do...
        } else {// Recursive case - add a node to the path
            ArrayList<MapData.Destination> connectedNodes = adjList.get(current);
            for (MapData.Destination c : connectedNodes) {
                if (!haveBeenThere(path, String.valueOf(c.node()))) {
                    path.add(String.valueOf(c.node()));
                    depthFirstRecursive(path, String.valueOf(c.node()), end);
                    // If we have a solution, stop the loop!
                    if (path.get(path.size() - 1).equals(end)) break;

                    // If we are here, remove the last node from the path, so we can add a different one
                    path.remove(path.size() - 1);
                }
            }
        }
        return path;
    }

    private static boolean haveBeenThere(ArrayList<String> path, String where) {
        boolean found = false;
        for (String node : path) {
            found = found || node == where;
        }
        return found;
    }

}
