package ru.otus.server;


import io.grpc.ServerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.server.service.NumberServiceImpl;


import java.io.IOException;

public class NumberServer {
    private static final Logger log = LoggerFactory.getLogger(NumberServer.class);

    public static final int SERVER_PORT = 8190;

    public static void main(String[] args) throws IOException, InterruptedException {

        var server = ServerBuilder
                .forPort(SERVER_PORT)
                .addService(new NumberServiceImpl())
                .build();
        server.start();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            log.info("Received shutdown request");
            server.shutdown();
            log.info("Server stopped");
        }));

        log.info("Server is waiting for client connections...");
        server.awaitTermination();
    }
}
