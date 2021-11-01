package org.sge.app;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import org.sge.app.fx.CollectionsBindings;
import org.sge.app.graph.EdgeData;
import org.sge.app.graph.GraphHelper;
import org.sge.app.graph.VertexData;
import org.sge.graph.ConnectionManager;
import org.sge.graph.Edge;
import org.sge.graph.Graph;
import org.sge.graph.Vertex;

import java.util.List;

public class SGEApplication extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        var graph = new Graph<VertexData, EdgeData>();
        GraphHelper graphHelper = new GraphHelper(graph);

        Pane vertexPane = new Pane();
        Pane edgesPane = new Pane();

        vertexPane.setPrefSize(640, 480);
        edgesPane.setPrefSize(640, 480);

        Pane viewport = new Pane(edgesPane, vertexPane);

        viewport.setStyle("-fx-background-color: #333333");
        viewport.setOnMousePressed(event -> {
            if(event.isControlDown()){
                graph.vertices().add(new VertexData(event.getX(), event.getY()));
            }
        });
        stage.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if(event.getCode() == KeyCode.F){
                var vertices = graphHelper.vertices();
                var a = vertices.get(0);
                var b = vertices.get(1);
                var connections = graph.connections();
                connections.findConnection(a, b).ifPresentOrElse(
                        connections::disconnect,
                        () -> connections.connect(a, b, new EdgeData())
                );
            }
            else if(event.getCode() == KeyCode.DELETE){
                var vertices = graphHelper.vertices();
                var a = vertices.get(0);
                var b = vertices.get(1);
                graph.vertices().removeAll(List.of(a, b));
            }
        });

        CollectionsBindings.bindContentWithConverter(graphHelper.vertices(), vertexPane.getChildren(), VertexView::new);
        CollectionsBindings.bindContentWithConverter(graphHelper.edges(), edgesPane.getChildren(), EdgeView::new);

        BorderPane root = new BorderPane(new ScrollPane(viewport));
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

    private static class EdgeView extends Line {
        public EdgeView(Edge<VertexData, EdgeData> edge) {
            startXProperty().bind(edge.a().data().xProperty());
            startYProperty().bind(edge.a().data().yProperty());

            endXProperty().bind(edge.b().data().xProperty());
            endYProperty().bind(edge.b().data().yProperty());

            setStrokeWidth(2);
            setFill(Color.BLACK);
        }
    }
}
