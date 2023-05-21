package il.ac.tau.cs.sw1.ex7;
import java.util.*;


public class Graph implements Greedy<Graph.Edge>{
    List<Edge> lst; //Graph is represented in Edge-List. It is undirected. Assumed to be connected.
    int n; //nodes are in [0,..., n]

    Graph(int n1, List<Edge> lst1){
        lst = lst1;
        n = n1;
    }

    public static class Edge{
        int node1, node2;
        double weight;

        Edge(int n1, int n2, double w) {
            node1 = n1;
            node2 = n2;
            weight = w;
        }

        @Override
        public String toString() {
            return "{" + "(" + node1 + "," + node2 + "), weight=" + weight + '}';
        }
    }

    @Override
    public Iterator<Edge> selection() {

        List<Edge> edges = new ArrayList<>(); // Create new edges list
        edges.addAll(this.lst); // Copy the original edges list
        Collections.sort(edges, new edgesWeightComparator()); // Sort the edges list with the costume comparator
        return edges.iterator();
    }

    // Comparator to compare edges according to their weight
    private class edgesWeightComparator implements Comparator<Edge> {

        public int compare(Edge a, Edge b) {
            if (a.weight != b.weight) {
                if (a.weight > b.weight) {
                    return 1;
                } else {
                    return -1;
                }
            } else {
                if (a.node1 != b.node1) {
                    return a.node1 - b.node1;
                } else {
                    return a.node2 - b.node2;
                }
            }
        }
    }

    @Override
    public boolean feasibility(List<Edge> candidates_lst, Edge element) {

        List<Edge> new_candidates_lst = new ArrayList<>();
        new_candidates_lst.addAll(candidates_lst);
        new_candidates_lst.add(element);
        for (int i = 0; i <= n; i++) {
            if (feasibilityRec(new_candidates_lst, i, new boolean[n + 1], new int[n + 1])) {
                return false;
            }
        }
        return true;
    }

    private boolean feasibilityRec(List<Edge> lst, int curr, boolean[] visited, int[] parents) {

        visited[curr] = true;
        for (Edge edge : lst) {
            int curr_neighbor = -1;
            if (edge.node1 == curr || edge.node2 == curr) {
                if (edge.node1 == curr) {
                    curr_neighbor = edge.node2;
                } else if (edge.node2 == curr) {
                    curr_neighbor = edge.node1;
                }
                if (!visited[curr_neighbor]) {
                    parents[curr_neighbor] = curr;
                    return feasibilityRec(lst, curr_neighbor, visited, parents);
                } else if (parents[curr] != curr_neighbor) {
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public void assign(List<Edge> candidates_lst, Edge element) {
        candidates_lst.add(element);
    }

    @Override
    public boolean solution(List<Edge> candidates_lst) {
        return candidates_lst.size() == n;
    }
}
