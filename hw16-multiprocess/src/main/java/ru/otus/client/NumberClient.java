package ru.otus.client;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.client.service.ClientStreamObserver;
import ru.otus.protobuf.generated.NumberRequest;
import ru.otus.protobuf.generated.NumberServiceGrpc;

import java.util.concurrent.TimeUnit;


public class NumberClient {
    private static final Logger log = LoggerFactory.getLogger(NumberClient.class);

    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 8190;

    private int currentValue = 0;

    private final ManagedChannel channel;
    private final NumberServiceGrpc.NumberServiceStub stub;
    private final ClientStreamObserver observer;

    public NumberClient(String host, int port, ClientStreamObserver observer) {
        this.channel = ManagedChannelBuilder.forAddress(host, port)
                .usePlaintext()
                .build();
        this.stub = NumberServiceGrpc.newStub(channel);
        this.observer = observer;
    }

    public void executeClient(int firstValue, int lastValue) throws InterruptedException {
        NumberRequest request = NumberRequest.newBuilder()
                .setFirstValue(firstValue)
                .setLastValue(lastValue)
                .build();
        stub.generateNumberStream(request, observer);
    }

    public int updateAndGetNextValue() {
        currentValue = currentValue + observer.updateAndGetLastNumber() + 1;
        return currentValue;
    }

    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    public static void main(String[] args) throws InterruptedException {

        var client = new NumberClient(SERVER_HOST, SERVER_PORT, new ClientStreamObserver());

        client.executeClient(0, 30);

        for (int i = 0; i <= 50; i++) {
            var printValue = client.updateAndGetNextValue();
            log.info("currentValue:{}", printValue);
            Thread.sleep(1000);
        }

        client.shutdown();
    }
}