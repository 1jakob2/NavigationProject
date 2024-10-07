package MapData;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MapData {

    private static final String EdgeFile = "src/MapData/Edges.csv";
    private static final String NodeFile = "src/MapData/Nodes.csv";
    private static final Map<String, ArrayList<Destination>> adjacencyList = new HashMap<>();
    private static final Map<String, GPS> nodes = new HashMap<>();

    public record GPS(Integer east, Integer north) { };

    public record Destination(String node, double distance) { };

    public MapData() throws Exception {
        createNodes();
        createAdjacencyList();
    }

    public Map<String, GPS> getNodes() {
        return nodes;
    }

    public Map<String, ArrayList<Destination>> getAdjacencyList() {
        return adjacencyList;
    }

    private void createNodes() throws Exception {
        File file = new File(NodeFile);
        Files.lines(Paths.get(file.toURI()))
                .map(line -> line.split(";"))
                .forEach(entry -> nodes.put(entry[0], new GPS(Integer.parseInt(entry[1]), Integer.parseInt(entry[2]))));
    }

    private void createAdjacencyList() throws Exception {
        File file = new File(EdgeFile); // See Readme.txt !!!
        Files.lines(Paths.get(file.toURI()))
                .map(line -> line.split(";"))
                .forEach(entry -> {
                    addDestination(entry[0], entry[1], entry[2]);
                    addDestination(entry[1], entry[0], entry[2]);
                }
        );
    }

    private void addDestination(String from, String to, String dist) {
        ArrayList<Destination> destinations = adjacencyList.get(from);
        if (destinations == null) {
            destinations = new ArrayList<Destination>();
            adjacencyList.put(from, destinations);
        }
        destinations.add(new Destination(to, Double.parseDouble(dist)));
    }
}
