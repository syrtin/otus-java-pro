package ru.otus.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(toBuilder = true)
public class Message implements Copyable<Message> {
    private final long id;
    private final String field1;
    private final String field2;
    private final String field3;
    private final String field4;
    private final String field5;
    private final String field6;
    private final String field7;
    private final String field8;
    private final String field9;
    private final String field10;
    private final String field11;
    private final String field12;
    private final ObjectForMessage field13;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Message message = (Message) o;

        return id == message.id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", field1='" + field1 + '\'' +
                ", field2='" + field2 + '\'' +
                ", field3='" + field3 + '\'' +
                ", field4='" + field4 + '\'' +
                ", field5='" + field5 + '\'' +
                ", field6='" + field6 + '\'' +
                ", field7='" + field7 + '\'' +
                ", field8='" + field8 + '\'' +
                ", field9='" + field9 + '\'' +
                ", field10='" + field10 + '\'' +
                ", field11='" + field11 + '\'' +
                ", field12='" + field12 + '\'' +
                ", field13='" + field13.toString() + '\'' +
                '}';
    }

    @Override
    public Message copy() {
        return Message.builder()
                .id(id)
                .field1(field1)
                .field2(field1)
                .field3(field1)
                .field4(field4)
                .field5(field5)
                .field6(field6)
                .field7(field7)
                .field8(field8)
                .field9(field9)
                .field9(field10)
                .field10(field10)
                .field11(field11)
                .field12(field12)
                .field13(field13.copy())
                .build();
    }
}