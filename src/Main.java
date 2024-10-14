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
        String start = "Rönerweg/1";
        String end = "Schänzlihalde/1";
        //BreadthFirst breadthFirst = new BreadthFirst(start, end);
        //DepthFirst depth = new DepthFirst(start, end);
        //BestFirst bestFirst = new BestFirst(start, end);
//        Long startTime = System.nanoTime();
//        AStar aStar = new AStar(start, end);
//        Long endTime = System.nanoTime();
//        double result = endTime - startTime;
//        System.out.println(result / 1000000000);
        Dijkstra dijkstra = new Dijkstra(start, end);
    }
}