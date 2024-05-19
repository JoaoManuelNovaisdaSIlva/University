package helloworld;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.57.2)",
    comments = "Source: Bank.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class BankGrpc {

  private BankGrpc() {}

  public static final java.lang.String SERVICE_NAME = "helloworld.Bank";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<helloworld.CreateAccountRequest,
      helloworld.CreateAccountReply> getCreateAccountMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "CreateAccount",
      requestType = helloworld.CreateAccountRequest.class,
      responseType = helloworld.CreateAccountReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<helloworld.CreateAccountRequest,
      helloworld.CreateAccountReply> getCreateAccountMethod() {
    io.grpc.MethodDescriptor<helloworld.CreateAccountRequest, helloworld.CreateAccountReply> getCreateAccountMethod;
    if ((getCreateAccountMethod = BankGrpc.getCreateAccountMethod) == null) {
      synchronized (BankGrpc.class) {
        if ((getCreateAccountMethod = BankGrpc.getCreateAccountMethod) == null) {
          BankGrpc.getCreateAccountMethod = getCreateAccountMethod =
              io.grpc.MethodDescriptor.<helloworld.CreateAccountRequest, helloworld.CreateAccountReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "CreateAccount"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  helloworld.CreateAccountRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  helloworld.CreateAccountReply.getDefaultInstance()))
              .setSchemaDescriptor(new BankMethodDescriptorSupplier("CreateAccount"))
              .build();
        }
      }
    }
    return getCreateAccountMethod;
  }

  private static volatile io.grpc.MethodDescriptor<helloworld.DepositRequest,
      helloworld.DepositReply> getDepositMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Deposit",
      requestType = helloworld.DepositRequest.class,
      responseType = helloworld.DepositReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<helloworld.DepositRequest,
      helloworld.DepositReply> getDepositMethod() {
    io.grpc.MethodDescriptor<helloworld.DepositRequest, helloworld.DepositReply> getDepositMethod;
    if ((getDepositMethod = BankGrpc.getDepositMethod) == null) {
      synchronized (BankGrpc.class) {
        if ((getDepositMethod = BankGrpc.getDepositMethod) == null) {
          BankGrpc.getDepositMethod = getDepositMethod =
              io.grpc.MethodDescriptor.<helloworld.DepositRequest, helloworld.DepositReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Deposit"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  helloworld.DepositRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  helloworld.DepositReply.getDefaultInstance()))
              .setSchemaDescriptor(new BankMethodDescriptorSupplier("Deposit"))
              .build();
        }
      }
    }
    return getDepositMethod;
  }

  private static volatile io.grpc.MethodDescriptor<helloworld.BalanceRequest,
      helloworld.BalanceReply> getBalanceMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Balance",
      requestType = helloworld.BalanceRequest.class,
      responseType = helloworld.BalanceReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<helloworld.BalanceRequest,
      helloworld.BalanceReply> getBalanceMethod() {
    io.grpc.MethodDescriptor<helloworld.BalanceRequest, helloworld.BalanceReply> getBalanceMethod;
    if ((getBalanceMethod = BankGrpc.getBalanceMethod) == null) {
      synchronized (BankGrpc.class) {
        if ((getBalanceMethod = BankGrpc.getBalanceMethod) == null) {
          BankGrpc.getBalanceMethod = getBalanceMethod =
              io.grpc.MethodDescriptor.<helloworld.BalanceRequest, helloworld.BalanceReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Balance"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  helloworld.BalanceRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  helloworld.BalanceReply.getDefaultInstance()))
              .setSchemaDescriptor(new BankMethodDescriptorSupplier("Balance"))
              .build();
        }
      }
    }
    return getBalanceMethod;
  }

  private static volatile io.grpc.MethodDescriptor<helloworld.ObterHistoricoRequest,
      helloworld.ObterHistoricoReply> getObterHistoricoMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ObterHistorico",
      requestType = helloworld.ObterHistoricoRequest.class,
      responseType = helloworld.ObterHistoricoReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<helloworld.ObterHistoricoRequest,
      helloworld.ObterHistoricoReply> getObterHistoricoMethod() {
    io.grpc.MethodDescriptor<helloworld.ObterHistoricoRequest, helloworld.ObterHistoricoReply> getObterHistoricoMethod;
    if ((getObterHistoricoMethod = BankGrpc.getObterHistoricoMethod) == null) {
      synchronized (BankGrpc.class) {
        if ((getObterHistoricoMethod = BankGrpc.getObterHistoricoMethod) == null) {
          BankGrpc.getObterHistoricoMethod = getObterHistoricoMethod =
              io.grpc.MethodDescriptor.<helloworld.ObterHistoricoRequest, helloworld.ObterHistoricoReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ObterHistorico"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  helloworld.ObterHistoricoRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  helloworld.ObterHistoricoReply.getDefaultInstance()))
              .setSchemaDescriptor(new BankMethodDescriptorSupplier("ObterHistorico"))
              .build();
        }
      }
    }
    return getObterHistoricoMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static BankStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<BankStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<BankStub>() {
        @java.lang.Override
        public BankStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new BankStub(channel, callOptions);
        }
      };
    return BankStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static BankBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<BankBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<BankBlockingStub>() {
        @java.lang.Override
        public BankBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new BankBlockingStub(channel, callOptions);
        }
      };
    return BankBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static BankFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<BankFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<BankFutureStub>() {
        @java.lang.Override
        public BankFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new BankFutureStub(channel, callOptions);
        }
      };
    return BankFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     */
    default void createAccount(helloworld.CreateAccountRequest request,
        io.grpc.stub.StreamObserver<helloworld.CreateAccountReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getCreateAccountMethod(), responseObserver);
    }

    /**
     */
    default void deposit(helloworld.DepositRequest request,
        io.grpc.stub.StreamObserver<helloworld.DepositReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getDepositMethod(), responseObserver);
    }

    /**
     */
    default void balance(helloworld.BalanceRequest request,
        io.grpc.stub.StreamObserver<helloworld.BalanceReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getBalanceMethod(), responseObserver);
    }

    /**
     */
    default void obterHistorico(helloworld.ObterHistoricoRequest request,
        io.grpc.stub.StreamObserver<helloworld.ObterHistoricoReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getObterHistoricoMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service Bank.
   */
  public static abstract class BankImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return BankGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service Bank.
   */
  public static final class BankStub
      extends io.grpc.stub.AbstractAsyncStub<BankStub> {
    private BankStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected BankStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new BankStub(channel, callOptions);
    }

    /**
     */
    public void createAccount(helloworld.CreateAccountRequest request,
        io.grpc.stub.StreamObserver<helloworld.CreateAccountReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getCreateAccountMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void deposit(helloworld.DepositRequest request,
        io.grpc.stub.StreamObserver<helloworld.DepositReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getDepositMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void balance(helloworld.BalanceRequest request,
        io.grpc.stub.StreamObserver<helloworld.BalanceReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getBalanceMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void obterHistorico(helloworld.ObterHistoricoRequest request,
        io.grpc.stub.StreamObserver<helloworld.ObterHistoricoReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getObterHistoricoMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service Bank.
   */
  public static final class BankBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<BankBlockingStub> {
    private BankBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected BankBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new BankBlockingStub(channel, callOptions);
    }

    /**
     */
    public helloworld.CreateAccountReply createAccount(helloworld.CreateAccountRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getCreateAccountMethod(), getCallOptions(), request);
    }

    /**
     */
    public helloworld.DepositReply deposit(helloworld.DepositRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getDepositMethod(), getCallOptions(), request);
    }

    /**
     */
    public helloworld.BalanceReply balance(helloworld.BalanceRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getBalanceMethod(), getCallOptions(), request);
    }

    /**
     */
    public helloworld.ObterHistoricoReply obterHistorico(helloworld.ObterHistoricoRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getObterHistoricoMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service Bank.
   */
  public static final class BankFutureStub
      extends io.grpc.stub.AbstractFutureStub<BankFutureStub> {
    private BankFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected BankFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new BankFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<helloworld.CreateAccountReply> createAccount(
        helloworld.CreateAccountRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getCreateAccountMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<helloworld.DepositReply> deposit(
        helloworld.DepositRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getDepositMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<helloworld.BalanceReply> balance(
        helloworld.BalanceRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getBalanceMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<helloworld.ObterHistoricoReply> obterHistorico(
        helloworld.ObterHistoricoRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getObterHistoricoMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_CREATE_ACCOUNT = 0;
  private static final int METHODID_DEPOSIT = 1;
  private static final int METHODID_BALANCE = 2;
  private static final int METHODID_OBTER_HISTORICO = 3;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final AsyncService serviceImpl;
    private final int methodId;

    MethodHandlers(AsyncService serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_CREATE_ACCOUNT:
          serviceImpl.createAccount((helloworld.CreateAccountRequest) request,
              (io.grpc.stub.StreamObserver<helloworld.CreateAccountReply>) responseObserver);
          break;
        case METHODID_DEPOSIT:
          serviceImpl.deposit((helloworld.DepositRequest) request,
              (io.grpc.stub.StreamObserver<helloworld.DepositReply>) responseObserver);
          break;
        case METHODID_BALANCE:
          serviceImpl.balance((helloworld.BalanceRequest) request,
              (io.grpc.stub.StreamObserver<helloworld.BalanceReply>) responseObserver);
          break;
        case METHODID_OBTER_HISTORICO:
          serviceImpl.obterHistorico((helloworld.ObterHistoricoRequest) request,
              (io.grpc.stub.StreamObserver<helloworld.ObterHistoricoReply>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  public static final io.grpc.ServerServiceDefinition bindService(AsyncService service) {
    return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
        .addMethod(
          getCreateAccountMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              helloworld.CreateAccountRequest,
              helloworld.CreateAccountReply>(
                service, METHODID_CREATE_ACCOUNT)))
        .addMethod(
          getDepositMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              helloworld.DepositRequest,
              helloworld.DepositReply>(
                service, METHODID_DEPOSIT)))
        .addMethod(
          getBalanceMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              helloworld.BalanceRequest,
              helloworld.BalanceReply>(
                service, METHODID_BALANCE)))
        .addMethod(
          getObterHistoricoMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              helloworld.ObterHistoricoRequest,
              helloworld.ObterHistoricoReply>(
                service, METHODID_OBTER_HISTORICO)))
        .build();
  }

  private static abstract class BankBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    BankBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return helloworld.BankOuterClass.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("Bank");
    }
  }

  private static final class BankFileDescriptorSupplier
      extends BankBaseDescriptorSupplier {
    BankFileDescriptorSupplier() {}
  }

  private static final class BankMethodDescriptorSupplier
      extends BankBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    BankMethodDescriptorSupplier(java.lang.String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (BankGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new BankFileDescriptorSupplier())
              .addMethod(getCreateAccountMethod())
              .addMethod(getDepositMethod())
              .addMethod(getBalanceMethod())
              .addMethod(getObterHistoricoMethod())
              .build();
        }
      }
    }
    return result;
  }
}
