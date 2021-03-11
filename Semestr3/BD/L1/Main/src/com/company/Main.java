package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {
    private String fileName;
    private File file;

    Main() {
        fileName = "world.csv";
        file = new File(this.fileName);
    }

    public static void main(String[] args) {
        Main m = new Main();
        m.zad4();
    }

    public void zad1() {
        try {
            Scanner inputStream = new Scanner(file);
            Set<String> set = new LinkedHashSet<>();
            while (inputStream.hasNextLine()) {
                String data = inputStream.nextLine().replace("\"", "");
                String[] values = data.split(";");
                if(Objects.equals(values[16], "1")) {
                    set.add(values[1] + " : " + values[15]);
                }
            }
            for (String s : set) {
                System.out.println(s);
            }
            inputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void zad2() {
        try {
            Scanner inputStream = new Scanner(file);
            Set<String> set = new LinkedHashSet<>();
            while (inputStream.hasNextLine()) {
                String data = inputStream.nextLine().replace("\"", "");
                String[] values = data.split(";");
                if(!values[4].equals("SurfaceArea") && Double.parseDouble(values[4].replace(',','.')) > 300.00) {
                    set.add(values[1] + " : " + values[4]);
                }
            }
            for (String s : set) {
                System.out.println(s);
            }
            inputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void zad3() {
        try {
            Scanner inputStream = new Scanner(file);
            Set<String> set = new LinkedHashSet<>();
            while (inputStream.hasNextLine()) {
                String data = inputStream.nextLine().replace("\"", "");
                String[] values = data.split(";");
                if(!Objects.equals(values[18], "CityPopulation") && Integer.parseInt(values[18])>10000) {
                    set.add(values[17] + ";" + values[15] +" : " + values[1]);
                }
            }
            ArrayList<String> list = new ArrayList<>();
            list.addAll(set);
            Collections.sort(list);
            for (String s : list) {
                System.out.println(s.substring(s.indexOf(';')+1));
            }
            inputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void zad4() {
        try {
            int asia = 0;
            int europe = 0;
            int north_america = 0;
            int africa = 0;
            int oceania = 0;
            int south_america = 0;


            Scanner inputStream = new Scanner(file);
            LinkedHashMap<String,String> map = new LinkedHashMap<>();
            while (inputStream.hasNextLine()) {
                String data = inputStream.nextLine().replace("\"", "");
                String[] values = data.split(";");
                map.put(values[1], values[2]);
            }
            Set set = map.entrySet();
            Iterator i = set.iterator();
            i.next();
            while(i.hasNext()) {
                Map.Entry me = (Map.Entry)i.next();
                switch (me.getValue().toString()) {
                    case "North America": {
                        north_america++;
                        break;
                    }
                    case "Asia": {
                        asia++;
                        break;
                    }
                    case "South America": {
                        south_america++;
                        break;
                    }
                    case "Europe": {
                        europe++;
                        break;
                    }
                    case "Africa": {
                        africa++;
                        break;
                    }
                    case "Oceania":{
                        oceania++;
                        break;
                    }
                }
            }
            System.out.println("Asia " + asia);
            System.out.println("Europe " + europe);
            System.out.println("North America " + north_america);
            System.out.println("Africa " + africa);
            System.out.println("Oceania " + oceania);
            System.out.println("South America " + south_america);

            inputStream.close();
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }
}
