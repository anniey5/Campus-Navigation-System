import java.util.*;

/**
 * BFSResult
 *
 * Stores the result produced by BFS shortest-route searching.
 *
 * Stores:
 * 1. Parent information
 * 2. BFS visiting order
 * 3. BFS trace information
 * 4. Final shortest route
 */
public class BFSResult {

    // Stores the parent of each discovered vertex
    private Map<Location, Location> parent;
    
    // Stores the order in which vertices were visited
    private List<Location> visitOrder;
    private List<String> trace;
    
    // Stores the final shortest route
    private List<Location> route;

    public BFSResult() {
        parent = new LinkedHashMap<>();
        visitOrder = new ArrayList<>();
        trace = new ArrayList<>();
        route = new ArrayList<>();
    }

    // PARENT
    public Map<Location, Location> getParent() {
        return parent;
    }

    public void setParent(
            Location child,
            Location parentLocation) {

        parent.put(child, parentLocation);
    }

    // VISITED ORDER
    public List<Location> getVisitOrder() {
        return visitOrder;
    }

    public void addVisited(Location location) {
        visitOrder.add(location);
    }

    // BFS TRACE
    public List<String> getTrace() {
        return trace;
    }

    public void addTrace(String traceLine) {
        trace.add(traceLine);
    }

    // FINAL ROUTE
    public List<Location> getRoute() {
        return route;
    }

    public void setRoute(List<Location> route) {

        if (route == null) {
            this.route = new ArrayList<>();
        } else {
            this.route = new ArrayList<>(route);
        }
    }

    // CHECK ROUTE
    public boolean hasRoute() {
        return !route.isEmpty();
    }
    
    // DISPLAY ROUTE
    public String getRouteString() {

        if (route.isEmpty()) {
            return "No route";
        }

        StringBuilder result = new StringBuilder();

        for (int i = 0; i < route.size(); i++) {

            if (i > 0) {
                result.append(" -> ");
            }

            result.append(route.get(i).getName());
        }

        return result.toString();
    }

    // NUMBER OF EDGES
    public int getNumberOfEdges() {

        if (route.isEmpty()) {
            return 0;
        }

        return route.size() - 1;
    }
}