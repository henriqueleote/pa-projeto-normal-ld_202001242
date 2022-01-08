package pt.pa.model;

import pt.pa.model.Hub;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;


import static java.lang.Integer.parseInt;


/*
* Esta classe é responsável pela leitura dos datasets
*
* */
public class FileLoader implements Serializable {
    private  Map<Integer, Integer> coordinatesList;
    private  ArrayList<Hub> hubList;

    public FileLoader(String folderName, String routeName) {
        this.hubList = new ArrayList<>();
        coordinatesList = new HashMap<>();
        importHubs(folderName);
        importWeight(folderName);
        importCoordinates(folderName);
        importRoutes(folderName,routeName);
    }

    public void importHubs(String foldername) {
        String file = foldername + "name.txt";
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

    public void importWeight(String foldername){
        String file = foldername + "weight.txt";
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

    public void importCoordinates(String foldername){
        String file = foldername + "xy.txt";

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
        //return coordinatesList;
        for(Hub h : hubList){
            System.out.println(h.getName() + " X - " + h.getX() +" Y - " + h.getY());
        }
    }

    public Map<Integer, ArrayList<Integer>> importRoutes (String foldername, String filename){
        String file = foldername + filename +  ".txt";
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
