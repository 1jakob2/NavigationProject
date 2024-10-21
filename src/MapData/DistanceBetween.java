package MapData;

import java.util.Map;

public class DistanceBetween {
    private Map<String, MapData.GPS> nodeList;
    public DistanceBetween(){
        MapData data = null;
        try {
            data = new MapData();
        } catch (Exception e) {
            System.out.println("Error reading map data");
        }
        nodeList = data.getNodes();
    }

    public long calculateDistance (String currentNode, String targetNode){
        MapData.GPS lastPos = nodeList.get(currentNode);
        MapData.GPS goalPos = nodeList.get(targetNode);
        long xDiff = lastPos.east() - goalPos.east();
        long yDiff = lastPos.north() - goalPos.north();
        return (long) Math.sqrt(xDiff * xDiff + yDiff * yDiff);
    }
}
