
import java.util.LinkedList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.HashMap;
import java.util.Collections;


public class Router {
    
    public static LinkedList<Long> shortestPath(GraphDB g, double stlon, double stlat,
                                                double destlon, double destlat) {
        Comparator<GraphDB.Node> comparator = new NodeComparator();
        GraphDB.Node startNode = g.nodes.get(g.closest(stlon, stlat));
        GraphDB.Node endNode = g.nodes.get(g.closest(destlon, destlat));
        startNode.totalDistance = 0.0;


        PriorityQueue<GraphDB.Node> open = new PriorityQueue<>(10, comparator);
        HashMap<GraphDB.Node, GraphDB.Node> parents = new HashMap<>();
        HashMap<GraphDB.Node, Double> priority = new HashMap<>();


        open.offer(startNode);

        while (!open.isEmpty()) {
            GraphDB.Node save = open.poll();
            save.seen = true;
            if (save.id.equals(endNode.id)) {
                for (GraphDB.Node i : g.nodes.values()) {
                    i.reset();
                }
                return construct(parents, save);
            } else {
                for (Long i : g.adjacent(save.id)) {
                    GraphDB.Node neighbor = g.nodes.get(i);
                    double currentCost = save.totalDistance + g.distance(save.id, i);
                    if (neighbor.seen && currentCost < neighbor.totalDistance) {
                        neighbor.seen = false;
                    }
                    if (!neighbor.seen) {
                        if (currentCost > neighbor.totalDistance) {
                            continue;
                        }
                        parents.put(neighbor, save);
                        neighbor.totalDistance = currentCost;
                        neighbor.priority = neighbor.totalDistance + g.distance(i,
                                endNode.id);
                        open.offer(neighbor);


                    }
                }
            }
        }

        return null;


    }


    public static class NodeComparator implements Comparator<GraphDB.Node> {
        @Override
        public int compare(GraphDB.Node x, GraphDB.Node y) {
            return x.compareTo(y);
        }
    }

    public static LinkedList<Long> construct(HashMap<GraphDB.Node, GraphDB.Node> map,
                                             GraphDB.Node n) {
        LinkedList<Long> list = new LinkedList<>();
        list.add(n.id);
        while (map.keySet().contains(n)) {
            n = map.get(n);
            list.add(n.id);
        }
        Collections.reverse(list);
        return list;
    }


}

