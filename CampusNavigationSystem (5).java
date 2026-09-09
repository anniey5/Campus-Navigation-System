import java.util.Scanner;

public class CampusNavigationSystem {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        CampusGraph graph = new CampusGraph();
        DisplayCampusMap.setGraph(graph);

        int choice;

        System.out.println("============================================================");
        System.out.println("              CAMPUS NAVIGATION SYSTEM");
        System.out.println("============================================================");
        System.out.println("Graph Representation : Adjacency List");
        System.out.println("Traversal Algorithm  : Breadth-First Search (BFS)");

        do {
            displayMainMenu();
            choice = readInt(sc, "Enter your choice: ");

            switch (choice) {
                case 1:
                    graph.loadDefaultCampusMap();
                    DisplayCampusMap.refreshGraph();
                    break;

                case 2:
                    addLocation(sc, graph);
                    DisplayCampusMap.refreshGraph();
                    break;

                case 3:
                    removeLocation(sc, graph);
                    DisplayCampusMap.refreshGraph();
                    break;

                case 4:
                    addWalkway(sc, graph);
                    DisplayCampusMap.refreshGraph();
                    break;

                case 5:
                    removeWalkway(sc, graph);
                    DisplayCampusMap.refreshGraph();
                    break;

                case 6:
                    graph.displayGraph();
                    break;

                case 7:
                    searchLocation(sc, graph);
                    break;

                case 8:
                    findShortestRoute(sc, graph);
                    break;

                case 9:
                    showGraphStatistics(graph);
                    break;

                case 10:
                    DisplayCampusMap.showGraph();
                    break;

                case 0:
                    System.out.println("\nThank you for using Campus Navigation System.");
                    break;

                default:
                    System.out.println("Invalid choice. Please select again.");
            }

        } while (choice != 0);

        sc.close();
    }

    private static void displayMainMenu() {
        System.out.println();
        System.out.println("============================================================");
        System.out.println("                         MAIN MENU");
        System.out.println("============================================================");
        System.out.println("1. Reset / Load Default Graph");
        System.out.println("2. Add Location (Vertex)");
        System.out.println("3. Remove Location (Vertex)");
        System.out.println("4. Add Walkway (Edge)");
        System.out.println("5. Remove Walkway (Edge)");
        System.out.println("6. Display Campus Network");
        System.out.println("7. Search Location");
        System.out.println("8. Find Shortest Route using BFS");
        System.out.println("9. Display Graph Statistics");
        System.out.println("10. Display Graph in GUI");
        System.out.println("0. Exit");
        System.out.println("============================================================");
    }

    private static void addLocation(Scanner sc, CampusGraph graph) {
        String again;
        do {
            System.out.println("\n---------------- ADD LOCATION ----------------");
            System.out.print("Enter location name: ");
            String name = sc.nextLine().trim();

            graph.addVertex(name);
            DisplayCampusMap.refreshGraph();

            again = readYesNo(sc, "\nDo you want to add another location? (Y/N): ");

        } while (again.equalsIgnoreCase("Y"));
    }

    private static void removeLocation(Scanner sc, CampusGraph graph) {
        String again;
        do {
            System.out.println("\n--------------- REMOVE LOCATION ---------------");
            System.out.print("Enter location name: ");
            String name = sc.nextLine().trim();

            graph.removeVertex(name);
            DisplayCampusMap.refreshGraph();

            again = readYesNo(sc, "\nDo you want to remove another location? (Y/N): ");

        } while (again.equalsIgnoreCase("Y"));
    }

    private static void addWalkway(Scanner sc, CampusGraph graph) {
        String again;
        do {
            System.out.println("\n---------------- ADD WALKWAY ----------------");
            System.out.print("Enter first location: ");
            String location1 = sc.nextLine().trim();
            
            System.out.print("Enter second location: ");
            String location2 = sc.nextLine().trim();

            graph.addEdge(location1, location2);
            DisplayCampusMap.refreshGraph();

            again = readYesNo(sc, "\nDo you want to add another walkway? (Y/N): ");

        } while (again.equalsIgnoreCase("Y"));
    }

    private static void removeWalkway(Scanner sc, CampusGraph graph) {
        String again;
        do {
            System.out.println("\n--------------- REMOVE WALKWAY ---------------");
            System.out.print("Enter first location: ");
            String location1 = sc.nextLine().trim();

            System.out.print("Enter second location: ");
            String location2 = sc.nextLine().trim();

            graph.removeEdge(location1, location2);
            DisplayCampusMap.refreshGraph();

            again = readYesNo(sc, "\nDo you want to remove another walkway? (Y/N): ");

        } while (again.equalsIgnoreCase("Y"));
    }

    private static void searchLocation(Scanner sc, CampusGraph graph) {
        System.out.println("\n--------------- SEARCH LOCATION ---------------");
        System.out.print("Enter location name: ");
        String name = sc.nextLine().trim();
        graph.searchLocation(name);
    }

    private static void findShortestRoute(Scanner sc, CampusGraph graph) {
        System.out.println("\n--------------- SHORTEST ROUTE ----------------");
        System.out.print("Enter starting location: ");
        String start = sc.nextLine().trim();

        System.out.print("Enter destination location: ");
        String destination = sc.nextLine().trim();

        graph.findShortestRoute(start, destination);
    }

    private static void showGraphStatistics(CampusGraph graph) {
        System.out.println();
        System.out.println("============================================================");
        System.out.println("                    GRAPH STATISTICS");
        System.out.println("============================================================");
        System.out.println("Number of vertices : " + graph.getVertexCount());
        System.out.println("Number of edges    : " + graph.getEdgeCount());
        System.out.println("Graph type         : Undirected");
        System.out.println("Weight             : Unweighted");
        System.out.println("Representation     : Adjacency List");
        System.out.println("Traversal          : Breadth-First Search (BFS)");
        System.out.println("============================================================");
    }

    private static int readInt(Scanner sc, String message) {
        while (true) {
            System.out.print(message);
            String input = sc.nextLine().trim();

            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }
    
    private static String readYesNo(Scanner sc, String message) {
        while (true) {
            System.out.print(message);
            String input = sc.nextLine().trim();

            if (input.equalsIgnoreCase("Y") || input.equalsIgnoreCase("N")) {
                return input;
            }

            System.out.println("Invalid input. Please enter Y or N.");
        }
    }
}
