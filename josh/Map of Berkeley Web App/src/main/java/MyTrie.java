
import java.util.*;

/**
 * Created by joshgoldman on 4/17/17.
 */
public class MyTrie {
    GraphDB graph;
    Set<String> cleaned;


    public void prefixes(StringBuilder prev, trieNode n, List<String> list) {
        Set<Character> neighborSet = n.neighbors.keySet();
        if (n == null) {
            return;
        }

        if (cleaned.contains(prev.toString())) {
            list.add(graph.locations.get(prev.toString()));
        }

        for (char c : neighborSet) {
            prev.append(c);
            prefixes(prev, n.neighbors.get(c), list);
            prev.deleteCharAt(prev.length() - 1);

        }

    }


    private class trieNode {
        Character c;
        HashMap<Character, trieNode> neighbors;

        public trieNode(char character) {
            c = character;
            neighbors = new HashMap<>();
        }

        public trieNode() {
            neighbors = new HashMap<>();
        }

    }


    trieNode root;

    public MyTrie(GraphDB graph, Set<String> wordsCleaned) {
        root = new trieNode();
        cleaned = wordsCleaned;
        this.graph = graph;

    }


    public void insert(String input) {
        HashMap<Character, trieNode> holder = root.neighbors;
        trieNode newNode;
        for (int i = 0; i < input.length(); i += 1) {
            char check = input.charAt(i);
            if (holder.get(check) != null) {
                newNode = holder.get(check);
            } else {
                newNode = new trieNode(check);
                holder.put(check, newNode);
            }

            holder = newNode.neighbors;

        }
    }

    public List<String> getPrefix(String prefix) {

        prefix = graph.cleanString(prefix);
        ArrayList<String> list = new ArrayList<>();
        trieNode place = root;
        StringBuilder b = new StringBuilder();
        for (char c : prefix.toCharArray()) {
            b.append(c);
            if (place.neighbors.get(c) == null) {
                return new ArrayList<>();
            }
            place = place.neighbors.get(c);
        }

        prefixes(b, place, list);
        return list;

    }


}

