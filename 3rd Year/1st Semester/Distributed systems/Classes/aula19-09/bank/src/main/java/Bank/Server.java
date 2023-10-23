package Bank;


import helloworld.*;
import io.grpc.Grpc;
import io.grpc.InsecureServerCredentials;
import io.grpc.stub.StreamObserver;

import java.util.concurrent.Executors;

// executar com: mvn exec:java -Dexec.mainClass="helloworld.Server"
public class Server extends BankGrpc.BankImplBase {



    public static void main(String[] args) throws Exception {
        Grpc.newServerBuilderForPort(12345, InsecureServerCredentials.create())
                .addService(new Server())
                .executor(Executors.newSingleThreadExecutor())
                .build().start().awaitTermination();
    }

    @Override
    public void createAccount(CreateAccountRequest request, StreamObserver<CreateAccountReply> responseObserver) {
        super.createAccount(request, responseObserver);
    }

    @Override
    public void deposit(DepositRequest request, StreamObserver<DepositReply> responseObserver) {
        super.deposit(request, responseObserver);
    }

    @Override
    public void balance(BalanceRequest request, StreamObserver<BalanceReply> responseObserver) {
        super.balance(request, responseObserver);
    }

    @Override
    public void obterHistorico(ObterHistoricoRequest request, StreamObserver<ObterHistoricoReply> responseObserver) {
        super.obterHistorico(request, responseObserver);
    }
}

