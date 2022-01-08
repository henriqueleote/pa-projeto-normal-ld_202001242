package pt.pa.model;

public class Hub {
    private String name;
    private int population;
    private int x;
    private int y;

    public Hub(String name) {
        this.name = name;
        this.population = 0;
        this.x = 0;
        this.y = 0;
    }

    public Hub(String name, int population, int x, int y) {
        this.name = name;
        this.population = population;
        this.x = x;
        this.y = y;
    }

    public Hub getHub(){
        return this;
    }
  
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setXY(int x, int y){
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString (){
        String toReturn = "";
        toReturn = getName();
        return toReturn;
    }

    /* Adicionar um toString() que vai mandar a Informação toda do Hub*/
}
