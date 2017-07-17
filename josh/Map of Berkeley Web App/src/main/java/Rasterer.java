import java.util.Comparator;
import java.util.Map;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Arrays;



public class Rasterer {
   
    Quadtree tree;
    Comparator nodeCompare = new NodeComparator();


    double ullat1;
    double ullon1;
    double lrlat1;
    double lrlon1;
    double xDist1;
    double lonDPP1;


    public Rasterer(String imgRoot) {
        
        double xDist = MapServer.ROOT_LRLON - MapServer.ROOT_ULLON;
        double lonDPP = xDist / 256;
        tree = new Quadtree("root.png", MapServer.ROOT_ULLAT, MapServer.ROOT_ULLON, MapServer
                .ROOT_LRLAT, MapServer.ROOT_LRLON, lonDPP);
    }

  

    public Map<String, Object> getMapRaster(Map<String, Double> params) {


        Map<String, Object> results = new HashMap<>();


        String[][] twoD;
        ullat1 = params.get("ullat");
        ullon1 = params.get("ullon");
        lrlat1 = params.get("lrlat");
        lrlon1 = params.get("lrlon");
        xDist1 = lrlon1 - ullon1;
        lonDPP1 = xDist1 / params.get("w");
        ArrayDeque<String> images;


        images = filter(ullat1, ullon1, lrlat1, lrlon1, tree.root.children, lonDPP1);

        twoD = twoDArray(images);


        results.put("render_grid", twoD);


        results.put("raster_ul_lon", tree.fullMap.get(images.getFirst()).coords[1]);
        results.put("raster_ul_lat", tree.fullMap.get(images.getFirst()).coords[0]);
        results.put("raster_lr_lon", tree.fullMap.get(images.getLast()).coords[3]);
        results.put("raster_lr_lat", tree.fullMap.get(images.getLast()).coords[2]);
        results.put("depth", tree.fullMap.get(images.getLast()).depth);

        results.put("query_success", true);


        return results;
    }

    public String[][] twoDArray(ArrayDeque<String> images) {
        String[] img = images.toArray(new String[images.size()]);
        Arrays.sort(img, nodeCompare);
        Double first = tree.lat.get(img[0]);
        int count = 1;
        for (int i = 1; i < img.length; i += 1) {
            if (!first.equals(tree.lat.get(img[i]))) {
                break;
            }
            count += 1;
        }

        String[][] twoD = new String[img.length / count][count];

        for (int i = 0; i < img.length / count; i += 1) {
            for (int j = 0; j < count; j += 1) {
                twoD[i][j] = img[(i * count) + j];
            }
        }

        return twoD;

    }


    public ArrayDeque<String> filter(double ullat, double ullon, double lrlat, double lrlon,
                                     Iterable<Quadtree.Node> nodes, double lonDPP) {
        ArrayDeque<String> images = new ArrayDeque<>();

        for (Quadtree.Node child : nodes) {
            if (intersectsTile(child, ullat, ullon, lrlat, lrlon)) {
                if (!lonDPPsmallerThanOrIsLeaf(lonDPP, child)) {
                    images.addAll(filter(ullat, ullon, lrlat, lrlon, child.children, lonDPP));
                } else {
                    images.addLast(child.full);
                }
            }
        }

        return images;
    }

    public boolean lonDPPsmallerThanOrIsLeaf(double queriesLonDPP, Quadtree.Node node) {
        return node.lonDPP < queriesLonDPP || node.nw == null;

    }


    public boolean intersectsTile(Quadtree.Node query, double ullat, double ullon, double lrlat,
                                  double lrlon) {
        return !(ullon > query.coords[3] || lrlat > query.coords[0] || query
                .coords[1] > lrlon || query.coords[2] > ullat);


    }

    public class NodeComparator implements Comparator<String> {
        @Override
        public int compare(String x, String y) {
            
            return tree.lat.get(y).compareTo(tree.lat.get(x));
        }
    }


}
