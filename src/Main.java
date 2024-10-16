import MapData.MapData;

import aStar.AStar;
import bestFirst.BestFirst;
import breadthFirst.BreadthFirst;
import depthFirst.DepthFirst;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        String start = "Rönerweg/1";
        String end = "Schänzlihalde/1";
        BreadthFirst breadthFirst = new BreadthFirst(start, end);

        DepthFirst depth = new DepthFirst(start, end);

        //BreadthFirst breadthFirst = new BreadthFirst(start, end);
        //BestFirst bestFirst = new BestFirst(start, end);
        AStar aStar = new AStar(start, end);
    }
}