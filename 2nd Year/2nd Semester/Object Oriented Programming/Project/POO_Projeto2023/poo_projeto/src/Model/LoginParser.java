package Model;

import java.io.*;
import java.util.*;

public class LoginParser implements Serializable{
    private Map<String, List<String>> contasCompradores; // A key é o email e o value é uma lista com a password e o id
    private Map<String, List<String>> contasVendedores;
    private Map<String, List<String>> contasTransportadoras;

    public LoginParser(){
        this.contasCompradores = new HashMap<>();
        this.contasVendedores = new HashMap<>();
        this.contasTransportadoras = new HashMap<>();
        this.readFiles();
    }

    public Map<String, List<String>> getContasCompradores() {
        return contasCompradores;
    }

    public void setContasCompradores(Map<String, List<String>> contasCompradores) {
        this.contasCompradores = contasCompradores;
    }

    public Map<String, List<String>> getContasVendedores() {
        return contasVendedores;
    }

    public void setContasVendedores(Map<String, List<String>> contasVendedores) {
        this.contasVendedores = contasVendedores;
    }

    public Map<String, List<String>> getContasTransportadoras() {
        return contasTransportadoras;
    }

    public void setContasTransportadoras(Map<String, List<String>> contasTransportadoras) {
        this.contasTransportadoras = contasTransportadoras;
    }

    public void readFiles(){
        readCompradoresFile();
        readVendedoresFile();
        readTransportadorasFile();
    }

    public void readCompradoresFile(){
        try{
            File file = new File("./DB/compradores.txt");
            Scanner scanner = new Scanner(file);

            while(scanner.hasNextLine()){
                List<String> comp = new ArrayList<>();
                String line = scanner.nextLine();
                String[] linesPart = line.split(";");
                if(linesPart.length == 3){
                    comp.add(linesPart[1]);
                    comp.add(linesPart[2]);
                    this.contasCompradores.put(linesPart[0], comp);
                } else System.out.println("Ficheiro de contas mal formatado!");
            }
            scanner.close();
        } catch (FileNotFoundException e){
            System.out.println("Ficheiro não encontrado!");
            e.printStackTrace();
        }
    }

    public void readVendedoresFile(){
        try{
            File file = new File("./DB/vendedores.txt");
            Scanner scanner = new Scanner(file);

            while(scanner.hasNextLine()){
                List<String> vend = new ArrayList<>();
                String line = scanner.nextLine();
                String[] linesPart = line.split(";");
                if(linesPart.length == 3){
                    vend.add(linesPart[1]);
                    vend.add(linesPart[2]);
                    this.contasVendedores.put(linesPart[0], vend);
                } else System.out.println("Ficheiro de contas mal formatado!");
            }
            scanner.close();
        } catch (FileNotFoundException e){
            System.out.println("Ficheiro não encontrado!");
            e.printStackTrace();
        }
    }

    public void readTransportadorasFile(){
        try{
            File file = new File("./DB/transportadoras.txt");
            Scanner scanner = new Scanner(file);

            while(scanner.hasNextLine()){
                List<String> trans = new ArrayList<>();
                String line = scanner.nextLine();
                String[] linesPart = line.split(";");
                if(linesPart.length == 3){
                    trans.add(linesPart[1]);
                    trans.add(linesPart[2]);
                    this.contasTransportadoras.put(linesPart[0], trans);
                } else System.out.println("Ficheiro de contas mal formatado!");
            }
            scanner.close();
        } catch (FileNotFoundException e){
            System.out.println("Ficheiro não encontrado!");
            e.printStackTrace();
        }
    }

    public void adicionaConta(TipoConta tipo, int id, String email, String passwaord){
        switch (tipo) {
            case Comprador -> adicionaComprador(id, email, passwaord);
            case Vendedor -> adicionaVendedor(id, email, passwaord);
            case Transportadora -> adicionaTransportadora(id, email, passwaord);
        }
    }

    public void adicionaComprador(int id, String email, String password){
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter("./DB/compradores.txt", true));
            writer.write(email + ";" + password + ";" + id);
            writer.newLine();
            writer.close();
            System.out.println("Comprador " + id + " adicionado aos logins!");
        } catch (IOException e){
            System.out.println("Erro ao adicionar um novo comprador aos logins!");
        }
        List<String> temp = new ArrayList<>();
        temp.add(password);
        temp.add(String.valueOf(id));
        this.contasCompradores.put(email, temp);
    }

    public void adicionaVendedor(int id, String email, String password){
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter("./DB/vendedores.txt", true));
            writer.write(email + ";" + password + ";" + id);
            writer.newLine();
            writer.close();
            System.out.println("Vendedor " + id + " adicionado aos logins!");
        } catch (IOException e){
            System.out.println("Erro ao adicionar um novo vendedor aos logins!");
        }
        List<String> temp = new ArrayList<>();
        temp.add(password);
        temp.add(String.valueOf(id));
        this.contasVendedores.put(email, temp);
    }

    public void adicionaTransportadora(int id, String email, String password){
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter("./DB/transportadoras.txt", true));
            writer.write(email + ";" + password + ";" + id);
            writer.newLine();
            writer.close();
            System.out.println("Transportadora " + id + " adicionado aos logins!");
        } catch (IOException e){
            System.out.println("Erro ao adicionar um novo transportadoras aos logins!");
        }
        List<String> temp = new ArrayList<>();
        temp.add(password);
        temp.add(String.valueOf(id));
        this.contasTransportadoras.put(email, temp);
    }

    public int validaLogin(TipoConta tipo, String email, String password){
        switch (tipo) {
            case Comprador: {
                List<String> dados = this.contasCompradores.getOrDefault(email, new ArrayList<>());
                if(dados.size() == 0){
                    System.out.println("Introduziu o email ou palavra-passe errada!");
                    return -1;
                } else if (password.equals(dados.get(0))) {
                    System.out.println("Login do comprador " + dados.get(1) + " foi fieto com sucesso!");
                    return Integer.parseInt(dados.get(1));
                }
            }
            case Vendedor: {
                List<String> dados = this.contasVendedores.getOrDefault(email, new ArrayList<>());
                if(dados.size() == 0){
                    System.out.println("Introduziu o email ou palavra-passe errada!");
                    return -1;
                } else if (password.equals(dados.get(0))) {
                    System.out.println("Login do vendedor " + dados.get(1) + " foi feito com sucesso!");
                    return Integer.parseInt(dados.get(1));
                }
                break;
            }
            case Transportadora: {
                List<String> dados = this.contasTransportadoras.getOrDefault(email, new ArrayList<>());
                if(dados.size() == 0){
                    System.out.println("Introduziu o email ou palavra-passe errada!");
                    return -1;
                }else if (password.equals(dados.get(0))) {
                    System.out.println("Login da transportadora " + dados.get(1) + " foi feito com sucesso!");
                    return Integer.parseInt(dados.get(1));
                }
                break;
            }
            default:
                return -1;
        }
        return -1;
    }

    public void alteraEmail(TipoConta tipo, String currentEmail, String newEmail, int id) throws IOException {
        switch (tipo){
            case Comprador -> atualizaEmailComprador(currentEmail, newEmail, id);
            case Vendedor -> atualizaEmailVendedor(currentEmail, newEmail, id);
            case Transportadora -> atualizaEmailTransportadora(currentEmail, newEmail, id);
        }
        this.readFiles(); // Atualizar maps
    }

    public void atualizaEmailComprador(String currentEmail, String newEmail, int id) throws IOException {
        File input = new File("./DB/compradores.txt");
        Scanner scanner = new Scanner(input);

        StringBuilder sb = new StringBuilder();

        while(scanner.hasNextLine()){
            String line = scanner.nextLine();
            String[] contents = line.split(";");
            if(Objects.equals(contents[0], currentEmail) && Objects.equals(contents[2], String.valueOf(id))){
                line = line.replaceAll(currentEmail, newEmail);
            }
            sb.append(line);
        }
        scanner.close();
        try{
            PrintWriter writer = new PrintWriter(input);
            writer.println("");
            writer.close();
        }catch (IOException e){
            e.printStackTrace();
        }

        try {
            PrintWriter writer = new PrintWriter(input);
            writer.write(sb.toString());
            writer.close();
        }catch (FileNotFoundException e){
            System.out.println("Ficheiro para atualizar o email não encontrado!");
        }
    }

    public void atualizaEmailVendedor(String currentEmail, String newEmail, int id) throws IOException {
        File input = new File("./DB/vendedores.txt");
        Scanner scanner = new Scanner(input);

        StringBuilder sb = new StringBuilder();

        while(scanner.hasNextLine()){
            String line = scanner.nextLine();
            String[] contents = line.split(";");
            if(Objects.equals(contents[0], currentEmail) && Objects.equals(contents[2], String.valueOf(id))){
                line = line.replaceAll(currentEmail, newEmail);
            }
            sb.append(line);
        }
        scanner.close();
        try{
            PrintWriter writer = new PrintWriter(input);
            writer.println("");
            writer.close();
        }catch (IOException e){
            e.printStackTrace();
        }

        try {
            PrintWriter writer = new PrintWriter(input);
            writer.write(sb.toString());
            writer.close();
        }catch (FileNotFoundException e){
            System.out.println("Ficheiro para atualizar o email não encontrado!");
        }
    }

    public void atualizaEmailTransportadora(String currentEmail, String newEmail, int id) throws IOException {
        File input = new File("./DB/transportadoras.txt");
        Scanner scanner = new Scanner(input);

        StringBuilder sb = new StringBuilder();

        while(scanner.hasNextLine()){
            String line = scanner.nextLine();
            String[] contents = line.split(";");
            if(Objects.equals(contents[0], currentEmail) && Objects.equals(contents[2], String.valueOf(id))){
                line = line.replaceAll(currentEmail, newEmail);
            }
            sb.append(line);
        }
        scanner.close();
        try{
            PrintWriter writer = new PrintWriter(input);
            writer.println("");
            writer.close();
        }catch (IOException e){
            e.printStackTrace();
        }

        try {
            PrintWriter writer = new PrintWriter(input);
            writer.write(sb.toString());
            writer.close();
        }catch (FileNotFoundException e){
            System.out.println("Ficheiro para atualizar o email não encontrado!");
        }
    }

    public boolean verificaExistenciaComprador(String email){
        for(String emails : contasCompradores.keySet()){
            if(emails.equals(email)) return true;
        }
        return false;
    }

    public boolean verificaExistenciaVendedor(String email){
        for(String emails : contasVendedores.keySet()){
            if(emails.equals(email)) return true;
        }
        return false;
    }

    public boolean verificaExistenciaTransporadora(String email)  {
        for(String emails : contasTransportadoras.keySet()){
            if(emails.equals(email)) return true;
        }
        return false;
    }
}
