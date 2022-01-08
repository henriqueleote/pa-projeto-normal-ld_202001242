package pt.pa.Command;

import pt.pa.graph.Vertex;
import pt.pa.model.Hub;
import pt.pa.model.Network;

public class RemoveCommand implements Command{
    private Network cart;
    private String name;
    private Vertex<Hub> removeProduct;

    public RemoveCommand(Network cart, String name) {
        this.cart = cart;
        this.name = name;
    }

    @Override
    public void execute() {
        System.out.println("EXECUTE REMOVE");
        removeProduct = cart.removeHub(name);
        return;

    }

    @Override
    public void unExecute() {
        cart.addHub(removeProduct.element().getName());
    }
}
