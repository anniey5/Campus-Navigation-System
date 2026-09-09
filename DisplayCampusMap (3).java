import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
/**
 * JavaFX application used to display the campus graph visually.
 *
 * The JavaFX graph uses the same CampusGraph object as the console-based CampusNavigationSystem.
 */
public class DisplayCampusMap extends Application {

    // SHARED GRAPH
    
    // Stores the CampusGraph shared with the console application.
    private static CampusGraph sharedGraph;

    // Stores the JavaFX GraphView used to display the graph.
    private static GraphView graphView;
    
    private static boolean launched = false;

    // Sets the graph used by the JavaFX visualisation.
    public static void setGraph(CampusGraph graph) {

        sharedGraph = graph;
    }

    @Override
    public void start(Stage primaryStage) {

        // Create a default graph only if no graph has been provided by the console application
        if (sharedGraph == null) {

            sharedGraph =
                    new CampusGraph();

            sharedGraph.loadDefaultCampusMap();
        }

        // Create a GraphView using the shared graph.
        graphView =
                new GraphView(sharedGraph);

        // Place the GraphView inside the JavaFX scene.
        Scene scene =
                new Scene(
                        graphView,
                        950,
                        800
                );

        // Set the title of the JavaFX window.
        primaryStage.setTitle(
                "Campus Navigation System"
        );

        // Set the scene.
        primaryStage.setScene(scene);

        // Display the JavaFX window.
        primaryStage.show();
    }

    // Refreshes the JavaFX graph after the CampusGraph changes.
    public static void refreshGraph() {

        // Check whether the GraphView has already been created
        if (graphView != null) {

            // Run the refresh operation on the JavaFX Application Thread.
            Platform.runLater(() -> {
                graphView.refresh();
            });
        }
    }

    public static void showGraph() {

        if (!launched) {

            launched = true;

            new Thread(() -> {

                launch(
                        DisplayCampusMap.class
                );

            }).start();

        } else {

            refreshGraph();
        }
    }
    
    // Starts the JavaFX application when this class is executed directly.
    public static void main(String[] args) {

        launch(args);
    }
}