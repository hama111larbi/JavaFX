package Controllers;

import com.gluonhq.maps.MapLayer;
import com.gluonhq.maps.MapPoint;
import com.gluonhq.maps.MapView;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class ContactController {

    private final MapPoint eiffelp=new MapPoint(48.8583701,2.2944813);
    @FXML
    private VBox address;
    public void initialize (){
        MapView mapView =createMapView();
        address.getChildren().add(mapView);
        VBox.setVgrow(mapView, Priority.ALWAYS);

    }
    public MapView createMapView(){
        MapView mapView=new MapView();
        mapView.setPrefSize(500,400);
        mapView.addLayer(new CustomMapLayer());
        mapView.setZoom(15);
        mapView.flyTo(0,eiffelp,0.1);
        return mapView;
    }
    private class CustomMapLayer extends MapLayer{
        private final Node marker;
        public  CustomMapLayer (){
            marker=new Circle(20, Color.RED);
            getChildren().add(marker);

        }
        @Override
        protected void layoutLayer(){
            Point2D point=getMapPoint(eiffelp.getLatitude(),eiffelp.getLongitude());
            marker.setTranslateX(point.getX());
            marker.setTranslateY(point.getY());
        }

    }
}
