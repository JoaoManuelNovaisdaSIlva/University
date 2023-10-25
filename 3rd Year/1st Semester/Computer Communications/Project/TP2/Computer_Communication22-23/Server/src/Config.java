import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Config {

    private String databaseLocation;
    private String logLocation;

    private String mainDomain;

    private Map<String, List<String>> servers;

    public Config(String configLocation){
        this.servers = new HashMap<>();
        this.readConfig(configLocation);
    }

    public String getLogLocation() {
        return logLocation;
    }

    public String getDatabaseLocation(){
        return databaseLocation;
    }

    public void setDatabaseLocation(String databaseLocation) {
        this.databaseLocation = databaseLocation;
    }

    public void setLogLocation(String logLocation) {
        this.logLocation = logLocation;
    }

    public String getMainDomain() {
        return mainDomain;
    }



    private void readConfig(String configLocation){

        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(configLocation));
            String line = reader.readLine();
            while (line != null) {
                System.out.println(line);
                String[] token = line.split(" ");
                if(token[0].contains(".")){
                    this.mainDomain = token[0];
                }
                this.parseLine(token);
                line = reader.readLine();
            }

            this.readFiles();
            reader.close();
        } catch (IOException e) {
            System.out.println("An error occurred while loading " + configLocation + ": " + e.getMessage());
            return;
        } catch (Exception e){
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void parseLine(String[] lineArgs){
        if(lineArgs.length < 3) return;
        if(lineArgs[1].equals("SS") || lineArgs[1].equals("SP") || lineArgs[1].equals("ST")){
            if(servers.get(lineArgs[1]) == null){
                List<String> list = new ArrayList<>();
                list.add(lineArgs[2]);
                servers.put(lineArgs[1], list);
            }
            else
            {
                List<String> list = servers.get(lineArgs[1]);
                list.add(lineArgs[2]);
            }
        }

        if(lineArgs[1].equals("LG")){
            this.logLocation = lineArgs[2];
            System.out.println("Log file location set to: " + this.logLocation);
            //Falta o codigo para o LG
        }
        if(lineArgs[1].equals("DB")){
            this.databaseLocation = lineArgs[2];
            System.out.println("Database location set to: " + this.databaseLocation);
        }
    }

    public List<String> getAddress(String serverType){
        return this.servers.get(serverType);
    }

    private void readFiles(){
        if(databaseLocation != null && !this.databaseLocation.isEmpty()){
            File yourFile = new File(this.databaseLocation);
            try {
                yourFile.createNewFile();

            } catch (Exception e){
                System.out.println("Impossible to create file: " + this.databaseLocation + " : " + e.getMessage());
            }
        }

        if(!this.logLocation.isEmpty()){
            File yourFile = new File(this.logLocation);
            try {
                yourFile.createNewFile();
            } catch (Exception e){
                System.out.println("Impossible to create file: " + this.logLocation + " : " + e.getMessage());
            }
        }
    }


}
