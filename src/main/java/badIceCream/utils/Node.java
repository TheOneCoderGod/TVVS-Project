package badIceCream.utils;

import badIceCream.model.Position;

public class Node implements Comparable<Node>{
    protected Position position;
    protected int cost, heuristic;
    protected Node parent;
    public Node(Position position, int cost, int heuristic, Node parent) {
        this.position = position;
        this.cost = cost;
        this.heuristic = heuristic;
        this.parent = parent;
    }

    @Override
    public int compareTo(Node other) {
        return Integer.compare(cost + heuristic, other.cost + other.heuristic);
    }
}
