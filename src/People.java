import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

import jodd.json.JsonParser;
import jodd.json.JsonSerializer;

public class People {
    public static void main(String[] args) throws IOException {

        HashMap<String, ArrayList<Person>> nationalityMap = readFile();

        for (ArrayList<Person> peopleNames : (nationalityMap.values())) {
            Collections.sort(peopleNames);
       }

        System.out.println(nationalityMap);
        saveFile(nationalityMap);
    }

    public static HashMap<String, ArrayList<Person>> readFile() throws FileNotFoundException {
        ArrayList<Person> people = new ArrayList<>();
        File f = new File("people.csv");
        Scanner fileScanner = new Scanner(f);
        HashMap<String, ArrayList<Person>> nationalityMap = new HashMap<>();


        int i = 0;
        while (fileScanner.hasNext()) {
            String line = fileScanner.nextLine();
            if (i != 0) {
                String[] column = line.split(",");
                Person p = new Person(column[0], column[1], column[2], column[3], column[4], column[5]);
                people.add(p);


                if (!nationalityMap.containsKey(p.country)) {

                    nationalityMap.put(p.country, new ArrayList<>());
                }
                nationalityMap.get(p.country).add(p);
            }
            i ++;
        }
        return nationalityMap;
    }

    public static void saveFile(HashMap<String, ArrayList<Person>> map) throws IOException {
        File f = new File("people.json");
        JsonSerializer serializer = new JsonSerializer();
        String json = serializer.include("*").serialize(map);
        FileWriter fw = new FileWriter(f);
        fw.write(json);
        fw.close();

    }
}
