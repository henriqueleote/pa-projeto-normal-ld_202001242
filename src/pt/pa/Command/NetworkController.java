package pt.pa.Command;

import java.util.Collection;

import pt.pa.graph.Vertex;
import pt.pa.model.Hub;
import pt.pa.model.Network;
import pt.pa.view.NetworkUI;
import sun.nio.ch.Net;

public class NetworkController {
    private Network cart;
    private NetworkUI view;
    private CommandManager commandmanager;


    public NetworkController(Network cart, NetworkUI view) {
        this.cart = cart;
        this.view = view;
        commandmanager= new CommandManager();

        this.view.setTriggers(this);

        this.cart.addObserver(this.view);
    }

    public void addRoute(){
        try {
            String origin = view.getOriginHubName();
            String destination = view.getDestinationHubName();
            int routeValue = view.getRouteValue();
            if(origin.isEmpty()){
                this.view.displayError("The origin Hub connot be empty");
                return;
            }
            if(destination.isEmpty()){
                this.view.displayError("The destination Hub connot be empty");
                return;
            }

            cart.addRoute(origin, destination, routeValue);
            Command c = new AddCommand(cart,origin,destination,routeValue);
            commandmanager.executeCommand(c);
            view.clearError();
            view.clearControls();

        } catch (Exception e) {
            this.view.displayError( e.getMessage() );
        }
    }

    public void removeRoute(){
        try {
            String origin = view.getOriginHubName();
            String destination = view.getDestinationHubName();
            if(origin.isEmpty()){
                this.view.displayError("The name connot be empty");
                return;
            }
            cart.removeRoute(origin, destination);
            Command c = new RemoveCommand(cart,origin, destination);
            commandmanager.executeCommand(c);
            view.clearError();
            view.clearControls();

        } catch (Exception e) {
            this.view.displayError( e.getMessage() );
        }
    }

/*
    public void reset() {
        Command c = new ResetCommand(cart);
        commandmanager.executeCommand(c);
    }

    public void removeProduct(String name) {
        Command c = new RemoveCommand(cart,name);
        commandmanager.executeCommand(c);
    }

    public Collection<Product> getProducts() {
        return cart.getProducts();
    }

*/

    public void undo() throws Exception {
        commandmanager.undo();
        System.out.println("UNDO");
    }


    public String showAll() {
        String str = cart.toString();
        return str;
    }
}
