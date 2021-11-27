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

public class FileManager implements Serializable {
    private  Map<Integer, Integer> coordinatesList;
    private  ArrayList<Hub> hubList;

    public FileManager() {
        this.hubList = new ArrayList<>();
        coordinatesList = new HashMap<>();
    }

    public ArrayList<Hub> importHubs(String filename) {
        String file = "dataset/sgb32/"+ filename + ".txt";
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String s;
            while((s = br.readLine()) != null){

                hubList.add(new Hub(s));
                System.out.println(s);
            }
            br.close();

        } catch (IOException e){
            System.out.println("dsadsadsa");
        }
        return hubList;
    }

    public ArrayList<Integer> importWeight(String filename){
        String file = "dataset/sgb32/"+ filename + ".txt";
        ArrayList<Integer> weightList = new ArrayList<>();
        String aux = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));

            while((aux = br.readLine()) != null){

                weightList.add(Integer.valueOf(aux));
                System.out.println(aux);
            }
            br.close();

        } catch (IOException e){
            System.out.println("dsadsadsa");
        }
        return weightList;
    }

    public ArrayList<Hub> importCoordinates(String filename){
        String file = "dataset/sgb32/"+ filename + ".txt";

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
                //System.out.println("teste - X"+hubList.get(i).getX() + "teste - Y"+hubList.get(i).getY());
                //auxs.put(x,y);
                //System.out.println("ANTES "+i + " " + x+" - X "+y+" - Y ");
                //coordinatesList.put(x,y);
                //System.out.println("LISTA APOS PUT "+coordinatesList);
                //auxs.put(x,y);
                //System.out.println("DEPOIS "+i + " " + x+" - X "+y+" - Y ");
                i++;


            }
            br.close();

        } catch (IOException e){
            System.out.println("dsadsadsa");
        }
        //return coordinatesList;
        for(Hub h : hubList){
            System.out.println(h.getName() + " X - " + h.getX() +" Y - " + h.getY());
        }
        return hubList;
    }

    public Map<Integer, List<String>> importRoutes (String filename){
        String file = "dataset/sgb32/"+ filename + ".txt";
        Map<Integer, List<String>> toReturn = new HashMap<>();
        List<String> listAux = new ArrayList<>();
        String aux = "";
        int i = 0;
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));

            while((aux = br.readLine()) != null){
                String[] a = aux.split(" ");
                for(int s = 0; s < a.length; s++) {
                    Array.get(a, s);
                    Object u = Array.get(a, s);
                    //System.out.println("Current index is: " + s + " ---> " + u);

                    List<String> listAux2 = Arrays.asList(a);

                    listAux.add(listAux2.get(s));
                    toReturn.put(i, listAux);
                    // = Arrays.stream(a).toArray();
                }
               i++;


            }
            br.close();

        } catch (IOException e){
            System.out.println("dsadsadsa");
        }
        System.out.println("TORETURN --> "+toReturn.get(0).get(5));
        return toReturn;
    }
}
