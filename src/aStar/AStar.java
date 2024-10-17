package aStar;

import java.util.*;

import MapData.MapData;

public class AStar {
    private record Path(ArrayList<String> nodes, double distanceSoFar, double distanceToGoal) {};

    private static Map<String, ArrayList<MapData.Destination>> adjList;
    private static Map<String, MapData.GPS> nodeList;

    private static Map<String, NodeInfo> searchInfo = new HashMap<>();
    private static ArrayList<String> queue = new ArrayList<>();

    List<String> path = null;
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

    private static List<String> aStar(String start, String end) {
        // Initialize the search-info with the start node
        searchInfo.put(start, new NodeInfo(0, distanceBetween(start, end), null));
        // Initialize the queue
        queue.add(start);

        String currentNode = findNodeLowestCost();
        while (currentNode != null && !currentNode.equals(end)) {
            // Look at all nodes that we can travel to.
            // - If a node is not in searchInfo, add it
            // - If a node is in search info, and we can lower its distanceTravelled, update it
            ArrayList<MapData.Destination> connectedNodes = adjList.get(currentNode);
            for (MapData.Destination destination : connectedNodes) {
                double distance = destination.distance() + searchInfo.get(currentNode).distanceSoFar;
                if (searchInfo.containsKey(destination.node())) {
                    NodeInfo nodeInfo = searchInfo.get(destination.node());
                    if (distance < nodeInfo.distanceSoFar) {
                        nodeInfo.distanceSoFar = distance;
                        nodeInfo.previousNode = currentNode;
                        queue.add(destination.node()); // Add into the queue, only if cost has been reduced
                    }
                } else {
                    NodeInfo nodeInfo = new NodeInfo(distance, distanceBetween(destination.node(), end), currentNode);
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

    private static class NodeInfo {
        double distanceSoFar;
        double distanceToGoal;
        String previousNode;

        public NodeInfo(double distanceSoFar, double distanceToGoal, String previousNode) {
            this.distanceSoFar = distanceSoFar;
            this.distanceToGoal = distanceToGoal;
            this.previousNode = previousNode;
        }
    }

    private static String findNodeLowestCost() {
        double shortestDistance = Double.MAX_VALUE;
        String result = null;
        // System.out.println("Queue start:");
        queue.stream().forEach(System.out::println);
        // System.out.println("Queue end");
        for (String node : queue) {
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

    private static List<String> reconstructPath(String start, String end) {
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

    private static double distanceBetween(String node, String goal) {
        MapData.GPS lastPos = nodeList.get(node);
        MapData.GPS goalPos = nodeList.get(goal);
        long xDiff = lastPos.east() - goalPos.east();
        long yDiff = lastPos.north() - goalPos.north();
        return Math.sqrt(xDiff * xDiff + yDiff * yDiff);
    }

    public void printPath(List<String> path) {
        System.out.print("Final solution: ");
        for (String node : path) System.out.printf("%s ", node);
        System.out.println();
    }
}
