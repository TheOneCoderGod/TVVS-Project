package badIceCream.utils;

import badIceCream.model.Position;
import badIceCream.model.game.arena.Arena;
import badIceCream.model.game.elements.monsters.Monster;

import java.util.*;

public class ShortestPathNextMove {
    public Position findShortestPath(Monster monster, Arena arena) {
        Position monsterPos = monster.getPosition();
        Position iceCreamPos = arena.getIceCream().getPosition();

        if (monsterPos.equals(iceCreamPos)) return null;

        PriorityQueue<Node> pq = new PriorityQueue<>();

        pq.offer(new Node(monsterPos, 0, manhattanDistance(monsterPos, iceCreamPos), null));

        Set<String> visited = new HashSet<>();

        while (!pq.isEmpty()) {
            Node current = pq.poll();

            if (current.position.equals(iceCreamPos)) {

                while (!Objects.equals(current.parent.position, monsterPos)) {
                    current = current.parent;
                }
                return current.position;
            }

            if (!visited.contains(current.position.getX() + "," + current.position.getY())) {
                visited.add(current.position.getX() + "," + current.position.getY());
                List<Position> options = new ArrayList<>(List.of(new Position[]{current.position.getDown(), current.position.getLeft(), current.position.getUp(), current.position.getRight()}));
                for (Position pos : options) {
                    if (arena.isEmptyMonsters(pos)) {
                        int newCost = current.cost + 1;
                        int newHeuristic = manhattanDistance(pos, iceCreamPos);
                        pq.offer(new Node(pos, newCost, newHeuristic, current));
                    }
                }
            }
        }
        return null;
    }
    private static int manhattanDistance(Position pos1, Position pos2) {
        return Math.abs(pos1.getX() - pos2.getX()) + Math.abs(pos1.getY() - pos2.getY());
    }
}
