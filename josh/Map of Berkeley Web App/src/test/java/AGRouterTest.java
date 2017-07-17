import org.junit.Test;

import java.util.LinkedList;

import static org.junit.Assert.*;

public class AGRouterTest extends AGMapTest {
    /**
     * Test the route-finding functionality by comparing the node id list item by item.
     * @throws Exception
     */
    @Test

    public void testShortestPath() throws Exception {
        for (int j = 0; j < 20; j += 1) {
            for (TestParameters p : params) {
                LinkedList<Long> studentRouteResult = Router.shortestPath(graph,
                        p.routeParams.get("start_lon"), p.routeParams.get("start_lat"),
                        p.routeParams.get("end_lon"), p.routeParams.get("end_lat"));
                assertEquals("Found route differs for input: " + p.routeParams + ".\n",
                        p.routeResult, studentRouteResult);
            }
        }
    }
}
