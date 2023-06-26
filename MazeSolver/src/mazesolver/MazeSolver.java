package mazesolver;

import java.util.*;

public class MazeSolver {

    private int numVertices; // Number of vertices
    private List<Integer>[] adjacencyLists; // To be able to hold adjacencies

    public MazeSolver(int numVertices) {
        this.numVertices = numVertices;
        this.adjacencyLists = (List<Integer>[]) new ArrayList[numVertices];

        for (int i = 0; i < numVertices; i++) {
            adjacencyLists[i] = new ArrayList<>();
        }
    }

    // Method that adds edges between two nodes
    public void addEdge(int v, int w) {
        adjacencyLists[v].add(w);
        adjacencyLists[w].add(v);
    }

    public Iterable<Integer> adj(int v) {
        return adjacencyLists[v];
    }

    // BFS Implementation
    public List<Integer> bfs(int source, int destination) {
        boolean[] visited = new boolean[numVertices];
        int[] prev = new int[numVertices];

        for (int i = 0; i < numVertices; i++) {
            prev[i] = -1;
        }

        Deque<Integer> queue = new ArrayDeque<>();
        queue.addLast(source);
        visited[source] = true;

        while (!queue.isEmpty()) {
            int v = queue.removeFirst();

            for (int w : adj(v)) {
                if (!visited[w]) {
                    queue.addLast(w);
                    visited[w] = true;
                    prev[w] = v;

                    if (w == destination) {
                        return buildPathBFS(prev, source, destination);
                    }
                }
            }
        }

        return null;
    }

    private List<Integer> buildPathBFS(int[] prev, int source, int destination) {
        List<Integer> path = new ArrayList<>();
        int v = destination;

        while (v != source) {
            path.add(0, v);
            v = prev[v];
        }

        path.add(0, source);
        return path;
    }

    //DFS Implementation
    public List<Integer> dfs(int source, int destination) {
        Set<Integer> visited = new HashSet<>();
        List<Integer> path = new ArrayList<>();

        getPathDFS(source, destination, visited, path);

        return path;
    }

    private boolean getPathDFS(int source, int destination, Set<Integer> visited, List<Integer> path) {
        if (visited.contains(source)) {
            return false;
        }

        visited.add(source);
        path.add(source);

        if (source == destination) {
            return true;
        }

        for (int neighbor : adjacencyLists[source]) {
            // Recursive implementation.
            if (getPathDFS(neighbor, destination, visited, path)) {
                return true;
            }
        }

        path.remove(path.size() - 1);
        return false;
    }

    public static void main(String[] args) {
        //Set matrices here. Ignore the outhermost walls.
        
        /*
        int[][] horizontal = {{0, 1, 1, 0, 0},
        {1, 1, 1, 1, 0},
        {0, 1, 0, 0, 1},
        {1, 0, 0, 0, 0}};

        int[][] vertical = {{1, 0, 0, 0},
        {0, 0, 0, 1},
        {0, 0, 1, 0},
        {0, 1, 1, 1},
        {0, 1, 0, 0}};
         */
 
        // 15 * 15 maze
        int[][] horizontal = {
            {0, 1, 1, 0, 0, 0, 1, 0, 0, 0, 1, 1, 1, 0, 1},
            {1, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 1, 0, 1, 0},
            {0, 1, 1, 0, 0, 1, 1, 0, 1, 0, 1, 0, 1, 1, 1},
            {1, 1, 0, 0, 1, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0},
            {0, 1, 1, 0, 0, 1, 0, 1, 1, 0, 1, 1, 1, 0, 0},
            {0, 0, 1, 1, 1, 0, 0, 1, 0, 1, 1, 1, 1, 1, 0},
            {0, 1, 0, 0, 0, 1, 1, 0, 0, 0, 1, 0, 1, 0, 0},
            {1, 1, 1, 0, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1},
            {0, 1, 1, 1, 0, 0, 0, 1, 1, 1, 1, 0, 0, 1, 0},
            {1, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 1, 0, 0, 0},
            {0, 1, 0, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 0, 0},
            {0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 1, 1, 0},
            {0, 0, 1, 1, 0, 1, 0, 1, 0, 1, 1, 0, 0, 1, 1},
            {0, 1, 1, 0, 1, 0, 0, 0, 1, 1, 0, 0, 1, 0, 1}
        };

        int[][] vertical = {
            {0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0},
            {1, 0, 1, 1, 1, 1, 0, 1, 1, 1, 0, 0, 1, 0},
            {0, 1, 0, 1, 0, 1, 0, 1, 1, 1, 0, 1, 0, 0},
            {0, 0, 1, 1, 0, 1, 1, 1, 0, 1, 0, 1, 0, 0},
            {0, 0, 1, 1, 0, 1, 1, 0, 1, 0, 1, 0, 1, 0},
            {1, 0, 0, 1, 0, 1, 0, 0, 1, 0, 0, 0, 0, 1},
            {1, 1, 0, 0, 1, 0, 1, 1, 1, 0, 1, 0, 0, 1},
            {0, 0, 1, 1, 0, 0, 0, 1, 1, 0, 0, 1, 0, 0},
            {0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0},
            {0, 1, 0, 0, 1, 0, 1, 0, 1, 0, 1, 1, 1, 1},
            {0, 1, 1, 0, 0, 0, 0, 1, 0, 1, 0, 0, 1, 1},
            {0, 1, 1, 1, 1, 0, 1, 0, 1, 0, 0, 0, 0, 1},
            {1, 1, 0, 1, 0, 1, 0, 1, 1, 0, 0, 1, 0, 0},
            {1, 0, 0, 1, 0, 1, 1, 1, 0, 0, 1, 0, 1, 0},
            {0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0},};
 
        /*
        // Maze that has 2 seperate ways 
        int[][] horizontal = {{1,1,1,0,1,1},
        {0,1,1,0,1,0},
        {0,1,0,1,0,0},
        {0,1,0,1,1,0},
        {0,1,1,1,0,0}};

        int[][] vertical = {{0,0,0,1,0},
        {0,0,1,0,0},
        {0,1,0,1,1},
        {1,0,1,0,1},
        {0,1,0,0,1},
        {0,0,0,0,0}};
        */

        int numVertices = vertical.length * horizontal[0].length; // Finding the number of vertices
        int source = 0; //Top left vertice is taken as source.
        int destination = numVertices - 1; //Bottom right vertice is taken as destination.

        MazeSolver graph = new MazeSolver(numVertices);
        int cnt = 0; // To keep track of the vertices.

        //When we look at a node in the horizontal plane, if there is no wall there,
        // we insert an edge between that node and the one below it.
        for (int i = 0; i < horizontal.length; i++) {
            for (int j = 0; j < horizontal[0].length; j++) {
                if (horizontal[i][j] == 0) {
                    graph.addEdge(cnt, cnt + horizontal[0].length);
                }
                cnt++;
            }
        }

        // When we look at a node in the vertical plane, if there is no wall there,
        // we place an edge between that node and the node to its right.
        cnt = 0;
        for (int i = 0; i < vertical.length; i++) {
            for (int j = 0; j < vertical[0].length; j++) {
                if (vertical[i][j] == 0) {
                    graph.addEdge(cnt, cnt + 1);
                }
                cnt++;
            }
            cnt++;
        }

        //BFS trial
        System.out.println("BFS path");
        List<Integer> path = graph.bfs(source, destination);
        if (path != null) {
            System.out.println(path);
        } else {
            System.out.println("No path found.");
        }

        //DFS trial
        System.out.println("\nDFS path");
        System.out.println(graph.dfs(source, destination));
    }

}
