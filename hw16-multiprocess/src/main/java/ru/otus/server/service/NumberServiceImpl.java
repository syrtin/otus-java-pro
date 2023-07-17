package ru.otus.server.service;

import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.protobuf.generated.NumberRequest;
import ru.otus.protobuf.generated.NumberResponse;
import ru.otus.protobuf.generated.NumberServiceGrpc;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class NumberServiceImpl extends NumberServiceGrpc.NumberServiceImplBase {
    private static final Logger log = LoggerFactory.getLogger(NumberServiceImpl.class);

    private final ScheduledExecutorService executor = Executors.newScheduledThreadPool(10);

    @Override
    public void generateNumberStream(NumberRequest request, StreamObserver<NumberResponse> responseObserver) {
        int firstValue = request.getFirstValue();
        int lastValue = request.getLastValue();

        var currentValue = new AtomicInteger(firstValue);

        Runnable task = () -> {
            log.info("Number generation begins");
            var value = currentValue.incrementAndGet();
            var response = NumberResponse.newBuilder().setValue(value).build();
            responseObserver.onNext(response);
            if (value > lastValue) {
                executor.shutdown();
                responseObserver.onCompleted();
                log.info("Number generation ends");
            }
        };
        executor.scheduleAtFixedRate(task, 0, 2, TimeUnit.SECONDS);
    }
}
