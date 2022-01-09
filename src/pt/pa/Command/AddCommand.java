package pt.pa.Command;


import pt.pa.model.*;

public class AddCommand implements Command{
    private Network cart;
    private String origin;
    private String destination;
    private int routeValue;

    public AddCommand(Network cart, String origin, String destination, int routeValue) {
        this.cart = cart;
        this.origin = origin;
        this.destination = destination;
        this.routeValue = routeValue;
    }

    @Override
    public void execute() throws Exception {
        System.out.println("EXECUTE ADD");
        cart.addRoute(origin,destination,routeValue);
    }

    @Override
    public void unExecute() throws Exception {
        cart.removeRoute(origin,destination);
    }

    /*
    @Override
    public void unExecute() {
        Product p= new Product(name,cost);
        cart.removeProduct(p);
    }
    */
}
