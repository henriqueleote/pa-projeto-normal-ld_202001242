package pt.pa.view;

import com.brunomnsilva.smartgraph.graphview.SmartGraphPanel;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import pt.pa.model.Hub;
import pt.pa.model.Network;
import pt.pa.model.Route;

public class NetworkView extends BorderPane {
    private Network graph;
    private SmartGraphPanel<Hub, Route> graphView;

    private Button btAddPerson;
    private Button btRemovePerson;
    private Button btAddGroupRelationship;
    private Button btAddClassRelationship;
    private Button btRemoveRelationship;
    private Label lblError;
    private ComboBox<String> cbRoles;
    private ComboBox<String> cbPersonId1;
    private ComboBox<String> cbPersonId2;
    private TextField txtPersonId;
    private TextField txtPersonName;
    private TextField txtRelationshipDescription;
    private Label lblPeopleCount;
    private Label lblMostPopular;

    public NetworkView(Network graph) {
        this.graph = graph;


    }
}
