package org.sge.app;

import javafx.application.Application;
import javafx.collections.ListChangeListener;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import org.sge.app.fx.CollectionsBindings;
import org.sge.app.graph.EdgeData;
import org.sge.app.graph.GraphHelper;
import org.sge.app.graph.VertexData;
import org.sge.graph.Graph;
import org.sge.graph.Vertex;

public class SGEApplication extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        var graph = new Graph<VertexData, EdgeData>();
        GraphHelper graphHelper = new GraphHelper(graph);
        graphHelper.addVertex(32, 32);

        Pane vertexPane = new Pane();
        vertexPane.setPrefSize(640, 480);
        vertexPane.setStyle("-fx-background-color: #333333");
        vertexPane.setOnMousePressed(event -> {
            if(event.isControlDown()){
                graphHelper.addVertex(event.getX(), event.getY());
            }
        });
        CollectionsBindings.bindContentWithConverter(graphHelper.vertices(), vertexPane.getChildren(), VertexView::new);

        BorderPane root = new BorderPane(new ScrollPane(vertexPane));
        stage.setScene(new Scene(root, 1280, 720));
        stage.show();
    }

    private static class VertexView extends Circle{
        public VertexView(Vertex<VertexData, EdgeData> vertex) {
            super(16, Color.PURPLE);

            centerXProperty().bind(vertex.data().xProperty());
            centerYProperty().bind(vertex.data().yProperty());
        }
    }
}
