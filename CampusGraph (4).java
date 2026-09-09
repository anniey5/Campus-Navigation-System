import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class CampusGraph {

    private final Map<Location, LinkedHashSet<Location>> adjList;
    private int autoLayoutCount;

    public CampusGraph() {
        adjList = new LinkedHashMap<>();
        autoLayoutCount = 0;
    }

    public Map<Location, LinkedHashSet<Location>> getAdjList() {
        Map<Location, LinkedHashSet<Location>> copy = new LinkedHashMap<>();

        for (Map.Entry<Location, LinkedHashSet<Location>> entry : adjList.entrySet()) {
            copy.put(entry.getKey(), new LinkedHashSet<>(entry.getValue()));
        }

        return Collections.unmodifiableMap(copy);
    }

    private boolean isValidName(String name) {
        if (name == null || name.trim().isEmpty()) {
            System.out.println("Location name cannot be empty.");
            return false;
        }
        return true;
    }

    private Location getExistingLocation(String name) {
        Location target = new Location(name);

        for (Location location : adjList.keySet()) {
            if (location.equals(target)) {
                return location;
            }
        }

        return null;
    }

    private void addVertexInternal(String name, int x, int y) {
        adjList.put(new Location(name, x, y), new LinkedHashSet<>());
    }

    private void addEdgeInternal(String name1, String name2) {
        Location location1 = getExistingLocation(name1);
        Location location2 = getExistingLocation(name2);

        adjList.get(location1).add(location2);
        adjList.get(location2).add(location1);
    }

    public void addVertex(String name) {
        if (!isValidName(name)) {
            return;
        }

        if (getExistingLocation(name) != null) {
            System.out.println("Location \"" + name + "\" already exists.");
            return;
        }

        int x = 100 + (autoLayoutCount % 8) * 120;
        int y = 100 + (autoLayoutCount / 8) * 120;
        autoLayoutCount++;

        addVertexInternal(name.trim(), x, y);
        System.out.println("Location \"" + name.trim() + "\" has been added.");
    }

    public void addVertex(String name, int x, int y) {
        if (!isValidName(name)) {
            return;
        }

        if (getExistingLocation(name) != null) {
            System.out.println("Location \"" + name + "\" already exists.");
            return;
        }

        addVertexInternal(name.trim(), x, y);
        System.out.println("Location \"" + name.trim() + "\" has been added.");
    }

    public void removeVertex(String name) {
        if (!isValidName(name)) {
            return;
        }

        Location location = getExistingLocation(name);

        if (location == null) {
            System.out.println("Location \"" + name + "\" does not exist.");
            return;
        }

        adjList.remove(location);

        for (Set<Location> neighbours : adjList.values()) {
            neighbours.remove(location);
        }

        System.out.println(
                "Location \"" + location.getName()
                        + "\" and all connected walkways have been removed."
        );
    }

    public void addEdge(String name1, String name2) {
        if (!isValidName(name1) || !isValidName(name2)) {
            return;
        }

        if (name1.trim().equalsIgnoreCase(name2.trim())) {
            System.out.println("A location cannot connect to itself.");
            return;
        }

        Location location1 = getExistingLocation(name1);
        Location location2 = getExistingLocation(name2);

        if (location1 == null || location2 == null) {
            System.out.println("Both locations must exist before adding a walkway.");
            return;
        }

        if (adjList.get(location1).contains(location2)) {
            System.out.println(
                    "A walkway already exists between \""
                            + location1.getName() + "\" and \""
                            + location2.getName() + "\"."
            );
            return;
        }

        adjList.get(location1).add(location2);
        adjList.get(location2).add(location1);

        System.out.println(
                "Walkway added between \""
                        + location1.getName() + "\" and \""
                        + location2.getName() + "\"."
        );
    }

    public void removeEdge(String name1, String name2) {
        if (!isValidName(name1) || !isValidName(name2)) {
            return;
        }

        Location location1 = getExistingLocation(name1);
        Location location2 = getExistingLocation(name2);

        if (location1 == null || location2 == null) {
            System.out.println("Both locations must exist before removing a walkway.");
            return;
        }

        if (!adjList.get(location1).contains(location2)) {
            System.out.println(
                    "No walkway exists between \""
                            + location1.getName() + "\" and \""
                            + location2.getName() + "\"."
            );
            return;
        }

        adjList.get(location1).remove(location2);
        adjList.get(location2).remove(location1);

        System.out.println(
                "Walkway removed between \""
                        + location1.getName() + "\" and \""
                        + location2.getName() + "\"."
        );
    }

    public void displayGraph() {
        if (adjList.isEmpty()) {
            System.out.println("\nThe campus graph is currently empty.");
            return;
        }

        System.out.println();
        System.out.println("============================================================");
        System.out.println("              CAMPUS NETWORK - ADJACENCY LIST");
        System.out.println("============================================================");

        for (Map.Entry<Location, LinkedHashSet<Location>> entry : adjList.entrySet()) {
            System.out.printf("%-20s -> ", entry.getKey().getName());

            if (entry.getValue().isEmpty()) {
                System.out.println("No direct connection");
            } else {
                System.out.println(formatLocations(entry.getValue(), ", "));
            }
        }

        System.out.println("------------------------------------------------------------");
        System.out.println("Vertices : " + getVertexCount());
        System.out.println("Edges    : " + getEdgeCount());
        System.out.println("============================================================");
    }

    public void searchLocation(String name) {
        if (!isValidName(name)) {
            return;
        }

        Location location = getExistingLocation(name);

        if (location == null) {
            System.out.println("\nLocation \"" + name + "\" was not found.");
            return;
        }

        Set<Location> neighbours = adjList.get(location);

        System.out.println("\nLocation found: " + location.getName());
        System.out.print("Direct neighbours: ");

        if (neighbours.isEmpty()) {
            System.out.println("None");
        } else {
            System.out.println(formatLocations(neighbours, ", "));
        }
    }

    public BFSResult findShortestRoute(String startName, String targetName) {
        BFSResult result = new BFSResult();

        if (!isValidName(startName) || !isValidName(targetName)) {
            return result;
        }

        Location start = getExistingLocation(startName);
        Location target = getExistingLocation(targetName);

        if (start == null || target == null) {
            System.out.println("\nBoth the starting location and destination must exist.");
            return result;
        }

        Queue<Location> queue = new ArrayDeque<>();
        Set<Location> visited = new LinkedHashSet<>();
        Map<Location, Location> parent = new LinkedHashMap<>();

        queue.add(start);
        visited.add(start);
        parent.put(start, null);

        boolean found = false;
        int step = 0;

        System.out.println();
        System.out.println("============================================================");
        System.out.println("                 BFS SHORTEST ROUTE");
        System.out.println("============================================================");
        System.out.println("Start       : " + start.getName());
        System.out.println("Destination : " + target.getName());
        System.out.println("------------------------------------------------------------");
        System.out.printf("%-6s %-20s %-55s%n", "Step", "Vertex Visited", "Queue After Processing");
        System.out.println("------------------------------------------------------------");

        while (!queue.isEmpty()) {
            Location current = queue.poll();
            step++;
            result.addVisited(current);

            for (Location neighbour : adjList.get(current)) {
                if (visited.add(neighbour)) {
                    parent.put(neighbour, current);
                    queue.add(neighbour);
                }
            }

            String queueDisplay = queue.toString();
            System.out.printf("%-6d %-20s %-55s%n", step, current.getName(), queueDisplay);
            result.addTrace(
                    "Step " + step + ": Visited " + current.getName()
                            + ", Queue = " + queueDisplay
            );

            if (current.equals(target)) {
                found = true;
                break;
            }
        }

        System.out.println("------------------------------------------------------------");

        for (Map.Entry<Location, Location> entry : parent.entrySet()) {
            result.setParent(entry.getKey(), entry.getValue());
        }

        if (!found) {
            System.out.println(
                    "No route is available between "
                            + start.getName() + " and " + target.getName() + "."
            );
            System.out.println("============================================================");
            return result;
        }

        LinkedList<Location> route = new LinkedList<>();
        Location current = target;

        while (current != null) {
            route.addFirst(current);
            current = parent.get(current);
        }

        result.setRoute(route);

        System.out.println();
        System.out.println("Route reconstruction:");
        current = target;

        while (current != null) {
            Location parentLocation = parent.get(current);
            System.out.println(
                    "Parent[" + current.getName() + "] = "
                            + (parentLocation == null ? "NULL" : parentLocation.getName())
            );
            current = parentLocation;
        }

        System.out.println();
        System.out.println("Shortest Route: " + result.getRouteString());
        System.out.println("Number of edges: " + result.getNumberOfEdges());
        System.out.println("Visited vertices: " + result.getVisitOrder().size());
        System.out.println("============================================================");

        return result;
    }

    private String formatLocations(Iterable<Location> locations, String separator) {
        StringBuilder output = new StringBuilder();

        for (Location location : locations) {
            if (output.length() > 0) {
                output.append(separator);
            }
            output.append(location.getName());
        }

        return output.toString();
    }

    public void clearGraph() {
        adjList.clear();
        autoLayoutCount = 0;
        System.out.println("Campus graph has been cleared.");
    }

    public int getVertexCount() {
        return adjList.size();
    }

    public int getEdgeCount() {
        int total = 0;

        for (Set<Location> neighbours : adjList.values()) {
            total += neighbours.size();
        }

        return total / 2;
    }

    public boolean isEmpty() {
        return adjList.isEmpty();
    }

    public void loadDefaultCampusMap() {
        adjList.clear();
        autoLayoutCount = 0;

        addVertexInternal("Library", 500, 100);
        addVertexInternal("Hostel", 700, 100);
        addVertexInternal("Office", 350, 250);
        addVertexInternal("Main Gate", 50, 400);
        addVertexInternal("Parking Area", 200, 400);
        addVertexInternal("Block A", 350, 550);
        addVertexInternal("Lecture Hall", 550, 550);
        addVertexInternal("Block B", 700, 450);
        addVertexInternal("Block C", 700, 650);
        addVertexInternal("Canteen", 350, 700);
        addVertexInternal("CITC", 550, 700);
        addVertexInternal("Sport Complex", 800, 700);

        addEdgeInternal("Main Gate", "Parking Area");
        addEdgeInternal("Parking Area", "Office");
        addEdgeInternal("Parking Area", "Block A");
        addEdgeInternal("Parking Area", "Lecture Hall");
        addEdgeInternal("Office", "Library");
        addEdgeInternal("Library", "Canteen");
        addEdgeInternal("Library", "Hostel");
        addEdgeInternal("Hostel", "Sport Complex");
        addEdgeInternal("Block A", "Lecture Hall");
        addEdgeInternal("Block A", "Canteen");
        addEdgeInternal("Lecture Hall", "Block B");
        addEdgeInternal("Lecture Hall", "Block C");
        addEdgeInternal("Canteen", "CITC");
        addEdgeInternal("CITC", "Sport Complex");

        autoLayoutCount = adjList.size();

        System.out.println();
        System.out.println("============================================================");
        System.out.println("             DEFAULT CAMPUS MAP LOADED");
        System.out.println("============================================================");
        
        // Display the default adjacency list
        for (Map.Entry<Location, LinkedHashSet<Location>> entry : adjList.entrySet()) {
            System.out.printf("%-20s -> ", entry.getKey().getName());

            if (entry.getValue().isEmpty()) {
                System.out.println("No direct connection");
            } else {
                System.out.println(
                    formatLocations(entry.getValue(), ", ")
                );
            }
        }
        System.out.println("------------------------------------------------------------");
        System.out.println("Vertices : " + getVertexCount());
        System.out.println("Edges    : " + getEdgeCount());
        System.out.println("============================================================");
    }
}
