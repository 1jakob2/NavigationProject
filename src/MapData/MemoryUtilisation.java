package MapData;

public class MemoryUtilisation {

    private Runtime runtime;
    public static long getMemoryUsage(){
        Runtime runtime = Runtime.getRuntime();
        return runtime.totalMemory() - runtime.freeMemory();
    }

    public static double getMemoryUsagePercentage(){
        Runtime runtime = Runtime.getRuntime();
        long totalMemory = runtime.totalMemory(); // currently available JVM memory
        long maxMemory = runtime.maxMemory(); // max possible JVM memory

        long usedMemory = totalMemory - runtime.freeMemory(); // current memory usage

        return (double) usedMemory / maxMemory * 100; // current usage in percent of max possible JVM memory
    }
}
