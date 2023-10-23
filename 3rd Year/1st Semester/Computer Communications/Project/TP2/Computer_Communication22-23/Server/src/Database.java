import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Integer.parseInt;

public class Database {


    private Log logger;
    private Cache cache;
    public Database(String location){
        cache = new Cache();
        if(!location.isEmpty())
            this.readDatabase(location);
    }

    public Cache getCache(){
        return cache;
    }

    public void readDatabase(String databaseLocation)  {
        Map<String, String> macros = new HashMap<>();
        Map<String, String> nomes = new HashMap<>();

        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(databaseLocation));
            String line;
            while ((line = reader.readLine()) != null) {
                //this.add(line);
                String[] lineArgs = line.split(" ");

                if(line.startsWith("#") || line.isEmpty() || lineArgs.length < 1) continue;

                if(lineArgs[1].equals("DEFAULT")) {
                    if (lineArgs.length != 3) {
                        System.out.println("Too many arguments!");
                    }
                    macros.put(lineArgs[0], lineArgs[2]);
                    cache.adicionaLinhaCache(lineArgs[0], lineArgs[1], lineArgs[2], -1, -1, "FILE", null, null);
                    continue;
                }

                for (int i = 0; i < lineArgs.length; i++) {
                    if (macros.containsKey(lineArgs[i])) {
                        lineArgs[i] = macros.get(lineArgs[i]);
                    }
                }
                /* FALTA LEITURA CNAMES */
                if (lineArgs[1].equals("CNAME")) {
                    if (lineArgs.length != 4) {
                        System.out.println("CNAME precisa de 4 argumentos");
                    }
                    if (!lineArgs[3].equals(".")) {
                        lineArgs[0] = macros.get("@");
                    }
                    if (nomes.containsKey(lineArgs[2])) {
                        System.out.println("A name should not point to another name");
                    }
                    if (nomes.containsKey(lineArgs[0])) {
                        System.out.println("The same name should not be given to 2 different parameters");
                    }
                    cache.adicionaLinhaCache(lineArgs[0], lineArgs[1], lineArgs[2], Integer.parseInt(lineArgs[3]), -1,"FILE", null, null);
                }

                if (lineArgs[1].equals("SOASP") || lineArgs[1].equals("SOAADMIN") || lineArgs[1].equals("SOASERIAL") ||
                        lineArgs[1].equals("SOAREFRESH") || lineArgs[1].equals("SOAEXPIRE") || lineArgs[1].equals("SOARETRY")) {
                    if (lineArgs.length != 4) {
                        logger.error("Invalid number of arguments while reading " + lineArgs[1]);
                        continue;
                    }

                }

                if (lineArgs[1].equals("NS")) {
                    if (lineArgs.length < 3) {
                        logger.error("Invalid numero of arguments while reading NS.");
                        continue;
                    }

                    int TTL = (!lineArgs[3].isEmpty()) ? -1 : parseInt(lineArgs[3]);
                    int priority = (lineArgs.length > 4) ?  parseInt(lineArgs[4]) : -1;


                }

                if (lineArgs[1].equals("MX")) {
                    if (lineArgs.length < 4) {
                        System.out.println("Error");
                    }
                    if (lineArgs.length == 4) {
                        cache.adicionaLinhaCache(lineArgs[0], lineArgs[1], lineArgs[2], Integer.parseInt(lineArgs[3]), -1, "FILE", null, null);
                    } else {
                        cache.adicionaLinhaCache(lineArgs[0], lineArgs[1], lineArgs[2], Integer.parseInt(lineArgs[3]), Integer.parseInt(lineArgs[4]), "FILE", null, null);
                    }
                }

                if (lineArgs[1].equals("A")) {
                    if (lineArgs.length < 4) {
                        System.out.println("Error");
                    }
                    if (lineArgs.length == 4) {
                        String[] x = line.split(" ");
                        lineArgs[0] = x[0];
                        cache.adicionaLinhaCache(lineArgs[0], lineArgs[1], lineArgs[2], Integer.parseInt(lineArgs[3]), -1, "FILE", null, null);
                    } else {
                        String[] x = line.split(" ");
                        lineArgs[0] = x[0];
                        cache.adicionaLinhaCache(lineArgs[0], lineArgs[1], lineArgs[2], Integer.parseInt(lineArgs[3]), Integer.parseInt(lineArgs[4]), "FILE", null, null);
                    }

                    line = reader.readLine();
                }

            }
            reader.close();
        } catch(IOException e){
            System.out.println("An error occurred while loading " + databaseLocation + ": " + e.getMessage());
            return;
        }
    }


}
