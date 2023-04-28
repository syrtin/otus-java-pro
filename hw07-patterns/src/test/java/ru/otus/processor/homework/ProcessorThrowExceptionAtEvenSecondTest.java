package ru.otus.processor.homework;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.exception.ProcessAtEvenSecondException;
import ru.otus.model.Message;

import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class ProcessorThrowExceptionAtEvenSecondTest {

    private ProcessorThrowExceptionAtEvenSecond processor;
    private final Message message = Message
            .builder()
            .id(1L)
            .field1("test")
            .build();

    @Test
    @DisplayName("Process will have no exception and return the message")
    void processShouldBeSucceeded() {
        processor = new ProcessorThrowExceptionAtEvenSecond(() -> LocalTime.ofSecondOfDay(101));
        assertThat(processor.process(message)).isEqualTo(message);
    }

    @Test
    @DisplayName("Process throws ProcessAtEvenSecondException at even second")
    void processShouldBeFinishedWithException() {
        processor = new ProcessorThrowExceptionAtEvenSecond(() -> LocalTime.ofSecondOfDay(100));
        assertThatExceptionOfType(ProcessAtEvenSecondException.class)
                .isThrownBy(() -> processor.process(message))
                .withMessage("Current message was processed at even second");
    }
}