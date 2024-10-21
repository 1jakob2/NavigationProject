package aStar;

import java.util.*;

import MapData.MapData;
import MapData.DistanceBetween;

public class AStar {
    private record Path(ArrayList<String> nodes, double distanceSoFar, double distanceToGoal) {};

    private Map<String, ArrayList<MapData.Destination>> adjList;
    private Map<String, MapData.GPS> nodeList;

    private Map<String, NodeInfo> searchInfo = new HashMap<>();
    private ArrayList<String> queue = new ArrayList<>();
    private List<String> path = null;
    private DistanceBetween distanceBetween = new DistanceBetween();
    private int loopCounter = 0; // find how many times the algorithm goes through loops to find a result
    public AStar(String start, String end){
        MapData data = null;
        try {
            data = new MapData();
        } catch (Exception e) {
            System.out.println("Error reading map data");
        }
        adjList = data.getAdjacencyList();
        nodeList = data.getNodes();
        path = aStar(start, end);

    }

    public List<String> getPath(){
        return path;
    }

    private List<String> aStar(String start, String end) {
        // Initialize the search-info with the start node
        searchInfo.put(start, new NodeInfo(0, distanceBetween.calculateDistance(start, end), null));
        // Initialize the queue
        queue.add(start);

        String currentNode = findNodeLowestCost();
        while (currentNode != null && !currentNode.equals(end)) {
            // Look at all nodes that we can travel to.
            // - If a node is not in searchInfo, add it
            // - If a node is in search info, and we can lower its distanceTravelled, update it
            ArrayList<MapData.Destination> connectedNodes = adjList.get(currentNode);
            for (MapData.Destination destination : connectedNodes) {
                loopCounter++;
                double distance = destination.distance() + searchInfo.get(currentNode).distanceSoFar;
                if (searchInfo.containsKey(destination.node())) {
                    NodeInfo nodeInfo = searchInfo.get(destination.node());
                    if (distance < nodeInfo.distanceSoFar) {
                        nodeInfo.distanceSoFar = distance;
                        nodeInfo.previousNode = currentNode;
                        queue.add(destination.node()); // Add into the queue, only if cost has been reduced
                    }
                } else {
                    NodeInfo nodeInfo = new NodeInfo(distance, distanceBetween.calculateDistance(destination.node(), end), currentNode);
                    searchInfo.put(destination.node(), nodeInfo);
                    queue.add(destination.node());
                }
            }
            currentNode = findNodeLowestCost();
        }

        if (currentNode == null) {
            return null; // no solution found
        } else {
            return reconstructPath(start, end);
        }
    }

    private class NodeInfo {
        double distanceSoFar;
        double distanceToGoal;
        String previousNode;

        public NodeInfo(double distanceSoFar, double distanceToGoal, String previousNode) {
            this.distanceSoFar = distanceSoFar;
            this.distanceToGoal = distanceToGoal;
            this.previousNode = previousNode;
        }
    }

    private String findNodeLowestCost() {
        double shortestDistance = Double.MAX_VALUE;
        String result = null;
        // System.out.println("Queue start:");
        // queue.stream().forEach(System.out::println);
        // System.out.println("Queue end");
        for (String node : queue) {
            loopCounter++;
            NodeInfo nodeInfo = searchInfo.get(node);
            if ((nodeInfo.distanceSoFar + nodeInfo.distanceToGoal) < shortestDistance) {
                result = node;
                shortestDistance = nodeInfo.distanceSoFar + nodeInfo.distanceToGoal;
                // System.out.println(result + ": " + shortestDistance);
            }
        }
        if (result != null) queue.remove(result);
        return result;
    }

    private List<String> reconstructPath(String start, String end) {
        ArrayList<String> nodeList = new ArrayList<>();
        nodeList.add(end);
        String currentNode = end;
        while (currentNode != start) {
            NodeInfo nodeInfo = searchInfo.get(currentNode);
            currentNode = nodeInfo.previousNode;
            nodeList.add(currentNode);
        }
        Collections.reverse(nodeList);
        return nodeList;
    }

    public void printPath(List<String> path) {
        System.out.print("Final solution: ");
        for (String node : path) System.out.printf("%s ", node);
        System.out.println();
    }

    public int getLoopCounter() {
        return loopCounter;
    }
}