package org.example;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        System.out.println("Hello world!");
        InMemoryKeyValueStore inMemoryKeyValueStore = new InMemoryKeyValueStore();
        inMemoryKeyValueStore.put("sde_bootcamp", List.of(new Pair<>("title", "SDE-Bootcamp")));
        inMemoryKeyValueStore.put("sde_bootcamp", List.of(new Pair<>("value", "5")));
        inMemoryKeyValueStore.put("sde_bootcamp2", List.of(new Pair<>("title", "SDE-Bootcamp")));
        inMemoryKeyValueStore.put("sde_bootcamp2", List.of(new Pair<>("5", "5")));

        System.out.println("keys are " + inMemoryKeyValueStore.keys());

        System.out.println("search" +inMemoryKeyValueStore.search("title","SDE-Bootcamp"));

        inMemoryKeyValueStore.delete("sde_bootcamp2");
        System.out.println("keys are " + inMemoryKeyValueStore.keys());

    }
}