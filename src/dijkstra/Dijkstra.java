package dijkstra;

import MapData.MapData;
import MapData.DistanceBetween;

import java.util.*;

public class Dijkstra {

    private Map<String, ArrayList<MapData.Destination>> adjList;
    private Map<String, MapData.GPS> nodeList;
    private record Node(String name, double distance){};
    private List<String> path = null;
    private DistanceBetween distanceBetween = new DistanceBetween();
    private int loopCounter = 0;// find how many times the algorithm goes through loops to find a result

    public Dijkstra(String start, String end){
        MapData data = null;
        try {
            data = new MapData();
        } catch (Exception e) {
            System.out.println("Error reading map data");
        }
        adjList = data.getAdjacencyList();
        nodeList = data.getNodes();

        path = dijkstra(start, end);
        //printPath(path);
    }

    private List<String> dijkstra (String start, String end){
        // sorts the queue so that the shortest distance comes in first
        PriorityQueue<Node> priorityQueue = new PriorityQueue<>(Comparator.comparingDouble(node -> node.distance));
        Map<String, Double> shortestDistances = new HashMap<>();
        Map<String, String> previousNode = new HashMap<>();

        // Initialize the distances of all nodes to infinity
        for (String node : nodeList.keySet()) {
            shortestDistances.put(node, Double.MAX_VALUE);
            //System.out.println(node + ": " + shortestDistances.get(node));
        }

        // Overwrite the first node's distance to 0
        shortestDistances.put(start, 0.0);
        //System.out.println(start + ": " + shortestDistances.get(start));
        priorityQueue.add(new Node(start, 0.0));

        //while PriorityQueue is not empty
        while (!priorityQueue.isEmpty()) {
            //shortestDistances.keySet().stream().forEach(System.out::println);
            //System.out.println();
            //previousNode.keySet().stream().forEach(System.out::println);
            //System.out.println();

            Node current = priorityQueue.poll(); // take and remove first item of the queue
            String currentNode = current.name;

            // If we have reached the destination node, stop and return the path
            if (currentNode.equals(end)) {
                return reconstructPath(previousNode, start, end);
            }

            if (adjList.containsKey(currentNode)) {
                //System.out.println();
                //System.out.println("currentNode: " + currentNode);
                //adjList.get(currentNode).stream().forEach(System.out::println);
                //System.out.println();

                // find all neighbors of currentNode and iterate over them
                for (MapData.Destination neighborNode : adjList.get(currentNode)) {
                    loopCounter++;
                    String neighborName = neighborNode.node();
                    // Calculate the actual distance using GPS coordinates
                    double edgeWeight = distanceBetween.calculateDistance(currentNode, neighborName); // calculate distance with the GPS coordinates for every neighborNode
                    //System.out.println("edgeWeight: " + currentNode + " to neighbor " + neighborName + " " + edgeWeight);

                    double newDistance = shortestDistances.get(currentNode) + edgeWeight;
                    //System.out.println("newDistance: " + currentNode + " " + newDistance);
                    //System.out.println("shortestDistance: " + currentNode + " " + shortestDistances.get(currentNode));

                    //System.out.println("newDistance: " + currentNode + " " + newDistance);
                    //System.out.println("Distance " + neighborName + ": " + newDistance + " " + shortestDistances.get(neighborName));
                    // if newDistance is shorter than distance of neighbor (they're all initialised with infinity, therefore first time will always be true)
                    if (newDistance < shortestDistances.get(neighborName)) {
                        shortestDistances.put(neighborName, newDistance);
                        previousNode.put(neighborName, currentNode); // keep track of the path
                        priorityQueue.add(new Node(neighborName, newDistance)); // add new item to priorityQueue to keep the loop going
                    }
                }
            }
        }

        // If we reach here, no path was found to the destination
        return new ArrayList<>();
    }

    // Helper method to reconstruct the shortest path from startNode to destinationNode
    public List<String> reconstructPath(Map<String, String> previousNode, String startNode, String destinationNode) {
        List<String> path = new ArrayList<>();
        String currentNode = destinationNode;

        while (currentNode != null && !currentNode.equals(startNode)) {
            path.add(currentNode);
            currentNode = previousNode.get(currentNode);
        }

        if (currentNode == null) {
            return new ArrayList<>(); // Return an empty list if no path is found
        }

        path.add(startNode);
        Collections.reverse(path); // Reverse the path to get the correct order
        //path.stream().forEach(System.out::println);
        return path;
    }

    public void printPath() {
        System.out.print("Final solution: ");
        for (String node : path) System.out.printf("%s ", node);
        System.out.println();
    }
    public List<String> getPath() {
        return path;
    }
    public int getLoopCounter() {
        return loopCounter;
    }
}
