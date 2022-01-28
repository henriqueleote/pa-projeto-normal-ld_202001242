package pt.pa.model;

public class Route {
    private int route;
    private static int id = 0;
    private int counter = 0;

    public Route(int route) {
        this.route = route;
        this.id++;
        counter = id;
    }

    public int getRoute() {
        return route;
    }

    @Override
    public String toString(){
        return getRoute() + "";
    }

}
