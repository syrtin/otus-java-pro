package ru.otus.processor.homework;

import ru.otus.exception.ProcessAtEvenSecondException;
import ru.otus.model.Message;
import ru.otus.processor.Processor;

public class ProcessorThrowExceptionAtEvenSecond implements Processor {

    public ProcessorThrowExceptionAtEvenSecond(TimeProvider timeProvider) {
        this.timeProvider = timeProvider;
    }

    private final TimeProvider timeProvider;

    @Override
    public Message process(Message message) {
        if (isEvenByBitwiseAndOperation(timeProvider.getTime().getSecond())) {
            throw new ProcessAtEvenSecondException("Current message was processed at even second");
        }
        return message;
    }

    private boolean isEvenByBitwiseAndOperation(int number) {
        return (number & 1) == 0;
    }
}