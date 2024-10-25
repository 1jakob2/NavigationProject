import MapData.MapData;

import aStar.AStar;
import bestFirst.BestFirst;
import breadthFirst.BreadthFirst;
import depthFirst.DepthFirst;
import dijkstra.Dijkstra;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        String start = "Buempliz/Buchdruckerweg/1";
        String end = "Bärenplatz/1";

        System.out.println("Breadth First:");
        BreadthFirst breadthFirst = new BreadthFirst(start, end);
        breadthFirst.printPath();

        System.out.println("Depth First:");
        DepthFirst depthFirst = new DepthFirst(start, end);
        depthFirst.printPath();

        System.out.println("Best First:");
        BestFirst bestFirst = new BestFirst(start, end);
        bestFirst.printPath();

        System.out.println("A*:");
        AStar aStar = new AStar(start, end);
        aStar.printPath();

        System.out.println("Dijkstra:");
        Dijkstra dijkstra = new Dijkstra(start, end);
        dijkstra.printPath();
    }
}