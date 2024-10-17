import aStar.AStar;

public class AStarMain {
    public static void main(String[] args) {
        String start = "Suhr30";
        String end = "Schänzlihalde/1";

        //Zeitmessung
        long startTime = System.nanoTime();

        AStar aStar = new AStar(start, end);

        long endTime = System.nanoTime();
        double durationInSeconds = (endTime - startTime) / 1_000_000_000.0;
        System.out.print("Dauer: " + durationInSeconds);
    }
}
