package ru.otus.homework;

import java.util.Comparator;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

public class CustomerService {
    private final NavigableMap<Customer, String> customerServiceMap = new TreeMap<>(Comparator.comparingLong(Customer::getScores));

    public Map.Entry<Customer, String> getSmallest() {
        Map.Entry<Customer, String> shallowEntry = customerServiceMap.firstEntry();
        return shallowEntry != null ? deepCopy(shallowEntry) : null;
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        Map.Entry<Customer, String> shallowEntry = customerServiceMap.higherEntry(customer);
        return shallowEntry != null ? deepCopy(shallowEntry) : null;
    }

    public void add(Customer customer, String data) {
        customerServiceMap.put(new Customer(customer.getId(), customer.getName(), customer.getScores()), data);
    }

    private Map.Entry<Customer, String> deepCopy(Map.Entry<Customer, String> shallowEntry) {
        return Map.entry(shallowEntry.getKey().getCopy(), shallowEntry.getValue());
    }
}