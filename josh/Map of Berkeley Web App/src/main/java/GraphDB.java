import org.xml.sax.SAXException;


import java.io.File;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.PriorityQueue;
import java.util.Arrays;
import java.util.ArrayDeque;

/**
 * Graph for storing all of the intersection (vertex) and road (edge) information.
 * Uses your GraphBuildingHandler to convert the XML files into a graph. Your
 * code must include the vertices, adjacent, distance, closest, lat, and lon
 * methods. You'll also need to include instance variables and methods for
 * modifying the graph (e.g. addNode and addEdge).
 *
 * @author Alan Yao, Josh Hug
 */
public class GraphDB {
    /** Your instance variables for storing the graph. You should consider
     * creating helper classes, e.g. Node, Edge, etc. */

    /**
     * Example constructor shows how to create and start an XML parser.
     * You do not need to modify this constructor, but you're welcome to do so.
     *
     * @param dbPath Path to the XML file to be parsed.
     */
    Map<Long, Node> nodes = new LinkedHashMap<>();
    HashMap<String, String> locations = new HashMap<>();
    ArrayList<String> list;
    Map<String, LinkedHashSet<Map<String, Object>>> searches = new LinkedHashMap<>();
    MyTrie trie;



    static class Edge {
        String name;
        Node node1;
        Node node2;
        String maxSpeed;
        boolean valid;



        Edge(Node node1, Node node2) {
            this.node1 = node1;
            this.node2 = node2;
            valid = false;


        }
    }

    public static class Node implements Comparable<Node> {
        String name;
        Long id;
        double lat;
        double lon;
        HashSet<Edge> edges;
        ArrayList<Edge> possibleEdges;
        double priority;
        boolean seen;
        double totalDistance;





        public Node(Long name, double latitude, double longitude) {
            id = name;
            lat = latitude;
            lon = longitude;
            edges = new HashSet<>();
            possibleEdges = new ArrayList<>();
            seen = false;
            priority = Double.POSITIVE_INFINITY;
            totalDistance = Double.POSITIVE_INFINITY;





        }

        @Override
        public int compareTo(Node o) {

            if (this.priority - (o.priority) > 0) {
                return 1;
            } else if (this.priority - o.priority < 0) {
                return -1;
            } else {
                return 0;
            }
        }

        public void reset() {
            priority = Double.POSITIVE_INFINITY;
            totalDistance = Double.POSITIVE_INFINITY;
        }
    }

    public GraphDB(String dbPath) {
        trie = new MyTrie(this, null);
        try {
            File inputFile = new File(dbPath);
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            GraphBuildingHandler gbh = new GraphBuildingHandler(this);
            saxParser.parse(inputFile, gbh);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }

        clean();

        trie.cleaned =  locations.keySet();
    }



    /**
     * Helper to process strings into their "cleaned" form, ignoring punctuation and capitalization.
     *
     * @param s Input string.
     * @return Cleaned string.
     */
    static String cleanString(String s) {
        return s.replaceAll("[^a-zA-Z ]", "").toLowerCase();
    }

    /**
     * Remove nodes with no connections from the graph.
     * While this does not guarantee that any two nodes in the remaining graph are connected,
     * we can reasonably assume this since typically roads are connected.
     */
    private void clean() {


        HashMap copy = new HashMap<Long, Node>(nodes);
        for (Long n : nodes.keySet()) {
            if (nodes.get(n).edges.size() < 1) {
                copy.remove(n, nodes.get(n));
            }

        }
        nodes = (HashMap) copy.clone();


    }


    /**
     * Returns an iterable of all vertex IDs in the graph.
     */
    Iterable<Long> vertices() {
        //YOUR CODE HERE, this currently returns only an empty list.
        ArrayList<Long> vertices = new ArrayList<>();
        for (Long i : nodes.keySet()) {
            vertices.add(i);
        }

        return vertices;
    }

    /**
     * Returns ids of all vertices adjacent to v.
     */
    Iterable<Long> adjacent(long v) {
        ArrayDeque<Long> adjacents = new ArrayDeque<>();
        for (Edge i : nodes.get(v).edges) {
            adjacents.addLast(i.node2.id);

        }


        return adjacents;
    }

/**<<<<<<< HEAD
 /**
 * Returns the distance in units of longitude between vertices v and w.
 */

    /**
     * Returns the Euclidean distance between vertices v and w, where Euclidean distance
     * is defined as sqrt( (lonV - lonV)^2 + (latV - latV)^2 ).
     */

    double distance(long v, long w) {
        return Math.sqrt(Math.pow((nodes.get(v).lat - nodes.get(w).lat), 2) + Math.pow((nodes
                .get(v).lon - nodes.get(w).lon), 2));
    }

    /**
     * Returns the vertex id closest to the given longitude and latitude.
     */
    long closest(double lon, double lat) {
        PriorityQueue<Double> pq = new PriorityQueue<>();
        Node[] nodeArray = nodes.values().toArray(new Node[nodes.size()]);
        Double[] distances = new Double[nodes.size()];

        for (int i = 0; i < nodes.size(); i += 1) {
            double distance = Math.sqrt(Math.pow((lat - nodeArray[i].lat), 2)
                    + Math.pow((lon - nodeArray[i].lon), 2));
            distances[i] = distance;
            pq.offer(distance);
        }
        return nodeArray[Arrays.asList(distances).indexOf(pq.poll())].id;
    }



    /**
     * Longitude of vertex v.
     */
    double lon(long v) {
        return nodes.get(v).lon;
    }

    /**
     * Latitude of vertex v.
     */
    double lat(long v) {
        return nodes.get(v).lat;
    }
}
