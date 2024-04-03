import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class CollectData {
    private static final int MAX_WEIGHT = 32767;
    private static Map<Character, Map<Character, Integer>> graph = new HashMap<>();
    private static char source;

    public static void main(String[] args) throws FileNotFoundException {
        try {
            readGraph();
            findShortestPath();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
    }

    public static void readGraph() throws FileNotFoundException {
        System.out.println("Enter the name of the input file: ");
        Scanner scanner = new Scanner(System.in);
        String fileName = scanner.nextLine();
        try (Scanner fileScanner = new Scanner(new File(fileName))) {
            if (fileScanner.hasNextLine()) {
                source = fileScanner.nextLine().trim().charAt(0);
            }
            while (fileScanner.hasNextLine()) {
                String[] parts = fileScanner.nextLine().split(" ");
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

    private static void findShortestPath() throws FileNotFoundException {
        Map<Character, Integer> distances = new HashMap<>();
        Map<Character, Character> predecessors = new HashMap<>();
        PriorityQueue<Character> verticesQueue = new PriorityQueue<>(Comparator.comparingInt(distances::get));

        for (char vertex : graph.keySet()) {
            distances.put(vertex, MAX_WEIGHT);
            verticesQueue.add(vertex);
        }
        distances.put(source, 0);
        verticesQueue.add(source);

        while (!verticesQueue.isEmpty()) {
            char current = verticesQueue.poll();
            if (!graph.containsKey(current))
                continue;
            for (Map.Entry<Character, Integer> adjacencyPair : graph.get(current).entrySet()) {
                char adjacentVertex = adjacencyPair.getKey();
                int weight = adjacencyPair.getValue();
                int totalDistance = distances.get(current) + weight;
                if (totalDistance < distances.get(adjacentVertex)) {
                    distances.put(adjacentVertex, totalDistance);
                    predecessors.put(adjacentVertex, current);
                    verticesQueue.add(adjacentVertex);
                }
            }

        }
        writeOutput(distances, predecessors);
    }

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
        System.out.println("Output written to output.txt");
    }

    private static String reconstructPath(Map<Character, Character> predecessors, char target) {
        List<Character> path = new ArrayList<>();
        for (char at = target; at != source; at = predecessors.getOrDefault(at, source)) {
            path.add(at);
        }
        Collections.reverse(path);
        return String.join(" ", path.stream().map(String::valueOf).collect(Collectors.toList()));
    }
}