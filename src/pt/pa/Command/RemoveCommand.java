package pt.pa.Command;

import pt.pa.graph.Edge;
import pt.pa.graph.Vertex;
import pt.pa.model.Hub;
import pt.pa.model.Network;
import pt.pa.model.Route;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class RemoveCommand implements Command{
    private Network cart;
    private String origin;
    private String destination;
    private Edge<Route, Hub> removeProduct;

    public RemoveCommand(Network cart, String origin, String destination) {
        this.cart = cart;
        this.origin = origin;
        this.destination = destination;
    }

    @Override
    public void execute() throws Exception {
        System.out.println("EXECUTE REMOVE");
        removeProduct = cart.removeRoute(origin, destination);

    }

    @Override
    public void unExecute() throws Exception {

        cart.addRoute(origin, destination, removeProduct.element().getRoute());
    }
}
