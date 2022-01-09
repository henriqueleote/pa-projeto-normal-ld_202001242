package pt.pa.view;
import pt.pa.Command.NetworkController;
import pt.pa.observerpattern.*;

public interface NetworkUI extends Observer {

    String getOriginHubName();
    String getDestinationHubName();
    int getRouteValue();

    void displayError(String msg);
    void clearError();
    void clearControls();

    void setTriggers(NetworkController controller);
}