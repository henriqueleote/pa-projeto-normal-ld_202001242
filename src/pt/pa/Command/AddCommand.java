package pt.pa.Command;


import pt.pa.model.*;

public class AddCommand implements Command{
    private Network cart;
    private String name;

    public AddCommand(Network cart, String name) {
        this.cart = cart;
        this.name = name;
    }

    @Override
    public void execute() {
        System.out.println("EXECUTE ADD");
        cart.addHub(name);
    }

    @Override
    public void unExecute() {

    }

    /*
    @Override
    public void unExecute() {
        Product p= new Product(name,cost);
        cart.removeProduct(p);
    }
    */
}
