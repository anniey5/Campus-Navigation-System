import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

public class GraphView extends Pane {

    private final CampusGraph graph;

    public GraphView(CampusGraph graph) {
        this.graph = graph;
        drawGraph();
    }

    private void drawGraph() {
        getChildren().clear();

        Map<Location, LinkedHashSet<Location>> adjacency = graph.getAdjList();
        List<Location> vertices = new ArrayList<>(adjacency.keySet());

        for (Location location : vertices) {
            for (Location neighbour : adjacency.get(location)) {
                if (location.getName().compareToIgnoreCase(neighbour.getName()) < 0) {
                    Line line = new Line(
                            location.getX(),
                            location.getY(),
                            neighbour.getX(),
                            neighbour.getY()
                    );
                    getChildren().add(line);
                }
            }
        }

        for (Location location : vertices) {
            Circle circle = new Circle(location.getX(), location.getY(), 20);
            Text text = new Text(
                    location.getX() - 30,
                    location.getY() - 28,
                    location.getName()
            );

            getChildren().addAll(circle, text);
        }
    }

    public void refresh() {
        drawGraph();
    }

    public CampusGraph getGraph() {
        return graph;
    }
}
