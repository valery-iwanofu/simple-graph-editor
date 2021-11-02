package org.sge.app;

import com.gdetotut.jundo.UndoCommand;
import com.gdetotut.jundo.UndoStack;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
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

import java.util.*;

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
                var vertices = graphHelper.vertices().stream().filter(vertex -> vertex.data().isSelected()).toList();
                if(vertices.size() < 2){
                    return;
                }
                for (int i = 0; i < vertices.size() - 1; i++) {
                    var a = vertices.get(i);
                    var b = vertices.get(i + 1);

                    graph.connections().findConnection(a, b).ifPresentOrElse(
                            edge -> graph.connections().disconnect(edge),
                            () -> graph.connections().connect(a, b, new EdgeData())
                    );
                }
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

    private static class AddVertex extends UndoCommand{
        private final Graph<VertexData, EdgeData> graph;
        private final VertexData vertexData;
        private final Vertex<VertexData, EdgeData> vertex;

        public AddVertex(UndoStack owner, UndoCommand parent, Graph<VertexData, EdgeData> graph, VertexData vertexData, Vertex<VertexData, EdgeData> vertex) {
            super(owner, "Add vertex", parent);
            this.graph = graph;
            this.vertexData = vertexData;
            this.vertex = vertex;
        }

        @Override
        protected void doUndo() {
            graph.vertices().remove(vertex);
        }

        @Override
        protected void doRedo() {
            graph.vertices().add(vertexData);
        }
    }

    private static class VertexView extends Circle{
        public VertexView(Vertex<VertexData, EdgeData> vertex) {
            super(16, Color.BLACK);

            centerXProperty().bind(vertex.data().xProperty());
            centerYProperty().bind(vertex.data().yProperty());

            strokeProperty().bind(
                    Bindings.when(vertex.data().selectedProperty()).then(Color.ORANGE).otherwise(Color.LIGHTGRAY)
            );

            setOnMouseClicked(event -> {
                vertex.data().setSelected(!vertex.data().isSelected());
            });
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

    private static class V{

    }
    private static class E{

    }
    private static class Vertices{
        private final Set<V> set = new HashSet<>();
        private final Map<V, Set<E>> edgesByVertex = new HashMap<>();

        public void add(V vertex){
            if(!set.add(vertex)){
                return;
            }
            edgesByVertex.put(vertex, new HashSet<>());
        }
        public void remove(V vertex){

        }
    }
}
