package breadthFirst;

import java.util.ArrayList;
import java.util.Map;

import MapData.MapData;

public class BreadthFirst {

    private static Map<String, ArrayList<MapData.Destination>> adjList;
    private static Map<String, MapData.GPS> nodeList;
    ArrayList<String> path = null;
    public BreadthFirst(String start, String end){
        // System.out.println("BreathFirst Constructor");
        // System.out.println("start: " + start + " - " + "end: " + end);
        MapData data = null;
        try {
            data = new MapData();
        } catch (Exception e) {
            System.out.println("Error reading map data");
        }
        adjList = data.getAdjacencyList();
        path = breadthFirst(start, end);
    }

    public ArrayList<String> getPath(){
        return path;
    }

    private static ArrayList<String> breadthFirst(String start, String end) {
        ArrayList<ArrayList<String>> paths = new ArrayList<>();
        ArrayList<String> startingPath = new ArrayList<>();
        startingPath.add(start);
        paths.add(startingPath); // Add starting node to path
        paths.stream().forEach(System.out::println);

        boolean solutionFound = false;
        while (!solutionFound && paths.size() > 0) {
            // Remove the first element from the paths
            ArrayList<String> oldPath = paths.remove(0);
            //System.out.println("oldPath at 0: " + oldPath.get(0));

            // Extend it in all possible ways, adding each new path to the end of the list
            String current = oldPath.get(oldPath.size() - 1);
            // System.out.println("Current Node: " + current);
            ArrayList<MapData.Destination> connectedNodes = adjList.get(current);
            // System.out.println("connected Nodes: " + connectedNodes.size());
            for (MapData.Destination destination : connectedNodes) {
                // System.out.println("Node: " + destination.node());
                ArrayList<String> newPath = (ArrayList<String>) oldPath.clone();
                newPath.add(destination.node());
                paths.add(newPath);
                if (destination.node().equals(end)) {
                    solutionFound = true;
                    break; // Otherwise will continue searching
                }
            }
        }
        return paths.size() == 0 ? null : paths.get(paths.size() - 1);
    }
    public void printPath(ArrayList<String> path) {
        System.out.print("Final solution: ");
        for (String node : path) {
            System.out.printf("%s ", node);
            System.out.println();
        }
    }
}
