import MapData.MapData;
import breadthFirst.BreadthFirst;
import depthFirst.DepthFirst;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        String start = "Neuengasse/1";
        String end = "Schänzlihalde/1";
        BreadthFirst breadthFirst = new BreadthFirst(start, end);

        DepthFirst depth = new DepthFirst(start, end);
    }
}