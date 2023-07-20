package ru.otus.client.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.protobuf.generated.NumberResponse;

public class ClientStreamObserver implements io.grpc.stub.StreamObserver<NumberResponse> {
    private static final Logger log = LoggerFactory.getLogger(ClientStreamObserver.class);

    private int lastNumber = 0;

    @Override
    public void onNext(NumberResponse response) {
        setLastNumber(response.getValue());
        log.info("new value:{}", lastNumber);
    }

    @Override
    public void onError(Throwable e) {
        log.error("Error appeared: ", e);
    }

    @Override
    public void onCompleted() {
        log.info("Request completed");
    }

    public synchronized int updateAndGetLastNumber() {
        var accountingNumber = lastNumber;
        lastNumber = 0;
        return accountingNumber;
    }

    public synchronized void setLastNumber(int lastNumber) {
        this.lastNumber = lastNumber;
    }
}
