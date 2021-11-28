import com.brunomnsilva.smartgraph.example.City;
import com.sun.corba.se.impl.corba.CORBAObjectImpl;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import pt.pa.graph.Vertex;
import pt.pa.model.Hub;

import java.io.*;
import java.lang.reflect.Array;
import java.nio.Buffer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;
import static java.lang.Integer.parseInt;


public class FileManager implements Serializable {
    private  Map<Integer, Integer> coordinatesList;
    private  ArrayList<Hub> hubList;

    public FileManager() {
        this.hubList = new ArrayList<>();
        coordinatesList = new HashMap<>();
    }

    public void importHubs(String filename) {
        String file = filename + ".txt";
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String s;
            while((s = br.readLine()) != null){
                hubList.add(new Hub(s));
                System.out.println(s);
            }
            br.close();

        } catch (IOException e){
            System.out.println("File doesn't exists or it may be broken!");
        }
    }

    public void importWeight(String filename){
        String file = filename + ".txt";
        ArrayList<Integer> weightList = new ArrayList<>();
        String aux = "";
        int i = 0;
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));

            while((aux = br.readLine()) != null){

                weightList.add(Integer.valueOf(aux));
                hubList.get(i).setPopulation(Integer.valueOf(aux));
                i++;
                System.out.println(aux);
            }
            br.close();

        } catch (IOException e){
            System.out.println("File doesn't exists or it may be broken!");
        }
    }

    public void importCoordinates(String filename){
        String file = filename + ".txt";

        //Map<Integer, Integer> coordinatesList = new HashMap<>();
        Map<Integer, Integer> auxs = new HashMap<>();
        String aux;
        int x = 0;
        int y = 0;
        int i = 0;
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));

            while((aux = br.readLine()) != null){
                x = Integer.parseInt(aux.substring(0, aux.indexOf(" ")));
                String sss = aux.substring(aux.indexOf(" ")+1).trim();
                y = Integer.parseInt(sss);
                hubList.get(i).setXY(x,y);
                i++;
            }
            br.close();

        } catch (IOException e){
            System.out.println("File doesn't exists or it may be broken!");
        }
    }

    public Map<Integer, ArrayList<Integer>> importRoutes (String filename){
        String file = filename + ".txt";
        Map<Integer, ArrayList<Integer>> toReturn = new HashMap<>();
        BufferedReader br = null;
        String aux = "";
        int as = 0;
        try {
            br = new BufferedReader(new FileReader(file));
            ArrayList<Integer> values = new ArrayList<>();

            while((aux = br.readLine()) != null){
                String[] a = aux.split(" ");
                for (int i = 0; i<a.length; i++){
                    values.add(parseInt(a[i]));
                }
                toReturn.put(as, values);
                as++;
                values = new ArrayList<>();
            }
            br.close();
        } catch (IOException e){
            System.out.println("File doesn't exists or it may be broken!");
        }
        return toReturn;
    }

    public ArrayList<Hub> loadHubs() {
        return hubList;
    }
}
