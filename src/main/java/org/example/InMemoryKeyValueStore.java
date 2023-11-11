package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class InMemoryKeyValueStore {
    private final Map<String, Map<String, Object>> database;
    private final Lock lock;

    public InMemoryKeyValueStore() {
        this.database = new HashMap<>();
        this.lock = new ReentrantLock();
    }

    public Map<String, Object> get(String key) {
        lock.lock();
        try {
            return database.get(key);
        } finally {
            lock.unlock();
        }
    }

    public List<String> search(String attributeKey, String attributeValue) {
        lock.lock();
        try {
            List<String> result = new ArrayList<>();
            for (Map.Entry<String, Map<String, Object>> entry : database.entrySet()) {
                String key = entry.getKey();
                Map<String, Object> attributes = entry.getValue();
                if (attributes.containsKey(attributeKey) && attributes.get(attributeKey).equals(attributeValue)) {
                    result.add(key);
                }
            }
            return result;
        } finally {
            lock.unlock();
        }
    }

    public void put(String key, List<Pair<String, String>> listOfAttributePairs) {
        lock.lock();
        try {
            Map<String, Object> attributes = database.getOrDefault(key, new LinkedHashMap<>());

            for (Pair<String, String> pair : listOfAttributePairs) {
                String attributeKey = pair.getKey();
                String attributeValue = pair.getValue();

                if (attributes.containsKey(attributeKey)) {
                    Object existingValue = attributes.get(attributeKey);
                    if (!existingValue.getClass().equals(getDataType(attributeValue))) {
                        throw new IllegalArgumentException("Data type mismatch for attribute '" + attributeKey + "'");
                    }
                } else {
                    attributes.put(attributeKey, convertToDataType(attributeValue));
                }
            }

            database.put(key, attributes);
        } finally {
            lock.unlock();
        }
    }


    public void delete(String key) {
        lock.lock();
        try {
            database.remove(key);
        } finally {
            lock.unlock();
        }
    }

    public Set<String> keys() {
        lock.lock();
        try {
            return new HashSet<>(database.keySet());
        } finally {
            lock.unlock();
        }
    }

    private Object convertToDataType(String value) {
        // Try to convert the value to the appropriate data type
        if (value.matches("\\d+")) {
            // Integer
            return Integer.parseInt(value);
        } else if (value.matches("\\d+\\.\\d+")) {
            // Double
            return Double.parseDouble(value);
        } else if (value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false")) {
            // Boolean
            return Boolean.parseBoolean(value);
        } else {
            // String
            return value;
        }
    }

    private Class getDataType(String value) {
        // Return the data type of the given value
        if (value.matches("\\d+")) {
            return Integer.class;
        } else if (value.matches("\\d+\\.\\d+")) {
            return Double.class;
        } else if (value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false")) {
            return Boolean.class;
        } else {
            return String.class;
        }
    }
}
