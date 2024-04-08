import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * This class implements the functionality to find and print the shortest paths
 * from a single source to all other vertices in a weighted graph.
 */
public class CollectData {
    private static final int MAX_WEIGHT = 32767;
    private static Map<Character, Map<Character, Integer>> graph = new HashMap<>();
    private static char source;

    /**
     * the main method of the program which initializes the process by reading
     * the graph from an input file and then finding the shortest paths.
     * 
     * @param args the command line arguments
     * @throws FileNotFoundException if the input file is not found
     */
    public static void main(String[] args) throws FileNotFoundException {
        try {
            readGraph();
            findShortestPath();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
    }

    /**
     * Reads the graph from an input file. The first line should contain
     * the source. Each line after should define the edges of the graph
     * in the format edge,weight separated by a comma.
     * 
     * @throws FileNotFoundException If the input file cannot be found.
     */
    public static void readGraph() throws FileNotFoundException {
        System.out.println("Enter the name of the input file: ");
        Scanner scanner = new Scanner(System.in);
        String fileName = scanner.nextLine();
        try (Scanner fileScanner = new Scanner(new File(fileName))) {
            if (fileScanner.hasNextLine()) {
                source = fileScanner.nextLine().trim().charAt(0);
            }
            while (fileScanner.hasNextLine()) {
                String[] parts = fileScanner.nextLine().trim().split(" ");
                if (parts[0] == " " || parts[0] == "") {
                    break;
                }
                char vertex = parts[0].charAt(0);
                graph.putIfAbsent(vertex, new HashMap<>());
                for (int i = 1; i < parts.length; i++) {
                    String[] edge = parts[i].split(",");
                    graph.get(vertex).put(edge[0].charAt(0), Integer.parseInt(edge[1]));
                }
            }
        }
        scanner.close();
    }

    /**
     * Finds the shortest path from the source to all other vertices in the graph
     * using Dijkstra's algorithm.
     * 
     * @throws FileNotFoundException if file cannot be found
     */
    private static void findShortestPath() throws FileNotFoundException {
        // Map to store the shortest distance from source to each vertex
        Map<Character, Integer> distances = new HashMap<>();
        // Map to store the last vertex visited before reaching a vertex. This
        // is used to reconstruct the path from the source to each vertex
        Map<Character, Character> predecessors = new HashMap<>();

        // PriorityQueue to select the next vertex to visit based on the edge weight.
        PriorityQueue<Character> verticesQueue = new PriorityQueue<>(Comparator.comparingInt(distances::get));

        // Initialize distance to max weight for all vertices and add them to the
        // priority queue.
        // This step ensures that the distances map contains all vertices and sets
        // initial
        // "infinite" distances since at the beginning the actual distances are unknown.
        for (char vertex : graph.keySet()) {
            distances.put(vertex, MAX_WEIGHT);
            verticesQueue.add(vertex);
        }
        // Set the distance to the source to 0 and add it to the queue.
        distances.put(source, 0);
        verticesQueue.add(source);

        // While there are vertices left to process...
        while (!verticesQueue.isEmpty()) {
            // Polling the queue retrieves and removes the vertex with the smallest distance
            // from the source that hasn't been processed.
            char current = verticesQueue.poll();

            // If the current vertex does not have any outgoing edges it is skipped.
            if (!graph.containsKey(current))
                continue;

            // For each adjacent vertex to the current vertex, calculate the total distance.
            for (Map.Entry<Character, Integer> adjacencyPair : graph.get(current).entrySet()) {
                char adjacentVertex = adjacencyPair.getKey();
                int weight = adjacencyPair.getValue();
                int totalDistance = distances.get(current) + weight;

                // If the calculated total distance is less than the previously known distance
                // to the adjacent vertex, update the distance and add the vertex to
                // predecessors.
                if (totalDistance < distances.get(adjacentVertex)) {
                    distances.put(adjacentVertex, totalDistance);
                    predecessors.put(adjacentVertex, current);
                    verticesQueue.add(adjacentVertex);
                }
            }

        }
        writeOutput(distances, predecessors);
    }

    /**
     * Writes the output to a file. The output contains the shortest path from the
     * source
     * to all other vertices in the graph.
     * 
     * @param distances
     * @param predecessors
     * @throws FileNotFoundException
     */
    private static void writeOutput(Map<Character, Integer> distances, Map<Character, Character> predecessors)
            throws FileNotFoundException {
        String outputFileName = "outputShortestPaths.txt.";
        try (PrintWriter writer = new PrintWriter(new File(outputFileName))) {
            for (Map.Entry<Character, Integer> entry : distances.entrySet()) {
                if (entry.getKey() == source)
                    continue;
                writer.printf("%s: %s\n", entry.getKey(), reconstructPath(predecessors, entry.getKey()));
            }
        }
        System.out.println("Output written to outputShortestPaths.txt");
    }

    /**
     * Reconstructs the path from the source to the target vertex.
     * 
     * @param predecessors
     * @param target
     * @return A string representation of the path from the source to the target.
     */
    private static String reconstructPath(Map<Character, Character> predecessors, char target) {
        List<Character> path = new ArrayList<>();
        for (char at = target; at != source; at = predecessors.getOrDefault(at, source)) {
            path.add(at);
        }
        Collections.reverse(path);
        return String.join(" ", path.stream().map(String::valueOf).collect(Collectors.toList()));
    }
}