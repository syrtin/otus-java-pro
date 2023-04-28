package ru.otus.model;

import lombok.ToString;

import java.util.List;

@ToString
public class ObjectForMessage implements Copyable<ObjectForMessage> {
    private List<String> data;

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }

    @Override
    public ObjectForMessage copy() {
        var obj = new ObjectForMessage();
        obj.setData(List.copyOf(data));
        return obj;
    }
}