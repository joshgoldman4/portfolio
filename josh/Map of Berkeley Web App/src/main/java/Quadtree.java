import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;

/**
 * Created by joshgoldman on 4/12/17.
 */

public class Quadtree {


    public class Node {
        Node nw;
        Node ne;
        Node sw;
        Node se;
        Node parent;
        String name;
        String filenumber;
        Double[] coords;
        int depth;
        double lonDPP;
        ArrayList<Node> children;
        HashMap<String, Node> nodes;
        String full;


        public Node(String filename, Node parent, double ullat, double ullon, double lrlat,
                    double lrlon, int d, double dpp) {
            nodes = new HashMap<>();
            name = filename;
            coords = new Double[4];
            coords[0] = ullat;
            coords[1] = ullon;
            coords[2] = lrlat;
            coords[3] = lrlon;
            double averageLat = (ullat + lrlat) / 2;
            double averageLon = (ullon + lrlon) / 2;
            depth = d;
            lonDPP = dpp;
            this.parent = parent;
            filenumber = filename.replace(".png", "");
            full = "img/" + filename;
            lat.put("img/" + filenumber + ".png", ullat);
            fullMap.put("img/" + filenumber + ".png", this);
            if (filenumber.length() == 7) {
                return;
            }
            if (filename.equals("root.png")) {
                filenumber = "";
            }
            nodes.put(filenumber, this);
            nw = new Node(filenumber + "1.png", this, ullat, ullon, averageLat, averageLon,
                    depth +
                            1, lonDPP / 2);
            ne = new Node(filenumber + "2.png", this, ullat, averageLon, averageLat, lrlon,
                    depth
                            + 1, lonDPP / 2);
            sw = new Node(filenumber + "3.png", this, averageLat, ullon, lrlat, averageLon,
                    depth
                            + 1, lonDPP / 2);
            se = new Node(filenumber + "4.png", this, averageLat, averageLon, lrlat, lrlon, depth +
                    1, lonDPP / 2);

            children = new ArrayList<>();
            children.add(nw);
            children.add(ne);
            children.add(sw);
            children.add(se);
        }


    }

    Node root;
    HashMap<String, Double> lat;
    HashMap<String, Node> fullMap;


    public Quadtree(String filename, double ullat, double ullon, double lrlat, double
            lrlon, double dpp) {
        lat = new HashMap<>();
        fullMap = new HashMap<>();
        root = new Node(filename, null, ullat, ullon, lrlat, lrlon, 0, dpp);


    }
}
