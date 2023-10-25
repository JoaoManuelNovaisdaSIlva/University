import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ClientMain {
    public static void main(String[] args) {
        Queries q;
        try{
            Socket socket = new Socket("localhost", 8888);
            q = new Queries(socket);
            System.out.println("Conectado com o servidor");
        }catch(Exception e){
            System.err.println("Erro ao conectar com o servidor");
            return;
        }

        Scanner sc = new Scanner(System.in);
        Map<Integer, ReservasTrotinete> reservas = new HashMap<>();
        boolean menuInicial = true;
        while(menuInicial){
            System.out.println("| Bem vindo ao Gestor da Frota de Trotinetes |\n");
            System.out.println("-----------------------------------------------\n");
            System.out.println("Selecione uma opcao\n");
            System.out.println("1: Iniciar Sessão\n");
            System.out.println("2: Registar nova Conta\n");
            System.out.println("3: Sair\n");
            System.out.println("Opcao: ");
            int op = sc.nextInt();

            switch (op){
                case 1:
                    System.out.println("| Menu de Login |\n");
                    System.out.println("Introduza o seu nome: ");
                    String name = sc.next();
                    System.out.println("Introduza a sua palavra passe: ");
                    String pw = sc.next();

                    boolean out = q.login(name,pw);
                    if(out == true){
                        System.out.println("Login feito com sucesso!");
                        menuInicial=false;
                    }else{
                        System.out.println("Erro no login");
                    }
                    break;
                case 2:
                    System.out.println("| Menu de Registo |\n");
                    System.out.println("Introduza o nome da sua nova conta: ");
                    String name1 = sc.next();
                    System.out.println("Introduza a palavra passe da sua nova conta: ");
                    String pw1 = sc.next();

                    int respostaServer = q.register(name1,pw1);
                    if(respostaServer != -1){
                        System.out.println("O seu id de cliente é: " + respostaServer);
                        menuInicial=false;
                    }
                    break;
                case 3:
                    try {
                        q.close();
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                    break;

            }
        }
        while(true){
            System.out.println("| Gestão de Frota de Trotinetes |\n");
            System.out.println("Selecione uma das opcoes");
            System.out.println("1: A lista de posicoes de trotinetes em funcao da sua posicao");
            System.out.println("2: Reservar uma trotinete");
            System.out.println("3: Terminar uma reserva");
            System.out.println("4: Sair");
            System.out.println("Opcao");
            int op1 = sc.nextInt();

            switch (op1){
                case 1:
                    System.out.println("Indique as suas coordenadas iniciais");
                    System.out.println("O seu x: ");
                    int x = sc.nextInt();
                    System.out.println("O seu y: ");
                    int y = sc.nextInt();

                    Localizacao p = new Localizacao(x,y);
                    try{
                        List<Localizacao> pos = q.get_available(p);

                        for(Localizacao p2 : pos){
                            System.out.println("-" + p2.toString());
                        }
                    } catch (NullPointerException e){
                        System.out.println("Nao consegui aceder o servidor");
                    }
                    break;
                case 2:
                    System.out.println("  Indique as suas coordenadas iniciais:");
                    System.out.print("O seu x: ");
                    int x1 = sc.nextInt();
                    System.out.print("O seu y: ");
                    int y1 = sc.nextInt();
                    System.out.println("\n");

                    Localizacao localizacaoS = new Localizacao(x1, y1);
                    try{
                        ReservasTrotinete reservasTrotinete = q.reserve_scooter(localizacaoS);
                        if (reservasTrotinete.getCode()==-1) System.out.println("Nao existem trotinetes disponiveis");
                        else{
                            reservas.put(reservasTrotinete.getCode(), reservasTrotinete);
                            System.out.println(reservasTrotinete.toString());
                        }
                    }catch(NullPointerException e){
                        System.out.println("Nao consegui aceder o servidor");
                    }
                    break;
                case 3:
                    System.out.println("  Indique as suas coordenadas finais:");
                    System.out.print("O seu x: ");
                    int x2 = sc.nextInt();
                    System.out.print("O seu y: ");
                    int y2 = sc.nextInt();

                    Localizacao end = new Localizacao(x2, y2);

                    System.out.print(("Indique o códico da reserva: "));
                    int code = sc.nextInt();
                    ReservasTrotinete resv = reservas.get(code);
                    resv.setEnd(end);

                    if(resv == null) {
                        System.out.println("Não foi possível obterr a reserva..");
                    }

                    try{

                        double price = q.park_scooter(resv);

                        System.out.println("O preço é: " + price);
                    }catch(NullPointerException e){
                        System.out.println("Nao consegui aceder o servidor");
                    }
                    break;
                case 4:
                    sc.close();
                    try {
                        q.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    System.exit(0);
                    break;
            }
        }
    }
}
