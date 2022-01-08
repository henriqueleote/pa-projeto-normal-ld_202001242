package pt.pa.Command;

import java.util.Collection;
import pt.pa.model.Network;
import sun.nio.ch.Net;

public class NetworkController {
    private Network cart;
    private CommandManager commandmanager;


    public NetworkController(Network cart) {
        this.cart = cart;
        commandmanager= new CommandManager();



    }

    public void addVertex(String name) {
        Command c = new AddCommand(cart,name);
        commandmanager.executeCommand(c);
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
